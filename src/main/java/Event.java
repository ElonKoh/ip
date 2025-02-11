public class Event extends Task {
    protected String start;
    protected String end;
    private static final String TASK_TYPE = "E";

    public Event(String description, boolean isDone, String start, String end) {
        super(description, isDone);
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    @Override
    public String getTaskType() {
        return TASK_TYPE;
    }

    @Override
    public String toString() {
        String taskCheck = super.isDone() ? "X" : " ";
        return "[" + TASK_TYPE + "]-[" + taskCheck + "] " + super.getDescription() +
                "(from " + start + " to " + end + ")";
    }
}
