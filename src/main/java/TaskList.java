import java.util.Arrays;

public class TaskList {
    private String listName;
    private Task[] entries = new Task[100];
    private int numberOfEntries;
    private static int numberOfTaskLists = 0;

    // Constructing empty list
    public TaskList(String listName) {
        this.listName = listName;
        this.entries = new Task[100];
        this.numberOfEntries = 0;
        numberOfTaskLists += 1;
    }

    // Constructing list with a single entry
    public TaskList(String listName, Task entry) {
        this.listName = listName;
        this.entries[0] = entry;
        this.numberOfEntries = 1;
        numberOfTaskLists += 1;
    }

    // Constructing list with existing list
    public TaskList(String listName, Task[] entries) {
        this.listName = listName;
        this.entries = entries;
        this.numberOfEntries = entries.length;
        numberOfTaskLists += 1;
    }

    public void AddEntry(Task entry) {
        this.entries[numberOfEntries] = entry;
        numberOfEntries += 1;
    }

    public Task[] getTaskList() {
        return Arrays.copyOf(entries, numberOfEntries);
    }

    public Task getTask(int index) {
        return entries[index];
    }

    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    public static int getNumberOfTaskLists() {
        return numberOfTaskLists;
    }

}
