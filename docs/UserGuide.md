---
layout: page
title: User Guide
---

Pied Piper is a user-friendly task management application tailored for computing students. The app helps them stay organised, collaborate with peers, and prepare for real-world situations by simulating performance reviews. Key features include creating tasks, assigning tasks to team members, setting due dates, rating tasks out of 5, and providing feedback through comments. Ideal for personal projects and team collaboration, Pied Piper aims to improve productivity and equip computing students with valuable skills for their future careers.

* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `piedpiper.jar` from [here](https://github.com/AY2223S2-CS2103T-W15-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for Pied Piper.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar piedpiper.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)


1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>

* Words within `Curly Bracket {}` are must have parameters.

* Extraneous parameters for commands that do not take in parameters (such as `view`) will be ignored.<br>
  e.g. if the command specifies `view 123`, it will be interpreted as `view`.

</div>

### Creating a task: `todo`

Creates a new task

Format: `todo task/{TASK_NAME}`

Example:
* `todo task/Edit User Guide`


### Creating a task with a deadline: `deadline`

Creates a new deadline task

Format: `deadline task/{TASK_NAME} by/{DD/MM/YYYY}`

Example:
* `deadline task/Complete Assignment by/12/09/2021`


### Creating a task that is an event: `event`

Creates a new event task

Format: `event task/{TASK_NAME} from/{DD/MM/YYYY} to/{DD/MM/YYYY}`

Example:
* `event task/Manage Open House from/11/12/2023 to/16/12/2023`


### Commenting on a task: `comment`

Adds a comment to a task

Format: `comment t/{TASK_ID} c/{COMMENTS} `

Example:
* `comment t/1 c/task was done well and on time`


### Editing a person: `edit`

Edits the properties of an existing person in the persons list

Format: `edit INDEX [n/{NAME}] [p/{PHONE}] [e/{EMAIL}] [a/{ADDRESS}] [r/{ROLE}]`

Example:

* `edit 1 p/98761234 e/john@nus.com`

### Editing a task: `edittask`

Edits the properties of an existing task in the task list

Format: `edittask INDEX type/{TASK_TYPE} [task/{TASK_DESCRIPTION}] [{DATE}]`

Note: 
* To denote todo, deadline and event tasks, parameters for `TASK_TYPE` are `T`, `D` and `E` respectively.
* When editing a task to change types, the {DATE} parameters are as follows:
  * `deadline`: `by/{DD/MM/YYYY}`
  * `event`: `from/{DD/MM/YYYY} to/{DD/MM/YYYY}`


### Assigning task to member: `assign`

Assign a task to a person

Format: `assign  t/{TASK_ID} i/{MEMBER_ID}`

Examples:
*  `assign t/1 i/3`


### Marking a task: `mark`

Marks a task as completed and give score to the marked task

Format: `mark t/{TASK_ID} s/{PERFORMANCE_SCORE}`

Examples:
* `mark t/1 s/4`


### Unmarking a task: `unmark`

Unmarks a task as not completed

Format: `unmark t/{TASK_ID}`

Examples:
* `unmark t/1`


### Deleting a person: `delete`

Deletes an existing person

Format: `delete {MEMBER_ID}`

Examples:
* `delete 1`


### Deleting a task: `deletetask`

Deletes an existing task

Format: `deletetask {TASK_ID}`

Examples:
* `deletetask 2`


### Viewing tasks: `view`

Shows all tasks

Format: `view`


### Viewing statistics: `review`

Shows task and score statistics of all persons in the persons list

Format: `review`

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Pied Piper home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Assign** | `assign  t/{TASK_ID} n/{MEMBER_NAME}`<br> e.g., `assign t/1 n/John Doe`
**Comment** | `comment t/{TASK_ID} c/{COMMENTS} ` <br> e.g., `comment t/1 c/task was done well and on time`
**Deadline** | `deadline task/{TASK_NAME}  by/{DD/MM/YYYY}` <br> e.g., `deadline task/Complete Assignment by/12/09/2021`
**Delete person** | `delete {MEMBER_ID}`<br> e.g., `delete 1`
**Delete task** | `deletetask  {TASK_ID}`<br> e.g., `deletetask 2`
**Edit person** | `edit INDEX [n/{NAME}] [p/{PHONE}] [e/{EMAIL}] [a/{ADDRESS}] [r/{ROLE}]` <br> e.g., `edit 1 p/98761234 e/john@nus.com`
**Edit task** | `edittask INDEX type/{TASK_TYPE} [task/{TASK_DESCRIPTION}] [{DATE}]` <br> e.g., `edittask 1 type/T`
**Event** | `event task/{TASK_NAME} from/{DD/MM/YYYY} to/{DD/MM/YYYY}` <br> e.g., `event task/Manage Open House from/11/12/2023 to 16/12/2023`
**List tasks** | `list`
**Mark** | `mark t/{TASK_ID} s/{PERFORMANCE_SCORE}` <br> e.g., `mark t/1 s/4`
**Todo** | `todo task/{TASK_NAME}` <br> e.g., `todo task/Edit User Guide`
**Role** | `role n/{MEMBER_NAME} r/{ROLE}` <br> e.g., `role n/John Doe r/Leader`
**Unmark** | `unmark t/{TASK_ID}` <br> e.g., `unmark t/1`
**View persons** | `view`
**View statistics** | `review`
