# Changelog

All notable changes to the CustomerPulse Android SDK will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [2.1.0] - 2026-06-08

### Added
- **Survey event listener** - `CustomerPulseSurveyListener` interface exposing
  `onCompleted()`, `onError()`, and `onDismissed()` (each a `default` no-op so you
  only implement what you need). Mirrors the Web widget events
  `so-widget-completed` / `so-widget-error` / `so-widget-closed`.
  - New `showSurveyPage(...)` and `showSurveyBottomSheet(...)` overloads accept a
    `CustomerPulseSurveyListener`.
  - `so-widget-error` and `so-widget-closed` are now surfaced to the host app; both
    previously fell through unhandled.
  - All callbacks are delivered on the main (UI) thread.
- Backward compatible: every existing overload, signature, and behavior is unchanged
  (`so-widget-completed` still auto-closes the survey in addition to firing
  `onCompleted()`).

### Fixed
- **Version drift** - the published artifact version was `0.1.0` while the changelog
  and Javadoc reported `2.0.0`. Aligned the Gradle publication version to the real
  baseline and applied the additive minor bump in one move: `0.1.0` → `2.1.0`.
  Consumers pinning the old `0.1.0` coordinate should update to `2.1.0`.

---

## [2.0.0] - 2025-12-10

### Added
- **Environment Configuration** - Switch between Production and Sandbox environments
  - `setEnvironment(Environment.PRODUCTION)` for live apps
  - `setEnvironment(Environment.SANDBOX)` for testing
- **Debug Logging** - Enable detailed SDK logging for development
  - `setDebugLogging(true)` to enable
  - `isDebugLoggingEnabled()` to check status
  - Logs prefixed with `[CustomerPulse]`
- Comprehensive API documentation in README
- Installation guide with screenshots (`docs/INSTALLATION.md`)

### Changed
- Restructured README to match iOS SDK style
- Improved error logging with full stack traces
- Updated example app with sandbox configuration and debug logging

### Fixed
- Handler threading issues - now uses explicit `Looper.getMainLooper()`
- BottomSheet dismiss behavior - changed `hide()` to `dismiss()` for proper cleanup
- Null safety improvements in WebViewActivity and JavaScript interfaces
- Context validation - SDK now checks for Activity context

---

## [0.3.4] - 2025-09-30

### Fixed
- Minor bug fixes and stability improvements

---

## [0.2.3] - 2023-03-09

### Changed
- Updated dependencies
- Minor improvements

---

## [0.2.1] - 2022-02-14

### Added
- Bottom sheet survey display mode
- Dismissible option for surveys
- Closing delay configuration

---

## [0.1.0] - 2021-11-25

### Added
- Initial release
- Full page survey display
- Multi-language support (English, Arabic)
- WebView-based survey rendering

---

[2.1.0]: https://github.com/KalvadTech/CustomerPulse-Android/releases/tag/v2.1.0
[2.0.0]: https://github.com/KalvadTech/CustomerPulse-Android/releases/tag/v2.0.0
[0.3.4]: https://github.com/KalvadTech/CustomerPulse-Android/releases/tag/v0.3.4
[0.2.3]: https://github.com/KalvadTech/CustomerPulse-Android/releases/tag/v0.2.3
[0.2.1]: https://github.com/KalvadTech/CustomerPulse-Android/releases/tag/v0.2.1
