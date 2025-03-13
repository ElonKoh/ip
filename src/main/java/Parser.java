import java.util.Scanner;

/**
 * Used to store and manage the <code>SCANNER</code> object for user inputs,
 *
 */
public final class Parser {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static String getUserInput() {
        return SCANNER.nextLine();
    }

    /**
     * Returns the task type based on the users input
     * If the task type is unknown, "unknown" is returned.
     *
     * @param userInput raw userinput.
     * @return String task type.
     */
    public static String parseInputTaskType(String userInput) {
        String cleanedUserInput = userInput.toLowerCase().replace(" ", "").replace("/", "");
        if (cleanedUserInput.toLowerCase().contains("todo")) {
            return "todo";
        } else if (cleanedUserInput.toLowerCase().contains("event")) {
            return "event";
        } else if (cleanedUserInput.toLowerCase().contains("deadline")) {
            return "deadline";
        } else {
            return "unknown";
        }
    }

    /**
     * Returns the task description based on userInput
     *
     * @param userInput raw userinput.
     * @return String task description.
     */
    public static String parseInputTaskDesc(String userInput) {
        String cleanedUserInput = userInput.toLowerCase().replace(" ", "").replace("/", "");
        String taskType = parseInputTaskType(userInput);
        String description = description = cleanedUserInput.replace(("todo"), "").replace("deadline", "").replace("event", "");
        int index;
        if (taskType == "event") {
            index = description.indexOf("from");
        } else if (taskType == "deadline") {
            index = description.indexOf("by");
        } else {
            index = description.length() - 1;
        }
        description = description.substring(0, index);
        return description;
    }

    /**
     * A boolean checker for if a missing keyword is detected in the user input
     *
     * @param userInput raw userinput.
     * @return Boolean missing keyword.
     */
    public static Boolean isMissingKeyword(String userInput) {
        String cleanedUserInput = userInput.toLowerCase().replace(" ", "").replace("/", "");
        String taskType = parseInputTaskType(userInput);
        Boolean output = false;
        if (taskType == "event") {
            if (!cleanedUserInput.contains("to") || !cleanedUserInput.contains("from")) {
                output = true;
            }
        } else if (taskType == "deadline") {
            if (!cleanedUserInput.contains("by")) {
                output = true;
            }
        }
        return output;
    }

    /**
     * Returns the event information <code>eventFrom</code> and <code>eventTo</code>
     * based on user input
     *
     * @param userInput raw userinput.
     * @return a String array containing <code>{eventFrom, eventTo}</code> in that order.
     */
    public static String[] parseEventInfo(String userInput) {
        String cleanedUserInput = userInput.toLowerCase().replace(" ", "").replace("/", "");
        String eventFrom = cleanedUserInput.substring(cleanedUserInput.lastIndexOf("from") + 4,
                cleanedUserInput.lastIndexOf("to"));
        String eventTo = cleanedUserInput.substring(
                cleanedUserInput.lastIndexOf("to") + 2);
        String[] output = new String[] {eventFrom, eventTo};
        return output;

    }

    /**
     * Returns the deadline information <code>deadlineBy</code>
     * based on user input
     *
     * @param userInput raw userinput.
     * @return a String <code>deadlineBy</code>.
     */
    public static String parseDeadlineInfo(String userInput) {
        String cleanedUserInput = userInput.toLowerCase().replace(" ", "").replace("/", "");
        String deadlineBy = cleanedUserInput.substring(
                cleanedUserInput.toLowerCase().lastIndexOf("by") + 2);
        return deadlineBy;
    }

    /**
     * A boolean checker for if missing information is detected in the user input
     *
     * @param userInput raw userinput.
     * @return Boolean missing information.
     */
    public static Boolean isMissingKeyInformation(String userInput) {
        String cleanedUserInput = userInput.toLowerCase().replace(" ", "").replace("/", "");
        String taskType = parseInputTaskType(userInput);
        Boolean output = false;
        if (taskType == "event") {
            String eventFrom = parseEventInfo(userInput)[0];
            String eventTo = parseEventInfo(userInput)[1];
            if (eventFrom.length() == 0 || eventTo.length() == 0) {
                output = true;
            }
        } else if (taskType == "deadline") {
            String deadlineBy = parseDeadlineInfo(userInput);
            if (deadlineBy.length() == 0) {
                output = true;
            }
        }
        return output;
    }
}
