package br.com.famel.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonManager {
//Leitura



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
