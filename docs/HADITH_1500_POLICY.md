# AR-RISALA — Hadith collection policy

## Target
The planned in-app selection is 1,500 narrations: 250 references from each of:
- Sahih al-Bukhari
- Sahih Muslim
- Sunan Abi Dawud
- Jami at-Tirmidhi
- Sunan an-Nasa'i
- Sunan Ibn Majah

The owner approved inclusion of all grades. Every record must preserve the grade exactly as supplied, including the grader when available. Use explicit labels for Sahih, Hasan, Da'if, and ungraded/unknown. Do not infer a grade from the collection name alone, except that the two Sahih collections are compiled as Sahih works; retain individual-source metadata where supplied.

## Required record fields
- collection key and collection title
- original reference number (never renumber silently)
- Arabic matn
- chapter/book title when supplied by the source
- grade label and grader when supplied
- source edition, canonical source URL, and retrieval/version metadata
- English translation only when its redistribution licence is verified

## Source and rights gate
The fawazahmed0/hadith-api repository publishes its code/data package under the Unlicense, but a repository-level licence alone does not establish that every digitised Arabic edition or contributed translation is free of third-party rights. Its edition metadata also leaves some translators/source fields unknown. English translations in particular must not be bundled until the rights holder and licence are confirmed.

The sahih-al-bukhari.com API documents a narrower Arabic reuse policy: it serves Arabic only where it matches the specified dataset, excludes records with non-commercial or unconfirmed provenance, and does not provide English translations. Its numbering, grades, chapter metadata, and other service metadata have separate terms. Do not copy that service's metadata or content into a bundled offline dataset without checking its current terms.

## Validation rules
1. Do not invent, paraphrase as a quotation, or reconstruct missing matn.
2. Do not invent collection numbers, chapter titles, grades, or graders.
3. Keep source numbering and variant numbering distinct.
4. Record conflicting gradings separately rather than choosing one without explanation.
5. Deduplicate only when the source identity and wording justify it; preserve meaningful variants.
6. A 1,500-item target is not complete until all 1,500 records pass source, text, reference, grading, and rights checks.

## Current status
The app currently offers an online reference-number lookup. It does not yet contain a verified offline dataset of 1,500 Hadith. This document records the acceptance criteria; it is not a claim that the target dataset has been completed.
