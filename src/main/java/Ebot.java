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

        // Program start
        String ebotSalutation = "\tEbot says: ";
        String userInput;
        Scanner in = new Scanner(System.in);
        userInput = "";
        while (!userInput.equalsIgnoreCase("bye")) {
            userInput = in.nextLine();
            if (!userInput.equalsIgnoreCase("bye")) {
                System.out.println(ebotSalutation + System.lineSeparator() + bigIndent + userInput);
                System.out.println(lineDivider);
            } else {
                System.out.println(exitMessage);
                System.out.println(lineDivider);
            }
        }
    }
}
