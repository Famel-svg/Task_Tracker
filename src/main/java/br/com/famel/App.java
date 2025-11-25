package br.com.famel;

import br.com.famel.model.Textos;

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
                System.out.println("1");
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
