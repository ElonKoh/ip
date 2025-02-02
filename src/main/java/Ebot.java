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
        String taskListName = ("list") + String.valueOf(numberOfLists);
        TaskList taskList = new TaskList(taskListName);

        while (!userInput.equalsIgnoreCase("bye")) {
            userInput = in.nextLine();
            // if not bye
            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println(exitMessage);
                System.out.println(lineDivider);
            } else if(userInput.equalsIgnoreCase("list")) {
                if(TaskList.getNumberOfTaskLists() < 1 || taskList.getNumberOfEntries() < 1) {
                    System.out.println(ebotSalutation + "no lists found");
                } else {
                    String[] taskListEntries = taskList.getTaskList();
                    for (int i = 0; i < taskList.getNumberOfEntries(); i++) {
                        System.out.println(bigIndent + (i+1) + ". " + taskListEntries[i]);
                    }
                }
            } else {
                System.out.println(ebotSalutation + bigIndent + userInput);
                System.out.println(lineDivider);
                taskList.AddEntry(userInput);
            }
        }
    }
}
