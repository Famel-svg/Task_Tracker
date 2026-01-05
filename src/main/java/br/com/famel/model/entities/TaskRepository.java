package br.com.famel.model.entities;

import br.com.famel.model.util.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repositório responsável por gerenciar todas as operações com Tasks
 */
public class TaskRepository {
    private List<Task> tasks;
    private final String caminhoArquivo;
    private boolean modificado;

    public TaskRepository(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        this.tasks = new ArrayList<>();
        this.modificado = false;
        Logger.debug("Inicializando TaskRepository com arquivo: " + caminhoArquivo);
        carregarTasks();
    }

    public TaskRepository() {
        this("tasks.json");
    }

    private void carregarTasks() {
        try {
            Logger.info("Carregando tasks do arquivo JSON...");
            this.tasks = JsonManager.lerDoJson();
            Logger.success(tasks.size() + " task(s) carregada(s) com sucesso");
        } catch (Exception e) {
            Logger.error("Erro ao carregar tasks", e);
            this.tasks = new ArrayList<>();
        }
    }

    /**
     * Adiciona uma nova task
     */
    public boolean adicionar(Task task) {
        if (task == null) {
            Logger.warn("Tentativa de adicionar task null");
            return false;
        }

        try {
            tasks.add(task);
            modificado = true;
            Logger.operacao("CRIAR", "Task #" + task.getId() + " - " + task.getNome());
            return true;
        } catch (Exception e) {
            Logger.error("Erro ao adicionar task", e);
            return false;
        }
    }

    /**
     * Busca uma task por ID usando Optional
     */
    public Optional<Task> buscarPorId(int id) {
        Logger.debug("Buscando task com ID: " + id);
        Optional<Task> resultado = tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst();

        if (resultado.isEmpty()) {
            Logger.warn("Task com ID " + id + " não encontrada");
        }

        return resultado;
    }

    /**
     * Atualiza uma task existente
     */
    public boolean atualizar(int id, Task taskAtualizada) {
        Optional<Task> taskOpt = buscarPorId(id);

        if (taskOpt.isEmpty()) {
            Logger.warn("Tentativa de atualizar task inexistente: ID " + id);
            return false;
        }

        Task task = taskOpt.get();
        task.setNome(taskAtualizada.getNome());
        task.setDescricao(taskAtualizada.getDescricao());
        task.setStatus(taskAtualizada.getStatus().getDescricao());

        modificado = true;
        Logger.operacao("ATUALIZAR", "Task #" + id + " - " + task.getNome());
        return true;
    }

    public boolean atualizarNome(int id, String novoNome) {
        return buscarPorId(id)
                .map(task -> {
                    task.setNome(novoNome);
                    modificado = true;
                    Logger.operacao("ATUALIZAR NOME", "Task #" + id + " -> " + novoNome);
                    return true;
                })
                .orElse(false);
    }

    public boolean atualizarDescricao(int id, String novaDescricao) {
        return buscarPorId(id)
                .map(task -> {
                    task.setDescricao(novaDescricao);
                    modificado = true;
                    Logger.operacao("ATUALIZAR DESCRIÇÃO", "Task #" + id);
                    return true;
                })
                .orElse(false);
    }

    public boolean atualizarStatus(int id, Status novoStatus) {
        return buscarPorId(id)
                .map(task -> {
                    task.setStatus(novoStatus.getDescricao());
                    modificado = true;
                    Logger.operacao("ATUALIZAR STATUS", "Task #" + id + " -> " + novoStatus.getDescricao());
                    return true;
                })
                .orElse(false);
    }

    /**
     * Remove uma task por ID
     */
    public boolean remover(int id) {
        Optional<Task> taskOpt = buscarPorId(id);

        if (taskOpt.isEmpty()) {
            return false;
        }

        Task task = taskOpt.get();
        boolean removeu = tasks.removeIf(t -> t.getId() == id);

        if (removeu) {
            modificado = true;
            Logger.operacao("DELETAR", "Task #" + id + " - " + task.getNome());
        }

        return removeu;
    }

    public List<Task> listarTodas() {
        Logger.debug("Listando todas as tasks: " + tasks.size() + " encontradas");
        return new ArrayList<>(tasks);
    }

