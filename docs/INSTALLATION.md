# Installation Guide (Manual)

This guide covers manual AAR installation with step-by-step screenshots.

For other installation methods, see the main [README](../README.md).

---

## Steps

### 1. Add AAR File

Add `CustomerPulseSurvey.aar` file to your `libs` folder inside the `app` folder:

<img src="https://user-images.githubusercontent.com/24971915/143434248-fb979977-4c01-48fd-b779-6c60d9ec8324.png" width="600" />

### 2. Open Project Structure

From the File menu, open Project Structure:

<img src="https://user-images.githubusercontent.com/24971915/143434434-bacc68ef-379f-4019-9ecc-03a71f3c5196.png" width="600" />

### 3. Select Dependencies

In the left side, select Dependencies:

<img src="https://user-images.githubusercontent.com/24971915/143434672-1c4dbe4e-fccb-4c25-a954-a3cf989a7bfc.png" width="600" />

### 4. Select App Module

In modules, select the `app` tab:

<img src="https://user-images.githubusercontent.com/24971915/143434850-d255916d-8a02-496e-b8d3-19b4125adb39.png" width="600" />

### 5. Add JAR/AAR Dependency

In declared dependencies, click the plus "+" icon and select JAR/AAR Dependency:

<img src="https://user-images.githubusercontent.com/24971915/143435226-87a088d5-f8f6-4592-8758-bf13787cbce4.png" width="600" />

### 6. Enter Library Path

In step 1, add your libs folder name (if you followed from step 1, it should be `libs`):

<img src="https://user-images.githubusercontent.com/24971915/143435484-69bc9f2d-294b-4329-b2df-d136e3ade528.png" width="600" />

### 7. Save Changes

Press **OK** button then **Apply** to save changes.

---

## Verify Installation

After installation, you should be able to import the SDK:

```java
import com.customerpulse.customerpulsesurvey.CustomerPulseSurvey;
```

---

## Next Steps

See the [Quick Start](../README.md#quick-start) guide to display your first survey.
