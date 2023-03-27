package seedu.address.ui;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.Task;

/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label taskDesc;
    @FXML
    private Label taskDeadline;
    @FXML
    private Label id;
    @FXML
    private Label personAssigned;
    @FXML
    private Label personRole;
    @FXML
    private Label isDone;
    @FXML
    private Circle circle1;
    @FXML
    private Circle circle2;
    @FXML
    private Circle circle3;
    @FXML
    private Circle circle4;
    @FXML
    private Circle circle5;

    /**
     * Creates a {@code TaskCode} with the given {@code Task} and index to display.
     */
    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        taskDesc.setText(task.getDescription().toString());
        if (task.isDone()) {
            isDone.setText("Completed");
            int taskScore = task.getScore().getValue();
            setScore(taskScore);
        } else {
            isDone.setText("Not done");
            resetScore();
        }
        if (task.getPersonAssignedName() == null) {
            personAssigned.setText("Not Assigned");
        } else {
            personAssigned.setText(task.getPersonAssignedName());
        }
        if (task instanceof DeadlineTask) {
            taskDeadline.setText(((DeadlineTask) task).getDate().toString());
        } else {
            taskDeadline.setText("No deadline");
        }

        if (task.getPersonAssignedRole() == null) {
            personRole.setText("None");
        } else {
            personRole.setText(task.getPersonAssignedRole());
        }
    }

    private void resetScore() {
        Paint white = Color.WHITE;
        circle1.setFill(white);
        circle2.setFill(white);
        circle3.setFill(white);
        circle4.setFill(white);
        circle5.setFill(white);
    }

    private void setScore(int score) {
        assert(score >= 0 && score <= 5);
        Paint gold = Color.GOLD;
        resetScore();
        if (score >= 1) {
            circle1.setFill(gold);
        }
        if (score >= 2) {
            circle2.setFill(gold);
        }
        if (score >= 3) {
            circle3.setFill(gold);
        }
        if (score >= 4) {
            circle4.setFill(gold);
        }
        if (score >= 5) {
            circle5.setFill(gold);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
