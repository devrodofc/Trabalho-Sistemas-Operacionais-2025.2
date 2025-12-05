Relatório: Simulador de Sistema de Arquivos
Alunos: Rodrigo Morais Herrmann e Lorrân Brito da Silva.
Disciplina: Sistemas Operacionais
Data: 05/12/2025

Resumo
Este trabalho apresenta o desenvolvimento de um simulador de sistema de arquivos em Java. O projeto visa demonstrar de forma prática como sistemas operacionais gerenciam a hierarquia de dados e a integridade das operações através de técnicas de Journaling.

Introdução
O gerenciamento eficiente de arquivos é crucial para o funcionamento dos sistemas operacionais. Entender como um sistema é montado e organizado é a base para a compreensão da computação moderna. Um sistema de arquivos é a estrutura lógica usada para controlar como os dados são armazenados e recuperados. Sem ele, os dados colocados em um meio de armazenamento seriam um grande corpo de dados sem maneira de saber onde um arquivo termina e o próximo começa.

Parte 1: Introdução ao Sistema de Arquivos com Journaling
O que é um Sistema de Arquivos? Um sistema de arquivos é um conjunto de estruturas lógicas e rotinas que permitem ao sistema operacional controlar o acesso ao disco rígido. Diferentes sistemas operacionais usam diferentes sistemas de arquivos (como NTFS, EXT4, FAT32), mas todos compartilham objetivos comuns: organização hierárquica e persistência.

Conceito de Journaling O Journaling é uma técnica utilizada para garantir a integridade dos dados em caso de falhas (como queda de energia). Antes de realizar qualquer alteração nos dados do disco, o sistema registra a intenção da mudança em um log circular chamado "Journal".

Propósito: Prevenir a corrupção do sistema de arquivos. Se o sistema travar durante uma operação, ao reiniciar, o SO lê o Journal e pode refazer ou desfazer as operações pendentes.

Tipos de Journaling:

Write-ahead logging: A operação é escrita no log antes de ser efetivada no disco. (Modelo utilizado neste simulador).

Log-structured: O próprio sistema de arquivos é estruturado como um log contínuo.

Parte 2: Arquitetura do Simulador
Para simular este ambiente, utilizamos a linguagem Java devido à sua robustez na orientação a objetos.

Estrutura de Dados Utilizamos o padrão de projeto Composite para representar a hierarquia:

FSNode (Abstrata): Representa um nó genérico do sistema.

Directory: Um nó que pode conter uma lista de filhos (List<FSNode> children).

FileNode: Um nó folha que contém dados (neste caso, uma String simulando o conteúdo).

Virtual Disk: O estado completo da árvore de diretórios é serializado em um arquivo binário (virtual_disk.ser), simulando o disco físico.

Implementação do Journaling O Journaling foi implementado através da classe Journal. Toda vez que um comando de escrita (como mkdir, rm, cp) é invocado, o sistema:

Abre o arquivo de log (filesystem_journal.log).

Escreve a operação pretendida e o alvo (timestamp + comando).

Executa a operação na memória.

Persiste o estado no arquivo de "disco virtual".

Parte 3: Implementação em Java
O projeto foi dividido nas seguintes classes principais:

FileSystemSimulator (Main): Implementa o modo Shell (CLI). Ele roda um loop infinito esperando comandos do usuário (mkdir, ls, etc.) e invoca o motor do sistema.

FileSystem: É o controlador. Mantém a referência para o diretório raiz (root) e o diretório atual (currentDirectory). Contém a lógica de:

Navegação: Alterar a referência do currentDirectory.

Manipulação: Adicionar ou remover objetos das listas de filhos.

Persistência: Usa ObjectOutputStream para salvar a árvore de objetos.

FileNode e Directory: Classes de modelo que estendem FSNode.

Journal: Gerencia a escrita no arquivo de texto que serve como log de auditoria.

Parte 4: Instalação e Funcionamento
Requisitos:

Java JDK 11 ou superior instalado.

IDE Java (Eclipse, IntelliJ) ou terminal.

Passo a Passo de Execução:

Clone o repositório: git clone https://github.com/devrodofc/Trabalho-Sistemas-Operacionais-2025.2

Compile os arquivos: javac *.java

Execute a classe principal: java Main

O sistema abrirá um prompt simulado: user@mysystem:/$.

Exemplos de uso:

mkdir documentos (Cria uma pasta)

cd documentos (Entra na pasta)

touch nota.txt (Cria um arquivo)

rm (remove uma pasta ou um arquivo)

ls (Lista o conteúdo)

rename nota.txt nota_final.txt (Renomeia)

cp nota_final.txt copia.txt (Copia o arquivo)

exit (Sai e salva o estado)

Ao verificar a pasta do projeto, dois arquivos terão sido criados:

virtual_disk.ser: O "HD" do simulador.

filesystem_journal.log: O registro de todas as operações feitas.

Link do Projeto
Acesse o código fonte completo em: https://github.com/devrodofc/Trabalho-Sistemas-Operacionais-2025.2

Resultados Esperados e Conclusão
O simulador atingiu os objetivos propostos. Foi possível replicar a lógica de árvore de diretórios e a importância do journaling. Ao abrir o arquivo de log gerado, é possível rastrear todas as ações do usuário, demonstrando como sistemas reais mantêm a consistência e auditabilidade dos dados.
