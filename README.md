# AR-RISALA

**Knowledge • Faith • Guidance**

An Android Islamic learning app by **Abdirahman Jarso (Abjata)**.

## Current working features

- Android app built with Java and a local WebView interface.
- Home, Qur'an, Hadith, Sahaba and About sections.
- Qur'an index containing all 114 Surahs. Selecting a Surah requests the complete Uthmani Arabic text and Sahih International English translation from AlQuran Cloud. The Qur'an tab also provides an optional download of all 114 Surahs into on-device IndexedDB for offline reading; first-time download requires internet and enough device storage.
- Hadith reader for six collections, plus a resumable in-app builder that downloads and caches 250 permitted Arabic records per collection (1,500 total) in IndexedDB. The builder preserves source references, available grades/graders, review status and canonical source links. It observes the provider's request limit. English translations are not included because their redistribution rights are not cleared.
- 150 starter Sahaba biographies, plus links to independent further-reading resources.
- Search within the Surah and starter Sahaba lists.
- Offline-first caching: opened Surahs are cached on-device, and users can explicitly download the complete Qur'an. Hadith records successfully opened online are also saved for later reading on the same device. These libraries are user-downloaded, not preloaded in the APK.
- On-device bookmarks for individual Qur'an verses and Hadith records, with a Saved section.
- GitHub Actions workflow builds a debug APK on pushes to the default branch.

## Content still to complete

This is a working foundation, **not yet the finished 1,500-Hadith / 150-Sahaba edition**.

- Qur'an text is downloaded from AlQuran Cloud on request. The all-Surah offline download is now available in the Qur'an tab; it is not bundled in the APK. Verify the chosen text and translation before publication.
- The 1,500-record offline cache is built on the user's device after tapping Build / Resume in the Hadith tab; it is not preloaded into the APK. The download requires internet and can take about 25–30 minutes at the source's published rate limit. English translations are not included.
- The app currently contains 150 short Sahaba entries. These are starter summaries; individual source verification and qualified editorial review are still required.
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
