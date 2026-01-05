package examples;

import br.com.famel.model.entities.Status;
import br.com.famel.model.entities.Task;
import br.com.famel.model.entities.TaskRepository;
import br.com.famel.model.util.Logger;

import java.time.LocalDate;

/**
 * Exemplos de uso do Builder Pattern para criar Tasks
 * Este arquivo demonstra como usar o Builder de forma eficiente
 */
public class ExemploUsoBuilder {

    public static void main(String[] args) {
        Logger.info("=== Exemplos de uso do Builder Pattern ===\n");

        // Exemplo 1: Task simples
        exemploTaskSimples();

        // Exemplo 2: Task em progresso
        exemploTaskEmProgresso();

        // Exemplo 3: Task pronta
        exemploTaskPronta();

        // Exemplo 4: Task com data customizada
        exemploTaskComDataCustomizada();

        // Exemplo 5: Criando múltiplas tasks
        exemploMultiplasTasks();
    }

    /**
     * Exemplo 1: Criando uma task simples (status padrão: Para fazer)
     */
    private static void exemploTaskSimples() {
        System.out.println("\n--- Exemplo 1: Task Simples ---");

        Task task = Task.builder()
                .nome("Estudar Java")
                .descricao("Revisar conceitos de POO")
                .build();

        System.out.println(task);
        Logger.success("Task criada com ID: " + task.getId());
    }

    /**
     * Exemplo 2: Criando uma task em progresso
     */
    private static void exemploTaskEmProgresso() {
        System.out.println("\n--- Exemplo 2: Task Em Progresso ---");

        Task task = Task.builder()
                .nome("Implementar Repository Pattern")
                .descricao("Adicionar camada de repositório ao projeto")
                .emProgresso() // Marca como "Fazendo"
                .build();

        System.out.println(task.toStringSimples());
        Logger.success("Task em progresso criada");
    }

    /**
     * Exemplo 3: Criando uma task já finalizada
     */
    private static void exemploTaskPronta() {
        System.out.println("\n--- Exemplo 3: Task Pronta ---");

        Task task = Task.builder()
                .nome("Configurar ambiente de desenvolvimento")
                .descricao("Instalar JDK, IDE e Maven")
                .pronta() // Marca como "Pronta" e define data de finalização
                .build();

        System.out.println(task.toStringSimples());
        task.getDataDeFinalizacao().ifPresent(data ->
                System.out.println("Finalizada em: " + data)
        );
    }

    /**
     * Exemplo 4: Task com datas customizadas
     */
    private static void exemploTaskComDataCustomizada() {
        System.out.println("\n--- Exemplo 4: Task com Data Customizada ---");

        Task task = Task.builder()
                .nome("Projeto iniciado semana passada")
                .descricao("Task criada com data no passado")
                .dataDeCriacao(LocalDate.now().minusDays(7))
                .status(Status.FAZENDO)
                .build();

        System.out.println("Criada em: " + task.getDataDeCriacao());
        System.out.println("Status: " + task.getStatus());
    }

    /**
     * Exemplo 5: Criando múltiplas tasks e adicionando ao repositório
     */
    private static void exemploMultiplasTasks() {
        System.out.println("\n--- Exemplo 5: Múltiplas Tasks ---");

        TaskRepository repo = new TaskRepository();

        // Task 1: Urgente
        Task urgente = Task.builder()
                .nome("Bug crítico em produção")
                .descricao("Corrigir erro de autenticação")
                .emProgresso()
                .build();

        // Task 2: Planejamento
        Task planejamento = Task.builder()
                .nome("Reunião de sprint planning")
                .descricao("Definir tarefas para próxima semana")
                .build();

        // Task 3: Concluída
        Task concluida = Task.builder()
                .nome("Documentação atualizada")
                .descricao("README.md com todas as instruções")
                .pronta()
                .build();

        // Adiciona todas ao repositório
        repo.adicionar(urgente);
        repo.adicionar(planejamento);
        repo.adicionar(concluida);

        System.out.println("\nTasks criadas e adicionadas:");
        repo.listarTodas().forEach(t -> System.out.println("- " + t.toStringSimples()));

        // Mostra estatísticas
        System.out.println(repo.obterEstatisticas());
    }

    /**
     * Exemplo 6: Comparação - COM Builder vs SEM Builder
     */
    private static void compararComESemBuilder() {
        System.out.println("\n--- Comparação: COM vs SEM Builder ---");

        // SEM Builder (forma antiga)
        System.out.println("\nSEM Builder:");
        Task semBuilder = new Task();
        semBuilder.gerarId();
        semBuilder.setNome("Tarefa teste");
        semBuilder.setDescricao("Descrição teste");
        semBuilder.setStatus(Status.PARA_FAZER);
        semBuilder.setDataDeCriacao(LocalDate.now());

        // COM Builder (forma nova)
        System.out.println("\nCOM Builder:");
        Task comBuilder = Task.builder()
                .nome("Tarefa teste")
                .descricao("Descrição teste")
                .build();

        System.out.println("\n✓ Builder é mais limpo e legível!");
        System.out.println("✓ Validações automáticas no build()");
        System.out.println("✓ Impossível esquecer campos obrigatórios");
    }
}