import java.util.Scanner;

public class Ebot {


    public static void main(String[] args) {
        String lineDivider = ("___________________________________________");
        String bigIndent = "\t\t";
        String smallIndent = "\t";
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
                    System.out.println(lineDivider);
                }
            // if user types "mark" or "unmark"
            } else if(userInput.toLowerCase().contains("mark")) {
                // "unmark" command
                if(userInput.toLowerCase().contains("unmark")) {
                    String unmarkString = "unmark ";
                    int unmarkIndex = userInput.indexOf(unmarkString);
                    int taskNumPointer = (unmarkIndex + unmarkString.length());
                    int taskNum = Character.getNumericValue(userInput.charAt(taskNumPointer));
                    try {
                        Task taskToUnmark = taskList.getTask(taskNum - 1);
                        taskToUnmark.setIsDone(false);
                        System.out.println(bigIndent + "Got it! I marked task " + taskNum + " as not done");
                        System.out.println(bigIndent + "[ ] " + taskToUnmark.getDescription());
                        System.out.println(lineDivider);
                    } catch(NullPointerException E) {
                        System.out.println(bigIndent + "Task not found");
                        System.out.println(lineDivider);
                    }

                // "mark" command
                } else {
                    String markString = "mark ";
                    int markIndex = userInput.indexOf(markString);
                    int taskNumPointer = (markIndex + markString.length());
                    int taskNum = Character.getNumericValue(userInput.charAt(taskNumPointer));
                    try {
                        Task taskToMark = taskList.getTask(taskNum - 1);
                        taskToMark.setIsDone(true);
                        System.out.println(bigIndent + "Good Job! I marked task " + taskNum + " as completed");
                        System.out.println(bigIndent + "[X] " + taskToMark.getDescription());
                        System.out.println(lineDivider);
                    } catch(NullPointerException E) {
                        System.out.println(bigIndent + "Task not found");
                        System.out.println(lineDivider);
                    }
                }
            } else {
                System.out.println(ebotSalutation + bigIndent + userInput);
                System.out.println(lineDivider);
                Task newTask = new Task(userInput, false);
                taskList.AddEntry(newTask);
            }
        }
    }
}
