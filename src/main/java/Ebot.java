import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

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
                break;

                // if user types "list" command
            } else if (userInput.toLowerCase().contains("list")) {
                ui.handleListOutput(tasks);

                // if user types "mark" or "unmark" command
            } else if (userInput.toLowerCase().contains("mark")) {
                String unmarkCommand = "unmark";
                String markCommand = "mark";
                handleMarkingOutput(userInput, tasks, unmarkCommand, markCommand, ui);
                storage.addFileWriter(OUTPUT_FILE_NAME);
                storage.writeToFile(tasks.toString());
                storage.closeFileWriter();

                // if user types any of the task types keyword command
            } else if (userInput.toLowerCase().contains("todo")
                    || userInput.toLowerCase().contains("event")
                    || userInput.toLowerCase().contains("deadline")) {
                handleTaskAdding(tasks, userInput, ui);
                storage.addFileWriter(OUTPUT_FILE_NAME);
                storage.writeToFile(tasks.toString());
                storage.closeFileWriter();

                // if user types "delete" command
            } else if (userInput.toLowerCase().contains("delete")) {
                handleTaskDeletion(tasks, userInput, ui);
                storage.addFileWriter(OUTPUT_FILE_NAME);
                storage.writeToFile(tasks.toString());
                storage.closeFileWriter();

                // if user types anything else
            } else if (userInput.toLowerCase().contains("find")) {
                handleTaskSearch(tasks, userInput, ui);

            } else {
                ui.echoUserInput(userInput);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Ebot ebot = new Ebot(OUTPUT_FILE_NAME);
        ebot.readFile();
        ebot.run();
    }

    private void readFile() throws FileNotFoundException {
        if (!storage.createFile()) {
            ArrayList<ArrayList<String>> fileTasks = storage.readFile(OUTPUT_FILE_NAME);
            for (ArrayList<String> taskDetails : fileTasks) {
                String taskType = taskDetails.get(0);
                handleTaskAddingFromFile(tasks, taskType, taskDetails);
            }
        }
    }

    private void handleTaskSearch(TaskList tasks, String userInput, Ui ui) {
        String searchWord = userInput.toLowerCase().replace("find", "").replace(" ", "");
        TaskList searchResults = new TaskList("TempSearchResult");
        for (Task task : tasks.getTaskList()) {
            if (task.getDescription().contains(searchWord)) {
                searchResults.addEntry(task);
            }
        }
        ui.printTaskSearch();
        ui.handleListOutput(searchResults);
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

    private static void handleTaskAddingFromFile(TaskList taskList, String taskType, ArrayList<String> taskDetails) {
        String inputTaskDescription = taskDetails.get(1);
        Task newTask;
        if (taskType.equals("T")) {
            newTask = new Todo(inputTaskDescription, false);
            taskList.addEntry(newTask);
        } else if (taskType.equals("E")) {
            newTask = new Event(inputTaskDescription, false, taskDetails.get(2), taskDetails.get(3));
            taskList.addEntry(newTask);
        } else if (taskType.equals("D")) {
            String deadlineBy = taskDetails.get(2);
            newTask = new Deadline(inputTaskDescription, false, deadlineBy);
            taskList.addEntry(newTask);
        }
    }

    private static void handleTaskAdding(TaskList taskList, String userInput, Ui ui) {
        String inputTaskType = Parser.parseInputTaskType(userInput);
        Task newTask;

        try {
            if (Parser.isMissingKeyword(userInput)) {
                throw new MissingKeywordException();
            }
            if (Parser.isMissingKeyInformation(userInput, ui)) {
                throw new MissingInformationException();
            }
            String inputTaskDescription = Parser.parseInputTaskDesc(userInput);
            if (inputTaskDescription.length() == 0 || inputTaskDescription.equals(" ")) {
                throw new MissingDescriptionException();
            }

            if (inputTaskType == "todo") {
                newTask = new Todo(inputTaskDescription, false);
                taskList.addEntry(newTask);
                ui.printAddTask(newTask);
            } else if (inputTaskType == "event") {
                String[] eventInfo = Parser.parseEventInfo(userInput, ui);
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
        } catch (IndexOutOfBoundsException E) {
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
        } catch (IndexOutOfBoundsException E) {
            ui.printNullPointerException();
        }
    }
}
