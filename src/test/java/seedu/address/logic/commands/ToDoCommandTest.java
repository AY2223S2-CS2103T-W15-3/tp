package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.task.Comment;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class ToDoCommandTest {

    @Test
    public void constructor_nullDeadlineTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventCommand(null));
    }

    @Test
    public void execute_deadlineTaskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult commandResult = new ToDoCommand(validTask).execute(modelStub);

        assertEquals(String.format(EventCommand.MESSAGE_SUCCESS, validTask),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_duplicateDeadlineTask_throwsCommandException() {
        Task validTask = new TaskBuilder().build();
        ToDoCommand toDoCommand = new ToDoCommand(validTask);
        ModelStub modelStub = new ModelStubWithTask(validTask);

        assertThrows(CommandException.class,
                DeadlineCommand.MESSAGE_DUPLICATE_TASK, () -> toDoCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Task firstTask = new TaskBuilder().withTaskDescription("obtain team approval").build();
        Task secondTask = new TaskBuilder().withTaskDescription("proofread documents").build();
        ToDoCommand addFirstTaskCommand = new ToDoCommand(firstTask);
        ToDoCommand addSecondTaskCommand = new ToDoCommand(secondTask);

        // same object -> returns true
        assertTrue(addFirstTaskCommand.equals(addFirstTaskCommand));

        // same values -> returns true
        ToDoCommand addFirstTaskCommandCopy = new ToDoCommand(firstTask);
        assertTrue(addFirstTaskCommand.equals(addFirstTaskCommandCopy));

        // different types -> returns false
        assertFalse(addFirstTaskCommand.equals(1));

        // null -> returns false
        assertFalse(addFirstTaskCommand.equals(null));

        // different person -> returns false
        assertFalse(addFirstTaskCommand.equals(addSecondTaskCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commentOnTask(Comment comment, Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTask(Task target) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTaskIndex(Index taskIndex) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPersonIndex(Index personIndex) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void assignTask(Task taskToAssign, Task assignedTask, Index taskIndex) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        public void markTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void unmarkTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithTask extends ModelStub {
        private final Task task;

        ModelStubWithTask(Task task) {
            requireNonNull(task);
            this.task = task;
        }

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return this.task.isSameTask(task);
        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return tasksAdded.stream().anyMatch(task::isSameTask);
        }

        @Override
        public void addTask(Task task) {
            requireNonNull(task);
            tasksAdded.add(task);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}


