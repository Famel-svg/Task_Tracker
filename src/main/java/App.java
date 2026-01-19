import br.com.famel.model.entities.*;
import br.com.famel.model.ressources.Textos;
import br.com.famel.model.util.Logger;
import static java.lang.IO.println;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.BiConsumer;

void main() {
    Scanner sc = new Scanner(System.in);
    Logger.inicioAplicacao();

    try {
        println(Textos.BemVindo);
        TaskRepository repo = new TaskRepository();

        // Map de ações usando lambdas - substitui o switch gigante
        Map<Integer, BiConsumer<TaskRepository, Scanner>> acoes = Map.of(
                1, OpcoesDaCase::adicionarTask,
                2, OpcoesDaCase::atualizarTask,
                3, OpcoesDaCase::removerTask,
                4, (r, s) -> OpcoesDaCase.listarTodas(r),
                5, (r, s) -> OpcoesDaCase.listarProntas(r),
                6, (r, s) -> OpcoesDaCase.listarFazendo(r),
                7, (r, s) -> OpcoesDaCase.mostrarEstatisticas(r)
        );

        int escolha = mostrarMenuELerOpcao(sc);

        while (escolha != 0) {
            executarAcao(escolha, acoes, repo, sc);
            escolha = mostrarMenuELerOpcao(sc);
        }

        Logger.info("Usuário solicitou encerramento");
        println("Encerrando programa...");

        // Salva com tratamento funcional
        salvarAlteracoes(repo, sc);

    } catch (Exception e) {
        Logger.error("Erro fatal na aplicação", e);
        System.err.println("Erro fatal na aplicação: " + e.getMessage());
        e.printStackTrace();
    } finally {
        sc.close();
        Logger.fimAplicacao();
        println("Programa encerrado.");
    }
}

/**
 * Mostra menu e lê opção do usuário
 */
private static int mostrarMenuELerOpcao(Scanner sc) {
    println(Textos.Opcoes);
    return InputValidator.lerOpcaoSegura(sc, 0, 7);
}

/**
 * Executa ação baseada na escolha usando Map funcional
 */
private static void executarAcao(
        int escolha,
        Map<Integer, BiConsumer<TaskRepository, Scanner>> acoes,
        TaskRepository repo,
        Scanner sc
) {
    try {
        Logger.debug("Usuário escolheu opção: " + escolha);

        // Busca e executa a ação, ou mostra mensagem padrão
        Optional.ofNullable(acoes.get(escolha))
                .ifPresentOrElse(
                        acao -> acao.accept(repo, sc),
                        () -> {
                            Logger.warn("Opção inválida selecionada: " + escolha);
                            println(Textos.Padrao);
                        }
                );

    } catch (Exception e) {
        Logger.error("Erro ao processar operação", e);
        System.err.println("Erro ao processar operação: " + e.getMessage());
        println("Tente novamente.");
    }
}

/**
 * Salva alterações com tratamento de erro funcional
 */
private static void salvarAlteracoes(TaskRepository repo, Scanner sc) {
    if (!repo.foiModificado()) {
        Logger.info("Nenhuma modificação detectada");
        println("Nenhuma alteração foi feita.");
        return;
    }

    try {
        repo.salvar();
    } catch (Exception e) {
        Logger.error("ERRO CRÍTICO ao salvar", e);
        System.err.println("ERRO CRÍTICO: Não foi possível salvar as alterações!");
        System.err.println("Detalhes: " + e.getMessage());

        tentarSalvarNovamente(repo, sc);
    }
}

/**
 * Tenta salvar novamente após confirmação
 */
private static void tentarSalvarNovamente(TaskRepository repo, Scanner sc) {
    println("\nDeseja tentar salvar novamente? (S/N)");
    sc.nextLine();
    String resposta = sc.nextLine().trim().toUpperCase();

    Optional.of(resposta)
            .filter(r -> r.equals("S"))
            .ifPresent(r -> {
                Logger.info("Tentando salvar novamente...");
                repo.salvarSeModificado();
            });
}