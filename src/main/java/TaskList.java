import java.util.ArrayList;
/**
 * A TaskList stores an ArrayList of tasks
 */
public class TaskList {
    private String listName;
    private ArrayList<Task> entries;
    private static int numberOfTaskLists = 0;

    // Constructing empty list
    public TaskList(String listName) {
        this.listName = listName;
        this.entries = new ArrayList<>();
        numberOfTaskLists += 1;
    }

    // Constructing list with a single entry
    public TaskList(String listName, Task entry) {
        this.listName = listName;
        this.entries = new ArrayList<>();
        entries.add(entry);
        numberOfTaskLists += 1;
    }

    // Constructing list with existing list
    public TaskList(String listName, ArrayList<Task> entries) {
        this.listName = listName;
        this.entries = entries;
        numberOfTaskLists += 1;
    }

    /**
     * Adds an entry to the TaskList
     *
     * @param entry Task to be added.
     */
    public void addEntry(Task entry) {
        entries.add(entry);
    }

    /**
     * Removes an entry to the TaskList
     *
     * @param deleteIndex Index of task to be removed.
     */
    public void deleteEntry(int deleteIndex) {
        entries.remove(deleteIndex);
    }


    public ArrayList<Task> getTaskList() {
        return entries;
    }

    /**
     * Returns a task from the TaskList
     *
     * @param index Index of task to be returned.
     */
    public Task getTask(int index) {
        return entries.get(index);
    }

    public int getNumberOfEntries() {
        return entries.size();
    }

    public static int getNumberOfTaskLists() {
        return numberOfTaskLists;
    }

    @Override
    public String toString() {
        String printList = "";
        for (int i = 0; i < entries.size(); i++) {
            String printEntry;
            if (i == (entries.size() - 1)) {
                printEntry = "\t\t" + (i + 1) + ". " + entries.get(i);
            } else {
                printEntry = "\t\t" + (i + 1) + ". " + entries.get(i) + System.lineSeparator();
            }
            printList += printEntry;
        }
        return printList;
    }
}
