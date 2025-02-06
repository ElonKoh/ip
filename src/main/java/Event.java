public class Event extends Task {
    protected String startBy;
    protected String endBy;
    private static final String taskType = "E";

    public Event(String description, boolean isDone, String startBy, String endBy) {
        super(description, isDone);
        this.startBy = startBy;
        this.endBy = endBy;
    }

    public String getStartBy() {
        return startBy;
    }

    public String getEndBy() {
        return endBy;
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
