public class Task {
    private String description;
    private boolean isDone;
    private static final String taskType = " ";

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String getTaskType() {
        return taskType;
    }

    @Override
    public String toString() {
        String taskCheck;
        if (isDone) {
            taskCheck = "X";
        } else {
            taskCheck = " ";
        }
        return "[" + taskType + "]-[" + taskCheck + "] " + description;
    }
}
