package main.java.com.exemplo.filesystem.fs;

import java.util.ArrayList;
import java.util.List;

public class DirectoryNode extends FileSystemEntry {

    private List<FileSystemEntry> children;
    private DirectoryNode parent;

    public DirectoryNode(String name) {
        super(name);
        this.children = new ArrayList<>();
        this.parent = null;
    }

    public List<FileSystemEntry> getChildren() {
        return children;
    }

    public DirectoryNode getParent() {
        return parent;
    }

    public void setParent(DirectoryNode parent) {
        this.parent = parent;
    }

    public void addEntry(FileSystemEntry entry) {

        // seta o parent apenas se for DirectoryNode
        if (entry instanceof DirectoryNode) {
            ((DirectoryNode) entry).setParent(this);
        }

        children.add(entry);
    }

    public void removeEntry(String name) {
        children.removeIf(e -> e.getName().equals(name));
    }

    public FileSystemEntry getEntry(String name) {
        for (FileSystemEntry e : children) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    // constrói o caminho completo até a raiz
    public String getFullPath() {
        if (parent == null) return "/";

        StringBuilder sb = new StringBuilder();
        DirectoryNode current = this;

        while (current != null && current.getParent() != null) {
            sb.insert(0, "/" + current.getName());
            current = current.getParent();
        }

        return sb.toString();
    }
}
