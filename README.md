# COMP3074 - Assignment 1: Payroll Calculator

A two-activity Kotlin Android application built for **COMP3074 (Fall 2026)**.

## Features (assignment checklist)
- **MainActivity**: accepts hours worked, hourly rate, and tax rate.
- **Pay formula**: for hours <= 40, `pay = hours * hourlyRate` and `overtimePay = 0`.
- **Overtime formula**: for hours > 40, `pay = 40 * hourlyRate` and `overtimePay = (hours - 40) * hourlyRate * 1.5`.
- **Total pay**: `pay + overtimePay`.
- **Tax**: `pay * taxRate` (intentionally **excludes overtime** per assignment instructions).
- Displays **pay, overtime pay, total pay, and tax**.
- **AboutActivity** displays student full name and student ID.
- About screen has a visible **ActionBar Up arrow**. It calls `finish()`, revealing the existing MainActivity (no new instance).
- Basic input validation and a clear button are included.

## Student details - completed
The About screen is configured using `app/src/main/res/values/strings.xml`:
- **Full name:** Taharim Haque
- **Student ID:** 101418321

Check these details in the About screen before submitting.

## Open and run in Android Studio (Windows)
1. Extract the ZIP file to a regular folder, such as `Documents/COMP3074_Payroll_Assignment1`.
2. On Windows, open a terminal in the extracted project folder and run `gradlew.bat --version` once to download the official Gradle wrapper and distribution (internet required). Then open **Android Studio** > **Open**. Choose the `COMP3074_Payroll_Assignment1` **folder**, NOT a Kotlin source file or the ZIP.
3. Let Gradle sync. Use a **JDK 17 or 21** as the Gradle JDK (File > Settings > Build, Execution, Deployment > Build Tools > Gradle). **Do not use JDK 25 with Gradle 8.9.**
4. If it asks to install Android SDK Platform 35, accept. Internet is required the first time for Gradle and Android tooling.
5. In **Device Manager**, start an Android emulator (Pixel 8 is fine) or connect an Android phone with USB debugging.
6. Choose the **app** run configuration, then press the green **Run** triangle.
7. Type `45`, `20`, and `0.10`, then tap **Calculate pay**. Results should be **$800.00**, **$150.00**, **$950.00**, **$80.00**.
8. Tap **About this app** to check your actual name and ID. Tap the top-left Up arrow; your previous calculator input and results should still be present.

### Gradle Wrapper setup
This project includes `gradlew` and `gradlew.bat`, which download the **official Gradle 8.9 wrapper JAR from the Gradle GitHub source** if it is not already present, then run the normal wrapper. If you have trouble syncing, open a terminal in the project folder, run `gradlew.bat --version` (Windows), then select **Sync Project with Gradle Files** in Android Studio. After the wrapper JAR is downloaded, commit the generated `gradle/wrapper/gradle-wrapper.jar` to your GitHub repository along with the other project files.

## Test cases

| Hours | Hourly rate | Tax rate | Regular pay | Overtime pay | Total pay | Tax |
| --- | --- | --- | --- | --- | --- | --- |
| 35 | 20 | 0.10 | 700.00 | 0.00 | 700.00 | 70.00 |
| 40 | 25 | 0.15 | 1000.00 | 0.00 | 1000.00 | 150.00 |
| 45 | 20 | 0.10 | 800.00 | 150.00 | 950.00 | 80.00 |
| 40.5 | 18.50 | 0.12 | 740.00 | 13.88 displayed | 753.88 displayed | 88.80 |
| 0 | 20 | 0.10 | 0.00 | 0.00 | 0.00 | 0.00 |

Unit tests are in `app/src/test/java/ca/comp3074/payroll/PayCalculatorTest.kt`. Run them with `gradlew.bat testDebugUnitTest` on Windows after setup. Currency is rounded to two decimals for display; calculations are performed with BigDecimal, without premature rounding.

## GitHub and D2L submission
1. Make a **new GitHub repository** (suggested name: `COMP3074-Assignment1-Payroll`).
2. Push or upload the Android Studio **project contents** to its root: `app`, `gradle`, `gradlew`, `gradlew.bat`, `settings.gradle.kts`, `build.gradle.kts`, `gradle.properties`, `README.md`.
3. Copy the GitHub repository URL (example format: `https://github.com/USERNAME/COMP3074-Assignment1-Payroll`).
4. On D2L/Brightspace, open **COMP3074 > Assignments > Assignment 1**, paste the GitHub link in the submission/comment field if available.
5. Upload the **separate** AI Usage Declaration as `aud.pdf`. Complete the editable fields and check the truthfulness of your responses before submission. Submit and check for confirmation.

**The assignment specifically requests a GitHub URL and `aud.pdf`, not a ZIP upload or APK.**

## Three interview questions to practice
1. **Why is 40 special?** Only hours beyond 40 receive 1.5x overtime, so regular pay is capped at 40 hours.
2. **What is tax applied to?** Regular pay only (`pay * taxRate`), following the given formula, even when overtime exists.
3. **How does About go back without duplicating MainActivity?** The Up button calls `finish()` on AboutActivity and exposes the existing MainActivity.

## Tooling note
Project sources were AI-assisted. The author must review, understand, test, and make an accurate AI Usage Declaration before academic submission.
