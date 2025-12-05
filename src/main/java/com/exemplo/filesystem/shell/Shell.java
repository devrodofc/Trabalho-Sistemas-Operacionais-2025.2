package main.java.com.exemplo.filesystem.shell;

import main.java.com.exemplo.filesystem.core.FileSystemSimulator;

import java.util.Scanner;

public class Shell {

    private final FileSystemSimulator fs;
    private final Scanner scanner = new Scanner(System.in);

    public Shell(FileSystemSimulator fs) {
        this.fs = fs;
    }

    public void run() {

        System.out.println("=== Shell do Sistema de Arquivos ===");

        while (true) {
            System.out.print("FS> ");
            String input = scanner.nextLine().trim();

            if (input.equals("exit"))
                break;

            handleCommand(input);
        }
    }

    private void handleCommand(String input) {
        String[] parts = input.split(" ");

        try {
            switch (parts[0]) {
                case "createdir":
                    fs.createDirectory(parts[1]);
                    break;

                case "rmdir":
                    fs.deleteDirectory(parts[1]);
                    break;

                case "create":
                    fs.createFile(parts[1]);
                    break;

                case "rm":
                    fs.deleteFile(parts[1]);
                    break;

                case "ls":
                    fs.listDirectory(parts.length > 1 ? parts[1] : "/");
                    break;

                case "re":
                    fs.rename(parts[1], parts[2]);
                    break;

                case "copy":
                    fs.copy(parts[1], parts[2]);
                    break;

                default:
                    System.out.println("Comando inválido.");
            }
        } catch (Exception e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }
}
