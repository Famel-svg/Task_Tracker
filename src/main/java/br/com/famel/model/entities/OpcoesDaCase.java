package br.com.famel.model.entities;

import br.com.famel.model.Textos;

import static java.lang.IO.println;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class OpcoesDaCase {

    /**
     * Adiciona uma nova task usando o Repository
     */
    public static void adicionarTask(TaskRepository repo, Scanner sc) {
        try {
            Task task = new Task();
            task.gerarId();

            // Pega o nome da Task com validação
            String nome = InputValidator.lerTextoNaoVazio(sc, "Nome da Task: ");
            task.setNome(nome);

            // Pega a descrição da Task com validação
            String descricao = InputValidator.lerTextoNaoVazio(sc, "Descrição da Task: ");
            task.setDescricao(descricao);

            // Pega o status da Task com validação (retorna Status enum)
            Status status = InputValidator.lerStatusSeguro(sc);
            task.setStatus(status);

            // Pega a data atual
            task.setDataDeCriacao(java.time.LocalDate.now());

            // Adiciona no repositório
            if (repo.adicionar(task)) {
                println(Textos.case1);
            } else {
                System.err.println("Erro ao adicionar task no repositório.");
            }

        } catch (Exception e) {
            System.err.println("Erro ao adicionar task: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Atualiza uma task existente usando o Repository
     * @return true se atualizou, false caso contrário
     */
    public static boolean atualizarTask(TaskRepository repo, Scanner sc) {
        if (repo.isEmpty()) {
            println("Nenhuma task cadastrada para atualizar.");
            return false;
        }

        try {
            // Mostra tasks disponíveis
            println("\n=== Tasks disponíveis ===");
            List<Task> tasks = repo.listarTodas();
            for (Task t : tasks) {
                println(t.toStringSimples());
            }
            println();

            // Lê o ID com validação
            int idEscolhido = InputValidator.lerIdSeguro(sc);

            // Busca a task usando Optional
            Optional<Task> taskOpt = repo.buscarPorId(idEscolhido);

            if (taskOpt.isEmpty()) {
                System.err.println("ID não encontrado. Digite um ID válido da lista acima.");
                return false;
            }

            Task task = taskOpt.get();

            // Mostra dados atuais
            println("\nDados atuais da task:");
            println(task);

            // Escolhe o que atualizar
            println(Textos.AtualizarOpcoes);
            int opcaoAtualizacao = InputValidator.lerOpcaoSegura(sc, 1, 3);

            boolean sucesso = false;
            switch (opcaoAtualizacao) {
                case 1:
                    String novoNome = InputValidator.lerTextoNaoVazio(sc, "Digite o novo nome da Task: ");
                    sucesso = repo.atualizarNome(idEscolhido, novoNome);
                    break;

                case 2:
                    String novaDescricao = InputValidator.lerTextoNaoVazio(sc, "Digite a nova descrição da Task: ");
                    sucesso = repo.atualizarDescricao(idEscolhido, novaDescricao);
                    break;

                case 3:
                    Status novoStatus = InputValidator.lerStatusSeguro(sc);
                    sucesso = repo.atualizarStatus(idEscolhido, novoStatus);
                    break;

                default:
                    System.err.println("Opção inválida.");
                    return false;
            }

            if (sucesso) {
                println(Textos.case2);
            }
            return sucesso;

        } catch (Exception e) {
            System.err.println("Erro ao atualizar task: " + e.getMessage());
            return false;
        }
    }

    /**
     * Remove uma task do repositório
     * @return true se removeu, false caso contrário
     */
    public static boolean removerTask(TaskRepository repo, Scanner sc) {
        if (repo.isEmpty()) {
            println("Nenhuma task cadastrada para remover.");
            return false;
        }

        try {
            println("\n=== Tasks disponíveis ===");
            List<Task> tasks = repo.listarTodas();
            for (Task t : tasks) {
                println(t.toStringSimples());
            }
            println();

            // Lê o ID com validação
            int idEscolhido = InputValidator.lerIdSeguro(sc);

            // Verifica se existe
            Optional<Task> taskOpt = repo.buscarPorId(idEscolhido);
            if (taskOpt.isEmpty()) {
                System.err.println("ID não encontrado. Digite um ID válido da lista acima.");
                return false;
            }

            // Mostra a task que será removida
            println("\nTask que será removida:");
            println(taskOpt.get());

            // Confirmação
            println("\nTem certeza que deseja remover esta task? (S/N)");
            String confirmacao = sc.nextLine().trim().toUpperCase();

            if (!confirmacao.equals("S")) {
                println("Remoção cancelada.");
                return false;
            }

            // Remove usando o repositório
            if (repo.remover(idEscolhido)) {
                println(Textos.case3);
                return true;
            } else {
                System.err.println("Erro ao remover a task.");
                return false;
            }

        } catch (Exception e) {
            System.err.println("Erro ao remover task: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todas as tasks prontas usando o Repository
     */
    public static void listarProntas(TaskRepository repo) {
        try {
            List<Task> prontas = repo.listarProntas();

            println("\n╔════════════════════════════════════════╗");
            println("║        TASKS PRONTAS (" + prontas.size() + ")              ║");
            println("╚════════════════════════════════════════╝");

            if (prontas.isEmpty()) {
                println("Nenhuma task pronta.");
            } else {
                for (Task t : prontas) {
                    println(t);
                    println();
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao listar tasks prontas: " + e.getMessage());
        }
    }

    /**
     * Lista todas as tasks em progresso usando o Repository
     */
    public static void listarFazendo(TaskRepository repo) {
        try {
            List<Task> emProgresso = repo.listarEmProgresso();

            println("\n╔════════════════════════════════════════╗");
            println("║     TASKS EM PROGRESSO (" + emProgresso.size() + ")        ║");
            println("╚════════════════════════════════════════╝");

            if (emProgresso.isEmpty()) {
                println("Nenhuma task sendo feita.");
            } else {
                for (Task t : emProgresso) {
                    println(t);
                    println();
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao listar tasks em progresso: " + e.getMessage());
        }
    }

    /**
     * Lista todas as tasks do repositório
     */
    public static void listarTodas(TaskRepository repo) {
        try {
            if (repo.isEmpty()) {
                println("\nNenhuma task cadastrada.");
                return;
            }

            List<Task> tasks = repo.listarTodas();

            println("\n╔════════════════════════════════════════╗");
            println("║       TODAS AS TASKS (" + tasks.size() + ")            ║");
            println("╚════════════════════════════════════════╝\n");

            for (Task t : tasks) {
                println(t);
                println();
            }

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