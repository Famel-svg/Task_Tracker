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
public static void salvarEmJson(List<Task> lista) {
    StringBuilder builder = new StringBuilder();

    // 1. Escrever [
    // 2. Loop para tasks
    // 3. Escrever cada objeto
    // 4. Fechar ]
    // 5. Gravar no arquivo com FileWriter
}

}
