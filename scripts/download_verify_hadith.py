#!/usr/bin/env python3
"""Fetch 1,500 Arabic Hadith texts whose individual API response grants Unlicense.
Only the Arabic text is retained in the artifact; provider metadata and translations
are deliberately excluded because their reuse rights are not cleared.
"""
import json, time, urllib.request, urllib.error, os, sys

BOOKS = ["bukhari", "muslim", "abudawud", "tirmidhi", "nasai", "ibnmajah"]
BASE = "https://sahih-al-bukhari.com/api/v1/hadith"
TARGET = 250
DELAY = 1.05
MAX_NUMBER = 12000
out = {}
report = {}
for book in BOOKS:
    texts, number, checked = [], 1, 0
    while len(texts) < TARGET and number <= MAX_NUMBER:
        url = f"{BASE}/{book}/{number}"
        req = urllib.request.Request(url, headers={"User-Agent": "AR-RISALA-Hadith-Library/1.0"})
        try:
            with urllib.request.urlopen(req, timeout=30) as resp:
                payload = json.loads(resp.read().decode("utf-8"))
        except urllib.error.HTTPError as e:
            if e.code == 404:
                number += 1; checked += 1; time.sleep(DELAY); continue
            if e.code == 429 or 500 <= e.code < 600:
                retries = transient_retries.get((book, number), 0) + 1
                transient_retries[(book, number)] = retries
                if retries > 8:
                    raise RuntimeError(f"{book}/{number}: stopped after {retries - 1} transient HTTP failures") from e
                retry_after = e.headers.get("Retry-After", "") if e.headers else ""
                try:
                    wait = int(retry_after)
                except (TypeError, ValueError):
                    wait = min(180, 10 * (2 ** (retries - 1)))
                wait = max(5, min(300, wait))
                print(f"{book}/{number}: HTTP {e.code}; retry {retries}/8 in {wait}s", flush=True)
                time.sleep(wait)
                continue
            raise
        checked += 1
        licence = str((payload.get("licence") or {}).get("arabic_text", {}).get("name", "")).strip()
        licence_key = licence.casefold()
        is_unlicense = licence_key in {"unlicense", "the unlicense (public-domain dedication)"}
        arabic = payload.get("arabic")
        if isinstance(arabic, str) and arabic.strip() and is_unlicense:
            texts.append(arabic.strip())
        number += 1
        if checked % 25 == 0:
            print(f"{book}: {len(texts)}/{TARGET} accepted; checked {checked}", flush=True)
        time.sleep(DELAY)
    if len(texts) != TARGET:
        raise RuntimeError(f"{book}: only {len(texts)} licensed Arabic texts found after checking {checked} records")
    out[book] = texts
    report[book] = {"accepted": len(texts), "checked": checked}
    print(f"VERIFIED {book}: {len(texts)} licensed Arabic texts", flush=True)

assert len(out) == 6 and sum(map(len, out.values())) == 1500
os.makedirs("build/hadith", exist_ok=True)
with open("build/hadith/ar-risala-hadith-arabic-1500.json", "w", encoding="utf-8") as f:
    json.dump(out, f, ensure_ascii=False, separators=(",", ":"))
with open("build/hadith/verification-report.json", "w", encoding="utf-8") as f:
    json.dump({"total": 1500, "per_collection": report,
               "selection": "first 250 individually Unlicense-labelled Arabic texts encountered per collection",
               "translation_included": False, "provider_metadata_included": False}, f, ensure_ascii=False, indent=2)
print("SUCCESS: exactly 1,500 individually license-checked Arabic texts written.", flush=True)
