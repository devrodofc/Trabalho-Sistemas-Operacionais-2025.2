import java.io.*;

public class FileSystem {
    private Directory root;
    private Directory currentDirectory;
    private Journal journal;
    private final String DISK_FILE = "virtual_disk.ser";

    public FileSystem() {
        this.journal = new Journal();
        // Tenta carregar o disco existente, se não, cria um novo
        if (!loadFileSystem()) {
            this.root = new Directory("/", null);
            this.currentDirectory = root;
            System.out.println("Sistema de arquivos inicializado (Novo Disco).");
        } else {
            System.out.println("Sistema de arquivos carregado do disco.");
        }
    }

    // --- Comandos ---

    public void mkdir(String name) {
        journal.log("MKDIR", name); 
        if (currentDirectory.getChild(name) != null) {
            System.out.println("Erro: Já existe item com este nome.");
            return;
        }
        Directory newDir = new Directory(name, currentDirectory);
        currentDirectory.addChild(newDir);
        saveFileSystem();
        System.out.println("Diretório criado: " + name);
    }

    public void touch(String name, String content) {
        journal.log("CREATE_FILE", name);
        if (currentDirectory.getChild(name) != null) {
            System.out.println("Erro: Já existe item com este nome.");
            return;
        }
        FileNode newFile = new FileNode(name, currentDirectory, content);
        currentDirectory.addChild(newFile);
        saveFileSystem();
        System.out.println("Arquivo criado: " + name);
    }

    public void ls() {
        journal.log("LIST", currentDirectory.getName());
        System.out.println("Conteúdo de " + currentDirectory.getName() + ":");
        if (currentDirectory.getChildren().isEmpty()) {
            System.out.println("(vazio)");
        }
        for (FSNode node : currentDirectory.getChildren()) {
            String type = node.isDirectory() ? "[DIR] " : "[FILE]";
            System.out.println(type + " " + node.getName());
        }
    }

    public void cd(String name) {
        if (name.equals("..")) {
            if (currentDirectory.getParent() != null) {
                currentDirectory = currentDirectory.getParent();
            }
        } else {
            FSNode node = currentDirectory.getChild(name);
            if (node != null && node.isDirectory()) {
                currentDirectory = (Directory) node;
            } else {
                System.out.println("Diretório não encontrado: " + name);
            }
        }
    }

    public void rm(String name) {
        journal.log("DELETE", name);
        FSNode node = currentDirectory.getChild(name);
        if (node != null) {
            currentDirectory.removeChild(node);
            saveFileSystem();
            System.out.println("Removido: " + name);
        } else {
            System.out.println("Item não encontrado.");
        }
    }

    public void rename(String oldName, String newName) {
        journal.log("RENAME", oldName + " -> " + newName);
        FSNode node = currentDirectory.getChild(oldName);
        if (node != null) {
            node.setName(newName);
            saveFileSystem();
            System.out.println("Renomeado com sucesso.");
        } else {
            System.out.println("Item não encontrado.");
        }
    }

    public void cp(String sourceName, String destName) {
        journal.log("COPY", sourceName + " -> " + destName);
        FSNode source = currentDirectory.getChild(sourceName);
        if (source == null) {
            System.out.println("Fonte não encontrada.");
            return;
        }
        
        if (source instanceof FileNode) {
            FileNode origin = (FileNode) source;
            // Cria uma cópia com o mesmo conteúdo
            FileNode copy = new FileNode(destName, currentDirectory, origin.getContent());
            currentDirectory.addChild(copy);
            saveFileSystem();
            System.out.println("Arquivo copiado.");
        } else {
             System.out.println("Cópia de diretórios não suportada nesta versão.");
        }
    }

    public String getCurrentPath() {
        // Mostra apenas o nome da pasta atual para simplificar
        return currentDirectory.getName(); 
    }

    // --- Persistência (Salvar no "Disco") ---
    private void saveFileSystem() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DISK_FILE))) {
            oos.writeObject(root);
        } catch (IOException e) {
            System.out.println("Erro ao gravar no disco: " + e.getMessage());
        }
    }

    private boolean loadFileSystem() {
        File f = new File(DISK_FILE);
        if(!f.exists()) return false;
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DISK_FILE))) {
            this.root = (Directory) ois.readObject();
            // Ao carregar, voltamos para a raiz para evitar erros de ponteiro
            this.currentDirectory = root;
            return true;
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }
}