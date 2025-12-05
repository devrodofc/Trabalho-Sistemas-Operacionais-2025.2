
# 📄 **README – Simulador de Sistema de Arquivos com Journaling**

## 📝 **Título**

**Simulador de Sistema de Arquivos com Journaling em Java**

---

## 📘 **Resumo**

Este projeto implementa um simulador de sistema de arquivos com funcionalidades básicas de manipulação de arquivos e diretórios, incluindo suporte a **journaling**, um mecanismo utilizado em sistemas reais para manter a integridade do sistema em caso de falhas.

O simulador permite criar, apagar, mover, copiar e listar arquivos e diretórios, utilizando uma interface em modo Shell. Todas as operações realizadas são registradas em um arquivo de log, que é usado para reconstruir o sistema quando o programa é reiniciado.

---

## 📌 **1. Introdução**

Sistemas de arquivos são componentes essenciais de qualquer sistema operacional, responsáveis pela organização, armazenamento e recuperação de dados.

Um conceito fundamental em sistemas modernos é o **journaling**, uma técnica que registra operações antes que elas sejam executadas, garantindo a consistência dos dados mesmo em caso de desligamentos inesperados ou falhas abruptas.

Este projeto tem como objetivo criar um simulador simples que permita explorar, de forma prática, como um sistema de arquivos funciona internamente e como o journaling auxilia na robustez do sistema.

---

## 🎯 **2. Objetivo**

Desenvolver um simulador de sistema de arquivos em Java com as seguintes capacidades:

* Manipulação de arquivos e diretórios:

  * Criar e remover arquivos
  * Criar e remover diretórios
  * Renomear arquivos e diretórios
  * Copiar arquivos e diretórios
  * Listar conteúdo de diretórios

* Implementação de um sistema de **Journaling**:

  * Registro de todas as operações
  * Reconstrução do estado do sistema no início da execução

* Criar um ambiente Shell para interação com o usuário, simulando comandos de um sistema operacional.

---

## 🧠 **3. Metodologia**

O projeto foi implementado em **Java**, utilizando um modelo orientado a objetos para representar:

* Arquivos
* Diretórios
* Estrutura hierárquica do sistema de arquivos
* Mecanismo de journaling

A aplicação utiliza chamadas de métodos para cada comando (mkdir, touch, rm etc.), e um módulo shell simples interpreta os comandos digitados pelo usuário.

---

## 📂 **4. Arquitetura do Simulador**

### 🔧 **4.1 Estruturas de Dados**

O sistema de arquivos é composto pelas classes:

* **FileSystemEntry** — classe abstrata representando tanto arquivos quanto diretórios.
* **FileNode** — representa um arquivo.
* **DirectoryNode** — representa um diretório, contendo uma lista de outras entradas.
* **FileSystemSimulator** — lógica principal das operações.
* **Journal** — gerencia o arquivo de log (`journal.txt`), gravando e recuperando operações.
* **Shell** — interface em modo texto para interação com o usuário.

---

## 📦 **5. Instalação e Funcionamento**

### 🔧 5.1 Requisitos

* **Java 17+**
* **Apache Maven**
* Sistema operacional Windows, Linux ou macOS

### 📥 5.2 Download do Projeto

```bash
git clone https://github.com/SEU_USUARIO/filesystem-simulator.git
cd filesystem-simulator
```

### 🏗️ 5.3 Compilar e Executar

```bash
mvn exec:java
```

### 🗂️ 5.4 Estrutura do Projeto

```
filesystem-simulator/
 ├── src/main/java/com/exemplo/filesystem/
 │     ├── Main.java
 │     ├── core/FileSystemSimulator.java
 │     ├── fs/
 │     │     ├── FileNode.java
 │     │     ├── DirectoryNode.java
 │     │     └── FileSystemEntry.java
 │     ├── journal/Journal.java
 │     └── shell/Shell.java
 ├── journal.txt
 └── pom.xml
```

### 💻 5.5 Lista de Comandos Disponíveis

| Comando            | Exemplo          | Ação             |
| ------------------ | ---------------- | ---------------- |
| `createdir <dir>`      | mkdir /docs      | Cria diretório   |
| `rmdir <dir>`      | rmdir /docs      | Remove diretório |
| `create <file>`     | touch /a.txt     | Cria arquivo     |
| `rm <file>`        | rm /a.txt        | Remove arquivo   |
| `re <orig> <dest>` | mv /a.txt /b.txt | Move/renomeia    |
| `copy <orig> <dest>` | cp /a /b         | Copia            |
| `ls <dir>`         | ls /docs         | Lista conteúdo   |
| `exit`             | —                | Encerra          |

---

## 📒 **6. Journaling**

O arquivo `journal.txt` registra cada operação executada, como:

```
mkdir /docs
touch /docs/arq.txt
rm /docs/arq.txt
```

Sempre que o simulador é iniciado, ele **reexecuta** cada comando registrado no jornal e restaura a árvore de arquivos exatamente como estava.

Isso simula um **sistema de arquivos com suporte a journaling**, semelhante a EXT3, NTFS e XFS.

---

## 📊 **7. Resultados Esperados**

Com o simulador funcionando, espera-se:

* Melhor compreensão da estrutura de um sistema de arquivos.
* Visualização prática de como comandos alteram a hierarquia.
* Entendimento do mecanismo de journaling e recuperação.
* Ferramenta útil para estudos de Sistemas Operacionais.

---

## 👥 **8. Autores**

* Nome 1
* Nome 2

---

## 🔗 **9. Repositório GitHub**

**👉 Adicione aqui o link do seu repositório:**

```
https://github.com/SEU_USUARIO/filesystem-simulator
```

---

## 📄 **10. Versão para Entregar em PDF**

