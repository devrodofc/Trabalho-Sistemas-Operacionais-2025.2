package main.java.com.exemplo.filesystem.fs;

public class FileNode extends FileSystemEntry {

    public FileNode(String name) {
        super(name);
    }

    @Override
    public boolean isDirectory() {
        return false;
    }
}
