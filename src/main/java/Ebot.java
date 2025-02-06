import java.util.Scanner;

public class Ebot {
    private static final String lineDivider = ("___________________________________________");
    private static final String bigIndent = "\t\t";
    private static final String smallIndent = "\t";

    public static void main(String[] args) {
        String welcomeMessage = (smallIndent + "Hello! I'm Ebot" +
                System.lineSeparator() +
                smallIndent + "What can i do for you?");
        String exitMessage = (smallIndent + "Bye. Hope to see you again soon!");
        System.out.println(lineDivider + System.lineSeparator() + welcomeMessage);
        System.out.println(lineDivider);

        // Program start, initialize scanner for user inputs
        String userInput;
        Scanner in = new Scanner(System.in);
        userInput = "";

        // Initialize list
        int numberOfLists = TaskList.getNumberOfTaskLists();
        String taskListName = ("list") + numberOfLists;
        TaskList taskList = new TaskList(taskListName);

        while (!userInput.equalsIgnoreCase("bye")) {
            userInput = in.nextLine();
            // if user types "bye" command
            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println(exitMessage);
                System.out.println(lineDivider);

            // if user types "list" command
            } else if(userInput.equalsIgnoreCase("list")) {
                handleListOutput(taskList);

            // if user types "mark" or "unmark" command
            } else if(userInput.toLowerCase().contains("mark")) {
                String unmarkCommand = "unmark";
                String markCommand = "mark";
                handleMarkingOutput(userInput, taskList, unmarkCommand, markCommand);

            // if user types any of the task types keyword command
            } else if(userInput.toLowerCase().contains("todo")
                    || userInput.toLowerCase().contains("event")
                    || userInput.toLowerCase().contains("deadline")) {
                handleTaskAdding(taskList, userInput);

                // if user types anything else
            } else {
                System.out.println(bigIndent + userInput);
                System.out.println(lineDivider);
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
            taskList.AddEntry(newTask);
            System.out.println(smallIndent + "Got it! I added a new to-do: " + System.lineSeparator() + bigIndent + newTask);
            System.out.println(lineDivider);
        } else if(userInput.toLowerCase().contains("event")) {
            String eventTo = userInput.substring(cleanedUserInput.lastIndexOf("from ") + 5,
                    cleanedUserInput.lastIndexOf("to"));
            String eventFrom = cleanedUserInput.substring(
                    cleanedUserInput.toLowerCase().lastIndexOf("to "));
            String eventDescription = taskDescription.substring(0, taskDescription.lastIndexOf("from"));

            newTask = new Event(eventDescription, false, eventFrom, eventTo);
            taskList.AddEntry(newTask);
            System.out.println(smallIndent + "Got it! I added a new event: " + System.lineSeparator() + bigIndent + newTask);
            System.out.println(lineDivider);
        } else if(userInput.toLowerCase().contains("deadline")) {
            String deadlineDescription = taskDescription.substring(0,
                    taskDescription.lastIndexOf("by"));
            String deadlineBy = cleanedUserInput.substring(
                    cleanedUserInput.toLowerCase().lastIndexOf("by "));
            newTask = new Deadline(deadlineDescription, false, deadlineBy);
            taskList.AddEntry(newTask);
            System.out.println(smallIndent + "Got it! I added a new deadline: " + System.lineSeparator() + bigIndent + newTask);
            System.out.println(lineDivider);
        }
    }

    private static void handleMarkingOutput(String userInput, TaskList taskList, String unmarkCommand, String markCommand) {
        if(userInput.toLowerCase().contains(unmarkCommand)) {
            unmarkTaskAndPrint(userInput, taskList, unmarkCommand);
        // if "mark" command received
        } else {
            markTaskAndPrint(userInput, taskList, markCommand);
        }
    }

    private static void handleListOutput(TaskList taskList) {
        // if there are no lists or only empty lists
        if(TaskList.getNumberOfTaskLists() < 1 || taskList.getNumberOfEntries() < 1) {
            System.out.println(bigIndent + "no lists found");
            System.out.println(lineDivider);
        // if there is a list to print
        } else {
            System.out.println(bigIndent + taskList);
            System.out.println(lineDivider);
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
                System.out.println(bigIndent + "Task " + taskNum + " is already marked as completed");
                System.out.println(bigIndent + "[X] " + taskToMark.getDescription());
                System.out.println(lineDivider);
            } else {
                taskToMark.setIsDone(true);
                System.out.println(bigIndent + "Good Job! I marked task " + taskNum + " as completed");
                System.out.println(bigIndent + "[X] " + taskToMark.getDescription());
                System.out.println(lineDivider);
            }
        } catch(NullPointerException E) {
            System.out.println(bigIndent + "Task not found");
            System.out.println(lineDivider);
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
                System.out.println(bigIndent + "Task " + taskNum + " is already marked as not done");
                System.out.println(bigIndent + "[ ] " + taskToUnmark.getDescription());
                System.out.println(lineDivider);
            } else {
                taskToUnmark.setIsDone(false);
                System.out.println(bigIndent + "Got it! I marked task " + taskNum + " as not done");
                System.out.println(bigIndent + "[ ] " + taskToUnmark.getDescription());
                System.out.println(lineDivider);
            }
        } catch(NullPointerException E) {
            System.out.println(bigIndent + "Task not found");
            System.out.println(lineDivider);
        }
    }
}
