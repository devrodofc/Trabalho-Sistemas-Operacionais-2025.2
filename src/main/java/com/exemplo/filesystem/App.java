package main.java.com.exemplo.filesystem;

import main.java.com.exemplo.filesystem.core.FileSystemSimulator;
import main.java.com.exemplo.filesystem.shell.Shell;

public class App {

    public static void main(String[] args) {

        FileSystemSimulator fs = new FileSystemSimulator();
        Shell shell = new Shell(fs);

        System.out.println("=== Simulador de Sistema de Arquivos ===");
        System.out.println("Digite 'exit' para sair.");

        shell.run();
    }
}
