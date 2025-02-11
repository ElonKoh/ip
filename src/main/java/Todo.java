public class Todo extends Task {
    private static final String TASK_TYPE = "T";

    public Todo(String description, Boolean isDone) {
        super(description, isDone);
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
        return "[" + TASK_TYPE + "]-[" + taskCheck + "] " + super.getDescription();
    }
}
