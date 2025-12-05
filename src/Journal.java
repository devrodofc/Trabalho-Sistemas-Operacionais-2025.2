import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class Journal {
    private static final String LOG_FILE = "filesystem_journal.log";

    public void log(String operation, String target) {
        // Tenta escrever no arquivo de log sem apagar o conteúdo anterior (append = true)
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println("[" + LocalDateTime.now() + "] OP: " + operation + " | TARGET: " + target);
        } catch (IOException e) {
            System.err.println("Erro crítico no Journaling: " + e.getMessage());
        }
    }
}