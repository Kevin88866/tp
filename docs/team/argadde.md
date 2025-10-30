# Abhiram Gadde - Project Portfolio Page

## Overview

CuddleCare is a CLI-based pet care tracker that helps users manage multiple pets, store important information, and
keep track of each pet's wellness records.

My primary contribution was developing the pet management system and persistence layer, including core classes for
pets, the list structure supporting them, adding new pets, and loading/saving persistent pet and treatment data.

### Summary of Contributions

#### New Features
#### 1. Pet Management Core Classes (Pet, PetList)

What it does:
Implements the fundamental data model for pets and the list structure storing all pets.

Justification:
These classes form the backbone of the application and are required for storing, searching, retrieving, and adding pets.

Highlights:
* Designed Pet with immutable pet attributes (name, species, age)
* Implemented PetList with: Duplicate pet validation, case-insensitive lookup by name,
and encapsulation for safe data manipulation

#### 2. Add Pet Feature (AddPetCommand)

What it does:
Allows users to add new pets to their tracked list using name, species, and age arguments.

Justification:
This is a core user operation and the first step in building personalized pet records.

Highlights:
* Validates mandatory inputs and age format
* Provides clear error handling and messages
* Ensures pets are stored uniquely (no duplicates)
* Clean parsing logic matching CLI patterns (n/, s/, a/)

#### 3. Persistent Storage of Pets & Treatments (Storage)

What it does:
Loads and saves both pet and treatment data to disk in a structured format.

Justification:
Ensures that users do not lose their pet and treatment information across sessions,
enabling a real, usable pet tracking system.

Highlights:
* Implemented file parsing & serialization to handle: Pet records, Treatment records
* Ensured application startup loads existing data
* Robust handling for missing save file (creates on startup)
* Structured output for readable save files

#### Testing

Added JUnit tests for:
* AddPetCommandTest

Focus areas:
* Valid pet creation
* Duplicate pet handling
* Parsing and error cases in command execution

#### Documentation
#### User Guide

Added sections for:
* Add Pet command (syntax, examples, validation rules)

#### Developer Guide

Added:
* Class diagram for AddPetCommand
* Sequence diagram for AddPetCommand

#### Project Management & Team Contributions

* Reviewed PRs from teammates and provided feedback
* Coordinated integration testing for loading + saving components
* Assisted team with debugging serialized data issues