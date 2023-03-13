package seedu.address.testutil;

import seedu.address.model.task.Date;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDescription;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_DESCRIPTION = "Do assignments";
    public static final String DEFAULT_DATE = "01/01/2020";
    public static final Date DEFAULT_DEADLINE = new Date(DEFAULT_DATE);

    private TaskDescription description;

    /**
     * Creates a {@code TaskBuilder} with the default details.
     */
    public TaskBuilder() {
        description = new TaskDescription(DEFAULT_DESCRIPTION);
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        description = taskToCopy.getDescription();
    }

    /**
     * Sets the {@code TaskDescription} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskDescription(String description) {
        this.description = new TaskDescription(description);
        return this;
    }

    public Task build() {
        return new Task(description, DEFAULT_DEADLINE);
    }

}
