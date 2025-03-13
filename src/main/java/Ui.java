/**
 * Used to manage all output print statements, stores all standard messages,
 * indents and dividers
 */
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

    /**
     * Prints output for a tasklist when "list" command is detected
     *
     * @param taskList the tasklist to print
     */
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

    /**
     * Prints output for a NumberFormatException (missing index error)
     *
     */
    public void printNumberFormatException() {
        System.out.println(SMALL_INDENT + "Error! Did you forget to indicate which index to delete?");
    }

    /**
     * Prints output for a IndexOutOfBoundsException (non-existant task indexed)
     *
     */
    public void printIndexOutOfBoundsException() {
        System.out.println(SMALL_INDENT + "Task not found");
    }

    public void printSeparator() {
        System.out.println(LINE_DIVIDER);
    }

    /**
     * Prints output for when a new task is added
     *
     * @param newTask the task to be added.
     */
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

    /**
     * Prints output for a MissingDescriptionException
     *
     */
    public void printMissingDescriptionException() {
        System.out.println(SMALL_INDENT + "No description provided!");
    }

    /**
     * Prints output for a MissingKeywordException
     *
     * @param inputTaskType type of task.
     */
    public void printMissingKeywordException(String inputTaskType) {
        if (inputTaskType == "event") {
            System.out.println(SMALL_INDENT + "Error! Did you forget to write From when To when?");
        } else if (inputTaskType == "deadline") {
            System.out.println(SMALL_INDENT + "Error! Did you forget to write by when?");
        }
    }

    /**
     * Prints output for when a task is marked
     *
     * @param markType unmark or mark.
     * @param taskNum the task's number.
     * @param taskToMark the task to mark.
     */
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

    /**
     * Prints output for a TaskMarkingException
     *
     * @param markType unmark or mark.
     * @param taskNum the task's number.
     * @param taskToMark the task to mark.
     */
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

    /**
     * Prints output for a NullPointerException (empty tasklist)
     *
     */
    public void printNullPointerException() {
        System.out.println(BIG_INDENT + "Task not found");
        System.out.println(LINE_DIVIDER);
    }

    public void printTaskSearch() {
        System.out.println(SMALL_INDENT + "Here are the matching tasks that I found: ");
    }
}
