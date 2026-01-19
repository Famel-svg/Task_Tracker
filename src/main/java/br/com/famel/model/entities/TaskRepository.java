package br.com.famel.model.entities;

import br.com.famel.model.util.Logger;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Repositório refatorado com lambdas e functional programming
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
        return Optional.ofNullable(task)
                .map(t -> {
                    tasks.add(t);
                    modificado = true;
                    Logger.operacao("CRIAR", "Task #" + t.getId() + " - " + t.getNome());
                    return true;
                })
                .orElseGet(() -> {
                    Logger.warn("Tentativa de adicionar task null");
                    return false;
                });
    }

    /**
     * Busca uma task por ID usando Optional
     */
    public Optional<Task> buscarPorId(int id) {
        Logger.debug("Buscando task com ID: " + id);

        return tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .or(() -> {
                    Logger.warn("Task com ID " + id + " não encontrada");
                    return Optional.empty();
                });
    }

    /**
     * Método genérico para atualizar qualquer campo de uma task
     */
    private boolean atualizarCampo(int id, String operacao, Consumer<Task> atualizacao) {
        return buscarPorId(id)
                .map(task -> {
                    atualizacao.accept(task);
                    modificado = true;
                    Logger.operacao(operacao, "Task #" + id);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Atualiza o nome usando o método genérico
     */
    public boolean atualizarNome(int id, String novoNome) {
        return atualizarCampo(id, "ATUALIZAR NOME",
                task -> task.setNome(novoNome));
    }

    /**
     * Atualiza a descrição usando o método genérico
     */
    public boolean atualizarDescricao(int id, String novaDescricao) {
        return atualizarCampo(id, "ATUALIZAR DESCRIÇÃO",
                task -> task.setDescricao(novaDescricao));
    }

    /**
     * Atualiza o status usando o método genérico
     */
    public boolean atualizarStatus(int id, Status novoStatus) {
        return atualizarCampo(id, "ATUALIZAR STATUS",
                task -> task.setStatus(novoStatus));
    }

    /**
     * Atualiza uma task completa
     */
    public boolean atualizar(int id, Task taskAtualizada) {
        return atualizarCampo(id, "ATUALIZAR", task -> {
            task.setNome(taskAtualizada.getNome());
            task.setDescricao(taskAtualizada.getDescricao());
            task.setStatus(taskAtualizada.getStatus());
        });
    }

    /**
     * Remove uma task por ID usando Optional
     */
    public boolean remover(int id) {
        return buscarPorId(id)
                .map(task -> {
                    boolean removeu = tasks.removeIf(t -> t.getId() == id);
                    if (removeu) {
                        modificado = true;
                        Logger.operacao("DELETAR", "Task #" + id + " - " + task.getNome());
                    }
                    return removeu;
                })
                .orElse(false);
    }

    /**
     * Lista todas as tasks (cópia imutável)
     */
    public List<Task> listarTodas() {
        Logger.debug("Listando todas as tasks: " + tasks.size() + " encontradas");
        return List.copyOf(tasks);
    }

    /**
     * Método genérico para filtrar tasks
     */
    private List<Task> filtrar(Predicate<Task> predicado, String descricao) {
        Logger.debug("Filtrando tasks: " + descricao);
        return tasks.stream()
                .filter(predicado)
                .collect(Collectors.toList());
    }

    /**
     * Lista por status usando o método genérico
     */
    public List<Task> listarPorStatus(Status status) {
        return filtrar(
                t -> t.getStatus().equals(status),
                "status=" + status.getDescricao()
        );
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
     * Busca tasks por texto usando Optional
     */
    public Optional<List<Task>> buscarPorTextoOptional(String texto) {
        return Optional.ofNullable(texto)
                .filter(t -> !t.trim().isEmpty())
                .map(this::buscarPorTexto)
                .filter(lista -> !lista.isEmpty());
    }

    /**
     * Busca tasks por texto no nome ou descrição
     */
    public List<Task> buscarPorTexto(String texto) {
        return Optional.ofNullable(texto)
                .filter(t -> !t.trim().isEmpty())
                .map(t -> {
                    String buscaLower = t.toLowerCase();
                    Logger.debug("Buscando tasks com texto: " + t);

                    List<Task> resultados = filtrar(
                            task -> task.getNome().toLowerCase().contains(buscaLower) ||
                                    task.getDescricao().toLowerCase().contains(buscaLower),
                            "texto contém '" + t + "'"
                    );

                    Logger.info("Busca retornou " + resultados.size() + " resultado(s)");
                    return resultados;
                })
                .orElse(new ArrayList<>());
    }

    /**
     * Lista ordenadas por comparador
     */
    public List<Task> listarOrdenadas(Comparator<Task> comparator) {
        return tasks.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    /**
     * Lista por data de criação (mais recentes primeiro)
     */
    public List<Task> listarPorDataCriacao() {
        Logger.debug("Ordenando tasks por data de criação");
        return listarOrdenadas(
                Comparator.comparing(Task::getDataDeCriacao).reversed()
        );
    }

    /**
     * Lista por nome alfabeticamente
     */
    public List<Task> listarPorNome() {
        Logger.debug("Ordenando tasks por nome");
        return listarOrdenadas(Comparator.comparing(Task::getNome));
    }

    /**
     * Conta total de tasks
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

    /**
     * Salva apenas se foi modificado
     */
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

    /**
     * Recarrega tasks do arquivo
     */
    public void recarregar() {
        Logger.info("Recarregando tasks do arquivo...");
        carregarTasks();
        modificado = false;
    }

    /**
     * Limpa todas as tasks
     */
    public void limparTudo() {
        Logger.warn("Limpando todas as tasks da memória");
        tasks.clear();
        modificado = true;
    }

    /**
     * Retorna estatísticas usando Map funcional
     */
    public String obterEstatisticas() {
        Map<String, Integer> stats = Map.of(
                "total", contar(),
                "pendentes", contarPorStatus(Status.PARA_FAZER),
                "emProgresso", contarPorStatus(Status.FAZENDO),
                "prontas", contarPorStatus(Status.PRONTA)
        );

        Logger.debug("Gerando estatísticas das tasks");

        double percentual = stats.get("total") > 0
                ? (stats.get("prontas") * 100.0) / stats.get("total")
                : 0.0;

        return String.format("""
            
            ╔════════════════════════════════════════╗
            ║           ESTATÍSTICAS               ║
            ╠════════════════════════════════════════╣
            ║ Total de tasks:        %-14d ║
            ║ Para fazer:            %-14d ║
            ║ Em progresso:          %-14d ║
            ║ Prontas:               %-14d ║
            ║ Progresso:             %.1f%%         ║
            ╚════════════════════════════════════════╝""",
                stats.get("total"),
                stats.get("pendentes"),
                stats.get("emProgresso"),
                stats.get("prontas"),
                percentual
        );
    }

    /**
     * Busca primeira task com status usando Optional
     */
    public Optional<Task> buscarPrimeiraComStatus(Status status) {
        return tasks.stream()
                .filter(t -> t.getStatus().equals(status))
                .findFirst();
    }

    /**
     * Verifica se existe task com determinado status
     */
    public boolean existeComStatus(Status status) {
        return tasks.stream()
                .anyMatch(t -> t.getStatus().equals(status));
    }
}