# AR-RISALA content sources and attribution

## Qur'an text and translation

- Provider: [AlQuran Cloud API](https://alquran.cloud/api)
- Endpoint used by the app: `/v1/surah/{number}/editions/quran-uthmani,en.sahih`
- Editions requested: Uthmani Arabic script and Sahih International English translation.
- The app fetches each Surah when selected. Internet access is required; the complete text is not bundled offline.
- Provider documentation describes the multi-edition Surah endpoint and available text editions. Confirm provider terms and translation permissions before public distribution.

## Hadith records

- Provider: [Sahih al-Bukhari API](https://sahih-al-bukhari.com/api/)
- Endpoint pattern: `/api/v1/hadith/{book}/{number}`
- Supported book slugs in the app: `bukhari`, `muslim`, `abudawud`, `tirmidhi`, `nasai`, `ibnmajah`.
- The app displays Arabic, reference, chapter and grade fields when the API supplies them. English translations are not supplied by this API.
- The API documentation states that Arabic text served by the API is matched to the fawazahmed0 hadith-api dataset and dedicated under The Unlicense; the API's metadata, grading labels, topic index and other database structure have separate terms. Follow the provider's attribution and terms, and link to each canonical record.
- Some records are intentionally unavailable because of source or licensing restrictions. A valid-looking number does not guarantee a record is served.
- The current feature is a reference browser across six collections, **not a preselected, reviewed set of 1,500 Hadith**. A curated collection, translations and editorial verification remain to be completed.

## Sahaba biographies

- The app currently includes ten short starter summaries. These are not a substitute for fully referenced biographies.
- Further reading link in the app: [Sahibukum — 140 Companion biographies](https://sahibukum.com/companions/). This is an independent resource; AR-RISALA does not claim authorship of its content.
- Bibliographical orientation: [Revive Sahaba — classical source books](https://revivesahaba.com/sources/), including works such as Ibn Sa'd's *Kitab al-Tabaqat al-Kabir*, Ibn Abd al-Barr's *al-Isti'ab*, Ibn al-Athir's *Usd al-Ghabah* and Ibn Hajar's *al-Isabah*.
- The planned 150 in-app biographies still need to be written, individually sourced and reviewed. Do not copy third-party prose without permission.

## Editorial rule

Never invent Qur'anic verses, translations, Hadith wording, Hadith grades, Companion identities or historical details. Mark missing or disputed information clearly and seek qualified scholarly review before treating app content as religious guidance.
