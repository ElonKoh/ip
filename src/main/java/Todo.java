public class Todo extends Task {
    private static final String taskType = "T";

    public Todo(String description, Boolean isDone) {
        super(description, isDone);
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
