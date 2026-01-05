import br.com.famel.model.entities.Textos;
import br.com.famel.model.entities.*;
import br.com.famel.model.util.Logger;
import static java.lang.IO.println;

void main() {
    Scanner sc = new Scanner(System.in);

    // Inicia logging
    Logger.inicioAplicacao();

    try {
        println(Textos.BemVindo);

        // Inicializa o Repository
        TaskRepository repo = new TaskRepository();

        println(Textos.Opcoes);
        int escolha = InputValidator.lerOpcaoSegura(sc, 0, 7);

        do {
            try {
                switch (escolha) {
                    case 1:
                        Logger.debug("Usuário escolheu: Adicionar Task");
                        OpcoesDaCase.adicionarTask(repo, sc);
                        println(Textos.Opcoes);
                        escolha = InputValidator.lerOpcaoSegura(sc, 0, 7);
                        break;

                    case 2:
                        Logger.debug("Usuário escolheu: Atualizar Task");
                        OpcoesDaCase.atualizarTask(repo, sc);
                        println(Textos.Opcoes);
                        escolha = InputValidator.lerOpcaoSegura(sc, 0, 7);
                        break;

                    case 3:
                        Logger.debug("Usuário escolheu: Remover Task");
                        OpcoesDaCase.removerTask(repo, sc);
                        println(Textos.Opcoes);
                        escolha = InputValidator.lerOpcaoSegura(sc, 0, 7);
                        break;

                    case 4:
                        Logger.debug("Usuário escolheu: Listar todas");
                        OpcoesDaCase.listarTodas(repo);
                        println(Textos.Opcoes);
                        escolha = InputValidator.lerOpcaoSegura(sc, 0, 7);
                        break;

                    case 5:
                        Logger.debug("Usuário escolheu: Listar prontas");
                        OpcoesDaCase.listarProntas(repo);
                        println(Textos.Opcoes);
                        escolha = InputValidator.lerOpcaoSegura(sc, 0, 7);
                        break;

                    case 6:
                        Logger.debug("Usuário escolheu: Listar em progresso");
                        OpcoesDaCase.listarFazendo(repo);
                        println(Textos.Opcoes);
                        escolha = InputValidator.lerOpcaoSegura(sc, 0, 7);
                        break;

                    case 7:
                        Logger.debug("Usuário escolheu: Ver estatísticas");
                        OpcoesDaCase.mostrarEstatisticas(repo);
                        println(Textos.Opcoes);
                        escolha = InputValidator.lerOpcaoSegura(sc, 0, 7);
                        break;

                    case 0:
                        Logger.info("Usuário solicitou encerramento");
                        println("Encerrando programa...");
                        break;

                    default:
                        Logger.warn("Opção inválida selecionada: " + escolha);
                        println(Textos.Padrao);
                        println(Textos.Opcoes);
                        escolha = InputValidator.lerOpcaoSegura(sc, 0, 7);
                }
            } catch (Exception e) {
                Logger.error("Erro ao processar operação", e);
                System.err.println("Erro ao processar operação: " + e.getMessage());
                println("Tente novamente.");
                println(Textos.Opcoes);
                escolha = InputValidator.lerOpcaoSegura(sc, 0, 7);
            }
        } while (escolha != 0);

        // Salva se houve modificações
        if (repo.foiModificado()) {
            try {
                repo.salvar();
            } catch (Exception e) {
                Logger.error("ERRO CRÍTICO ao salvar", e);
                System.err.println("ERRO CRÍTICO: Não foi possível salvar as alterações!");
                System.err.println("Detalhes: " + e.getMessage());
                println("\nDeseja tentar salvar novamente? (S/N)");
                sc.nextLine();
                String resposta = sc.nextLine().trim().toUpperCase();

                if (resposta.equals("S")) {
                    Logger.info("Tentando salvar novamente...");
                    repo.salvarSeModificado();
                }
            }
        } else {
            Logger.info("Nenhuma modificação detectada");
            println("Nenhuma alteração foi feita.");
        }

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