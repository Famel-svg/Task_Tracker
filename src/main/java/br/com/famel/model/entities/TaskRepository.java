package br.com.famel.model.entities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repositório responsável por gerenciar todas as operações com Tasks
 * Centraliza o acesso aos dados e operações CRUD
 */
public class TaskRepository {
    private List<Task> tasks;
    private final String caminhoArquivo;
    private boolean modificado;

    /**
     * Construtor que inicializa o repositório
     * @param caminhoArquivo Caminho do arquivo JSON
     */
    public TaskRepository(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        this.tasks = new ArrayList<>();
        this.modificado = false;
        carregarTasks();
    }

    /**
     * Construtor padrão que usa "tasks.json"
     */
    public TaskRepository() {
        this("tasks.json");
    }

    /**
     * Carrega as tasks do arquivo JSON
     */
    private void carregarTasks() {
        try {
            this.tasks = JsonManager.lerDoJson();
            System.out.println("✓ " + tasks.size() + " task(s) carregada(s) com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao carregar tasks: " + e.getMessage());
            this.tasks = new ArrayList<>();
        }
    }

    /**
     * Adiciona uma nova task
     * @param task Task a ser adicionada
     * @return true se adicionou com sucesso
     */
    public boolean adicionar(Task task) {
        if (task == null) {
            System.err.println("Erro: Task não pode ser null");
            return false;
        }

        try {
            tasks.add(task);
            modificado = true;
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao adicionar task: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca uma task por ID
     * @param id ID da task
     * @return Optional contendo a task se encontrada
     */
    public Optional<Task> buscarPorId(int id) {
        return tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst();
    }

    /**
     * Atualiza uma task existente
     * @param id ID da task a ser atualizada
     * @param taskAtualizada Task com os novos dados
     * @return true se atualizou com sucesso
     */
    public boolean atualizar(int id, Task taskAtualizada) {
        Optional<Task> taskOpt = buscarPorId(id);

        if (taskOpt.isEmpty()) {
            System.err.println("Task com ID " + id + " não encontrada.");
            return false;
        }

        Task task = taskOpt.get();
        task.setNome(taskAtualizada.getNome());
        task.setDescricao(taskAtualizada.getDescricao());
        task.setStatus(taskAtualizada.getStatus().getDescricao());

        modificado = true;
        return true;
    }

    /**
     * Atualiza o nome de uma task
     */
    public boolean atualizarNome(int id, String novoNome) {
        Optional<Task> taskOpt = buscarPorId(id);
        if (taskOpt.isEmpty()) return false;

        taskOpt.get().setNome(novoNome);
        modificado = true;
        return true;
    }

    /**
     * Atualiza a descrição de uma task
     */
    public boolean atualizarDescricao(int id, String novaDescricao) {
        Optional<Task> taskOpt = buscarPorId(id);
        if (taskOpt.isEmpty()) return false;

        taskOpt.get().setDescricao(novaDescricao);
        modificado = true;
        return true;
    }

    /**
     * Atualiza o status de uma task
     */
    public boolean atualizarStatus(int id, Status novoStatus) {
        Optional<Task> taskOpt = buscarPorId(id);
        if (taskOpt.isEmpty()) return false;

        taskOpt.get().setStatus(novoStatus.getDescricao());
        modificado = true;
        return true;
    }

    /**
     * Remove uma task por ID
     * @param id ID da task
     * @return true se removeu com sucesso
     */
    public boolean remover(int id) {
        boolean removeu = tasks.removeIf(t -> t.getId() == id);
        if (removeu) {
            modificado = true;
        }
        return removeu;
    }

    /**
     * Lista todas as tasks
     * @return Cópia da lista de tasks (para evitar modificação externa)
     */
    public List<Task> listarTodas() {
        return new ArrayList<>(tasks);
    }

    /**
     * Lista tasks por status
     * @param status Status desejado
     * @return Lista de tasks com o status especificado
     */
    public List<Task> listarPorStatus(Status status) {
        return tasks.stream()
                .filter(t -> t.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    /**
     * Lista todas as tasks prontas
     */
    public List<Task> listarProntas() {
        return listarPorStatus(Status.PRONTA);
    }

    /**
     * Lista todas as tasks em progresso
     */
    public List<Task> listarEmProgresso() {
        return listarPorStatus(Status.FAZENDO);
    }

    /**
     * Lista todas as tasks pendentes
     */
    public List<Task> listarPendentes() {
        return listarPorStatus(Status.PARA_FAZER);
    }

    /**
     * Busca tasks por texto no nome ou descrição
     * @param texto Texto a buscar
     * @return Lista de tasks que contêm o texto
     */
    public List<Task> buscarPorTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String buscaLower = texto.toLowerCase();
        return tasks.stream()
                .filter(t ->
                        t.getNome().toLowerCase().contains(buscaLower) ||
                                t.getDescricao().toLowerCase().contains(buscaLower)
                )
                .collect(Collectors.toList());
    }

    /**
     * Lista tasks ordenadas
     * @param comparator Comparador para ordenação
     * @return Lista ordenada de tasks
     */
    public List<Task> listarOrdenadas(Comparator<Task> comparator) {
        return tasks.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    /**
     * Lista tasks ordenadas por data de criação (mais recentes primeiro)
     */
    public List<Task> listarPorDataCriacao() {
        return listarOrdenadas(
                Comparator.comparing(Task::getDataDeCriacao).reversed()
        );
    }

    /**
     * Lista tasks ordenadas por nome
     */
    public List<Task> listarPorNome() {
        return listarOrdenadas(
                Comparator.comparing(Task::getNome)
        );
    }

    /**
     * Conta quantas tasks existem
     */
    public int contar() {
        return tasks.size();
    }

    /**
     * Conta tasks por status
     */
    public int contarPorStatus(Status status) {
        return (int) tasks.stream()
                .filter(t -> t.getStatus().equals(status))
                .count();
    }

    /**
     * Verifica se há tasks cadastradas
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Verifica se houve modificações
     */
    public boolean foiModificado() {
        return modificado;
    }

    /**
     * Salva as tasks no arquivo JSON
     * @throws IOException se houver erro ao salvar
     */
    public void salvar() throws IOException {
        JsonManager.salvarEmJson(tasks);
        modificado = false;
        System.out.println("✓ Alterações salvas com sucesso!");
    }

    /**
     * Salva apenas se houve modificações
     * @return true se salvou, false se não havia modificações
     */
    public boolean salvarSeModificado() {
        if (!modificado) {
            System.out.println("Nenhuma alteração foi feita.");
            return false;
        }

        try {
            salvar();
            return true;
        } catch (IOException e) {
            System.err.println("ERRO ao salvar: " + e.getMessage());
            return false;
        }
    }

    /**
     * Recarrega as tasks do arquivo (descarta alterações não salvas)
     */
    public void recarregar() {
        carregarTasks();
        modificado = false;
        System.out.println("Tasks recarregadas do arquivo.");
    }

    /**
     * Limpa todas as tasks (cuidado!)
     */
    public void limparTudo() {
        tasks.clear();
        modificado = true;
        System.out.println("Todas as tasks foram removidas da memória.");
    }

    /**
     * Retorna estatísticas sobre as tasks
     */
    public String obterEstatisticas() {
        int total = contar();
        int pendentes = contarPorStatus(Status.PARA_FAZER);
        int emProgresso = contarPorStatus(Status.FAZENDO);
        int prontas = contarPorStatus(Status.PRONTA);

        StringBuilder sb = new StringBuilder();
        sb.append("\n=== ESTATÍSTICAS ===\n");
        sb.append("Total de tasks: ").append(total).append("\n");
        sb.append("Para fazer: ").append(pendentes).append("\n");
        sb.append("Em progresso: ").append(emProgresso).append("\n");
        sb.append("Prontas: ").append(prontas).append("\n");

        if (total > 0) {
            double percentualCompleto = (prontas * 100.0) / total;
            sb.append("Progresso: ").append(String.format("%.1f%%", percentualCompleto)).append("\n");
        }

        return sb.toString();
    }
}