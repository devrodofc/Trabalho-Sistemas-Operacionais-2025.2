package main.java.com.exemplo.filesystem.journal;

public class JournalEntry {
    private String action;
    private String target;

    public JournalEntry(String action, String target) {
        this.action = action;
        this.target = target;
    }

    @Override
    public String toString() {
        return action + ": " + target;
    }
}
