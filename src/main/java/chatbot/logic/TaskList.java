package chatbot.logic;

import java.util.ArrayList;
import java.util.stream.IntStream;

import chatbot.exception.InvalidIndexException;
import chatbot.task.Task;
import chatbot.task.TimeTask;

/**
 * Represents the task list, and contains methods that manipulate the task list
 * Contains an ArrayList of tasks
 */
public class TaskList {
    /** ArrayList of tasks encapsulated by TaskList */
    private ArrayList<Task> tasks;

    /** Represents the two possible orders to sort */
    public enum SortOrder {
        ASC,
        DESC
    }

    /**
     * Constructs the TaskList object
     *
     * @param tasks ArrayList of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
        if (tasks == null) {
            this.tasks = new ArrayList<>();
        }
    }

    /**
     * Gets the ArrayList of tasks encapsulated by TaskList
     *
     * @return the ArrayList of tasks
     */
    public ArrayList<Task> getList() {
        return this.tasks;
    }

    /**
     * Gets a task from the list
     *
     * @param idx The index of the task to be returned
     * @return the Task at the index specified
     * @throws InvalidIndexException Exception thrown if the index specified is invalid
     */
    public Task get(int idx) throws InvalidIndexException {
        assert this.tasks != null : "tasks should not be null";

        if (idx >= this.tasks.size() || idx < 0) {
            throw new InvalidIndexException();
        }
        return this.tasks.get(idx);
    }

    /**
     * Lists the task list out
     *
     * @return the String representation of the task list
     */
    public String listTasks() {
        assert this.tasks != null : "tasks should not be null";

        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n");

        // converted for loop into stream implementation
        IntStream.range(0, this.tasks.size())
                .forEach(idx -> sb.append(idx + 1 + ". " + this.tasks.get(idx) + "\n"));

        return sb.toString();
    }

    /**
     * Modifies a task in the task list to be either completed or incomplete
     *
     * @param idx Index of the task to be modified
     * @param state Boolean value representing whether the task is complete
     * @return The bot's response to marking a task
     * @throws InvalidIndexException Exception thrown if the index specified is invalid
     */
    public String mark(int idx, boolean state) throws InvalidIndexException {
        this.get(idx).setIsDone(state);

        StringBuilder sb = new StringBuilder();
        if (state) {
            sb.append("Nice! I've marked this task as done:\n");
        } else {
            sb.append("Ok, I've marked this task as not done yet:\n");
        }
        sb.append(this.get(idx) + "\n");

        return sb.toString();
    }

    /**
     * Removes a task from the task list
     *
     * @param idx Index of the task to be removed
     * @return The bot's response to removing a task
     * @throws InvalidIndexException Exception thrown if the index specified is invalid
     */
    public String remove(int idx) throws InvalidIndexException {
        if (idx >= this.tasks.size() || idx < 0) {
            throw new InvalidIndexException();
        }
        Task deleted = this.tasks.remove(idx);

        StringBuilder sb = new StringBuilder();
        sb.append("Got it. I've removed this task:\n");
        sb.append(deleted + "\n");
        sb.append("Now you have " + this.tasks.size() + " tasks in the list\n");

        return sb.toString();
    }

    /**
     * Adds a task to back of the task list
     *
     * @param newTask Task to be added to the task list
     * @return The bot's response to adding a task
     */
    public String add(Task newTask) {
        assert this.tasks != null : "tasks should not be null";

        this.tasks.add(newTask);

        StringBuilder sb = new StringBuilder();
        sb.append("Got it. I've added this task:\n");
        sb.append(newTask + "\n");
        sb.append("Now you have " + tasks.size() + " tasks in the list\n");

        return sb.toString();
    }

    /**
     * Finds any tasks containing the query string in the task name
     * Query is not strict, so it does not consider capitalisation
     *
     * @param query String containing the user's query
     * @return The bot's response to attempting a query
     */
    public String find(String query) {
        assert this.tasks != null : "tasks should not be null";

        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks matching your query:\n");

        // converted for loop into stream implementation
        IntStream.range(0, this.tasks.size())
                .filter(idx -> this.tasks.get(idx)
                        .getName()
                        .toLowerCase()
                        .contains(query.toLowerCase()))
                .forEach(idx -> sb.append(idx + 1 + ". " + this.tasks.get(idx) + "\n"));

        return sb.toString();
    }

    /**
     * Sorts the tasklist based on the order specified
     *
     * @param order The order that the tasklist should be sorted in
     * @return The confirmation of the sorting
     */
    public String sort(SortOrder order) {
        if (order == SortOrder.ASC) {
            this.tasks.sort((t1, t2) -> {
                if ((t1 instanceof TimeTask newT1) && (t2 instanceof TimeTask newT2)) {
                    if (newT2.getTime().isBefore(newT1.getTime())) {
                        return 1; // move t2 up in the list
                    } else if (newT1.getTime().isBefore(newT2.getTime())) {
                        return -1; // move t1 up in the list
                    } else {
                        return 0; // order can stay the same
                    }
                } else if (t1 instanceof TimeTask) { // t2 is not a TimeTask
                    return 1; // move t2 up in the list
                } else if (t2 instanceof TimeTask) {
                    return -1; // move t1 up in the list
                } else {
                    return 0; // order can stay the same
                }
            });
            return "List has been sorted in ascending order!";
        } else if (order == SortOrder.DESC) {
            this.tasks.sort((t1, t2) -> {
                if ((t1 instanceof TimeTask newT1) && (t2 instanceof TimeTask newT2)) {
                    if (newT2.getTime().isBefore(newT1.getTime())) {
                        return -1; // move t1 up the list
                    } else if (newT1.getTime().isBefore(newT2.getTime())) {
                        return 1; // move t2 up the list
                    } else {
                        return 0; // order can stay the same
                    }
                } else if (t1 instanceof TimeTask) { // t2 is not a TimeTask
                    return 1; // move t2 up in the list
                } else if (t2 instanceof TimeTask) {
                    return -1; // move t1 up in the list
                } else {
                    return 0; // order can stay the same
                }
            });
            return "List has been sorted in descending order!";
        } else {
            return "Invalid ordering!"; // this line should be unreachable
        }
    }
}
