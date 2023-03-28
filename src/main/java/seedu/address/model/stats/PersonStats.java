package seedu.address.model.stats;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.person.Person;

/**
 * Represents statistics of a Person in the address book.
 */
public class PersonStats {
    private Person person;
    private int tasksAssigned;
    private int tasksCompleted;
    private double averageScore;

    public PersonStats(Person person, int tasksAssigned, int tasksCompleted, double averageScore) {
        requireAllNonNull(person, tasksAssigned, tasksCompleted, averageScore);
        this.person = person;
        this.tasksAssigned = tasksAssigned;
        this.tasksCompleted = tasksCompleted;
        this.averageScore = averageScore;
    }

    public Person getPerson() {
        return person;
    }

    public int getTasksAssigned() {
        return tasksAssigned;
    }

    public int getTasksCompleted() {
        return tasksCompleted;
    }

    public double getAverageScore() {
        return averageScore;
    }
}
