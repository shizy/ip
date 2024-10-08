package chatbot.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents the concept of a task that the user adds into his todolist
 * task is implemented as an abstract class to prevent instantiation
 */
public abstract class Task {
    /** Name of task */
    private String name;
    /** Boolean variable that represents if the task is completed */
    private boolean isDone;

    /**
     * Constructs the Task object
     *
     * @param name Name of the task
     * @param isDone Boolean value representing whether the task has been marked as done
     */
    public Task(String name, boolean isDone) {
        assert name != null : "name should not be null";
        assert !name.isEmpty() : "name should not be an empty string";

        this.name = name;
        this.isDone = isDone;
    }

    /**
     * Formats a LocalDateTime object in the
     * desired format of LLL dd yyyy HH:mm
     *
     * @param date the LocalDateTime object to be formatted
     * @return the String representation of the LocalDateTime object
     */
    public static String formatDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLL dd yyyy HH:mm");
        return formatter.format(date);
    }

    /**
     * Sets the state of the task, representing whether the task is marked as done
     *
     * @param isDone Boolean value representing whether the task has been marked as done
     */
    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Displays the status and name of the task
     *
     * @return String representation of the abstract task
     */
    @Override
    public String toString() {
        char status = this.isDone ? 'X' : ' ';
        String str = String.format("[%c] %s", status, this.name);
        return str;
    }

    /**
     * Gets the name of the Task
     *
     * @return A string of the task's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Encodes the task into a string, to be written to a text file
     *
     * @return String encoding of the task
     */
    public String encode() {
        return (this.isDone ? "1" : "0") + "|" + this.name;
    }
}
