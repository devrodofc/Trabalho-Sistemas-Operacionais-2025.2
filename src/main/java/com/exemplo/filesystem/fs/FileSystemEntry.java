package main.java.com.exemplo.filesystem.fs;

public abstract class FileSystemEntry {
    protected String name;

    public FileSystemEntry(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void rename(String newName) {
        this.name = newName;
    }

    public abstract boolean isDirectory();
}
