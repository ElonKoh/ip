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
        String ebotSalutation = ("\tEbot added: " + System.lineSeparator());
        String userInput;
        Scanner in = new Scanner(System.in);
        userInput = "";

        // Intialize list
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
                // if there are no lists or only empty lists
                if(TaskList.getNumberOfTaskLists() < 1 || taskList.getNumberOfEntries() < 1) {
                    System.out.println(ebotSalutation + "no lists found");
                    System.out.println(lineDivider);
                // if there is a list to print
                } else {
                    printListEntries(taskList);
                    System.out.println(lineDivider);
                }
            // if user types "mark" or "unmark"
            } else if(userInput.toLowerCase().contains("mark")) {
                // "unmark" command
                String unmarkCommand = "unmark";
                // "mark" command
                String markCommand = "mark";

                // if "unmark" command received
                if(userInput.toLowerCase().contains(unmarkCommand)) {
                    unmarkTaskAndPrint(userInput, taskList, unmarkCommand);

                // if "mark" command received
                } else {
                    markTaskAndPrint(userInput, taskList, markCommand);
                }
            // if user types anything else
            } else {
                System.out.println(ebotSalutation + bigIndent + userInput);
                System.out.println(lineDivider);
                Task newTask = new Task(userInput, false);
                taskList.AddEntry(newTask);
            }
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

    private static void printListEntries(TaskList taskList) {
        Task[] taskListEntries = taskList.getTaskList();
        for (int i = 0; i < taskList.getNumberOfEntries(); i++) {
            String taskDescription = taskListEntries[i].getDescription();
            boolean taskIsDone = taskListEntries[i].isDone();
            String taskCheck;
            if (taskIsDone) {
                taskCheck = "X";
            } else {
                taskCheck = " ";
            }
            System.out.println(bigIndent + (i+1) + ". [" + taskCheck + "] " + taskDescription);
        }
    }
}
