import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Used to store and manage the <code>FileWriter</code> object and the outputs for storage
 *
 */
public class Storage {
    private File ebotOutput;
    private FileWriter filewriter;

    public Storage(String outputFilename) throws IOException {
        this.ebotOutput = new File(outputFilename);
    }

    public void addFileWriter(String outputFilename) throws IOException {
        this.filewriter = new FileWriter(outputFilename);
    }

    public void createFile() {
        try {
            if ((ebotOutput.getParentFile().mkdirs()) && (ebotOutput.createNewFile())) {
                System.out.println("Output file created: " + ebotOutput.getName());
            } else {
                System.out.println("Output file already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred");
        }
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
}
