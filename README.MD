[![Tests](https://github.com/AKJAW/Timi-Compose/actions/workflows/test.yml/badge.svg)](https://github.com/AKJAW/Timi-Compose/actions/workflows/test.yml)
[![Linting check](https://github.com/AKJAW/Timi-Compose/actions/workflows/ktlint.yml/badge.svg)](https://github.com/AKJAW/Timi-Compose/actions/workflows/ktlint.yml)

<a href="https://androidweekly.net/issues/issue-468">
<img src="https://androidweekly.net/issues/issue-468/badge">
</a>
<a href="https://androidweekly.net/issues/issue-514">
<img src="https://androidweekly.net/issues/issue-514/badge">
</a>

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

- Move settings logic with tests to KMM
- Add settings to iOS
- Add Maestro tests
- Improve the UI for both o the Apps
- Add charts for the time spent
- Add Android stopwatch notification Service
- Add iOS notification with the stopwatch somehow :)
- Fix the Calendar
- Add iOS Calendar
- Add swipe to reveal for delete on time entry list instead of a hardcoded icon

### Medium priority:
- Figure out GitHub status requirements before merging
- Clean up Android time picker
  - Allow seconds
  - Fix last time pick being remembered?
- Add "string resources" to the iOS app

### High priority:
- ~~Clean up Repository insert id~~
- ~~add core-test~~
- ~~add contract test for time entry repository?~~
- ~~Add KMM ViewModel for TaskDetails just for one task~
  - ~~Add date and time formatting~~
- ~~Add Android UI for adding time to task~~
- ~~Add Android UI for listing ALL time details~~
- Add iOS UI for adding time to task
- Add iOS UI for listing ALL time details
- Improve ViewModel Contract to allow passing in hours and minutes?
- Add basic calendar for just one month Without bubbles just allows selecting one day
- Add current day selection to KMM VM
- Add calendar day bubbles
- Add multiple day selection 
- Add other months in calendar
- Add support for multiple tasks
- Clean up string resources
- Merge task-ui modules into one?

### Known issues:
- Android: Going to task "details -> Stopwatch -> task list" Does not open the task details but the list again
  - This might be solved in the future once a different library for navigation is used :) 
- Android: Bottom nav names are broken
- iOS: Long pressing tasks is broken
- iOS: Clicking on task ripple is out of bounds
- iOS: Missing delete function for the Task list

### Brain storming
- Animations
- Compose different navigation library