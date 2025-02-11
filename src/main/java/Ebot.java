import java.util.Scanner;

public class Ebot {
    private static final String LINE_DIVIDER = ("___________________________________________");
    private static final String BIG_INDENT = "\t\t";
    private static final String SMALL_INDENT = "\t";

    public static void main(String[] args) {
        String welcomeMessage = (SMALL_INDENT + "Hello! I'm Ebot" +
                System.lineSeparator() +
                SMALL_INDENT + "What can i do for you?");
        String exitMessage = (SMALL_INDENT + "Bye. Hope to see you again soon!");
        System.out.println(LINE_DIVIDER + System.lineSeparator() + welcomeMessage);
        System.out.println(LINE_DIVIDER);

        // Program start, initialize scanner for user inputs
        String userInput;
        Scanner in = new Scanner(System.in);
        userInput = "";

        // Initialize list
        int numberOfLists = TaskList.getNumberOfTaskLists();
        String taskListName = ("list") + numberOfLists;
        TaskList taskList = new TaskList(taskListName);

        while (true) {
            userInput = in.nextLine();
            // if user types "bye" command
            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println(exitMessage);
                System.out.println(LINE_DIVIDER);
                break;
                // if user types "list" command
            } else if (userInput.equalsIgnoreCase("list")) {
                handleListOutput(taskList);

                // if user types "mark" or "unmark" command
            } else if (userInput.toLowerCase().contains("mark")) {
                String unmarkCommand = "unmark";
                String markCommand = "mark";
                handleMarkingOutput(userInput, taskList, unmarkCommand, markCommand);

                // if user types any of the task types keyword command
            } else if (userInput.toLowerCase().contains("todo")
                    || userInput.toLowerCase().contains("event")
                    || userInput.toLowerCase().contains("deadline")) {
                handleTaskAdding(taskList, userInput);

                // if user types anything else
            } else {
                System.out.println(BIG_INDENT + userInput);
                System.out.println(LINE_DIVIDER);
            }
        }
    }

    private static void handleTaskAdding(TaskList taskList, String userInput) {
        String cleanedUserInput = userInput.replace("/", "");
        String taskType = cleanedUserInput.split(" ")[0];
        String taskDescription = cleanedUserInput.replace((taskType + " "), "");
        Task newTask;
        if (userInput.toLowerCase().contains("todo")) {
            newTask = new Todo(taskDescription, false);
            taskList.addEntry(newTask);
            System.out.println(SMALL_INDENT + "Got it! I added a new to-do: " + System.lineSeparator() + BIG_INDENT + newTask);
            System.out.println(LINE_DIVIDER);
        } else if (userInput.toLowerCase().contains("event")) {
            String eventFrom = userInput.substring(cleanedUserInput.lastIndexOf("from ") + 5,
                    cleanedUserInput.lastIndexOf("to"));
            String eventTo = cleanedUserInput.substring(
                    cleanedUserInput.toLowerCase().lastIndexOf(" to ") + 3);
            String eventDescription = taskDescription.substring(0, taskDescription.lastIndexOf("from"));

            newTask = new Event(eventDescription, false, eventFrom, eventTo);
            taskList.addEntry(newTask);
            System.out.println(SMALL_INDENT + "Got it! I added a new event: " + System.lineSeparator() + BIG_INDENT + newTask);
            System.out.println(LINE_DIVIDER);
        } else if (userInput.toLowerCase().contains("deadline")) {
            String deadlineDescription = taskDescription.substring(0,
                    taskDescription.lastIndexOf("by "));
            String deadlineBy = cleanedUserInput.substring(
                    cleanedUserInput.toLowerCase().lastIndexOf("by ") + 3);
            newTask = new Deadline(deadlineDescription, false, deadlineBy);
            taskList.addEntry(newTask);
            System.out.println(SMALL_INDENT + "Got it! I added a new deadline: " + System.lineSeparator() + BIG_INDENT + newTask);
            System.out.println(LINE_DIVIDER);
        }
    }

    private static void handleMarkingOutput(String userInput, TaskList taskList, String unmarkCommand, String markCommand) {
        if (userInput.toLowerCase().contains(unmarkCommand)) {
            unmarkTaskAndPrint(userInput, taskList, unmarkCommand);
            // if "mark" command received
        } else {
            markTaskAndPrint(userInput, taskList, markCommand);
        }
    }

    private static void handleListOutput(TaskList taskList) {
        // if there are no lists or only empty lists
        if (TaskList.getNumberOfTaskLists() < 1 || taskList.getNumberOfEntries() < 1) {
            System.out.println(BIG_INDENT + "no lists found");
            System.out.println(LINE_DIVIDER);
            // if there is a list to print
        } else {
            System.out.println(taskList);
            System.out.println(LINE_DIVIDER);
        }
    }

    private static void markTaskAndPrint(String userInput, TaskList taskList, String markCommand) {
        String markString = markCommand + " ";
        int markIndex = userInput.indexOf(markString);
        int taskNumPointer = (markIndex + markString.length());
        int taskNum = Character.getNumericValue(userInput.charAt(taskNumPointer));
        try {
            Task taskToMark = taskList.getTask(taskNum - 1);
            if (taskToMark.isDone()) {
                System.out.println(BIG_INDENT + "Task " + taskNum + " is already marked as completed");
                System.out.println(BIG_INDENT + taskToMark);
                System.out.println(LINE_DIVIDER);
            } else {
                taskToMark.setIsDone(true);
                System.out.println(BIG_INDENT + "Good Job! I marked task " + taskNum + " as completed");
                System.out.println(BIG_INDENT + taskToMark);
                System.out.println(LINE_DIVIDER);
            }
        } catch (NullPointerException E) {
            System.out.println(BIG_INDENT + "Task not found");
            System.out.println(LINE_DIVIDER);
        }
    }

    private static void unmarkTaskAndPrint(String userInput, TaskList taskList, String unmarkCommand) {
        String unmarkString = (unmarkCommand + " ");
        int unmarkIndex = userInput.indexOf(unmarkString);
        int taskNumPointer = (unmarkIndex + unmarkString.length());
        int taskNum = Character.getNumericValue(userInput.charAt(taskNumPointer));
        try {
            Task taskToUnmark = taskList.getTask(taskNum - 1);
            if (!taskToUnmark.isDone()) {
                System.out.println(BIG_INDENT + "Task " + taskNum + " is already marked as not done");
                System.out.println(BIG_INDENT + taskToUnmark);
                System.out.println(LINE_DIVIDER);
            } else {
                taskToUnmark.setIsDone(false);
                System.out.println(BIG_INDENT + "Got it! I marked task " + taskNum + " as not done");
                System.out.println(BIG_INDENT + taskToUnmark);
                System.out.println(LINE_DIVIDER);
            }
        } catch (NullPointerException E) {
            System.out.println(BIG_INDENT + "Task not found");
            System.out.println(LINE_DIVIDER);
        }
    }
}
