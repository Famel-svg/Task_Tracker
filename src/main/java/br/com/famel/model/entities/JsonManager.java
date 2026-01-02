package br.com.famel.model.entities;

import module java.base;
import static java.lang.IO.println;

public class JsonManager {

    void main() throws IOException {
        println("Testando leitura e escrita de JSON...");
        List<Task> lista = new ArrayList<>();
        lista = lerDoJson();
        println("Tasks carregadas: " + lista.size());
        salvarEmJson(lista);
        println("JSON salvo com sucesso.");
    }

    public static List<Task> lerDoJson() throws IOException {
        List<Task> lista = new ArrayList<>();

        // Verifica se o arquivo existe
        File file = new File("tasks.json");
        if (!file.exists()) {
            // Se não existe, retorna lista vazia
            return lista;
        }

        StringBuilder json = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.json"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                json.append(linha.trim());
            }
        }

        String conteudo = json.toString().trim();

        // Se o arquivo está vazio ou só tem [], retorna lista vazia
        if (conteudo.isEmpty() || conteudo.equals("[]")) {
            return lista;
        }

        // Remove [ ]
        conteudo = conteudo.substring(1, conteudo.length() - 1);

        // Separa os objetos
        String[] blocos = conteudo.split("\\},\\s*\\{");

        for (String bloco : blocos) {
            bloco = bloco.replace("{", "").replace("}", "");

            int id = 0;
            String nome = "";
            String descricao = "";
            String status = "";
            String dataCriacao = "";
            String dataFinalizacao = "";

            String[] linhas = bloco.split(",(?=\\s*\"[^\"]+\"\\s*:)"); // Split melhorado

            for (String linha : linhas) {
                String[] partes = linha.split(":", 2);
                if (partes.length < 2) continue;

                String campo = partes[0].replace("\"", "").trim();
                String valor = partes[1].replace("\"", "").trim();

                switch (campo) {
                    case "Id":
                        id = Integer.parseInt(valor);
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
                        } else {
                            dataFinalizacao = null;
                        }
                        break;
                }
            }

            // Só adiciona se não for uma task com dados null
            if (!nome.equals("null") && id > 0) {
                Task task = new Task(id, nome, descricao, status, dataCriacao, dataFinalizacao);
                lista.add(task);
            }
        }

        return lista;
    }

    private static String indent(int nivel) {
        return "    ".repeat(nivel);
    }

    public static void salvarEmJson(List<Task> lista) throws IOException {
        StringBuilder builder = new StringBuilder();

        builder.append("[");

        for (int i = 0; i < lista.size(); i++) {
            Task task = lista.get(i);
            builder.append("\n");
            builder.append(indent(1));
            builder.append("{\n");

            // Task ID
            builder.append(indent(2));
            builder.append("\"Id\": ");
            builder.append(task.getId());
            builder.append(",\n");

            // Task Nome
            builder.append(indent(2));
            builder.append("\"Nome\": \"");
            builder.append(task.getNome() != null ? task.getNome() : "");
            builder.append("\",\n");

            // Task Descricao
            builder.append(indent(2));
            builder.append("\"Descricao\": \"");
            builder.append(task.getDescricao() != null ? task.getDescricao() : "");
            builder.append("\",\n");

            // Task Status
            builder.append(indent(2));
            builder.append("\"Status\": \"");
            builder.append(task.getStatus() != null ? task.getStatus() : "");
            builder.append("\",\n");

            // Task Data de Criacao
            builder.append(indent(2));
            builder.append("\"DataDeCriacao\": \"");
            builder.append(task.getDataDeCriacao() != null ? task.getDataDeCriacao().toString() : "");
            builder.append("\",\n");

            // Task Data de Finalizacao
            builder.append(indent(2));
            builder.append("\"DataDeFinalizacao\": ");
            if (task.getDataDeFinalizacao() != null) {
                builder.append("\"");
                builder.append(task.getDataDeFinalizacao().toString());
                builder.append("\"");
            } else {
                builder.append("null");
            }
            builder.append("\n");

            builder.append(indent(1));
            builder.append("}");

            if (i < lista.size() - 1) {
                builder.append(",");
            }
        }

        builder.append("\n");
        builder.append("]");

        try (FileWriter writer = new FileWriter("tasks.json")) {
            writer.write(builder.toString());
        }
    }
}