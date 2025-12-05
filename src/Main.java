import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Simulador de Sistema de Arquivos (Java)");
        System.out.println("Comandos disponíveis: mkdir, touch, ls, cd, rm, rename, cp, exit");
        System.out.println("-------------------------------------------------------------");

        while (true) {
            System.out.print("root@java-fs:" + fs.getCurrentPath() + "$ ");
            String input = scanner.nextLine();
            
            if (input.trim().isEmpty()) continue;

            String[] parts = input.split(" ");
            String command = parts[0];

            try {
                switch (command) {
                    case "mkdir":
                        if(parts.length > 1) fs.mkdir(parts[1]);
                        else System.out.println("Uso: mkdir <nome_pasta>");
                        break;
                    case "touch": 
                        if(parts.length > 2) fs.touch(parts[1], parts[2]);
                        else if(parts.length > 1) fs.touch(parts[1], "");
                        else System.out.println("Uso: touch <nome_arquivo> [conteudo]");
                        break;
                    case "ls":
                        fs.ls();
                        break;
                    case "cd":
                        if(parts.length > 1) fs.cd(parts[1]);
                        else System.out.println("Uso: cd <nome_pasta> (ou .. para voltar)");
                        break;
                    case "rm":
                        if(parts.length > 1) fs.rm(parts[1]);
                        else System.out.println("Uso: rm <nome_item>");
                        break;
                    case "rename":
                        if(parts.length > 2) fs.rename(parts[1], parts[2]);
                        else System.out.println("Uso: rename <antigo> <novo>");
                        break;
                    case "cp":
                        if(parts.length > 2) fs.cp(parts[1], parts[2]);
                        else System.out.println("Uso: cp <origem> <destino>");
                        break;
                    case "exit":
                        System.out.println("Salvando estado e saindo...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Comando não reconhecido.");
                }
            } catch (Exception e) {
                System.out.println("Erro ao executar comando: " + e.getMessage());
            }
        }
    }
}