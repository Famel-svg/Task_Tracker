package br.com.famel.model.entities;

import br.com.famel.model.util.Logger;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class JsonManager {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * Lê tasks do JSON usando Streams e lambdas
     */
    public static List<Task> lerDoJson() throws IOException {
        File file = new File("tasks.json");

        if (!file.exists()) {
            Logger.info("Arquivo tasks.json não encontrado, será criado ao salvar");
            return new ArrayList<>();
        }

        Logger.debug("Lendo arquivo tasks.json...");

        try {
            // Lê todo o conteúdo usando Files.readString (Java 11+)
            String conteudo = Files.readString(Path.of("tasks.json")).trim();

            if (conteudo.isEmpty() || conteudo.equals("[]")) {
                Logger.info("Arquivo JSON vazio");
                return new ArrayList<>();
            }

            // Remove colchetes e divide em blocos de tasks
            List<Task> tasks = Arrays.stream(
                            conteudo.substring(1, conteudo.length() - 1)
                                    .split("\\},\\s*\\{")
                    )
                    .map(JsonManager::parsearTask)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            Logger.success("Total de " + tasks.size() + " task(s) carregadas");
            return tasks;

        } catch (Exception e) {
            Logger.error("Erro ao processar JSON", e);
            throw new IOException("Erro ao processar JSON", e);
        }
    }

    /**
     * Parseia uma task usando Map funcional para handlers
     */
    private static Task parsearTask(String bloco) {
        bloco = bloco.replace("{", "").replace("}", "");

        // Holders mutáveis para captura em lambdas
        final int[] id = {0};
        final String[] nome = {""};
        final String[] descricao = {""};
        final String[] status = {""};
        final String[] dataCriacao = {""};
        final String[] dataFinalizacao = {null};

        // Map de handlers funcionais para cada campo
        Map<String, BiConsumer<String, String>> fieldHandlers = Map.of(
                "Id", (campo, valor) -> {
                    try {
                        id[0] = Integer.parseInt(valor);
                    } catch (NumberFormatException e) {
                        Logger.warn("ID inválido encontrado: " + valor);
                    }
                },
                "Nome", (campo, valor) -> nome[0] = valor,
                "Descricao", (campo, valor) -> descricao[0] = valor,
                "Status", (campo, valor) -> status[0] = valor,
                "DataDeCriacao", (campo, valor) -> dataCriacao[0] = valor,
                "DataDeFinalizacao", (campo, valor) -> {
                    if (!valor.equals("null") && !valor.isEmpty()) {
                        dataFinalizacao[0] = valor;
                    }
                }
        );

        // Processa todas as linhas usando streams
        Arrays.stream(bloco.split(",(?=\\s*\"[^\"]+\"\\s*:)"))
                .map(linha -> linha.split(":", 2))
                .filter(partes -> partes.length >= 2)
                .forEach(partes -> {
                    String campo = partes[0].replace("\"", "").trim();
                    String valor = partes[1].replace("\"", "").trim();

                    fieldHandlers.getOrDefault(
                            campo,
                            (c, v) -> Logger.warn("Campo desconhecido: " + c)
                    ).accept(campo, valor);
                });

        // Validação
        if (nome[0].equals("null") || nome[0].isEmpty() || id[0] <= 0) {
            Logger.warn("Task com dados inválidos (ID: " + id[0] + "), ignorando");
            return null;
        }

        try {
            return new Task(id[0], nome[0], descricao[0], status[0],
                    dataCriacao[0], dataFinalizacao[0]);
        } catch (Exception e) {
            Logger.error("Erro ao criar Task", e);
            return null;
        }
    }

    /**
     * Salva tasks em JSON usando StringBuilder funcional
     */
    public static void salvarEmJson(List<Task> lista) throws IOException {
        if (lista == null) {
            Logger.error("Lista de tasks não pode ser null");
            throw new IllegalArgumentException("Lista não pode ser null");
        }

        Logger.debug("Construindo JSON com " + lista.size() + " task(s)");

        try {
            String json = construirJson(lista);
            Files.writeString(Path.of("tasks.json"), json);
            Logger.success("Arquivo tasks.json salvo com sucesso");
        } catch (IOException e) {
            Logger.error("Erro ao escrever arquivo tasks.json", e);
            throw e;
        }
    }

    /**
     * Constrói o JSON usando streams e lambdas
     */
    private static String construirJson(List<Task> lista) {
        String tasksJson = lista.stream()
                .filter(Objects::nonNull)
                .map(JsonManager::taskParaJson)
                .collect(Collectors.joining(",\n"));

        return "[\n" + tasksJson + "\n]";
    }

    /**
     * Converte uma Task para JSON usando lambda
     */
    private static String taskParaJson(Task task) {
        return String.format("""
                {
                    "Id": %d,
                    "Nome": "%s",
                    "Descricao": "%s",
                    "Status": "%s",
                    "DataDeCriacao": "%s",
                    "DataDeFinalizacao": %s
                }""",
                        task.getId(),
                        escaparString(task.getNome()),
                        escaparString(task.getDescricao()),
                        task.getStatus().getDescricao(),
                        task.getDataDeCriacao().toString(),
                        task.getDataDeFinalizacao()
                                .map(data -> "\"" + data.toString() + "\"")
                                .orElse("null")
                ).lines()
                .map(linha -> "    " + linha)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Escapa caracteres especiais
     */
    private static String escaparString(String str) {
        return Optional.ofNullable(str)
                .map(s -> s.replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\n", "\\n")
                        .replace("\r", "\\r")
                        .replace("\t", "\\t"))
                .orElse("");
    }
}