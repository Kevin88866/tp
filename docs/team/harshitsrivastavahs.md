# Harshit Srivastava — Project Portfolio Page

## Overview

**CuddleCare** is a desktop-based pet management application for pet owners and caregivers.  
It helps users record and track pets, treatments, and schedules through a simple command-line interface.  
The app aims to make pet care organized and stress-free, combining structured data handling with intuitive command execution.

---

## Summary of Contributions

[:link: View my contributions on RepoSense](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=HarshitSrivastavaHS&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByAuthors&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

### **1. System Architecture Foundations**

I was responsible for designing and implementing the **core command architecture** of the CuddleCare application.  
This includes the **`Command` interface**, **`CommandWithArguments`**, **`Parser`**, and **`LoggingConfigurator`** classes — all of which serve as the backbone for every command feature.

#### **Key Components Implemented**

1. **`Command` Interface**
    * Defined the blueprint for all executable commands in the application.
    * Enforced consistent method structure (`exec()`, `getSyntax()`, `getShortDescription()`, etc.).
    * Introduced default methods for cleaner subclass implementations.

1. **`CommandWithArguments`**
    * Enabled delayed command execution with argument storage and logging.
    * Ensured separation of parsing and execution logic.
    * Added `java.util.logging` integration for detailed command tracing.

1. **`Parser`**
    * Implemented parsing logic that maps raw user input to executable `Command` objects.
    * Added handling for empty and invalid inputs with user-friendly feedback.
    * Integrated `CommandWithArguments` for argument retention and consistent execution.
    * Included `assert` and `LOGGER` usage for defensive and traceable parsing.

1. **`LoggingConfigurator`**
    * Implemented centralized logging setup for the entire app.
    * Automatically creates `logs/` directory and writes logs to `cuddlecare_app.log`.
    * Removes default console handlers to prevent duplicate log messages.
    * Facilitated maintainability and debugging across all modules.

1. **Integration in `CuddleCare`**
    * Integrated all the above components into the **main application loop**.
    * Implemented clean command initialization using `Map.ofEntries()`.
    * Connected `HelpCommand` dynamically to the command registry for real-time updates. 
   
1. Wrote **JUnit tests** to verify parser correctness, logging behavior, and command execution flow.

---

### **2. Command Implementations**

I also implemented four key **user-facing commands**, forming the core functionality of the user interaction layer:

1. **`HelpCommand`**
    * Displays all available commands with syntax, descriptions, and categories.
    * Allows flexible filtering by command category (e.g., "General", "Pet", "Treatment").
    * Focused on clean, readable output formatting for console display.
    * Added detailed assertions and logging for debugging and testing reliability.

1. **`DeletePetCommand`**
    * Deletes a pet from the pet list by name.
    * Validates user input and ensures safe deletion with confirmation output.
    * Logs each deletion for better traceability.
    * Added input validation and handled incorrect command usage cases.

1. **`ByeCommand`**
    * Terminates the application with a farewell message.
    * Implemented clean exit handling with graceful console output and zero errors.
    * Included assertions and ensured test safety using mock command flows.

1. **`OverdueTreatmentsCommand`**
    * Identifies and lists all **overdue treatments** for each pet.
    * Supports both global view and pet-specific view (`overdue-treatments [n/PET_NAME]`).
    * Calculates how many days each treatment is overdue based on the current date.
    * Handles logging, null-safety assertions, and well-formatted output.
    * Designed with testability in mind — accepts a custom date parameter for testing.

#### **Highlights**
* Each command uses **assertions** and **logging** for robustness and traceability.
* `OverdueTreatmentsCommand` supports flexible date testing and dynamic overdue day calculation.
* `HelpCommand` dynamically reflects all available commands for maintainability.

---

### **3. Contributions to the User Guide (UG)**

I wrote and refined the following UG sections:

* **Help Command** – Format, arguments, and screenshots.
* **DeletePet Command** – Examples of successful and failed deletions.
* **Bye Command** – Simple farewell description.
* **OverdueTreatments Command** – Detailed syntax, example outputs, and logical explanation.

These were written in **consistent Markdown format**, ensuring proper code fencing and linkable syntax.

---

### **4. Contributions to the Developer Guide (DG)**

**Design and Implementation**
* Added **sequence diagrams** for:
    * `HelpCommand`
    * `DeletePetCommand`
    * `OverdueTreatmentsCommand`
    * `ByeCommand`
* Documented command execution flow under the “Design & Implementation” section.
* Added explanation for **assertions**, **logging**, and **parser-command integration**.

**Testing**
* Authored **manual testing instructions** for all 4 commands, including edge cases.
* Added **JUnit tests** for parser functionality, logging setup, and command integration.

---

### **5. Team-Based Contributions**

* Helped establish the **base command structure** used by all teammates.
* Standardized **logging and assertion practices** across the codebase.
* Reviewed PRs related to command execution and parser integration.
* Assisted teammates in integrating their commands into the main `CuddleCare` loop.
* Helped maintain overall code quality and naming consistency.

---

### **6. Review / Mentoring Contributions**

* Regularly reviewed teammates’ PRs for code readability, modularity, and adherence to coding standards.
* Guided others on:
    * Proper Javadoc and logging format.
    * Using assertions effectively for defensive programming.
* Provided detailed feedback on UML diagram formatting and documentation tone.

---

### **7. Contributions Beyond the Team**

* Shared code quality and PlantUML best practices in peer review sessions.
* Reported minor documentation inconsistencies in other teams’ UG/DG during peer reviews.

---

### **Summary**

My main contribution was in **architecting and implementing the command system** that powers CuddleCare’s interaction layer.  
This included parser logic, logging setup, command encapsulation, and core user-facing commands.  
These form the foundation that enables smooth execution, extensibility, and maintainability for all future features.

Through these efforts, I ensured that **CuddleCare** adheres to strong **Software Engineering principles** such as modularity, testability, defensive coding, and clarity of documentation.

---

