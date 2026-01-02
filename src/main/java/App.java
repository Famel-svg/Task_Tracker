import br.com.famel.model.Textos;
import br.com.famel.model.entities.*;
import static java.lang.IO.println;

void main() {
    Scanner sc = new Scanner(System.in);

    try {
        println(Textos.BemVindo);

        // Inicializa o Repository - ele já carrega as tasks automaticamente
        TaskRepository repo = new TaskRepository();

        println(Textos.Opcoes);
        int escolha = InputValidator.lerOpcaoSegura(sc, 0, 7);

        do {
            try {
                switch (escolha) {
                    // Adicionar nova Task
                    case 1:
                        OpcoesDaCase.adicionarTask(repo, sc);
                        println(Textos.Opcoes);
                        escolha = InputValidator.lerOpcaoSegura(sc, 0, 7);
                        break;

                    // Atualizar uma Task
                    case 2:
                        OpcoesDaCase.atualizarTask(repo, sc);
                        println(Textos.Opcoes);
                        escolha = InputValidator.lerOpcaoSegura(sc, 0, 7);
                        break;

                    // Apagar uma Task
                    case 3:
                        OpcoesDaCase.removerTask(repo, sc);
                        println(Textos.Opcoes);
                        escolha = InputValidator.lerOpcaoSegura(sc, 0, 7);
                        break;

                    // Listar todas as Tasks
                    case 4:
                        OpcoesDaCase.listarTodas(repo);
                        println(Textos.Opcoes);
                        escolha = InputValidator.lerOpcaoSegura(sc, 0, 7);
                        break;

                    // Listar todas as Tasks prontas
                    case 5:
                        OpcoesDaCase.listarProntas(repo);
                        println(Textos.Opcoes);
                        escolha = InputValidator.lerOpcaoSegura(sc, 0, 7);
                        break;

                    // Listar todas as Tasks em progresso
                    case 6:
                        OpcoesDaCase.listarFazendo(repo);
                        println(Textos.Opcoes);
                        escolha = InputValidator.lerOpcaoSegura(sc, 0, 7);
                        break;

                    // Mostrar estatísticas
                    case 7:
                        OpcoesDaCase.mostrarEstatisticas(repo);
                        println(Textos.Opcoes);
                        escolha = InputValidator.lerOpcaoSegura(sc, 0, 7);
                        break;

                    case 0:
                        println("Encerrando programa...");
                        break;

                    default:
                        println(Textos.Padrao);
                        println(Textos.Opcoes);
                        escolha = InputValidator.lerOpcaoSegura(sc, 0, 7);
                }
            } catch (Exception e) {
                System.err.println("Erro ao processar operação: " + e.getMessage());
                println("Tente novamente.");
                println(Textos.Opcoes);
                escolha = InputValidator.lerOpcaoSegura(sc, 0, 7);
            }
        } while (escolha != 0);

        // Salva apenas se houve modificações usando o método do Repository
        if (repo.foiModificado()) {
            try {
                repo.salvar();
            } catch (Exception e) {
                System.err.println("ERRO CRÍTICO: Não foi possível salvar as alterações!");
                System.err.println("Detalhes: " + e.getMessage());
                println("\nDeseja tentar salvar novamente? (S/N)");
                sc.nextLine();
                String resposta = sc.nextLine().trim().toUpperCase();

                if (resposta.equals("S")) {
                    repo.salvarSeModificado();
                }
            }
        } else {
            println("Nenhuma alteração foi feita.");
        }

    } catch (Exception e) {
        System.err.println("Erro fatal na aplicação: " + e.getMessage());
        e.printStackTrace();
    } finally {
        sc.close();
        println("Programa encerrado.");
    }
}