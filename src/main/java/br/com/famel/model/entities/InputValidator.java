package br.com.famel.model.entities;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class InputValidator {

    /**
     * Lê uma opção numérica do usuário com validação
     * @param sc Scanner para leitura
     * @param min valor mínimo aceito
     * @param max valor máximo aceito
     * @return número válido escolhido pelo usuário
     */
    public static int lerOpcaoSegura(Scanner sc, int min, int max) {
        while (true) {
            try {
                System.out.print("Digite sua escolha: ");
                int opcao = sc.nextInt();
                sc.nextLine(); // limpa buffer

                if (opcao >= min && opcao <= max) {
                    return opcao;
                } else {
                    System.err.println("Opção inválida! Digite um número entre " + min + " e " + max);
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida! Por favor, digite apenas números.");
                sc.nextLine(); // limpa o buffer do scanner
            }
        }
    }

    /**
     * Lê um ID de task com validação
     * @param sc Scanner para leitura
     * @return ID válido (maior que 0)
     */
    public static int lerIdSeguro(Scanner sc) {
        while (true) {
            try {
                System.out.print("Digite o ID da task: ");
                int id = sc.nextInt();
                sc.nextLine(); // limpa buffer

                if (id > 0) {
                    return id;
                } else {
                    System.err.println("ID inválido! O ID deve ser maior que zero.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida! Por favor, digite apenas números.");
                sc.nextLine(); // limpa o buffer do scanner
            }
        }
    }

    /**
     * Lê uma string não vazia do usuário
     * @param sc Scanner para leitura
     * @param mensagem Mensagem a ser exibida
     * @return String não vazia
     */
    public static String lerTextoNaoVazio(Scanner sc, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = sc.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            } else {
                System.err.println("O texto não pode estar vazio! Tente novamente.");
            }
        }
    }

    /**
     * Lê uma opção de status com validação usando Enum
     * @param sc Scanner para leitura
     * @return Status escolhido
     */
    public static Status lerStatusSeguro(Scanner sc) {
        while (true) {
            try {
                System.out.println(Status.listarOpcoes());
                System.out.print("Digite o status (1-3): ");
                String input = sc.nextLine().trim();

                return Status.fromString(input);

            } catch (IllegalArgumentException e) {
                System.err.println("Status inválido! " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Erro ao ler status. Tente novamente.");
            }
        }
    }

    /**
     * Valida se um ID existe na lista de tasks
     * @param lista Lista de tasks
     * @param id ID a ser validado
     * @return true se o ID existe, false caso contrário
     */
    public static boolean validarIdExistente(List<Task> lista, int id) {
        return lista.stream().anyMatch(t -> t.getId() == id);
    }
}