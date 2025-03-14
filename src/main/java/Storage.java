import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Used to store and manage the <code>FileWriter</code> object and the outputs for storage
 *
 */
public class Storage {
    private File ebotOutput;
    private FileWriter filewriter;
    private Scanner fileScanner;

    public Storage(String outputFilename) throws IOException {
        this.ebotOutput = new File(outputFilename);
    }

    public void addFileWriter(String outputFilename) throws IOException {
        this.filewriter = new FileWriter(outputFilename);
    }

    public boolean createFile() {
        try {
            if ((ebotOutput.getParentFile().mkdirs()) && (ebotOutput.createNewFile())) {
                System.out.println("Output file created: " + ebotOutput.getName());
                return true;
            } else {
                System.out.println("Output file already exists.");
                return false;
            }
        } catch (IOException e) {
            System.out.println("An error occurred");
        }
        return false;
    }

    public void writeToFile(String textToAdd) {
        try {
            filewriter.write(textToAdd);
        } catch (IOException E) {
            System.out.println("Unable to write to output file: " + E.getMessage());
        }
    }

    public void closeFileWriter() throws IOException {
        filewriter.close();
    }

    public ArrayList<ArrayList<String>> readFile(String outputFileName) throws FileNotFoundException {
        this.fileScanner = new Scanner(ebotOutput);
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        while (fileScanner.hasNext()) {
            ArrayList<String> task = new ArrayList<>();
            String taskLine = fileScanner.nextLine();
            String taskType = taskLine.substring(taskLine.indexOf("[") + 1, taskLine.indexOf("]"));
            String description;
            if (taskType.equals("D")) {
                description = taskLine.substring(taskLine.lastIndexOf("] ") + 2, taskLine.indexOf(" (by"));
                String deadlineBy = taskLine.substring(taskLine.indexOf("(by: ") + 5, taskLine.lastIndexOf(")"));
                task.add(taskType);
                task.add(description);
                task.add(deadlineBy);
            } else if (taskType.equals("E")) {
                description = taskLine.substring(taskLine.lastIndexOf("] ") + 2, taskLine.indexOf(" (from"));
                String eventFrom = taskLine.substring(taskLine.indexOf("(from ") + 6, taskLine.lastIndexOf(" to"));
                String eventTo = taskLine.substring(taskLine.lastIndexOf("to ") + 3, taskLine.lastIndexOf(")"));
                task.add(taskType);
                task.add(description);
                task.add(eventFrom);
                task.add(eventTo);
            } else {
                description = taskLine.substring(taskLine.lastIndexOf("] ") + 2);
                task.add(taskType);
                task.add(description);
            }
            output.add(task);
        }
        return output;
    }
}
