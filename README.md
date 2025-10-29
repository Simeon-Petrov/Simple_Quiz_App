# Spring MVC Quiz Application

## Project Overview

This is a **web-based quiz application** built using **Spring Boot** and **Spring MVC** with **Thymeleaf** for templating.  
The quiz loads questions from a JSON file, features advanced **time management** on the server side, and includes **dynamic navigation and custom styling**.

---

## Key Features Implemented

- **Interactive Countdown Timer:**  
  A 10-minute total timer managed accurately by the server and visually updated on the client side via JavaScript.

- **Time Management:**  
  Quiz start time is tracked using `HttpSession` to ensure the countdown remains accurate even when navigating between questions.

- **Navigation:**  
  Allows *Next / Previous* / Submit* navigation between questions.

- **Progress Tracking:**  
  Displays progress as **"Question X of Y"**.

- **Shuffling:**  
  Randomly shuffles both the **questions** and **answers** each time the quiz is loaded.

- **Theming:**  
  Custom color palette implemented via `style.css` (dark purple theme).

- **Results:**  
  Displays the final score and highlights incorrect answers with the correct solution.

---

## Technologies Used

| Category    | Technology              | Version / Role |
|--------------|--------------------------|----------------|
| **Backend** | Java Development Kit (JDK) | 17+ |
| **Framework** | Spring Boot | 3+ |
| **Web** | Spring MVC | Controller, Session Management |
| **Templating** | Thymeleaf | Dynamic HTML generation |
| **Data** | Jackson | JSON serialization/deserialization |

---

## Getting Started

Follow these steps to get the application up and running locally.

### 1.Prerequisites

Make sure you have the following installed on your machine:

- **Java Development Kit (JDK):** Version 17 or later  
- **Apache Maven:** Installed, or use an IDE with built-in Maven support

---

### 2.Build and Run

#### Step 1: Navigate to the project root (where `pom.xml` is located):

```bash
cd path/to/your/project
```

#### Step 2: Ensure the quiz data file exists:
```
src/main/resources/quiz.json
```

#### Step 3: Build and run the Spring Boot application

```bash
# (Optional) Build the project
mvn clean install

# Start the application
mvn spring-boot:run
```

---

### 3. Access the Application

Once the application starts (typically runs on port **8080**), open your browser and go to:

 [http://localhost:8080/](http://localhost:8080/)

---

## Code Highlights

| Feature | File / Location | Implementation Detail |
|----------|------------------|------------------------|
| **Interactive Timer** | `QuizController.java` (`displayQuiz`) | Calculates `remainingSeconds` using `System.currentTimeMillis()` and `HttpSession` attributes |
| **Client Countdown** | `quiz-single-question.html` | JavaScript uses `setInterval()` to countdown from the server-supplied `remainingSeconds` value |
| **Question Shuffling** | `QuizService.java` (`shuffleQuiz`) | Uses `Collections.shuffle()` twice — once for the main question list and once for the options within each question |
| **Navigation Logic** | `QuizController.java` (`handleAnswer`) | Processes the action (*next* or *previous*) and redirects with updated `?index=` parameter |
| **Styling** | `style.css` | Implements the dark purple theme (`#2a2958`, `#3b3a72`) and styles for the timer and results area |

---

## Author

**Simeon Petrov**  

---
