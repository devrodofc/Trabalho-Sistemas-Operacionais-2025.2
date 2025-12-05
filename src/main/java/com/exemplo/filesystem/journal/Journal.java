package main.java.com.exemplo.filesystem.journal;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Journal {

    private final File journalFile;

    public Journal() {
        journalFile = new File("journal.txt");

        try {
            if (!journalFile.exists()) {
                journalFile.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("[ERRO] Não foi possível criar o arquivo journal.");
        }
    }

    // Grava nova operação no arquivo
    public void record(String command, String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(journalFile, true))) {
            bw.write(command + " " + path);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("[ERRO] Não foi possível gravar no journal.");
        }
    }

    // Lê todas as operações já registradas
    public List<String> loadOperations() {
        List<String> ops = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(journalFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                ops.add(line);
            }
        } catch (IOException e) {
            System.out.println("[ERRO] Não foi possível ler o journal.");
        }

        return ops;
    }
}
