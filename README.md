<p align="center">
  <h1 align="center">CustomerPulse Android SDK</h1>
  <p align="center">
    Easily integrate CustomerPulse surveys into your Android applications
    <br />
    <a href="docs/API.md"><strong>Explore the API docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/KalvadTech/CustomerPulse-Android/issues">Report Bug</a>
    ·
    <a href="https://github.com/KalvadTech/CustomerPulse-Android/issues">Request Feature</a>
  </p>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android%20API%2021+-green.svg" alt="Platform Android API 21+" />
  <img src="https://img.shields.io/badge/Language-Java-orange.svg" alt="Language Java" />
  <img src="https://img.shields.io/badge/License-MIT-lightgrey.svg" alt="License MIT" />
</p>

---

## Overview

CustomerPulse Android SDK provides a simple and elegant way to display customer satisfaction surveys in your Android applications. With just a few lines of code, you can gather valuable feedback from your users.

### Features

- **Simple Integration** - Get started with just a few lines of code
- **Secure** - All communications over HTTPS
- **Multi-language Support** - Built-in support for English and Arabic
- **Native Experience** - Surveys displayed in WebView or Bottom Sheet
- **Configurable** - Environment switching, dismissal control, auto-close timing
- **Debug Mode** - Built-in logging for development

---

## Table of Contents

