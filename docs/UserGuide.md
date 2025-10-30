# User Guide

## Introduction

{Give a product intro}

## Quick Start

- **Java**: Ensure Java 17+.
- **Run**: `java -jar cuddlecare.jar`
- **Prompt**: You should see a greeting and a prompt symbol `> `.

```
Hello! Welcome to CuddleCare.
>
```

## Features 

---

### Add Pet — `add-pet`
Adds a new pet to the tracker.

**Format**
```
add-pet n/PET_NAME s/SPECIES_NAME a/AGE
```
* n/ (required): name of the pet
* s/ (required): species of the pet 
* a/ (required): age of the pet

**Example**
```
> add-pet n/Peanut s/Golden Retriever a/7
Peanut has been successfully added.

> add-pet n/Peanut s/Husky a/3
A pet with that name already exists.
```

**Notes**
* Name and species can be 1 or more words.
* Each pet's name must be unique or user will have to re-enter
(as seen above).
* Age must be a number and is in units of years. 
* If input is invalid: `Invalid input. Please try again.`

---

### List Pets — `list-pets`
Lists all pets.

**Format**
```
list-pets
```

**Example**
```
> list-pets
Here are your pets:
1. Milo (Species: Dog, Age: 3 years old)
2. Luna (Species: Cat, Age: 2 years old)
```
- If no pets: `No pets found.`

**Notes**
- Output uses **1-based index**.
- Command word is **`list-pets`** (with a dash).

**Screenshot**
![List Pets](./docs/diagrams/ug-list-pets.png)

---

### Edit Pet — `edit-pet`
Edit a pet’s **name**, **species**, and/or **age** by addressing it with its **current name**.

