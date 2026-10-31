# Maths Quiz App

A modern, interactive Android application that fetches dynamic math questions from a RESTful API and tracks user scores in real-time. Built with Kotlin, this project demonstrates modern Android development practices using MVVM architecture, the Repository pattern, and Jetpack components.

## ✨ Features

* **Dynamic Question Loading:** Asynchronously retrieves quiz questions from a backend REST API using Retrofit.
* **Interactive Quiz Interface:** Presents multiple-choice questions with a clean, radio-button-based selection system.
* **Live Answer Validation & Score Tracking:** Instantly validates user selections, updates the score dynamically, and uses Material Snackbars to prevent skipping questions without an answer.
* **Dedicated Results Screen:** Displays the final score tally (Score / Total Questions) with intuitive options to restart the quiz or exit.
* **Modern Android UI:** 
  * Implements Material Design 3 components and Data Binding for a reactive UI.
  * Features seamless startup via the AndroidX Core Splashscreen API.
  * Utilizes Edge-to-Edge display rendering with custom status bar styling for a premium feel.

## 🛠 Tech Stack & Libraries

* **Language:** Kotlin
* **Architecture:** MVVM (Model-View-ViewModel) + Repository Pattern
* **Concurrency:** Kotlin Coroutines (`kotlinx-coroutines-android`)
* **Networking:** Retrofit 2, Gson Converter
* **State Management:** Jetpack ViewModel, LiveData, Data Binding
* **UI Components:** AppCompat, Material Components 3, ConstraintLayout
* **System Integration:** System UI Edge-to-Edge, AndroidX Core Splashscreen
* **Build System:** Gradle (Kotlin DSL - `build.gradle.kts`)

## 📐 Architecture & Project Structure

The application is modularized into distinct layers following Clean Architecture principles:

* **`model/`**: Contains data classes like `Questions.kt` (single question/options) and `QuestionsList.kt` (API response wrapper).
* **`retrofit/`**: Houses the network layer with `RetrofitInstance.kt` for the client setup and `QuestionsAPI.kt` for API endpoints.
* **`repository/`**: Includes `QuizRepository.kt` which fetches API data and exposes it to the ViewModel.
* **`viewmodel/`**: Contains `QuizViewModel.kt` to manage UI state and handle repository interactions.
* **`view/`**: Contains UI controllers including `MainActivity.kt` (active quiz logic) and `ResultsActivity.kt` (final score and navigation).

## 📡 API Integration

The app expects a RESTful GET endpoint returning a JSON array of question objects. 

**Expected JSON Response Format:**
```json
[
  {
    "question": "What is 15 + 27?",
    "option1": "42",
    "option2": "32",
    "option3": "45",
    "option4": "52",
    "correctOption": "42"
  }
]
