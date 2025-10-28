# User Guide

## Introduction

{Give a product intro}

## Quick Start

- **Java**: Ensure Java 17+.
- **Run**: `java -jar cuddlecare.jar`
- **Prompt**: You should see a greeting and a prompt symbol `> `.

## Features 

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



## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

* List Pets `list-pets`
* Edit Pet `edit-pet n/OLD_NAME [nn/NEW_NAME] [s/SPECIES] [a/AGE]`
* Mark a Treatment as Done `mark n/PET_NAME i/INDEX`
* Unmark a Treatment `unmark n/PET_NAME i/INDEX`
* Group Treatments by Type `group-treatments [n/PET_NAME]`
