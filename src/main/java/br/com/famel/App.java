package br.com.famel;

import br.com.famel.model.JsonManager;
import br.com.famel.model.OpcoesDaCase;
import br.com.famel.model.Task;
import br.com.famel.model.Textos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App
{
    public static void main(String[] args ) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.println(Textos.BemVindo);
        System.out.println(Textos.Opcoes);
        int escolha = sc.nextInt();

        List<Task> lista = new ArrayList<>();

        do {
            switch (escolha){

                //Adicionar nova Task
                case 1:
                    OpcoesDaCase.adicionarTask(lista, sc);
                    System.out.println(Textos.Opcoes);
                    escolha = sc.nextInt();
                    break;

                //Atualizar uma Task
                case 2:
                    OpcoesDaCase.atualizarTask(lista, sc);
                    System.out.println(Textos.Opcoes);
                    escolha = sc.nextInt();
                    break;
                    
                //Apagar uma Task
                case 3:
                    OpcoesDaCase.removerTask(lista, sc);
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
                    OpcoesDaCase.listarProntas(lista);
                    System.out.println(Textos.Opcoes);
                    escolha = sc.nextInt();
                    break;

                //Listar todas as Task em progresso
                case 6:
                    OpcoesDaCase.listarFazendo(lista);
                    System.out.println(Textos.Opcoes);
                    escolha = sc.nextInt();
                    break;

                default:
                    System.out.println(Textos.Padrao);

            }
        } while (escolha != 0);

        JsonManager.salvarEmJson(lista);

    sc.close();
    }
}
