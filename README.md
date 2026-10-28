# QuickBank - Android Quiz Application

**QuickBank** is an interactive, category-based quiz application built for Android. It allows users to test their knowledge across multiple subjects including Mathematics, Computer Science, Web Development, Data Science, and more, complete with timed questions, score tracking, and Firebase Authentication.

---

## 🚀 Features

* 🔐 **User Authentication:** Firebase Email/Password Sign Up & Login with persistent login sessions (`SharedPreferences`).
* 📚 **Diverse Quiz Categories:**
  * Math
  * English
  * Computer Science
  * Reasoning
  * Science
  * Web Development
  * Data Analyst
  * Data Science
* ⏱️ **Interactive Timed Quizzes:**
  * 30-second countdown timer per question with a dynamic circular progress indicator.
  * Real-time visual feedback (Green for correct answers, Red for incorrect answers).
* 🏆 **Scoreboard & History:**
  * Saves quiz scores locally per category.
  * Dedicated Scores Activity to view past performance.
* 🎨 **Dynamic UI & Theming:**
  * Custom card layouts, icons, and dynamic color themes matching each quiz subject.

---

## 📱 Screenshots

<p align="center">
  <img src="screenshots/category_screen.png" width="280" alt="Category Dashboard"/>
  <img src="screenshots/quiz_screen.png" width="280" alt="Quiz Engine Screen"/>
  <img src="screenshots/scores_screen.png" width="280" alt="Scores Screen"/>
</p>

---

## 🛠️ Tech Stack & Prerequisites

* **Language:** Java (JDK 11)
* **Minimum SDK:** `24` (Android 7.0 Nougat)
* **Target / Compile SDK:** `36` (Android 15/16)
* **Architecture:** Android View System (XML layouts, Material Components, CardView)
* **Authentication:** Firebase Authentication
* **Build System:** Gradle (Kotlin DSL - `.gradle.kts`)

---

## 📁 Project Structure

```
quickbank/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/reason/quickbank/
│   │   │   │   ├── MainActivity.java     # Session router / Launcher
│   │   │   │   ├── LoginActivity.java    # Firebase Login & Session Save
│   │   │   │   ├── SignupActivity.java   # Firebase Account Creation
│   │   │   │   ├── CategoryActivity.java # Category Selection Grid & Menu
│   │   │   │   ├── QuestionActivity.java # Quiz Engine & CountDownTimer
│   │   │   │   ├── ScoresActivity.java   # Local Scoreboard Display
│   │   │   │   └── Question.java         # Question Data Model
│   │   │   ├── res/                     # Layouts, Drawables, Colors & Themes
│   │   │   └── AndroidManifest.xml
│   └── build.gradle.kts
└── settings.gradle.kts
```

---

## 📦 How to Setup & Run

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/YOUR_USERNAME/quickbank.git
   ```

2. **Open in Android Studio:**
   * Open Android Studio and select **Open**.
   * Choose the `quickbank` project root folder.

3. **Configure Firebase:**
   * Add your `google-services.json` file to the `app/` folder (`quickbank/app/google-services.json`).
   * Enable **Email/Password** authentication in your Firebase Console.

4. **Build & Run:**
   * Connect an Android device or launch an Emulator.
   * Click **Run (Shift + F10)** or build via Gradle:
     ```bash
     ./gradlew assembleDebug
     ```

---

## 📝 License

This project is open source and available under the [MIT License](LICENSE).
