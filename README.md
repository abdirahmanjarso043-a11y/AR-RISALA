# AR-RISALA

**Knowledge • Faith • Guidance**

An Android Islamic learning app by **Abdirahman Jarso (Abjata)**.

## Current working features

- Android app built with Java and a local WebView interface.
- Home, Qur'an, Hadith, Sahaba and About sections.
- Qur'an index containing all 114 Surah names. Selecting a Surah requests the complete Uthmani Arabic text and Sahih International English translation from AlQuran Cloud. An internet connection is required for full Surah reading.
- Hadith reader that can request a numbered narration from six collections (Bukhari, Muslim, Abu Dawud, Tirmidhi, Nasa'i and Ibn Majah), displaying Arabic text, available grading, chapter and source link. English translations are not provided by this API.
- Twenty starter Sahaba biographies, plus links to independent further-reading resources.
- Search within the Surah and starter Sahaba lists.
- Offline-first caching: Surahs and Hadith records successfully opened online are saved in the app's WebView storage for later reading on the same device. First-time downloads still require internet; this is not a preloaded offline library.
- On-device bookmarks for individual Qur'an verses and Hadith records, with a Saved section.
- GitHub Actions workflow builds a debug APK on pushes to the default branch.

## Content still to complete

This is a working foundation, **not yet the finished 1,500-Hadith / 150-Sahaba edition**.

- Qur'an text is fetched online rather than bundled for offline use. Verify the chosen text and translation before publication.
- The Hadith reader can retrieve numbered records from supported collections, but it is not yet a reviewed, curated in-app set of 1,500 entries, and it has no English translations.
- The app currently contains twenty short Sahaba summaries. The remaining 130 biographies, source notes and editorial review are still required.
- A full preloaded offline Qur'an/Hadith library, and a signed release build are still future work.

Religious content must be checked against reliable sources. Do not invent wording, grading, historical details or attributions.

## Sources and attribution

See [SOURCES.md](SOURCES.md) for data providers, attribution and known limitations.

## Build APK

A push to the default branch triggers **Actions → Build AR-RISALA APK**. When the run succeeds, download the `AR-RISALA-debug-apk` artifact from that run. A debug APK is for testing; a signed release build is needed for distribution.

## Build locally

Requires JDK 17, Android SDK platform 35, and Gradle 8.9:

```bash
gradle assembleDebug
```

APK output: `app/build/outputs/apk/debug/app-debug.apk`.
