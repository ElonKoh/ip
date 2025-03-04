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
            } else if (userInput.toLowerCase().contains("list")) {
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

                // if user types "delete" command
            } else if (userInput.toLowerCase().contains("delete")) {
                handleTaskDeletion(taskList, userInput);

                // if user types anything else
            } else {
                System.out.println(BIG_INDENT + userInput);
                System.out.println(LINE_DIVIDER);
            }
        }
    }

    private static void handleTaskDeletion(TaskList taskList, String userInput) {
        try {
            Integer deleteIndex = Integer.valueOf(userInput.toLowerCase().replace("delete", "").replace(" ", "")) - 1;
            taskList.deleteEntry(deleteIndex);
            System.out.println(SMALL_INDENT + "Got it! I deleted task " + (deleteIndex + 1));
            System.out.println(taskList);
        } catch (NumberFormatException E) {
            System.out.println(SMALL_INDENT + "Error! Did you forget to indicate which index to delete?");
        } catch (IndexOutOfBoundsException E) {
            System.out.println(SMALL_INDENT + "Task not found");
        } finally {
            System.out.println(LINE_DIVIDER);
        }
    }

    private static void handleTaskAdding(TaskList taskList, String userInput) {
        String cleanedUserInput = userInput.toLowerCase().replace(" ", "").replace("/", "");
        String taskDescription;
        Task newTask;
        if (userInput.toLowerCase().contains("todo")) {
            taskDescription = cleanedUserInput.replace(("todo"), "");
            try {
                if (taskDescription.length() == 0) {
                    throw new MissingDescriptionException();
                }
                newTask = new Todo(taskDescription, false);
                taskList.addEntry(newTask);
                System.out.println(SMALL_INDENT + "Got it! I added a new to-do: " + System.lineSeparator() + BIG_INDENT + newTask);
            } catch (MissingDescriptionException E) {
                System.out.println(SMALL_INDENT + "No description provided!");
            } finally {
                System.out.println(LINE_DIVIDER);
            }
        } else if (userInput.toLowerCase().contains("event")) {
            try {
                if (!cleanedUserInput.contains("to") || !cleanedUserInput.contains("from")) {
                    throw new MissingKeywordException();
                }

                taskDescription = cleanedUserInput.substring(cleanedUserInput.indexOf("event") + 5, cleanedUserInput.indexOf("from"));

                if (taskDescription.length() == 0) {
                    throw new MissingDescriptionException();
                }
                String eventFrom = cleanedUserInput.substring(cleanedUserInput.lastIndexOf("from") + 4,
                        cleanedUserInput.lastIndexOf("to"));
                String eventTo = cleanedUserInput.substring(
                        cleanedUserInput.lastIndexOf("to") + 2);
                if (eventFrom.length() == 0 || eventTo.length() == 0) {
                    throw new MissingInformationException();
                }
                newTask = new Event(taskDescription, false, eventFrom, eventTo);
                taskList.addEntry(newTask);
                System.out.println(SMALL_INDENT + "Got it! I added a new event: " + System.lineSeparator() + BIG_INDENT + newTask);
            } catch (MissingKeywordException | MissingInformationException e) {
                System.out.println(SMALL_INDENT + "Error! Did you forget to write From when To when?");
            } catch (MissingDescriptionException e) {
                System.out.println(SMALL_INDENT + "Error! Did you forget to write a description?");
            } finally {
                System.out.println(LINE_DIVIDER);
            }
        } else if (userInput.toLowerCase().contains("deadline")) {
            try {
                if (!cleanedUserInput.contains("by")) {
                    throw new MissingKeywordException();
                }

                taskDescription = cleanedUserInput.substring(cleanedUserInput.indexOf("deadline") + 8, cleanedUserInput.indexOf("by"));

                if (taskDescription.length() == 0) {
                    throw new MissingDescriptionException();
                }
                String deadlineBy = cleanedUserInput.substring(
                        cleanedUserInput.toLowerCase().lastIndexOf("by") + 2);
                if (deadlineBy.length() == 0) {
                    throw new MissingInformationException();
                }
                newTask = new Deadline(taskDescription, false, deadlineBy);
                taskList.addEntry(newTask);
                System.out.println(SMALL_INDENT + "Got it! I added a new deadline: " + System.lineSeparator() + BIG_INDENT + newTask);
            } catch (MissingKeywordException | MissingInformationException e) {
                System.out.println(SMALL_INDENT + "Error! Did you forget to write by when?");
            } catch (MissingDescriptionException e) {
                System.out.println(SMALL_INDENT + "No description provided!");
            } finally {
                System.out.println(LINE_DIVIDER);
            }
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
            System.out.println(SMALL_INDENT + "no lists found");
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
                System.out.println(SMALL_INDENT + "Task " + taskNum + " is already marked as completed");
                System.out.println(BIG_INDENT + taskToMark);
                System.out.println(LINE_DIVIDER);
            } else {
                taskToMark.setIsDone(true);
                System.out.println(SMALL_INDENT + "Good Job! I marked task " + taskNum + " as completed");
                System.out.println(BIG_INDENT + taskToMark);
                System.out.println(LINE_DIVIDER);
            }
        } catch (NullPointerException E) {
            System.out.println(SMALL_INDENT + "Task not found");
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
            try {
                if (!taskToUnmark.isDone()) {
                    throw new TaskMarkingException();
                }
                taskToUnmark.setIsDone(false);
                System.out.println(SMALL_INDENT + "Got it! I marked task " + taskNum + " as not done");
                System.out.println(BIG_INDENT + taskToUnmark);
                System.out.println(LINE_DIVIDER);
            } catch (TaskMarkingException E) {
                System.out.println(SMALL_INDENT + "Task " + taskNum + " is already marked as not done");
                System.out.println(BIG_INDENT + taskToUnmark);
                System.out.println(LINE_DIVIDER);
            }
        } catch (NullPointerException E) {
            System.out.println(SMALL_INDENT + "Task not found");
            System.out.println(LINE_DIVIDER);
        }
    }
}
