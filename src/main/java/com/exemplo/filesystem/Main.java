package main.java.com.exemplo.filesystem;

import main.java.com.exemplo.filesystem.shell.Shell;
import main.java.com.exemplo.filesystem.core.FileSystemSimulator;

public class Main {
    public static void main(String[] args) {

        FileSystemSimulator fs = new FileSystemSimulator();
        Shell shell = new Shell(fs);
        shell.run();
    }
}