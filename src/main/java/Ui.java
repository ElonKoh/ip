public class Ui {
    private static final String LINE_DIVIDER = ("___________________________________________");
    private static final String BIG_INDENT = "\t\t";
    private static final String SMALL_INDENT = "\t";
    private static final String welcomeMessage = (SMALL_INDENT + "Hello! I'm Ebot" +
            System.lineSeparator() +
            SMALL_INDENT + "What can i do for you?");
    private static final String exitMessage = (SMALL_INDENT + "Bye. Hope to see you again soon!");

    public Ui() {

    }

    public void printWelcome() {
        System.out.println(LINE_DIVIDER + System.lineSeparator() + welcomeMessage);
        System.out.println(LINE_DIVIDER);
    }

    public void printExit() {
        System.out.println(exitMessage);
        System.out.println(LINE_DIVIDER);
    }

    public void echoUserInput(String userInput) {
        System.out.println(SMALL_INDENT + userInput);
        System.out.println(LINE_DIVIDER);
    }

    public void printDeleteTask(int deleteIndex, TaskList taskList) {
        System.out.println(SMALL_INDENT + "Got it! I deleted task " + (deleteIndex + 1));
        System.out.println(taskList);
    }

    public void handleListOutput(TaskList taskList) {
        // if there are no lists or only empty lists
        if (TaskList.getNumberOfTaskLists() < 1 || taskList.getNumberOfEntries() < 1) {
            System.out.println(SMALL_INDENT + "no lists found");
            System.out.println(LINE_DIVIDER);
            // if there is a list to print
        } else {
            System.out.println(taskList);
            System.out.println(LINE_DIVIDER);
        }
    }

    public void printNumberFormatException() {
        System.out.println(SMALL_INDENT + "Error! Did you forget to indicate which index to delete?");
    }

    public void printIndexOutOfBoundsException() {
        System.out.println(SMALL_INDENT + "Task not found");
    }

    public void printSeparator() {
        System.out.println(LINE_DIVIDER);
    }

    public void printAddTask(Task newTask) {
        String newTaskType = newTask.getTaskType();
        if (newTaskType == "T") {
            System.out.println(SMALL_INDENT + "Got it! I added a new to-do: " + System.lineSeparator() + BIG_INDENT + newTask);
        } else if (newTaskType == "D") {
            System.out.println(SMALL_INDENT + "Got it! I added a new deadline: " + System.lineSeparator() + BIG_INDENT + newTask);
        } else if (newTaskType == "E") {
            System.out.println(SMALL_INDENT + "Got it! I added a new event: " + System.lineSeparator() + BIG_INDENT + newTask);
        }
    }

    public void printMissingDescriptionException() {
        System.out.println(SMALL_INDENT + "No description provided!");
    }

    public void printMissingKeywordException(String inputTaskType) {
        if (inputTaskType == "event") {
            System.out.println(SMALL_INDENT + "Error! Did you forget to write From when To when?");
        } else if (inputTaskType == "deadline") {
            System.out.println(SMALL_INDENT + "Error! Did you forget to write by when?");
        }
    }

    public void printMarkTask(String markType, int taskNum, Task taskToMark) {
        if (markType == "unmark") {
            System.out.println(SMALL_INDENT + "Got it! I marked task " + taskNum + " as not done");
            System.out.println(BIG_INDENT + taskToMark);
            System.out.println(LINE_DIVIDER);
        } else {
            System.out.println(SMALL_INDENT + "Good Job! I marked task " + taskNum + " as completed");
            System.out.println(BIG_INDENT + taskToMark);
            System.out.println(LINE_DIVIDER);
        }
    }

    public void printTaskMarkingException(String markType, int taskNum, Task taskToMark) {
        if (markType == "unmark") {
            System.out.println(SMALL_INDENT + "Task " + taskNum + " is already marked as not done");
            System.out.println(BIG_INDENT + taskToMark);
            System.out.println(LINE_DIVIDER);
        } else {
            System.out.println(SMALL_INDENT + "Task " + taskNum + " is already marked as completed");
            System.out.println(BIG_INDENT + taskToMark);
            System.out.println(LINE_DIVIDER);
        }
    }

    public void printNullPointerException() {
        System.out.println(SMALL_INDENT + "Task not found");
        System.out.println(LINE_DIVIDER);
    }

    public void printTaskSearch() {
        System.out.println(SMALL_INDENT + "Here are the matching tasks that I found: ");
    }
}
