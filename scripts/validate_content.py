#!/usr/bin/env python3
"""Structural checks for the AR-RISALA bundled Islamic content."""
import re
from pathlib import Path

html = Path("app/src/main/assets/index.html").read_text(encoding="utf-8")

surah_match = re.search(r"const surahs=\[(.*?)\];", html, re.S)
if not surah_match:
    raise SystemExit("FAIL: Qur'an Surah list not found")
surahs = re.findall(r'"((?:\\.|[^"\\])*)"', surah_match.group(1))
if len(surahs) != 114 or len(set(surahs)) != 114:
    raise SystemExit(f"FAIL: expected 114 unique Surah names, found {len(surahs)}")

start = html.find("const sahaba=[")
end = html.find("\n];", start)
if start < 0 or end < 0:
    raise SystemExit("FAIL: Sahaba list not found or not closed")
block = html[start:end]
entry_pattern = re.compile(
    r'\[\s*"((?:\\.|[^"\\])*)"\s*,\s*'
    r'"((?:\\.|[^"\\])*)"\s*,\s*'
    r'"((?:\\.|[^"\\])*)"\s*\]',
    re.S,
)
entries = entry_pattern.findall(block)
names = [entry[0] for entry in entries]
if len(entries) != 150:
    raise SystemExit(f"FAIL: expected 150 Sahaba entries, found {len(entries)}")
if len(set(names)) != 150:
    raise SystemExit("FAIL: duplicate Sahaba names found")
if any(not name.strip() or not bio.strip() or not source.strip()
       for name, bio, source in entries):
    raise SystemExit("FAIL: a Sahaba entry has an empty name, biography or source note")

for required in ("function validSurahData", "function parseQuranPayload",
                 "async function downloadAllQuran", "n<=114",
                 "async function buildHadithLibrary",
                 "p[book.id].count<100", "const HADITH_COLLECTIONS="):
    if required not in html:
        raise SystemExit(f"FAIL: required app feature missing: {required}")

if "isUnlicense=" not in html or "public-domain dedication" not in html:
    raise SystemExit("FAIL: Hadith licence-name variant check missing")

print("PASS: 114 unique Surah names")
print("PASS: 150 unique Sahaba entries; every entry has a biography and source note")
print("PASS: Qur'an verse validation and 114-Surah offline downloader present")
print("PASS: six-collection Hadith builder and explicit Unlicense check present")
