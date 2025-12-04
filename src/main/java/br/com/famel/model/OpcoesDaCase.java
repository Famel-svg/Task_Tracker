package br.com.famel.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static br.com.famel.model.Textos.AtualizarOpcoes;
import static br.com.famel.model.Textos.OpcoesStatus;

public class OpcoesDaCase {
    static int idEscolhido;
    static boolean achou = false;
    static int atualizacaoDaTask;

    //Adicionar nova Task
    public static void adicionarTask(List<Task> lista, Scanner sc) {
        // lógica do case 1
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
        System.out.println(Textos.case1);

        lista.add(task);
    }

    //Atualizar uma Task
    public static void atualizarTask(List<Task> lista, Scanner sc) {
        // lógica do case 2
        System.out.println("Qual task deseja atualizar? Digite o ID:");
        idEscolhido = sc.nextInt();

        achou = false; // controla se encontramos a task

        for (Task t : lista) {
            if (t.getId() == idEscolhido) {

                achou = true; // achamos a task correta

                System.out.println(AtualizarOpcoes);

                atualizacaoDaTask = sc.nextInt();

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
                System.out.println(Textos.case2);
                break;
            }
        }
        if (!achou) {
            System.out.println("ID não encontrado. Digite um ID válido.");
        }
    }

    //Apagar uma Task
    public static void removerTask(List<Task> lista, Scanner sc) {
        // lógica do case 3
        System.out.println("Qual task deseja apagar?");
        System.out.println(lista);
        idEscolhido = sc.nextInt();

        achou = false; // controla se encontramos a task

        for (Task t : lista) {
            if (t.getId() == idEscolhido) {

                achou = true; // achamos a task correta

                lista.remove(t);
                System.out.println(Textos.case3);
                break;
            }
        }

        if (!achou) {
            System.out.println("ID não encontrado. Digite um ID válido.");
        }
    }

    //Listar todas as Task prontas
    public static void listarProntas(List<Task> lista) {
        // lógica do case 5
        achou = false;
        for (Task t : lista) {
            if (t.getStatus().equals("Pronto")) {
                achou = true;

                System.out.println(t);
            }
        }

        if (!achou) {
            System.out.println("Nenhuma Task pronta.");
        }
    }

    //Listar todas as Task em progresso
    public static void listarFazendo(List<Task> lista) {
        // lógica do case 6
        achou = false;
        for (Task t : lista) {
            if (t.getStatus().equals("Fazendo")) {
                achou = true;
                System.out.println(t);}
        }
        if (!achou) {
            System.out.println("Nenhuma Task sendo feita.");
        }
    }
}
