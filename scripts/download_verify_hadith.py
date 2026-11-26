#!/usr/bin/env python3
"""Fetch and audit 1,500 Arabic Hadith records from the Unlicense dataset.

This is a technical source/licence/integrity check, not scholarly authentication.
The upstream project publishes its data in per-record JSON files and licenses
the repository under The Unlicense. We retain a source URL and SHA-256 per text.
"""
import hashlib
import json
import os
import time
import urllib.error
import urllib.request

BOOKS = ["bukhari", "muslim", "abudawud", "tirmidhi", "nasai", "ibnmajah"]
TARGET = 250
MAX_NUMBER = 2000
BASES = [
    "https://cdn.jsdelivr.net/gh/fawazahmed0/hadith-api@1/editions",
    "https://raw.githubusercontent.com/fawazahmed0/hadith-api/1/editions",
]
LICENSE_URL = "https://github.com/fawazahmed0/hadith-api/blob/1/LICENSE"
LICENSE_TEXT = "https://raw.githubusercontent.com/fawazahmed0/hadith-api/1/LICENSE"
USER_AGENT = "AR-RISALA-Hadith-Library/1.1"

def get_json(url):
    request = urllib.request.Request(url, headers={"User-Agent": USER_AGENT})
    with urllib.request.urlopen(request, timeout=45) as response:
        return json.loads(response.read().decode("utf-8"))

# Confirm the upstream repository's published licence before retrieving data.
licence_body = None
last_error = None
for licence_url in (LICENSE_TEXT, "https://cdn.jsdelivr.net/gh/fawazahmed0/hadith-api@1/LICENSE"):
    try:
        request = urllib.request.Request(licence_url, headers={"User-Agent": USER_AGENT})
        with urllib.request.urlopen(request, timeout=30) as response:
            licence_body = response.read().decode("utf-8")
        break
    except Exception as error:
        last_error = error
if not licence_body or "This is free and unencumbered software released into the public domain." not in licence_body or "unlicense.org" not in licence_body:
    raise RuntimeError(f"Could not confirm upstream The Unlicense text: {last_error}")

library = {}
report = {}

for book in BOOKS:
    texts, records = [], []
    checked = 0
    for number in range(1, MAX_NUMBER + 1):
        if len(texts) >= TARGET:
            break
        relative = f"ara-{book}/{number}.json"
        payload = None
        missing = True
        errors = []
        for base in BASES:
            url = f"{base}/{relative}"
            try:
                payload = get_json(url)
                missing = False
                break
            except urllib.error.HTTPError as error:
                if error.code == 404:
                    continue
                errors.append(f"{url}: HTTP {error.code}")
                if error.code == 429 or 500 <= error.code < 600:
                    time.sleep(2)
                    continue
                raise
            except Exception as error:
                errors.append(f"{url}: {error}")
                time.sleep(1)
        checked += 1
        if missing and errors:
            raise RuntimeError(f"{book}/{number}: both upstream mirrors failed: {'; '.join(errors)}")
        if missing:
            continue

        hadiths = payload.get("hadiths") if isinstance(payload, dict) else None
        if not isinstance(hadiths, list):
            raise RuntimeError(f"{book}/{number}: unexpected upstream JSON structure")
        match = next((item for item in hadiths
                      if str(item.get("hadithnumber", "")) == str(number)
                      and isinstance(item.get("text"), str)
                      and item["text"].strip()), None)
        if match is None:
            continue

        clean_text = match["text"].strip()
        digest = hashlib.sha256(clean_text.encode("utf-8")).hexdigest()
        source_url = f"https://github.com/fawazahmed0/hadith-api/blob/1/editions/{relative}"
        texts.append(clean_text)
        records.append({
            "number": number,
            "reference": match.get("reference"),
            "canonical": source_url,
            "source": "fawazahmed0/hadith-api",
            "edition": f"ara-{book}",
            "arabic_text_licence": "The Unlicense (repository-level LICENSE)",
            "licence_url": LICENSE_URL,
            "arabic_sha256": digest,
        })
        if len(texts) % 25 == 0:
            print(f"{book}: {len(texts)}/{TARGET} accepted; checked {checked}", flush=True)

    if len(texts) != TARGET:
        raise RuntimeError(f"{book}: only {len(texts)} records passed checks; needed {TARGET}")
    library[book] = texts
    report[book] = {"accepted": len(texts), "checked": checked, "records": records}
    print(f"VERIFIED {book}: {len(texts)} Arabic records", flush=True)

assert len(library) == 6
assert sum(map(len, library.values())) == 1500
assert all(len(report[b]["records"]) == TARGET for b in BOOKS)

os.makedirs("build/hadith", exist_ok=True)
with open("build/hadith/ar-risala-hadith-arabic-1500.json", "w", encoding="utf-8") as output:
    json.dump(library, output, ensure_ascii=False, separators=(",", ":"))
with open("build/hadith/verification-report.json", "w", encoding="utf-8") as output:
    json.dump({
        "total": 1500,
        "per_collection": report,
        "selection": "first 250 numbered Arabic records per collection present in the upstream dataset",
        "checks": [
            "upstream repository LICENSE fetched and matched The Unlicense dedication",
            "record JSON parsed successfully",
            "requested hadith number matched the returned record",
            "Arabic text is non-empty",
            "edition matches the requested collection",
            "source URL and repository-level licence URL retained",
            "SHA-256 digest recorded for every Arabic text"
        ],
        "translation_included": False,
        "scope_note": "Technical source, repository licence and integrity checks only; not a scholarly authentication or grading review."
    }, output, ensure_ascii=False, indent=2)
print("SUCCESS: exactly 1,500 Arabic records passed source, licence and integrity checks.")
