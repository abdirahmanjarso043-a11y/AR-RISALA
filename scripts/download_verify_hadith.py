#!/usr/bin/env python3
"""Fetch and audit 1,500 Arabic Hadith records from the Unlicense dataset.

This is a technical source/licence/integrity check, not scholarly authentication.
The upstream project publishes its data in per-record JSON files and licenses
the repository under The Unlicense. We retain a source URL and SHA-256 per text.
"""
import hashlib
import json
import os
import argparse
import time
import urllib.error
import urllib.request

BOOKS = ["bukhari", "muslim", "abudawud", "tirmidhi", "nasai", "ibnmajah"]
MAX_NUMBER = 2000
parser = argparse.ArgumentParser(description="Verify a batch of Arabic Hadith records.")
parser.add_argument("--total", type=int, default=1500, help="Total records across all six collections (1-1500).")
parser.add_argument("--skip-per-book", type=int, default=0, help="Number of valid records to skip in each collection before collecting this batch.")
parser.add_argument("--batch-number", type=int, default=1, help="Batch number used in output filenames.")
args = parser.parse_args()
if not 1 <= args.total <= 1500:
    raise SystemExit("--total must be between 1 and 1500")
if args.skip_per_book < 0 or args.batch_number < 1:
    raise SystemExit("--skip-per-book must be non-negative and --batch-number must be positive")
quotas = {book: args.total // len(BOOKS) + (1 if i < args.total % len(BOOKS) else 0) for i, book in enumerate(BOOKS)}
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
    accepted_seen = 0
    target = quotas[book]
    for number in range(1, MAX_NUMBER + 1):
        if len(texts) >= target:
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
                time.sleep(2)
                continue
            except Exception as error:
                errors.append(f"{url}: {error}")
                time.sleep(1)
        checked += 1
        if missing:
            if errors:
                print(f"SKIP {book}/{number}: source unavailable on both mirrors; continuing", flush=True)
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

        accepted_seen += 1
        if accepted_seen <= args.skip_per_book:
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
        if len(texts) % 10 == 0 or len(texts) == target:
            print(f"{book}: {len(texts)}/{target} accepted; checked {checked}", flush=True)

    if len(texts) != target:
        raise RuntimeError(f"{book}: only {len(texts)} records passed checks; needed {target}")
    library[book] = texts
    report[book] = {"accepted": len(texts), "checked": checked, "records": records}
    print(f"VERIFIED {book}: {len(texts)} Arabic records", flush=True)

assert len(library) == 6
assert sum(map(len, library.values())) == args.total
assert all(len(report[b]["records"]) == quotas[b] for b in BOOKS)

os.makedirs("build/hadith", exist_ok=True)
with open(f"build/hadith/ar-risala-hadith-arabic-{args.total}-batch-{args.batch_number}.json", "w", encoding="utf-8") as output:
    json.dump(library, output, ensure_ascii=False, separators=(",", ":"))
with open("build/hadith/verification-report.json", "w", encoding="utf-8") as output:
    json.dump({
        "total": args.total,
        "batch_number": args.batch_number,
        "skipped_valid_records_per_collection": args.skip_per_book,
        "per_collection": report,
        "selection": "numbered Arabic records after skipping the requested number of earlier valid records per collection; quotas: " + json.dumps(quotas),
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
print(f"SUCCESS: exactly {args.total} Arabic records passed source, licence and integrity checks in batch {args.batch_number}.")