**Format**
```
edit-pet n/OLD_NAME [nn/NEW_NAME] [s/SPECIES] [a/AGE]
```
- **n/** *(required)*: current name to identify the pet
- **nn/** *(optional)*: new name
- **s/** *(optional)*: new species
- **a/** *(optional)*: new age (positive integer)

**Examples**
```
edit-pet n/Milo a/4
edit-pet n/Milo nn/Millie s/Dog
edit-pet n/Luna s/Cat a/3
```
**Constraints & Pitfalls**
- Flags must be **space-separated** (e.g., `n/Milo a/4`, not `n/Miloa/4`).
- Age must be a **positive integer**.
- If no optional field is provided, command is invalid (nothing to change).
- Usage help (on invalid input): `Usage: edit-pet n/OLD_NAME [nn/NEW_NAME] [s/SPECIES] [a/AGE]`.

**Screenshot**
![Edit Pet (before/after)](./docs/diagrams/ug-edit-pet-before-after.png)

---

### Add Treatment — `add-treatment`
Adds a new treatment record for a specified pet.

**Format**
```
add-treatment n/PET_NAME t/TREATMENT_NAME d/DATE [note/NOTE]
```
* n/ (required): name of the pet 
* t/ (required): name of the treatment 
* d/ (required): date in yyyy-MM-dd format 
* note/ (optional): additional notes about the treatment

**Example**
```
> add-treatment n/Milo t/Vaccination d/2025-10-06
Added treatment "Vaccination" on 2025-10-06 for Milo.

> add-treatment n/Luna t/Dental Cleaning d/2025-11-15 note/Dr. Smith, $200
Added treatment "Dental Cleaning" on 2025-11-15 for Luna.
Note: Dr. Smith, $200
```

**Notes**
* Date must be in yyyy-MM-dd format (e.g., 2025-10-06).
* If pet not found: Pet not found: <name>. 
* If date is invalid: `Invalid date format. Please use yyyy-MM-dd format (e.g., 2024-12-25).`
* On missing required fields: `Invalid input. Usage: add-treatment n/PET_NAME t/TREATMENT_NAME d/DATE note/{NOTE}.`

---

### Delete Treatment — `delete-treatment`
Deletes a treatment from a pet's record.

**Format**
```
delete-treatment n/PET_NAME i/INDEX
```

* n/ (required): name of the pet 
* i/ (required): 1-based index of the treatment to delete

**Example**
```
> delete-treatment n/Milo i/1
Deleted treatment "Vaccination" for Milo.
```

**Notes**
* If pet not found: `Pet not found: <name>.`
* If index is invalid: `Invalid treatment index.`
* On malformed input: `Invalid input. Usage: delete-treatment n/PET_NAME i/INDEX.`

---

### Mark a Treatment as Done — `mark`
Marks a **per-pet treatment** as completed by **index** in that pet’s list.

**Format**
```
mark n/PET_NAME i/INDEX
```

**Example**
```
> mark n/Milo i/2
Marked as done
Pet: Milo
Index: 2
```
- On malformed args or non-integer index, usage help is shown: `Usage: mark n/PET_NAME i/INDEX`.
- If pet not found / index invalid: an error message is shown.
- Marking toggles the status in subsequent listings as `[X]` (done).

**Screenshot**
![Mark](./docs/diagrams/ug-mark.png)

---

### Unmark a Treatment — `unmark`
Unmarks (sets **not completed**) by **1-based index** in the selected pet’s list.

**Format**
```
unmark n/PET_NAME i/INDEX
```

**Example**
```
> unmark n/Milo i/2
Unmarked
Pet: Milo
Index: 2
```
- On malformed args or non-integer index, usage help is shown: `Usage: unmark n/PET_NAME i/INDEX`.

**Screenshot**
![Unmark](./docs/diagrams/ug-unmark.png)

---

### Group Treatments by Type — `group-treatments`
Groups treatments by **type** either **for a specific pet** or **across all pets**.

- **Type definition**: the **first word** of the treatment name (case-insensitive).  
  Examples: `Vaccine A` → *Vaccine*; `Checkup` → *Checkup*; `Dental Cleaning` → *Dental*.
- **Sorting**:
    - Type groups are sorted **alphabetically (case-insensitive)**.
    - Inside each type, items are sorted by **date (ascending)**.

### For a specific pet
**Format**
```
group-treatments n/PET_NAME
```

**Example (per-pet)**
```
> group-treatments n/Milo
== Checkup ==
1. Milo: [ ] Checkup on 2024-02-05
== Vaccine ==
1. Milo: [ ] Vaccine A on 2024-01-10
```
- If pet not found: `No such pet: <name>`.
- If pet has no treatments: `No treatments for <name> to group.`

**Screenshot**
![Group by Type (per pet)](./docs/diagrams/ug-group-by-type-pet.png)

### Across all pets
**Format**
```
group-treatments
```
*(If no name is supplied, the app groups treatments for **all pets**.)*

**Example (all pets)**
```
> group-treatments
== Checkup ==
1. Milo: [ ] Checkup on 2024-02-05
2. Luna: [X] Checkup on 2024-02-08
== Vaccine ==
1. Milo: [ ] Vaccine A on 2024-01-10
```

**Screenshot**
![Group by Type (all pets)](./docs/diagrams/ug-group-by-type-all.png)

---

### Filter Treatment by Date — `filter-treatment`
Displays all treatments that fall within a specified date range across all pets.

**Format**
```
filter-treatment from/FROM_DATE to/TO_DATE
```
* from/ (required): start date in yyyy-MM-dd format 
* to/ (required): end date in yyyy-MM-dd format 
* Date range is inclusive (includes both start and end dates)

**Example**
```
> filter-treatment from/2025-11-01 to/2025-11-30
Treatments between 2025-11-01 and 2025-11-30:
1. Annual Vaccine (Luna) - 2025-11-15
2. Dental Checkup (Milo) - 2025-11-22
```

If no treatments in range: `No treatments found between <FROM_DATE> and <START_DATE>.`

**Notes**
* Start date must be before or equal to end date. 
* If start date is after end date: `Error: Start date cannot be after end date.`
* If date format is invalid: `Invalid date format. Please use yyyy-MM-dd format.`
* On missing parameters: `Invalid input. Usage: filter-treatment start/START_DATE end/END_DATE.`

---

### Find Treatment — `find-treatment`
Searches for treatments across all pets by keyword. The search is case-insensitive and uses substring matching.

**Format**
```
find-treatment KEYWORD
```

**Example**
```
> find-treatment vaccine
Found 2 treatments containing 'vaccine':
1. Rabies Vaccine (Milo) - 2025-10-10
2. Annual Vaccine (Luna) - 2025-11-15

> find-treatment checkup
Found 1 treatments containing 'checkup':
1. Regular Checkup (Milo) - 2025-09-20
```

**Notes**
* Keyword cannot be empty or whitespace-only.
* If no matches: `No treatments found containing '<keyword>'.`
* If keyword is empty: `Error: Please provide a keyword to search for.`

---

### List All Treatments — `list-all-treatments`
Lists all treatments.

**Format**
```
list-all-treatments
```

**Example**
```
> list-all-treatments
1. mimi: [ ] vaccine on 2025-10-10
2. snoopy: [ ] vaccine on 2025-10-11
```

**Notes**
* If no treatments: `No treatments logged.` will be displayed.

---

### List a Pet's Treatments — `list-treatments`
Lists all treatments specific to a pet.

**Format**
```
list-treatments n/PET_NAME
```

**Example**
```
> list-treatments n/mimi
mimi's treatment history:
1. [ ] vaccine on 2025-10-10
2. [ ] dental appointment on 2025-10-11

> list-treatments n/snoopy
snoopy has no logged treatments.
```

**Notes**
* If no treatments: `<PET_NAME> has no logged treatments.` will be displayed.
* If pet is not found: `Pet not found: <PET_NAME>` will be displayed.
* Pet name is not case-sensitive.

---

### Summary of completed treatments — `summary`
Displays a summary of all completed treatments within a specific date range.

**Format**
```
summary from/FROM_DATE to/TO_DATE
```
* from/ (required): start date in yyyy-MM-dd format
* to/ (required): end date in yyyy-MM-dd format
* Date range is inclusive (includes both start and end dates)

**Example**
```
> summary from/2025-10-10 to/2025-12-10
1. mimi: [X] vaccine on 2025-10-10
2. snoopy: [X] vaccine on 2025-10-11
```

**Notes**
* If no completed treatments: `No treatments found from <FROM_DATE> to <TO_DATE>' will be displayed.`

---

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

* List Pets `list-pets`
* Edit Pet `edit-pet n/OLD_NAME [nn/NEW_NAME] [s/SPECIES] [a/AGE]`
* Mark a Treatment as Done `mark n/PET_NAME i/INDEX`
* Unmark a Treatment `unmark n/PET_NAME i/INDEX`
* Group Treatments by Type `group-treatments [n/PET_NAME]`
* List All Treatments `list-all-treatments`
* List a Pet's Treatments `list-treatments n/PET_NAME`
* Completed Treatment Summary `summary from/FROM_DATE to/TO_DATE`
