# AnonQuestions

 Tech Stack

    Backend: Java 17+, Spring Boot 4.x (Spring Security, Spring Data JPA)

    Frontend: HTML5, CSS3, JavaScript (ES6+)


    Database: PostgreSQL 

    Template Engine: Thymeleaf 

 Installation & Setup
Prerequisites

    JDK 17 or higher

    Maven 3.6+

Steps

    Clone the repository:
    Bash

    git clone https://github.com/MaxSych/AnonQuestions.git
    cd ask-fm-clone

    Configure Database:
    Update src/main/resources/application.properties with your database credentials.

    Build and Run:
    Bash

    mvn clean install
    mvn spring-boot:run

    Access the app:
    Open http://localhost:8080 in your browser.

 Project Logic

    User Profiles: Every user has a unique page.

    Questions & Answers:Anonim users can ask questions, and owners can answer them.

    