    public List<Task> listarPorStatus(Status status) {
        Logger.debug("Filtrando tasks por status: " + status.getDescricao());
        return tasks.stream()
                .filter(t -> t.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    public List<Task> listarProntas() {
        return listarPorStatus(Status.PRONTA);
    }

    public List<Task> listarEmProgresso() {
        return listarPorStatus(Status.FAZENDO);
    }

    public List<Task> listarPendentes() {
        return listarPorStatus(Status.PARA_FAZER);
    }

    /**
     * Busca tasks por texto retornando Optional se não encontrar nada
     */
    public Optional<List<Task>> buscarPorTextoOptional(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return Optional.empty();
        }

        List<Task> resultados = buscarPorTexto(texto);
        return resultados.isEmpty() ? Optional.empty() : Optional.of(resultados);
    }

    public List<Task> buscarPorTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String buscaLower = texto.toLowerCase();
        Logger.debug("Buscando tasks com texto: " + texto);

        List<Task> resultados = tasks.stream()
                .filter(t ->
                        t.getNome().toLowerCase().contains(buscaLower) ||
                                t.getDescricao().toLowerCase().contains(buscaLower)
                )
                .collect(Collectors.toList());

        Logger.info("Busca retornou " + resultados.size() + " resultado(s)");
        return resultados;
    }

    public List<Task> listarOrdenadas(Comparator<Task> comparator) {
        return tasks.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<Task> listarPorDataCriacao() {
        Logger.debug("Ordenando tasks por data de criação");
        return listarOrdenadas(
                Comparator.comparing(Task::getDataDeCriacao).reversed()
        );
    }

    public List<Task> listarPorNome() {
        Logger.debug("Ordenando tasks por nome");
        return listarOrdenadas(
                Comparator.comparing(Task::getNome)
        );
    }

    public int contar() {
        return tasks.size();
    }

    public int contarPorStatus(Status status) {
        return (int) tasks.stream()
                .filter(t -> t.getStatus().equals(status))
                .count();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public boolean foiModificado() {
        return modificado;
    }

    /**
     * Salva as tasks no arquivo JSON
     */
    public void salvar() throws IOException {
        Logger.info("Salvando tasks no arquivo JSON...");
        try {
            JsonManager.salvarEmJson(tasks);
            modificado = false;
            Logger.success("Alterações salvas com sucesso!");
        } catch (IOException e) {
            Logger.error("Falha ao salvar tasks", e);
            throw e;
        }
    }

    public boolean salvarSeModificado() {
        if (!modificado) {
            Logger.info("Nenhuma alteração detectada, não é necessário salvar");
            return false;
        }

        try {
            salvar();
            return true;
        } catch (IOException e) {
            Logger.error("ERRO ao salvar", e);
            return false;
        }
    }

    public void recarregar() {
        Logger.info("Recarregando tasks do arquivo...");
        carregarTasks();
        modificado = false;
    }

    public void limparTudo() {
        Logger.warn("Limpando todas as tasks da memória");
        tasks.clear();
        modificado = true;
    }

    /**
     * Retorna estatísticas sobre as tasks
     */
    public String obterEstatisticas() {
        int total = contar();
        int pendentes = contarPorStatus(Status.PARA_FAZER);
        int emProgresso = contarPorStatus(Status.FAZENDO);
        int prontas = contarPorStatus(Status.PRONTA);

        Logger.debug("Gerando estatísticas das tasks");

        StringBuilder sb = new StringBuilder();
        sb.append("\n╔════════════════════════════════════════╗\n");
        sb.append("║           ESTATÍSTICAS               ║\n");
        sb.append("╠════════════════════════════════════════╣\n");
        sb.append(String.format("║ Total de tasks:        %-14d ║\n", total));
        sb.append(String.format("║ Para fazer:            %-14d ║\n", pendentes));
        sb.append(String.format("║ Em progresso:          %-14d ║\n", emProgresso));
        sb.append(String.format("║ Prontas:               %-14d ║\n", prontas));

        if (total > 0) {
            double percentualCompleto = (prontas * 100.0) / total;
            sb.append(String.format("║ Progresso:             %.1f%%         ║\n", percentualCompleto));
        }

        sb.append("╚════════════════════════════════════════╝");

        return sb.toString();
    }

    /**
     * Retorna a primeira task com determinado status usando Optional
     */
    public Optional<Task> buscarPrimeiraComStatus(Status status) {
        return tasks.stream()
                .filter(t -> t.getStatus().equals(status))
                .findFirst();
    }

    /**
     * Verifica se existe alguma task com determinado status
     */
    public boolean existeComStatus(Status status) {
        return tasks.stream()
                .anyMatch(t -> t.getStatus().equals(status));
    }
}