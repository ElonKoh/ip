public class Deadline extends Task {
    protected String doBy;
    private static final String taskType = "D";

    public Deadline(String description, boolean isDone, String doBy) {
        super(description, isDone);
        this.doBy = doBy;
    }

    public String getDoBy() {
        return doBy;
    }

    @Override
    public String getTaskType() {
        return taskType;
    }

    @Override
    public String toString() {
        String taskCheck;
        if (super.isDone()) {
            taskCheck = "X";
        } else {
            taskCheck = " ";
        }
        return "[" + taskType + "]-[" + taskCheck + "] " + super.getDescription();
    }

}
