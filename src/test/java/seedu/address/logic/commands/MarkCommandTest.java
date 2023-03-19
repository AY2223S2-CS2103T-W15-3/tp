package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalDeadlineTasks.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Score;
import seedu.address.model.task.Task;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code MarkCommand}.
 */
public class MarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() throws Exception {
        Task taskToMark = model.getFilteredTaskList().get(INDEX_FIRST_PERSON.getZeroBased());
        Score score = new Score(4);
        CommandResult commandResult = new MarkCommand(INDEX_FIRST_PERSON, score).execute(model);

        assertEquals(String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, taskToMark),
                commandResult.getFeedbackToUser());
        assertTrue(taskToMark.isDone());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        Score score = new Score(4);
        MarkCommand markCommand = new MarkCommand(outOfBoundIndex, score);

        assertCommandFailure(markCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

}
