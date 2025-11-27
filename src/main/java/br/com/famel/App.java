package br.com.famel;

import br.com.famel.model.Task;
import br.com.famel.model.Textos;
import java.time.LocalDate;


import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        Scanner sc = new Scanner(System.in);


        System.out.println(Textos.BemVindo);
        System.out.println(Textos.Opcoes);
        int escolha = sc.nextInt();



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
                System.out.println("Status da Task:\n" +
                        "(1) Para fazer\n(2) Fazendo\n(3) Pronta\n");
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

                System.out.println(task);
                break;

            //Atualizar uma Task
            case 2:
                System.out.println("2");
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


    sc.close();
    }
}
