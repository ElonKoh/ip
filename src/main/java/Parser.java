import java.util.Scanner;

public final class Parser {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static String getUserInput() {
        return SCANNER.nextLine();
    }

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
            index = description.length();
        }
        description = description.substring(0, index);
        return description;
    }

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

    public static String[] parseEventInfo(String userInput) {
        String cleanedUserInput = userInput.toLowerCase().replace(" ", "").replace("/", "");
        String eventFrom = cleanedUserInput.substring(cleanedUserInput.lastIndexOf("from") + 4,
                cleanedUserInput.lastIndexOf("to"));
        String eventTo = cleanedUserInput.substring(
                cleanedUserInput.lastIndexOf("to") + 2);
        String[] output = new String[] {eventFrom, eventTo};
        return output;

    }

    public static String parseDeadlineInfo(String userInput) {
        String cleanedUserInput = userInput.toLowerCase().replace(" ", "").replace("/", "");
        String deadlineBy = cleanedUserInput.substring(
                cleanedUserInput.toLowerCase().lastIndexOf("by") + 2);
        return deadlineBy;
    }

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
