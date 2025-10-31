# Harshit Srivastava — Project Portfolio Page

## Overview

**CuddleCare** is a desktop-based pet management application for pet owners and caregivers.  
It allows users to manage their pets, track treatments, and keep records in an intuitive command-line interface.  
The app provides a simple yet powerful experience for organizing multiple pets’ health-related data, ensuring users never miss important treatments or updates.

---

### **Enhancements Implemented**

#### 🧩 Command Feature Implementations
I implemented multiple core commands that form the backbone of the user interaction layer:

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

**Depth and Difficulty:**  
These commands required integrating multiple components (UI, model, and logic) while maintaining code readability and adherence to SE principles. I also introduced logging and assertions to ensure defensive programming and easier debugging.

---

### **Contributions to the User Guide (UG)**

I authored or refined the following UG sections:
* **Help Command** — Usage format, examples, and output screenshots.
* **DeletePet Command** — Step-by-step examples and failure cases.
* **Bye Command** — Description and expected output.
* **OverdueTreatments Command** — Comprehensive examples and explanation of overdue logic.

All UG content was written in clear, reader-friendly language, with consistent Markdown formatting and proper code fencing.

---

### **Contributions to the Developer Guide (DG)**

I contributed the following to the DG:

* **Design Diagrams**
    * Sequence diagrams for `HelpCommand`, `ListPetsCommand`, and `DeletePetCommand` (created using PlantUML).
    * Added notes on exception handling, assertions, and logging for command execution.
    * Contributed to the **Design & Implementation** section for command-related features.

* **Testing**
    * Wrote manual testing instructions for all 4 commands to aid QA verification.
    * Included valid, invalid, and edge case test examples.

---

### **Team-Based Contributions**

* Helped standardize the **command structure** for the project (`Command` interface and `exec()` method format).
* Set up consistent **logging conventions** across all commands.
* Assisted in reviewing PRs for feature integration and UI consistency.
* Helped design the folder structure under `seedu.cuddlecare.command.impl`.

---

### **Review / Mentoring Contributions**

* Reviewed PRs for teammates’ command implementations and ensured adherence to coding standards.
* Assisted in debugging argument parsing logic in commands.
* Provided feedback on DG and UG writing to maintain tone consistency and diagram clarity.

---

### **Contributions Beyond the Project Team**

* Shared feedback and tips with other teams on forum discussions regarding PlantUML diagram conventions.
* Reported minor documentation formatting issues in other teams’ repos during peer review sessions.

---

Summary

Through my contributions, I implemented several core features essential to the user experience and maintainability of CuddleCare.
My work focused on robust command logic, testing, and clean architecture, all while maintaining strong SE principles (defensive programming, modularity, and documentation clarity).