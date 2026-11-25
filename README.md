# AR-RISALA

**Knowledge • Faith • Guidance**

An Android Islamic learning app by **Abdirahman Jarso (Abjata)**.

## Current project status

The repository now contains an Android Gradle application and a GitHub Actions workflow that builds a debug APK. The app opens an offline-friendly home screen with Qur'an surah index, Hadith sample cards, Sahaba biographies, search within the visible lists, and an About page.

## Planned content

- Complete Qur'an: all 114 surahs with verified Arabic text and carefully sourced translations.
- 1,500 Hadith with collection references and authenticity grading where available.
- 150 Sahaba biographies with reliable historical references.
- Offline reading, bookmarks, search, and accessible Arabic typography.

**Important:** The current interface is a foundation, not yet the complete library. The Qur'an index is present, but the full ayah text is not yet bundled. The Hadith and Sahaba entries currently shown are a small initial sample. Content will be added in reviewed batches to avoid fabricated or incorrectly attributed religious texts.

## Build APK

A push to `main` triggers the workflow in **Actions → Build AR-RISALA APK**. When the run succeeds, download the `AR-RISALA-debug-apk` artifact from that workflow run. A debug APK is for testing; a signed release build is needed for distribution.

## Build locally

Requires JDK 17, Android SDK platform 35, and Gradle 8.9:

```bash
gradle assembleDebug
```

APK output: `app/build/outputs/apk/debug/app-debug.apk`.
