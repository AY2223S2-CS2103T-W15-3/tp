---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------
## **Introduction**

Pied Piper is a desktop application for managing projects with a significant level of complexity. It functions
via a Command-Line Interface (CLI) that allows more efficient management of events.

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/se-edu/addressbook-level3/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** has two classes called [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.


**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

How the `Logic` component works:
1. When `Logic` is called upon to execute a command, it uses the `AddressBookParser` class to parse the user command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to add a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores all `Person` objects (which are contained in a `UniquePersonList` object).
* stores all `Task` objects (which are contained in a `UniqueTaskList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores the currently 'selected' `Task` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Task>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in Pied Piper, which `Person` references. This allows `Pied Piper` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in json format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Add Task feature

#### Implementation

The implementation of the add task function is facilitated by `LogicManager`. It takes user input as 2 arguments, the command word being "todo". The second argument is a description of the task.

The format is as follows:
- `todo task/{Task_Description}`

Given below is an example scenario and how the delete function woks at each step.

Step 1. The user executes `todo task/organise pantry` command to add the task in Pied Piper. The command is read by `LogicManager`, which parses the user's input into a `commandText`.

Step 2. `LogicManager` then calls `parseCommand` on the commandText in `AddressBookParser`.

Step 3. `AddressBookParser` then uses `Matcher` to group the commandText into `commandWord` and `arguments`.

Step 4. A `ToDoCommandParser` is created, which generates a new `toAdd:Task` and a new `ToDoCommand`.

Step 5. `LogicManager` then calls `execute` in `ToDoCommand`, which carries out the addition if the task is valid. It then returns a `CommandResult` to be displayed to the user to acknowledge whether the addition has taken place.

**Note:** The command can only work if a task with the same description is not already present in Pied Piper.

The following sequence diagram shows how the addition operation works:

<img src="images/ToDoSequenceDiagramUML.png"/>

### Delete/deletetask feature

#### Implementation

The implementation of the delete/deletetask function is facilitated by `LogicManager`. It takes user input as 2 arguments, the command word being either `delete` or `deletetask`, depending on whether the user wants to delete a person or task respectively. The second argument is an index, denoting the index of the person or task the user wishes to delete.

The format is as follows:
- `delete {Person_ID}`
- `deletetask {TASK_ID}`

Given below is an example scenario and how the delete function woks at each step.

Step 1. The user executes `delete 1` command to delete the 5th task in Pied Piper. The command is read by `LogicManager`, which parses the user's input into a `commandText`.

Step 2. `LogicManager` then calls `parseCommand` on the commandText in `AddressBookParser`.

Step 3. `AddressBookParser` then uses `Matcher` to group the commandText into `commandWord` and `arguments`.

Step 4. A `DeleteCommandParser` is created, which generated a new `DeleteCommand`.

Step 5. `LogicManager` then calls `execute` in `DeleteCommand`, which carries out the deletion if the task exists. It then returns a `CommandResult` to be displayed to the user to acknowledge whether the deletion has taken place.

**Note:** The command can only work if there is at least 1 person or task present in Pied Piper, and the specified index must not be greater than the index of the last item on the list.

**Note:** The same sequence applies to the deletion of tasks, but the following classes are different:
1. `DeleteCommandParser` &rarr; `DeleteTaskCommandParser`
2. `DeleteCommand` &rarr; `DeleteTaskCommand`

The following sequence diagram shows how the delete operation works:

<img src="images/DeleteCommandUML.png"/>


#### Proposed Implementation
#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

- NUS Computing Students
- has a need to manage a project with significant complexity
- prefer desktop apps over other types
- can type fast
- prefers typing to mouse interactions
- is reasonably comfortable using CLI apps

**Value proposition**:

- Adding of various tasks with descriptions.
- Get an overview via the List view so that you can find the event that you need easily.

### User Stories

**Priorities**: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​ | I want to …​                          | So that I can…​                                                     |
| -------- | ---- |---------------------------------------|---------------------------------------------------------------------|
| `* * *`  | user | create tasks                          | keep track of the tasks I need to accomplish                        |
| `* * *`  | user | delete tasks                          | remove tasks that are outdated or specified incorrectly             |
| `* * *`  | user | view each team member's tasks         | have a clear and easily understandable list of what they have to do |
| `* * *`  | user | assign tasks                          | keep track of who is responsible for which task                     |
| `* * *`  | user | assign roles                          | remember the responsibilities of each member                        |
| `* * *`  | user | create tasks with a deadline          | see the due date of the task easily                                 |
| `* * *`  | user | create tasks that are events          | keep track of events we conduct as a group                          |
| `* * *`  | user | comment on tasks                      | record my feedback for each accomplished task                       |
| `* * *`  | user | mark a task as completed               | have a clear view of what tasks are accomplished                    |
| `* * *`  | user | unmark a task as uncompleted          | undo any possible false marking of tasks                            |
| `* * *`  | user | assign scores for each completed task | better evaluate overall performance of teammates                    |

### Use cases

(For all use cases below, the **System** is `Pied Piper` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add a person**

**MSS**

1.  User requests to list persons
2.  Pied Piper shows a list of persons
3.  User requests to delete a specific person in the list
4.  Pied Piper deletes the person

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. Pied Piper shows an error message.

      Use case resumes at step 2.


**Use case: Create a task**

**MSS**

1. User requests to add a specific task in the list.
2. Pied Piper adds the task.

    Use case ends.

**Extensions**

* 1a. The task already exists.

  Use case ends.

* 1a. The given specifications are invalid.

    * 1a1. Pied Piper shows an error message.

      Use case resumes at step 2.

**Use case: Delete a task**

**MSS**

1. User requests to delete a specified task in the list.
2. Pied Piper removes the task.

    Use case ends

**Extensions**

* 1a. The list is empty.

    Use case ends

* 1a. The given index is invalid.
  * 1a1. Pied Piper shows an error message.

    Use case resumes at step 2.

**Use Case: view tasks**


**MSS**
1. User requests to view all tasks
2. Pied Piper displays all existing tasks as a numbered list

    Use case ends

**Extensions**
* 1a. No existing tasks
  * 1a1. Display message to user that there are no existing tasks

    Use case ends

**Use case: mark a task**

**MSS**

1. User requests to mark a specified task in the list with a score.
2. Pied Piper marks the task with a valid score.

    Use case ends

**Extensions**

* 1a. The task is not assigned.

    Use case ends

* 1a. The given index is invalid.
  * 1a1. Pied Piper shows an error message.

    Use case resumes at step 1.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The response to any use action should become visible within 2 seconds.
5.  The user interface should be intuitive enough for users who are not IT-savvy.



### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_

### Clearing people data

1. Clearing data of all people while list of all people is being shown
    1. Prerequisites: List all persons using the `list` command.

    1. Test case: `clear`<br>
       Expected: Every person is removed. Status message is displayed.

### Editing a person

1. Editing details of person
    1. Prerequisites: List all persons using the `list` command. Multiple persons on the list. 

    1. Test case: `edit 1 n/James Lee e/jameslee@example.com`<br>
       Expected: Person name and email is updated. Status message is shown.

    1. Test case: `edit 0 n/John Doe p/22224444`<br>
       Expected: No person is edited. Error details are shown in the status message.
