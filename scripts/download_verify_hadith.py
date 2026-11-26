#!/usr/bin/env python3
"""Fetch and audit 1,500 Arabic Hadith records with per-record Unlicense evidence.

The Arabic text is stored separately. The verification report records the source
reference, source edition, licence declaration and SHA-256 digest for each text.
This is a technical/provenance check, not a scholarly authentication of matn or grade.
"""
import hashlib
import json
import os
import time
import urllib.error
import urllib.request

BOOKS = ["bukhari", "muslim", "abudawud", "tirmidhi", "nasai", "ibnmajah"]
BASE = "https://sahih-al-bukhari.com/api/v1/hadith"
TARGET = 250
DELAY = 1.05
MAX_NUMBER = 12000
ACCEPTED_LICENCES = {
    "unlicense",
    "the unlicense (public-domain dedication)",
}

library = {}
report = {}
transient_retries = {}

for book in BOOKS:
    texts = []
    records = []
    number = 1
    checked = 0

    while len(texts) < TARGET and number <= MAX_NUMBER:
        url = f"{BASE}/{book}/{number}"
        request = urllib.request.Request(
            url, headers={"User-Agent": "AR-RISALA-Hadith-Library/1.0"}
        )
        try:
            with urllib.request.urlopen(request, timeout=30) as response:
                payload = json.loads(response.read().decode("utf-8"))
        except urllib.error.HTTPError as error:
            if error.code == 404:
                # The API documents 404 for unavailable/licence-excluded records.
                number += 1
                checked += 1
                time.sleep(DELAY)
                continue
            if error.code == 429 or 500 <= error.code < 600:
                key = (book, number)
                retries = transient_retries.get(key, 0) + 1
                transient_retries[key] = retries
                if retries > 8:
                    raise RuntimeError(
                        f"{book}/{number}: stopped after {retries - 1} transient HTTP failures"
                    ) from error
                retry_after = error.headers.get("Retry-After", "") if error.headers else ""
                try:
                    wait = int(retry_after)
                except (TypeError, ValueError):
                    wait = min(180, 10 * (2 ** (retries - 1)))
                wait = max(5, min(300, wait))
                print(
                    f"{book}/{number}: HTTP {error.code}; retry {retries}/8 in {wait}s",
                    flush=True,
                )
                time.sleep(wait)
                continue
            raise

        transient_retries.pop((book, number), None)
        checked += 1

        licence = str(
            (payload.get("licence") or {}).get("arabic_text", {}).get("name", "")
        ).strip()
        source = payload.get("source") or {}
        arabic = payload.get("arabic")
        canonical = payload.get("canonical")
        returned_number = payload.get("number")
        returned_book = payload.get("book")

        # Require the response to identify the exact requested record, name the
        # documented upstream dataset, provide Arabic, and grant the text licence.
        valid = (
            returned_book == book
            and str(returned_number) == str(number)
            and payload.get("status") == "ok"
            and isinstance(arabic, str)
            and bool(arabic.strip())
            and source.get("name") == "fawazahmed0 hadith-api"
            and source.get("edition") == f"ara-{book}"
            and licence.casefold() in ACCEPTED_LICENCES
            and isinstance(canonical, str)
            and canonical.startswith("https://")
        )

        if valid:
            clean_text = arabic.strip()
            digest = hashlib.sha256(clean_text.encode("utf-8")).hexdigest()
            texts.append(clean_text)
            records.append({
                "number": number,
                "reference": payload.get("reference"),
                "canonical": canonical,
                "source": source.get("name"),
                "edition": source.get("edition"),
                "edition_number": source.get("edition_number"),
                "arabic_text_licence": licence,
                "arabic_sha256": digest,
            })

        number += 1
        if checked % 25 == 0:
            print(
                f"{book}: {len(texts)}/{TARGET} accepted; checked {checked}",
                flush=True,
            )
        time.sleep(DELAY)

    if len(texts) != TARGET:
        raise RuntimeError(
            f"{book}: only {len(texts)} records passed all checks after {checked} responses"
        )

    library[book] = texts
    report[book] = {"accepted": len(texts), "checked": checked, "records": records}
    print(f"VERIFIED {book}: {len(texts)} records passed provenance/licence checks", flush=True)

assert len(library) == 6
assert sum(len(items) for items in library.values()) == 1500
assert all(len(report[book]["records"]) == TARGET for book in BOOKS)

os.makedirs("build/hadith", exist_ok=True)
with open("build/hadith/ar-risala-hadith-arabic-1500.json", "w", encoding="utf-8") as output:
    json.dump(library, output, ensure_ascii=False, separators=(",", ":"))

with open("build/hadith/verification-report.json", "w", encoding="utf-8") as output:
    json.dump({
        "total": 1500,
        "per_collection": report,
        "selection": "first 250 integer-numbered records per collection that passed every per-record check",
        "checks": [
            "returned collection and number match the request",
            "record status is ok",
            "Arabic text is non-empty",
            "source is fawazahmed0 hadith-api",
            "source edition matches the requested collection",
            "per-record Arabic text licence is The Unlicense",
            "canonical HTTPS source page is present",
            "SHA-256 digest recorded for each Arabic text"
        ],
        "translation_included": False,
        "provider_metadata_included_in_library": False,
        "scope_note": "Technical source, licence and integrity checks only; not a scholarly authentication or grading review."
    }, output, ensure_ascii=False, indent=2)

print("SUCCESS: exactly 1,500 Arabic records passed all technical provenance and licence checks.")
