import br.com.famel.model.JsonManager;
import br.com.famel.model.OpcoesDaCase;
import br.com.famel.model.Task;
import br.com.famel.model.Textos;
import static java.lang.IO.println;

void main() throws IOException {
        Scanner sc = new Scanner(System.in);

        println(Textos.BemVindo);
        println(Textos.Opcoes);
        int escolha = sc.nextInt();

        List<Task> lista = JsonManager.lerDoJson();

        do {
            switch (escolha){

                //Adicionar nova Task
                case 1:
                    OpcoesDaCase.adicionarTask(lista, sc);
                    println(Textos.Opcoes);
                    escolha = sc.nextInt();
                    break;

                //Atualizar uma Task
                case 2:
                    OpcoesDaCase.atualizarTask(lista, sc);
                    println(Textos.Opcoes);
                    escolha = sc.nextInt();
                    break;
                    
                //Apagar uma Task
                case 3:
                    OpcoesDaCase.removerTask(lista, sc);
                    println(Textos.Opcoes);
                    escolha = sc.nextInt();
                    break;

                //Listar todas as Task
                case 4:
                    println(lista);
                    println(Textos.Opcoes);
                    escolha = sc.nextInt();
                    break;

                //Listar todas as Task prontas
                case 5:
                    OpcoesDaCase.listarProntas(lista);
                    println(Textos.Opcoes);
                    escolha = sc.nextInt();
                    break;

                //Listar todas as Task em progresso
                case 6:
                    OpcoesDaCase.listarFazendo(lista);
                    println(Textos.Opcoes);
                    escolha = sc.nextInt();
                    break;

                default:
                    println(Textos.Padrao);

            }
        } while (escolha != 0);

        JsonManager.salvarEmJson(lista);

    sc.close();
    }

