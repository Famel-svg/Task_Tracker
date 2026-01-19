package br.com.famel.model.entities;

import br.com.famel.model.ressources.Textos;
import static java.lang.IO.println;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class OpcoesDaCase {

    /**
     * Adiciona uma nova task usando o Repository
     */
    public static void adicionarTask(TaskRepository repo, Scanner sc) {
        try {
            Task task = Task.builder()
                    .nome(InputValidator.lerTextoNaoVazio(sc, "Nome da Task: "))
                    .descricao(InputValidator.lerTextoNaoVazio(sc, "Descrição da Task: "))
                    .status(InputValidator.lerStatusSeguro(sc))
                    .build();

            // Verifica se adicionou com sucesso usando Optional
            Optional.of(repo.adicionar(task))
                    .filter(Boolean::booleanValue)
                    .ifPresentOrElse(
                            sucesso -> println(Textos.case1),
                            () -> System.err.println("Erro ao adicionar task no repositório.")
                    );

        } catch (Exception e) {
            System.err.println("Erro ao adicionar task: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Atualiza uma task existente usando Map funcional para opções
     */
    public static boolean atualizarTask(TaskRepository repo, Scanner sc) {
        if (repo.isEmpty()) {
            println("Nenhuma task cadastrada para atualizar.");
            return false;
        }

        try {
            mostrarTasksDisponiveis(repo);
            int idEscolhido = InputValidator.lerIdSeguro(sc);

            return buscarEAtualizarTask(repo, sc, idEscolhido);

        } catch (Exception e) {
            System.err.println("Erro ao atualizar task: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca task e executa atualização
     */
    private static boolean buscarEAtualizarTask(TaskRepository repo, Scanner sc, int id) {
        return repo.buscarPorId(id)
                .map(task -> {
                    println("\nDados atuais da task:");
                    println(task);
                    return executarAtualizacao(repo, sc, id);
                })
                .orElseGet(() -> {
                    System.err.println("ID não encontrado. Digite um ID válido da lista acima.");
                    return false;
                });
    }

    /**
     * Executa atualização usando Map de estratégias
     */
    private static boolean executarAtualizacao(TaskRepository repo, Scanner sc, int id) {
        // Map de estratégias de atualização
        Map<Integer, BiFunction<TaskRepository, Integer, Boolean>> estrategias = Map.of(
                1, (r, taskId) -> {
                    String novoNome = InputValidator.lerTextoNaoVazio(sc, "Digite o novo nome da Task: ");
                    return r.atualizarNome(taskId, novoNome);
                },
                2, (r, taskId) -> {
                    String novaDescricao = InputValidator.lerTextoNaoVazio(sc, "Digite a nova descrição da Task: ");
                    return r.atualizarDescricao(taskId, novaDescricao);
                },
                3, (r, taskId) -> {
                    Status novoStatus = InputValidator.lerStatusSeguro(sc);
                    return r.atualizarStatus(taskId, novoStatus);
                }
        );

        println(Textos.AtualizarOpcoes);
        int opcaoAtualizacao = InputValidator.lerOpcaoSegura(sc, 1, 3);

        return Optional.ofNullable(estrategias.get(opcaoAtualizacao))
                .map(estrategia -> {
                    boolean sucesso = estrategia.apply(repo, id);
                    if (sucesso) {
                        println(Textos.case2);
                    }
                    return sucesso;
                })
                .orElseGet(() -> {
                    System.err.println("Opção inválida.");
                    return false;
                });
    }

    /**
     * Remove uma task do repositório
     */
    public static boolean removerTask(TaskRepository repo, Scanner sc) {
        if (repo.isEmpty()) {
            println("Nenhuma task cadastrada para remover.");
            return false;
        }

        try {
            mostrarTasksDisponiveis(repo);
            int idEscolhido = InputValidator.lerIdSeguro(sc);

            return confirmarERemover(repo, sc, idEscolhido);

        } catch (Exception e) {
            System.err.println("Erro ao remover task: " + e.getMessage());
            return false;
        }
    }

    /**
     * Confirma e remove a task
     */
    private static boolean confirmarERemover(TaskRepository repo, Scanner sc, int id) {
        return repo.buscarPorId(id)
                .map(task -> {
                    println("\nTask que será removida:");
                    println(task);

                    return confirmarRemocao(sc)
                            .filter(Boolean::booleanValue)
                            .map(confirmar -> {
                                boolean removido = repo.remover(id);
                                if (removido) {
                                    println(Textos.case3);
                                } else {
                                    System.err.println("Erro ao remover a task.");
                                }
                                return removido;
                            })
                            .orElseGet(() -> {
                                println("Remoção cancelada.");
                                return false;
                            });
                })
                .orElseGet(() -> {
                    System.err.println("ID não encontrado. Digite um ID válido da lista acima.");
                    return false;
                });
    }

    /**
     * Confirma remoção usando Optional
     */
    private static Optional<Boolean> confirmarRemocao(Scanner sc) {
        println("\nTem certeza que deseja remover esta task? (S/N)");
        String confirmacao = sc.nextLine().trim().toUpperCase();
        return Optional.of(confirmacao.equals("S"));
    }

    /**
     * Mostra tasks disponíveis
     */
    private static void mostrarTasksDisponiveis(TaskRepository repo) {
        println("\n=== Tasks disponíveis ===");
        repo.listarTodas().forEach(t -> println(t.toStringSimples()));
        println();
    }

    /**
     * Lista tasks com formatação usando Consumer
     */
    private static void listarComFormatacao(
            TaskRepository repo,
            String titulo,
            List<Task> tasks,
            Consumer<Task> exibir
    ) {
        println("\n╔════════════════════════════════════════╗");
        println(String.format("║  %-36s  ║", titulo));
        println("╚════════════════════════════════════════╝");

        Optional.of(tasks)
                .filter(lista -> !lista.isEmpty())
                .ifPresentOrElse(
                        lista -> lista.forEach(t -> {
                            exibir.accept(t);
                            println();
                        }),
                        () -> println("Nenhuma task encontrada.")
                );
    }

    /**
     * Lista todas as tasks prontas
     */
    public static void listarProntas(TaskRepository repo) {
        try {
            List<Task> prontas = repo.listarProntas();
            listarComFormatacao(
                    repo,
                    "TASKS PRONTAS (" + prontas.size() + ")",
                    prontas,
                    task -> println(task)
            );
        } catch (Exception e) {
            System.err.println("Erro ao listar tasks prontas: " + e.getMessage());
        }
    }

    /**
     * Lista todas as tasks em progresso
     */
    public static void listarFazendo(TaskRepository repo) {
        try {
            List<Task> emProgresso = repo.listarEmProgresso();
            listarComFormatacao(
                    repo,
                    "TASKS EM PROGRESSO (" + emProgresso.size() + ")",
                    emProgresso,
                    task -> println(task)
            );
        } catch (Exception e) {
            System.err.println("Erro ao listar tasks em progresso: " + e.getMessage());
        }
    }

    /**
     * Lista todas as tasks
     */
    public static void listarTodas(TaskRepository repo) {
        try {
            if (repo.isEmpty()) {
                println("\nNenhuma task cadastrada.");
                return;
            }

            List<Task> tasks = repo.listarTodas();
            listarComFormatacao(
                    repo,
                    "TODAS AS TASKS (" + tasks.size() + ")",
                    tasks,
                    task -> println(task)
            );

        } catch (Exception e) {
            System.err.println("Erro ao listar tasks: " + e.getMessage());
        }
    }

    /**
     * Mostra estatísticas das tasks
     */
    public static void mostrarEstatisticas(TaskRepository repo) {
        try {
            println(repo.obterEstatisticas());
        } catch (Exception e) {
            System.err.println("Erro ao obter estatísticas: " + e.getMessage());
        }
    }
}