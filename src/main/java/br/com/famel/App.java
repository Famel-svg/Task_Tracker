package br.com.famel;

import br.com.famel.model.Task;
import br.com.famel.model.Textos;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;
import static br.com.famel.model.Textos.OpcoesStatus;

public class App
{
    public static void main( String[] args )
    {
        Scanner sc = new Scanner(System.in);

        System.out.println(Textos.BemVindo);
        System.out.println(Textos.Opcoes);
        int escolha = sc.nextInt();

        do {

            switch (escolha){

                //Adicionar nova Task
                case 1:
                    //Construtor da classe Task
                    Task task = new Task();

                    //Pega o nome da Task
                    System.out.println("Nome da Task: ");
                    task.setNome(sc.next());

                    //Pega a descrição da Task
                    System.out.println("Descrição da Task: ");
                    task.setDescricao(sc.next());

                    //Pega o status da Task
                    System.out.println(OpcoesStatus);
                    String status = sc.next();
                    if(Objects.equals(status, "1")){
                        task.setStatus("Para fazer");
                    } else if (Objects.equals(status, "2")) {
                        task.setStatus("Fazendo");
                    } else {
                        task.setStatus("Pronta");
                    }

                    //Pega a data atual
                    LocalDate currentDate = LocalDate.now();
                    task.setDataDeCriacao(currentDate);
                    System.out.println("Task adicionada com sucesso!");

                    //System.out.println(task);

                    System.out.println(Textos.Opcoes);
                    escolha = sc.nextInt();

                    break;

                //Atualizar uma Task
                case 2:
                    System.out.println("Qual task deseja atualizar?");

                    System.out.println(Textos.Opcoes);
                    escolha = sc.nextInt();

                    break;

                //Apagar uma Task
                case 3:
                    System.out.println("3");
                    break;

                //Listar todas as Task
                case 4:
                    System.out.println("4");
                    break;

                //Listar todas as Task prontas
                case 5:
                    System.out.println("5");
                    break;

                //Listar todas as Task em progresso
                case 6:
                    System.out.println("6");
                    break;
                default:
                    System.out.println(Textos.Padrao);

            }
        } while (escolha != 0);

    sc.close();
    }
}
