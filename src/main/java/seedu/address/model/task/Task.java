package seedu.address.model.task;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Person;

/**
 * This class is the parent class of tasks that make up the tasklist.
 */

public class Task {

    protected boolean isDone;
    private final TaskDescription description;
    private Comment taskComment;
    private Index personAssignedIndex;
    private String personAssignedName;
    private String personAssignedRole;
    private Score score;

    /**
     * The constructor of the Task that takes in description of the task.
     */
    public Task(TaskDescription description) {
        this.description = description;
        this.isDone = false;
        this.personAssignedIndex = null;
        this.personAssignedName = null;
        this.personAssignedRole = null;
        this.score = null;
        this.taskComment = null;
    }

    /**
     * Supplies description of the current task when requested.
     *
     * @return TaskDescription description of the task
     */
    public TaskDescription getDescription() {
        return this.description;
    }

    /**
     * Supplies description of the completeness of current task when requested.
     *
     * @return String description of the completeness of current task
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns whether task is done or not.
     *
     * @return Boolean value of whether task is done.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Sets the score of the current task.
     *
     * @param score Score to be assigned to the current task
     */
    public void setScore(Score score) {
        this.score = score;
    }

    /**
     * Gets the score of the current task.
     */
    public Score getScore() {
        return this.score;
    }

    /**
     * Leaves a comment to the current task.
     *
     * @param comment Comment by user to be assigned to the current task
     */
    public void setTaskComment(Comment comment) {
        this.taskComment = comment;
    }

    /**
     * Returns the comment of the current task.
     *
     * @return Comment value assigned to the current task
     */
    public Comment getTaskComment() {
        return this.taskComment;
    }

    /**
     * Assigns a person to the current task.
     *
     * @param personIndex Index of the person to be assigned to the current task
     */
    public void assignPerson(Index personIndex, Person personToAssign) {
        this.personAssignedIndex = personIndex;
        this.personAssignedName = personToAssign.getName().toString();
        this.personAssignedRole = personToAssign.getRole();
    }

    /**
     * Supplies the index of the person assigned to the current task when requested.
     *
     * @return Index index of the person assigned to the current task
     */
    public Index getPersonAssignedIndex() {
        return personAssignedIndex;
    }

    /**
     * Supplies the name of the person assigned to the current task when requested.
     *
     * @return String name of the person assigned to the current task
     */
    public String getPersonAssignedName() {
        return personAssignedName;
    }

    /**
     * Supplies the role of the person assigned to the current task when requested.
     * @return
     */
    public String getPersonAssignedRole() {
        return personAssignedRole;
    }

    /**
     * Changes status of current task as done by assigning isDone as true.
     */
    public String mark(Score score) {
        this.isDone = true;
        this.setScore(score);
        return "This task has been marked as completed:\n" + this + "\n";
    }

    /**
     * Changes status of current task as not done by assigning isDone as false.
     */
    public String unmark() {
        this.isDone = false;
        return "This task has been marked as uncompleted:\n" + this + "\n";
    }

    @Override
    public String toString() {
        String statusIcon = this.getStatusIcon();
        String str = "";
        str = String.format("[" + statusIcon + "] " + this.description);
        return str;
    }

    /**
     * Returns true if both tasks have the same description.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getDescription().equals(getDescription());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Task)) {
            return false;
        }
        Task checkedObj = (Task) obj;
        boolean isSameDescription = this.description.equals(checkedObj.description);

        if (isSameDescription) {
            return true;
        }

        return false;
    }


}
