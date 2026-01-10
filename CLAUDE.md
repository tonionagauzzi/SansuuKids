# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

さんすうキッズ (SansuuKids) - A math learning app for children (preschool to early elementary school) that gamifies arithmetic practice through medal collection. See `spec.md` for detailed feature specifications.

## Technology Stack

- **Framework**: Kotlin Multiplatform (KMP)
- **UI**: Jetpack Compose with Compose Multiplatform
- **Navigation**: Navigation 3 in Compose Multiplatform
- **Target Platforms**: iOS and Android

## Build Commands

```bash
# Clean build
./gradlew clean

# Build Android APK
./gradlew :composeApp:assembleDebug

# Install on Android emulator/device
./gradlew :composeApp:installDebug

# Build iOS framework (simulator, requires macOS)
./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64

# Open iOS project in Xcode
open iosApp/iosApp.xcodeproj

# Run all tests
./gradlew allTests

# Run shared module tests only
./gradlew :shared:test

# Run a single test class
./gradlew :shared:test --tests "ClassName"
```

## Project Structure

```
composeApp/   # Compose Multiplatform UI (shared between platforms)
shared/       # Business logic and domain models
iosApp/       # iOS-specific entry point and Xcode project
```

## Architecture

- **Pattern**: MVVM with Compose state management
- **Screens**: Home → Mode Selection → Level Selection → Quiz → Results
- **Quiz Logic**: 10 questions per session, answers never negative
- **Medal System**: Gold (100%), Silver (80-99%), Bronze (60-79%), Star (<60%)
