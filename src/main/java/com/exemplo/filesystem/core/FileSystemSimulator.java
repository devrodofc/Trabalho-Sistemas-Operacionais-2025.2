package main.java.com.exemplo.filesystem.core;

import main.java.com.exemplo.filesystem.fs.*;
import main.java.com.exemplo.filesystem.journal.Journal;

public class FileSystemSimulator {

    private final DirectoryNode root;
    private DirectoryNode currentDir;   // <-- NOVO
    private final Journal journal;

    public FileSystemSimulator() {
        this.root = new DirectoryNode("/");
        this.currentDir = root;
        this.journal = new Journal();

        restoreFromJournal();
    }

    // -----------------------------
    // Utils
    // -----------------------------

    private DirectoryNode resolvePath(String path) {
        if (path.equals("/") || path.isEmpty()) return root;
        if (!path.startsWith("/")) {
            // Caminho relativo ao diretório atual
            return resolvePath(currentDir.getFullPath() + "/" + path);
        }

        String[] parts = path.split("/");
        DirectoryNode cur = root;

        for (int i = 1; i < parts.length; i++) {
            if (parts[i].isEmpty()) continue;

            FileSystemEntry e = cur.getEntry(parts[i]);
            if (e instanceof DirectoryNode)
                cur = (DirectoryNode) e;
            else
                return null;
        }

        return cur;
    }

    private DirectoryNode getParentDirectory(String path) {
        path = fixRelative(path);
        String[] parts = path.split("/");
        DirectoryNode current = root;

        for (int i = 1; i < parts.length - 1; i++) {
            FileSystemEntry e = current.getEntry(parts[i]);
            if (e instanceof DirectoryNode)
                current = (DirectoryNode) e;
            else
                return null;
        }
        return current;
    }

    private String getName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    private String fixRelative(String path) {
        if (path.startsWith("/")) return path;
        return currentDir.getFullPath() + "/" + path;
    }

    // -----------------------------
    // RESTORE
    // -----------------------------

    private void restoreFromJournal() {
        System.out.println("[INFO] Restaurando sistema de arquivos...");

        for (String line : journal.loadOperations()) {
            String[] parts = line.split(" ", 2);

            String cmd = parts[0];
            String path = parts.length > 1 ? parts[1].trim() : "";

            switch (cmd) {
                case "createdir": createDirectory(path); break;
                case "create": createFile(path); break;
                case "rm": deleteFile(path); break;
                case "rmdir": deleteDirectory(path); break;

                case "re": {
                    String[] reParts = path.split(" ");
                    if (reParts.length == 2)
                        rename(reParts[0], reParts[1]);
                    break;
                }

                case "copy": {
                    String[] copyParts = path.split(" ");
                    if (copyParts.length == 2)
                        copy(copyParts[0], copyParts[1]);
                    break;
                }
            }
        }
    }

    // -----------------------------
    // CD — NOVO COMANDO
    // -----------------------------

    public void changeDirectory(String path) {
        path = fixRelative(path);

        if (path.equals("/")) {
            currentDir = root;
            return;
        }

        DirectoryNode target = resolvePath(path);

        if (target == null) {
            System.out.println("[ERRO] Caminho não encontrado: " + path);
            return;
        }

        currentDir = target;
    }

    public String getCurrentPath() {
        return currentDir.getFullPath();
    }

    // -----------------------------
    // OPERATIONS
    // -----------------------------

    public void createDirectory(String path) {
        path = fixRelative(path);
        DirectoryNode parent = getParentDirectory(path);

        if (parent == null) {
            System.out.println("[ERRO] Caminho inválido: " + path);
            return;
        }

        String name = getName(path);
        parent.addEntry(new DirectoryNode(name));
        journal.record("createdir", path);
    }

    public void deleteDirectory(String path) {
        path = fixRelative(path);
        DirectoryNode parent = getParentDirectory(path);

        if (parent == null) {
            System.out.println("[ERRO] Caminho inválido: " + path);
            return;
        }

        parent.removeEntry(getName(path));
        journal.record("rmdir", path);
    }

    public void createFile(String path) {
        path = fixRelative(path);
        DirectoryNode parent = getParentDirectory(path);

        if (parent == null) {
            System.out.println("[ERRO] Caminho inválido: " + path);
            return;
        }

        parent.addEntry(new FileNode(getName(path)));
        journal.record("create", path);
    }

    public void deleteFile(String path) {
        path = fixRelative(path);
        DirectoryNode parent = getParentDirectory(path);

        if (parent == null) {
            System.out.println("[ERRO] Caminho inválido: " + path);
            return;
        }

        parent.removeEntry(getName(path));
        journal.record("rm", path);
    }

    public void listDirectory(String path) {
        if (path.equals("")) path = ".";

        DirectoryNode dir = resolvePath(fixRelative(path));

        if (dir == null) {
            System.out.println("[ERRO] Diretório inválido: " + path);
            return;
        }

        for (FileSystemEntry e : dir.getChildren()) {
            System.out.println((e.isDirectory() ? "[DIR] " : "[FILE] ") + e.getName());
        }
    }

    public void rename(String oldPath, String newPath) {
        oldPath = fixRelative(oldPath);
        newPath = fixRelative(newPath);

        DirectoryNode parent = getParentDirectory(oldPath);
        if (parent == null) {
            System.out.println("[ERRO] Caminho inválido: " + oldPath);
            return;
        }

        FileSystemEntry e = parent.getEntry(getName(oldPath));
        if (e != null) {
            e.rename(getName(newPath));
            journal.record("re", oldPath + " " + newPath);
        }
    }

    public void copy(String source, String dest) {
        source = fixRelative(source);
        dest = fixRelative(dest);

        DirectoryNode parent = getParentDirectory(source);
        DirectoryNode destParent = getParentDirectory(dest);

        if (parent == null || destParent == null) {
            System.out.println("[ERRO] Caminho inválido.");
            return;
        }

        FileSystemEntry original = parent.getEntry(getName(source));

        if (original instanceof FileNode) {
            destParent.addEntry(new FileNode(getName(dest)));
        }
        else if (original instanceof DirectoryNode) {
            destParent.addEntry(new DirectoryNode(getName(dest)));
        }

        journal.record("copy", source + " " + dest);
    }
}
