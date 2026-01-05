package br.com.famel.model.entities;

import br.com.famel.model.util.Logger;
import module java.base;

public class JsonManager {

    void main() throws IOException {
        Logger.info("Testando leitura e escrita de JSON...");
        List<Task> lista = new ArrayList<>();
        lista = lerDoJson();
        Logger.info("Tasks carregadas: " + lista.size());
        salvarEmJson(lista);
        Logger.success("JSON salvo com sucesso");
    }

    public static List<Task> lerDoJson() throws IOException {
        List<Task> lista = new ArrayList<>();
        File file = new File("tasks.json");

        if (!file.exists()) {
            Logger.info("Arquivo tasks.json não encontrado, será criado ao salvar");
            return lista;
        }

        Logger.debug("Lendo arquivo tasks.json...");
        StringBuilder json = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.json"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                json.append(linha.trim());
            }
        } catch (IOException e) {
            Logger.error("Erro ao ler arquivo JSON", e);
            throw e;
        }

        String conteudo = json.toString().trim();

        if (conteudo.isEmpty() || conteudo.equals("[]")) {
            Logger.info("Arquivo JSON vazio");
            return lista;
        }

        try {
            conteudo = conteudo.substring(1, conteudo.length() - 1);
            String[] blocos = conteudo.split("\\},\\s*\\{");

            Logger.debug("Processando " + blocos.length + " task(s) do JSON");

            for (String bloco : blocos) {
                try {
                    Task task = parsearTask(bloco);
                    if (task != null) {
                        lista.add(task);
                    }
                } catch (Exception e) {
                    Logger.warn("Erro ao processar uma task, pulando para próxima");
                }
            }

            Logger.success("Total de " + lista.size() + " task(s) carregadas");

        } catch (Exception e) {
            Logger.error("Erro ao processar JSON", e);
            throw new IOException("Erro ao processar JSON", e);
        }

        return lista;
    }

    private static Task parsearTask(String bloco) {
        bloco = bloco.replace("{", "").replace("}", "");

        int id = 0;
        String nome = "";
        String descricao = "";
        String status = "";
        String dataCriacao = "";
        String dataFinalizacao = null;

        String[] linhas = bloco.split(",(?=\\s*\"[^\"]+\"\\s*:)");

        for (String linha : linhas) {
            try {
                String[] partes = linha.split(":", 2);
                if (partes.length < 2) continue;

                String campo = partes[0].replace("\"", "").trim();
                String valor = partes[1].replace("\"", "").trim();

                switch (campo) {
                    case "Id":
                        try {
                            id = Integer.parseInt(valor);
                        } catch (NumberFormatException e) {
                            Logger.warn("ID inválido encontrado: " + valor);
                            return null;
                        }
                        break;
                    case "Nome":
                        nome = valor;
                        break;
                    case "Descricao":
                        descricao = valor;
                        break;
                    case "Status":
                        status = valor;
                        break;
                    case "DataDeCriacao":
                        dataCriacao = valor;
                        break;
                    case "DataDeFinalizacao":
                        if (!valor.equals("null") && !valor.isEmpty()) {
                            dataFinalizacao = valor;
                        }
                        break;
                }
            } catch (Exception e) {
                Logger.warn("Erro ao processar campo: " + linha);
            }
        }

        if (nome.equals("null") || nome.isEmpty() || id <= 0) {
            Logger.warn("Task com dados inválidos (ID: " + id + "), ignorando");
            return null;
        }

        try {
            return new Task(id, nome, descricao, status, dataCriacao, dataFinalizacao);
        } catch (Exception e) {
            Logger.error("Erro ao criar Task", e);
            return null;
        }
    }

    private static String indent(int nivel) {
        return "    ".repeat(nivel);
    }

    public static void salvarEmJson(List<Task> lista) throws IOException {
        if (lista == null) {
            Logger.error("Lista de tasks não pode ser null");
            throw new IllegalArgumentException("Lista não pode ser null");
        }

        Logger.debug("Construindo JSON com " + lista.size() + " task(s)");
        StringBuilder builder = new StringBuilder();

        try {
            builder.append("[");

            for (int i = 0; i < lista.size(); i++) {
                Task task = lista.get(i);

                if (task == null) {
                    Logger.warn("Task null na posição " + i + ", ignorando");
                    continue;
                }

                builder.append("\n");
                builder.append(indent(1));
                builder.append("{\n");

                builder.append(indent(2));
                builder.append("\"Id\": ");
                builder.append(task.getId());
                builder.append(",\n");

                builder.append(indent(2));
                builder.append("\"Nome\": \"");
                builder.append(escaparString(task.getNome()));
                builder.append("\",\n");

                builder.append(indent(2));
                builder.append("\"Descricao\": \"");
                builder.append(escaparString(task.getDescricao()));
                builder.append("\",\n");

                builder.append(indent(2));
                builder.append("\"Status\": \"");
                builder.append(task.getStatus().getDescricao());
                builder.append("\",\n");

                builder.append(indent(2));
                builder.append("\"DataDeCriacao\": \"");
                builder.append(task.getDataDeCriacao().toString());
                builder.append("\",\n");

                builder.append(indent(2));
                builder.append("\"DataDeFinalizacao\": ");

                // Usando Optional
                task.getDataDeFinalizacao().ifPresentOrElse(
                        data -> {
                            builder.append("\"");
                            builder.append(data.toString());
                            builder.append("\"");
                        },
                        () -> builder.append("null")
                );

                builder.append("\n");
                builder.append(indent(1));
                builder.append("}");

                if (i < lista.size() - 1) {
                    builder.append(",");
                }
            }

            builder.append("\n");
            builder.append("]");

        } catch (Exception e) {
            Logger.error("Erro ao construir JSON", e);
            throw new IOException("Erro ao construir JSON", e);
        }

        Logger.debug("Escrevendo JSON no arquivo...");
        try (FileWriter writer = new FileWriter("tasks.json")) {
            writer.write(builder.toString());
            writer.flush();
            Logger.success("Arquivo tasks.json salvo com sucesso");
        } catch (IOException e) {
            Logger.error("Erro ao escrever arquivo tasks.json", e);
            throw e;
        }
    }

    private static String escaparString(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}