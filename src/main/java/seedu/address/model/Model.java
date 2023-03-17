package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Person;
import seedu.address.model.task.Comment;
import seedu.address.model.task.Task;


/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a task with the same specifications as {@code task} exists in the address book.
     */
    boolean hasTask(Task task);

    /**
     * Returns true if a task exists in the address book.
     */
    boolean hasTaskIndex(Index taskIndex);

    /**
     * Returns true if a person exists in the address book.
     */
    boolean hasPersonIndex(Index personIndex);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Deletes the given task.
     * Task must exist in the address book.
     */
    void deleteTask(Task target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Adds the given task.
     * {@code task} must not already exist in the address book.
     * @param target
     */
    void addTask(Task target);

    /**
     * Marks the given task {@code task} as done.
     * {@code task} must exist in the address book.
     */
    void markTask(Task task);

    /**
     * Unmarks the given task {@code task} as not done.
     * {@code task} must exist in the address book.
     */
    void unmarkTask(Task task);

    /**
     * Adds the given comment to the specified task.
     *  @param comment Comment by user
     * @param task specified task to receive comment*/
    void commentOnTask(Comment comment, Task task);

    // /**
    //  * Assign a task to a person.
    //  * @param taskIndex
    //  * @param personIndex
    //  */
    // void assignTask(Index taskIndex, Index personIndex);

    /**
     * Replace the task to be assigned with the assigned task.
     * @param taskToAssign
     * @param assignedTask
     * @param taskIndex
     */
    void assignTask(Task taskToAssign, Task assignedTask, Index taskIndex);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);
}