- [Requirements](#requirements)
- [Installation](#installation)
- [Quick Start](#quick-start)
- [Configuration](#configuration)
- [API Reference](#api-reference)
- [Example Project](#example-project)
- [Support](#support)

---

## Requirements

| Requirement | Minimum Version |
|-------------|-----------------|
| Android API | 21+ |
| Java | 8+ |

---

## Installation

### Option 1: AAR File (Recommended)

1. Download `CustomerPulseSurvey.aar` from the [Releases](https://github.com/KalvadTech/CustomerPulse-Android/releases) page
2. Add it to your `libs` folder
3. Add as JAR/AAR dependency in Project Structure

For detailed steps with screenshots, see [docs/INSTALLATION.md](docs/INSTALLATION.md).

### Option 2: Gradle (Coming Soon)

```groovy
implementation 'ae.gov.customerpulse:customerpulsesurvey:1.0.0'
```

---

## Quick Start

### 1. Import the SDK

```java
import com.customerpulse.customerpulsesurvey.CustomerPulseSurvey;
```

### 2. Show Survey

```java
HashMap<String, String> options = new HashMap<>();
options.put("lang", "en");

CustomerPulseSurvey.showSurveyPage(this, "APP_ID", "TOKEN", options);
```

That's it!

---

## Configuration

### Environment

Switch between production and sandbox environments:

```java
// Production (default)
CustomerPulseSurvey.setEnvironment(CustomerPulseSurvey.Environment.PRODUCTION);

// Sandbox (for testing)
CustomerPulseSurvey.setEnvironment(CustomerPulseSurvey.Environment.SANDBOX);
```

Set the environment before calling any survey display methods.

### Debug Logging

Enable console logging during development:

```java
CustomerPulseSurvey.setDebugLogging(true);
```

**Output:**
```
[CustomerPulse] Debug logging enabled
[CustomerPulse] Environment set to: SANDBOX
[CustomerPulse] Loading survey page
[CustomerPulse] URL: https://sandboxsurvey.customerpulse.gov.ae/TOKEN?lang=en&app_id=APP_ID
[CustomerPulse] Dismissible: true
[CustomerPulse] Closing delay: 2000ms
```

**Note:** Disable debug logging in production builds.

---

## API Reference

### Static Methods

| Method | Description |
|--------|-------------|
| `setEnvironment(Environment env)` | Set SDK environment (PRODUCTION/SANDBOX) |
| `getEnvironment()` | Get current environment |
| `setDebugLogging(boolean enabled)` | Enable/disable debug logging |
| `isDebugLoggingEnabled()` | Check if debug logging is enabled |

### Survey Display Methods

#### Full Page Survey

```java
CustomerPulseSurvey.showSurveyPage(context, app_id, token, options);
CustomerPulseSurvey.showSurveyPage(context, app_id, token, options, dismissible);
CustomerPulseSurvey.showSurveyPage(context, app_id, token, options, closingDelayMs);
CustomerPulseSurvey.showSurveyPage(context, app_id, token, options, dismissible, closingDelayMs);
```

<table>
  <td><img src="https://user-images.githubusercontent.com/24971915/143430605-4ca77e06-8b71-4932-8f2a-84bfdd4304c7.png" width="200" /></td>
  <td><img src="https://user-images.githubusercontent.com/24971915/143430887-fd966724-ec4e-48d9-8df2-bef42367c9c7.png" width="200" /></td>
</table>

#### Bottom Sheet Survey

```java
CustomerPulseSurvey.showSurveyBottomSheet(context, app_id, token, options);
CustomerPulseSurvey.showSurveyBottomSheet(context, app_id, token, options, dismissible);
CustomerPulseSurvey.showSurveyBottomSheet(context, app_id, token, options, closingDelayMs);
CustomerPulseSurvey.showSurveyBottomSheet(context, app_id, token, options, dismissible, closingDelayMs);
```

<table>
  <td><img src="https://user-images.githubusercontent.com/24971915/143431354-d8eecceb-ab00-4fe5-b7f7-1ebb68ab6193.png" width="200" /></td>
  <td><img src="https://user-images.githubusercontent.com/24971915/143431360-59e15169-a892-415d-ba5f-894f866fa8e6.png" width="200" /></td>
</table>

With dismissible enabled (close button visible):

<table>
  <td><img src="https://user-images.githubusercontent.com/24971915/143432252-53618c6e-c9c3-450a-a68e-5285bb05f3c1.png" width="200" /></td>
  <td><img src="https://user-images.githubusercontent.com/24971915/143432266-3bf7c603-6871-470e-9396-28caccde6f43.png" width="200" /></td>
</table>

### Parameters

| Parameter | Type | Description | Default |
|-----------|------|-------------|---------|
| `context` | `Context` | **Activity context** (required). Do not use Application context. | Required |
| `app_id` | `String` | Application ID provided by CustomerPulse | Required |
| `link_or_token` | `String` | Token or linking ID provided by CustomerPulse | Required |
| `options` | `HashMap<String, String>` | Additional parameters (e.g., language) | Required |
| `dismissible` | `boolean` | Allow user to dismiss the survey | `true` |
| `closingDelayMs` | `int` | Auto-close delay after completion (ms) | `2000` |

### Language Support

Set the survey language using the `lang` option:

```java
HashMap<String, String> options = new HashMap<>();

// English
options.put("lang", "en");

// Arabic
options.put("lang", "ar");
```

### Important: Context Requirement

**The `context` parameter must be an Activity context, not an Application context.**

```java
// Correct - using Activity context
CustomerPulseSurvey.showSurveyPage(this, app_id, token, options);

// Correct - from a Fragment
CustomerPulseSurvey.showSurveyPage(getActivity(), app_id, token, options);

// INCORRECT - do not use Application context
CustomerPulseSurvey.showSurveyPage(getApplicationContext(), ...); // Will not work properly
```

The SDK requires an Activity context to:
- Launch the survey Activity (for full-page surveys)
- Display the BottomSheetDialog (for bottom sheet surveys)
- Properly close the survey when completed

---

## Example Project

Check out the example project in the [`app/`](app/) directory for a complete implementation.

```bash
# Open in Android Studio
File -> Open -> Select CustomerPulse-Android folder
```

---

## Changelog

See [CHANGELOG.md](CHANGELOG.md) for a list of changes in each version.

---

## Support

- **Bug Reports**: [GitHub Issues](https://github.com/KalvadTech/CustomerPulse-Android/issues)
- **Feature Requests**: [GitHub Issues](https://github.com/KalvadTech/CustomerPulse-Android/issues)

---

<p align="center">
  Made with care by <a href="https://customerpulse.gov.ae">CustomerPulse</a>
</p>
