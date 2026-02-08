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

# Build iOS framework (simulator, requires macOS)
./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64

# Run test (use desktop target)
./gradlew :composeApp:desktopTest
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

## Testing Guidelines

- **Test Structure**: Write tests in Given/When/Then format
  - **Given**: Set up the initial state and test data
  - **When**: Execute the action being tested
  - **Then**: Assert the expected outcomes
- Use clear section comments or variable names to separate the three phases
- Each test should clearly show what is being tested and why

## Agent Team

For the development of this app, an agent team is formed with the following roles:

- **UX**: Reviews and provides suggestions from a user experience and UI design perspective
- **Technical Architecture**: Reviews and provides suggestions from a technical design and architecture perspective
- **Devil's Opinion**: Critically challenges proposals and designs by identifying potential issues and risks
