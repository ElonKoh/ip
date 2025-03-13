import java.io.IOException;

/**
 * The main chatbot class, runs the main logic with user inputs.
 * An Ebot object saves changes to the to-do tasklist
 * to an output file specified by <code>OUTPUT_FILE_NAME</code>
 */
public class Ebot {
    private static final String OUTPUT_FILE_NAME = "./data/ebot.txt";
    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    public Ebot(String filepath) throws IOException {
        ui = new Ui();
        int numberOfLists = TaskList.getNumberOfTaskLists();
        String taskListName = ("list ") + numberOfLists;
        tasks = new TaskList(taskListName);
        storage = new Storage(filepath);
    }

    public void createFile() {
        storage.createFile();
    }

    /**
     * The main logic of the chatbot, runs a user chat loop until "bye" keyword
     * is given by user, which ends the program.
     * @throws IOException If unable to write to file for whatever reason
     */
    public void run() throws IOException {
        ui.printWelcome();
        // User chat loop
        while (true) {
            String userInput = Parser.getUserInput();
            // if user types "bye" command
            if (userInput.equalsIgnoreCase("bye")) {
                ui.printExit();
                storage.closeFileWriter();
                break;

                // if user types "list" command
            } else if (userInput.toLowerCase().contains("list")) {
                ui.handleListOutput(tasks);

                // if user types "mark" or "unmark" command
            } else if (userInput.toLowerCase().contains("mark")) {
                String unmarkCommand = "unmark";
                String markCommand = "mark";
                handleMarkingOutput(userInput, tasks, unmarkCommand, markCommand, ui);
                storage.writeToFile(tasks.toString());

                // if user types any of the task types keyword command
            } else if (userInput.toLowerCase().contains("todo")
                    || userInput.toLowerCase().contains("event")
                    || userInput.toLowerCase().contains("deadline")) {
                handleTaskAdding(tasks, userInput, ui);
                storage.writeToFile(tasks.toString());

                // if user types "delete" command
            } else if (userInput.toLowerCase().contains("delete")) {
                handleTaskDeletion(tasks, userInput, ui);
                storage.writeToFile(tasks.toString());

                // if user types anything else
            } else {
                ui.echoUserInput(userInput);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Ebot ebot = new Ebot(OUTPUT_FILE_NAME);
        ebot.createFile();
        ebot.run();
    }

    private static void handleTaskDeletion(TaskList taskList, String userInput, Ui ui) {
        try {
            int deleteIndex = ((Integer.parseInt(userInput.toLowerCase().replace("delete", "").replace(" ", "")))-1);
            taskList.deleteEntry(deleteIndex);
            ui.printDeleteTask(deleteIndex, taskList);
        } catch (NumberFormatException E) {
            ui.printNumberFormatException();
        } catch (IndexOutOfBoundsException E) {
            ui.printIndexOutOfBoundsException();
        } finally {
            ui.printSeparator();
        }
    }

    private static void handleTaskAdding(TaskList taskList, String userInput, Ui ui) {
        String inputTaskType = Parser.parseInputTaskType(userInput);
        String inputTaskDescription = Parser.parseInputTaskDesc(userInput);
        Task newTask;

        try {
            if (inputTaskDescription.length() == 0) {
                throw new MissingDescriptionException();
            }
            if (Parser.isMissingKeyword(userInput)) {
                throw new MissingKeywordException();
            }
            if (Parser.isMissingKeyInformation(userInput)) {
                throw new MissingInformationException();
            }

            if (inputTaskType == "todo") {
                newTask = new Todo(inputTaskDescription, false);
                taskList.addEntry(newTask);
                ui.printAddTask(newTask);
            } else if (inputTaskType == "event") {
                String[] eventInfo = Parser.parseEventInfo(userInput);
                newTask = new Event(inputTaskDescription, false, eventInfo[0], eventInfo[1]);
                taskList.addEntry(newTask);
                ui.printAddTask(newTask);
            } else if (inputTaskType == "deadline") {
                String deadlineBy = Parser.parseDeadlineInfo(userInput);
                newTask = new Deadline(inputTaskDescription, false, deadlineBy);
                taskList.addEntry(newTask);
                ui.printAddTask(newTask);
            }

        } catch (MissingDescriptionException E) {
            ui.printMissingDescriptionException();
        } catch (MissingKeywordException | MissingInformationException e) {
            ui.printMissingKeywordException(inputTaskType);
        } finally {
            ui.printSeparator();
        }
    }

    private static void handleMarkingOutput(String userInput, TaskList taskList, String unmarkCommand, String markCommand, Ui ui) {
        if (userInput.toLowerCase().contains(unmarkCommand)) {
            unmarkTaskAndPrint(userInput, taskList, unmarkCommand, ui);
            // if "mark" command received
        } else {
            markTaskAndPrint(userInput, taskList, markCommand, ui);
        }
    }


    private static void markTaskAndPrint(String userInput, TaskList taskList, String markCommand, Ui ui) {
        String markString = markCommand + " ";
        int markIndex = userInput.indexOf(markString);
        int taskNumPointer = (markIndex + markString.length());
        int taskNum = Character.getNumericValue(userInput.charAt(taskNumPointer));
        try {
            Task taskToMark = taskList.getTask(taskNum - 1);
            try {
                if (taskToMark.isDone()) {
                    throw new TaskMarkingException();
                } else {
                    taskToMark.setIsDone(true);
                    ui.printMarkTask("mark", taskNum, taskToMark);
                }
            } catch (TaskMarkingException E) {
                ui.printTaskMarkingException("mark", taskNum, taskToMark);
            }
        } catch (NullPointerException E) {
            ui.printNullPointerException();
        }
    }

    private static void unmarkTaskAndPrint(String userInput, TaskList taskList, String unmarkCommand, Ui ui) {
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
                ui.printMarkTask("unmark", taskNum, taskToUnmark);
            } catch (TaskMarkingException E) {
                ui.printTaskMarkingException("unmark", taskNum, taskToUnmark);
            }
        } catch (NullPointerException E) {
            ui.printNullPointerException();
        }
    }
}
