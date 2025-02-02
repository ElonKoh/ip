import java.util.Arrays;

public class TaskList {
    private String listName;
    private String[] entries = new String[100];
    private int numberOfEntries;
    private static int numberOfTaskLists = 0;

    // Constructing empty list
    public TaskList(String listName) {
        this.listName = listName;
        this.entries = new String[100];
        this.numberOfEntries = 0;
        numberOfTaskLists += 1;
    }

    // Constructing list with a single entry
    public TaskList(String listName, String entry) {
        this.listName = listName;
        this.entries[0] = entry;
        this.numberOfEntries = 1;
        numberOfTaskLists += 1;
    }

    // Constructing list with existing list
    public TaskList(String listName, String[] entries) {
        this.listName = listName;
        this.entries = entries;
        this.numberOfEntries = entries.length;
        numberOfTaskLists += 1;
    }

    public void AddEntry(String entry) {
        this.entries[numberOfEntries] = entry;
        numberOfEntries += 1;
    }

    public String[] getTaskList() {
        return Arrays.copyOf(entries, numberOfEntries);
    }

    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    public static int getNumberOfTaskLists() {
        return numberOfTaskLists;
    }
}
