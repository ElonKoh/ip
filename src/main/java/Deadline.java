/**
 * An extension of Task object, a <code>Deadline</code> object stores
 * a String deadline <code>by</code> and <code>TASK_TYPE</code> "D"
 */
public class Deadline extends Task {
    protected String by;
    private static final String TASK_TYPE = "D";

    public Deadline(String description, boolean isDone, String by) {
        super(description, isDone);
        this.by = by;
    }

    public String getBy() {
        return by;
    }

    @Override
    public String getTaskType() {
        return TASK_TYPE;
    }

    @Override
    public String toString() {
        String taskCheck;
        if (super.isDone()) {
            taskCheck = "X";
        } else {
            taskCheck = " ";
        }
        return "[" + TASK_TYPE + "]-[" + taskCheck + "] " + super.getDescription() + " (by: " + by + ")";
    }

}
