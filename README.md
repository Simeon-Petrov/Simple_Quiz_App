\# Spring MVC Quiz Application


## Project Overview


This is a web-based quiz application built using Spring Boot and Spring MVC with Thymeleaf for templating. The quiz loads questions from a JSON file, features advanced time management on the server side, and includes dynamic navigation and styling.



\## Key Features Implemented:


Interactive Countdown Timer: A 10-minute total timer managed accurately by the server and visually updated on the client side via JavaScript.

Time Management: Quiz start time is tracked using HttpSession to ensure the countdown is accurate even when navigating between questions.

Navigation: Allows Next / Previous navigation between questions.
Progress Tracking: Displays progress as "Question X of Y".
Shuffling: Implements logic to randomly shuffle the questions and the answers each time the quiz is loaded.
Theming: Custom color palette implemented via style.css (dark purple theme).
Results: Displays the final score and highlights incorrect answers with the correct solution.

## Technologies Used


Category             Technology                         Version / Role
Backend              Java Development Kit (JDK)         17+

Framework            Spring Boot                        3+

Web                  SpringMVC                          Controller, Session Management

Templating           Thymeleaf                          Dynamic HTML generation

Data                 Jackson                            JSON serialization/deserialization
 

\## Getting Started


Follow these steps to get the application up and running locally.

1\. Prerequisites

Make sure you have the following installed on your machine:

Java Development Kit (JDK): Version 17 or later.

Apache Maven: (If not using an IDE with built-in Maven support).



2\. Build and Run

&nbsp;  2.1. Navigate to the root directory of the project (where the pom.xml file is located) in your terminal.

&nbsp;  2.2. Ensure the quiz data file (quiz.json) is present in the src/main/resources directory.

&nbsp;  2.3. Build and run the Spring Boot application using the Maven wrapper:



Bash

# Build the project (optional, if needed)

mvn clean install



\# Start the application

mvn spring-boot:run



3\. Access the Application



Once the application starts (it typically runs on port 8080), open your web browser and navigate to:

http://localhost:8080/




\## Code Highlights



Feature              File / Location                   Implementation Detail



Interactive          QuizController.java               Calculates remainingSeconds using
Timer                  (displayQuiz)                   System.currentTimeMillis() and
						       HttpSession attributes. 



Interactive          quiz-single-                      JavaScript uses setInterval to countdown from
Timer		     question.html	               the server-supplied remainingSeconds value.



Question             QuizService.java                  Uses Collections.shuffle() twice: once for the
Shuffling            (shuffleQuiz)                     main question list and again for the options within each

&nbsp;                                                      question

&nbsp;                                                      

Navigation           QuizController.java               Processes the action (next or previous) and
Logic                (handleAnswer)                    redirects to the new ?index= parameter.



Styling              style.css                         Defines the dark theme (#2a2958 and #3b3a72)

&nbsp;                                                      and specific styles for the timer (#timer-

&nbsp;                                                      container) and results area.















            

