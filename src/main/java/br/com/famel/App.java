package br.com.famel;

import br.com.famel.model.Task;
import br.com.famel.model.Textos;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static br.com.famel.model.Textos.AtualizarOpções;
import static br.com.famel.model.Textos.OpcoesStatus;

public class App
{
    public static void main( String[] args )
    {
        Scanner sc = new Scanner(System.in);

        System.out.println(Textos.BemVindo);
        System.out.println(Textos.Opcoes);
        int escolha = sc.nextInt();

        List<Task> lista = new ArrayList<>();


        do {
            switch (escolha){

                //Adicionar nova Task
                case 1:

                    //Construtor da classe Task
                    Task task = new Task();
                    task.gerarId();

                    //Pega o nome da Task
                    System.out.println("Nome da Task: ");
                    task.setNome(sc.next());

                    //Pega a descrição da Task
                    System.out.println("Descrição da Task: ");
                    task.setDescricao(sc.next());

                    //Pega o status da Task
                    System.out.println(OpcoesStatus);
                    task.setStatus(sc.next());


                    //Pega a data atual
                    LocalDate currentDate = LocalDate.now();
                    task.setDataDeCriacao(currentDate);
                    System.out.println("Task adicionada com sucesso!");

                    System.out.println(Textos.Opcoes);
                    escolha = sc.nextInt();
                    lista.add(task);
                    break;

                //Atualizar uma Task
                case 2:
                    System.out.println("Qual task deseja atualizar? Digite o ID:");
                    int idEscolhido = sc.nextInt();

                    boolean achou = false; // controla se encontramos a task

                    for (Task t : lista) {
                        if (t.getId() == idEscolhido) {

                            achou = true; // achamos a task correta

                            System.out.println(AtualizarOpções);

                            int atualizacaoDaTask = sc.nextInt();

                            switch (atualizacaoDaTask) {
                                case 1:
                                    System.out.println("Digite o novo nome da Task: ");
                                    t.setNome(sc.next());
                                    break;

                                case 2:
                                    System.out.println("Digite a nova descrição da Task: ");
                                    t.setDescricao(sc.next());
                                    break;

                                case 3:
                                    System.out.println("Digite o novo status da Task: ");
                                    t.setStatus(sc.next());
                                    break;
                            }

                            break;
                        }
                    }

                    if (!achou) {
                        System.out.println("ID não encontrado. Digite um ID válido.");
                    }

                    System.out.println(Textos.Opcoes);
                    escolha = sc.nextInt();
                    break;


                //Apagar uma Task
                case 3:
                    System.out.println("Qual task deseja apagar?");
                    System.out.println(lista);
                    idEscolhido = sc.nextInt();

                    achou = false; // controla se encontramos a task

                    for (Task t : lista) {
                        if (t.getId() == idEscolhido) {

                            achou = true; // achamos a task correta

                            lista.remove(t);

                            break;
                        }
                    }

                    if (!achou) {
                        System.out.println("ID não encontrado. Digite um ID válido.");
                    }


                    System.out.println(Textos.Opcoes);
                    escolha = sc.nextInt();
                    break;

                //Listar todas as Task
                case 4:
                    System.out.println(lista);
                    System.out.println(Textos.Opcoes);
                    escolha = sc.nextInt();
                    break;

                //Listar todas as Task prontas
                case 5:
                    System.out.println("5");
                    System.out.println(Textos.Opcoes);
                    escolha = sc.nextInt();
                    break;

                //Listar todas as Task em progresso
                case 6:
                    System.out.println("6");
                    System.out.println(Textos.Opcoes);
                    escolha = sc.nextInt();
                    break;
                default:
                    System.out.println(Textos.Padrao);

            }
        } while (escolha != 0);

    sc.close();
    }
}
