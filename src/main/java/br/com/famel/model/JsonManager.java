package br.com.famel.model;

//Nao rode esse Arquivo ira gerar um JSON vazio
import module java.base;

import static java.lang.IO.println;

public class JsonManager {

    void main() throws IOException {
        // Só para permitir execução direta deste arquivo
        println("Testando leitura e escrita de JSON...");

        List<Task> lista = new ArrayList<>();

        // Testar leitura
        lista = lerDoJson();
        println("Tasks carregadas: " + lista.size());

        // Testar escrita (pode comentar se quiser)
        salvarEmJson(lista);
        println("JSON salvo com sucesso.");
    }

public static List<Task> lerDoJson() throws IOException {
    List<Task> lista = new ArrayList<>();

    StringBuilder json = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader("tasks.json"))) {
        String linha;
        while ((linha = reader.readLine()) != null) {
            json.append(linha.trim());
        }
    }

    // Remove [ ]
    String conteudo = json.toString();
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

        String[] linhas = bloco.split(",");

        for (String linha : linhas) {

            String[] partes = linha.split(":", 2);
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
                    if (!valor.equals("null")) {
                        dataFinalizacao = valor;
                    }
                    break;
            }
        }

        Task task = new Task(id, nome, descricao, status, dataCriacao, dataFinalizacao);
        lista.add(task);
    }

    return lista;
}

//Identa as informações
private static String indent(int nivel) {
    return "    ".repeat(nivel);
}

//Gera o JSON
public static void salvarEmJson(List<Task> lista) throws IOException {
    StringBuilder builder = new StringBuilder();
    
    // 1. Escrever [
    builder.append("[");
    
    // 2. Loop para tasks
    for (int i = 0; i < lista.size(); i++) {
        Task task = lista.get(i);
        builder.append("\n");
        builder.append(indent(1));
        builder.append("{\n");

        //Task ID
        builder.append(indent(2));
        builder.append("\"Id\": ");
        builder.append(task.getId());
        builder.append(",\n");

        //Task Nome
        builder.append(indent(2));
        builder.append("\"Nome\": ");
        builder.append("\"");
        builder.append(task.getNome());
        builder.append("\",\n");

        //Task Descricao
        builder.append(indent(2));
        builder.append("\"Descricao\": ");
        builder.append("\"");
        builder.append(task.getDescricao());
        builder.append("\"");
        builder.append(",\n");

        //Task Status
        builder.append(indent(2));
        builder.append("\"Status\": ");
        builder.append("\"");
        builder.append(task.getStatus());
        builder.append("\",\n");

        //Task Data de Criacao
        builder.append(indent(2));
        builder.append("\"DataDeCriacao\": ");
        builder.append("\"");
        builder.append(task.getDataDeCriacao());
        builder.append("\",\n");

        //Task Data de Finalizacao
        builder.append(indent(2));
        builder.append("\"DataDeFinalizacao\": ");
        builder.append("\"");
        builder.append(task.getDataDeFinalizacao());
        builder.append("\"\n");
        builder.append(indent(1));
        builder.append("}");
        // 3. Escrever cada objeto
        if (i < lista.size() - 1) {
            builder.append(",");
        }
    }
    
    // 4. Fechar ]
    builder.append("\n");
    builder.append("]");
    
    // 5. Gravar no arquivo com FileWriter
    try (FileWriter writer = new FileWriter("tasks.json")) {
        writer.write(builder.toString());
    }
}
}