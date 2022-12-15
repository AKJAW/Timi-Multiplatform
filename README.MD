[![Tests](https://github.com/AKJAW/Timi-Compose/actions/workflows/test.yml/badge.svg)](https://github.com/AKJAW/Timi-Compose/actions/workflows/test.yml)
[![Linting check](https://github.com/AKJAW/Timi-Compose/actions/workflows/ktlint.yml/badge.svg)](https://github.com/AKJAW/Timi-Compose/actions/workflows/ktlint.yml)

# What this project is

It's a rewrite of my first Android application and also a way for me to learn Compose. This app
focuses on productivity and keeping track of time spent doing various activities (Learning,
Freelancing etc.)

## App Features

- [x] In-app stopwatch
- [ ] Persisting time spent to a database
- [ ] Service stopwatch with a notification
- [ ] Charts and stats of time spent
- [ ] More to come

## Technology stack

- Jetpack Compose
- Kotlin Coroutines
- Koin
- SQLDelight

## What's in this project:

- Compose and Compose UI tests
- JUnit5 Unit tests
- Gradle modularization

## What's next

### Low priority:

#### Settings
- Move logic to KMM
- Move tests to KMM
- Add settings to iOS
- Add Maestro tests
- Improve the UI for both o the Apps
- Add charts for the time spent
- Add Android stopwatch notification Service
- Add iOS notification with the stopwatch somehow :)

### Medium priority:
- Replace Dagger with Koin
- Update dependencies
- Fix the Calendar
- Remove light mode and settings for the time being?

### High priority:
- Merge to latest branch to main
- Remove stale branches
- Synchronize the Android and iOS features so they're up to date
- Add iOS stopwatch dialog
- Add Stopwatch time database table with relations
- Think of what to do next