package br.com.famel.model.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Sistema de logging simples para rastrear operações e erros
 */
public class Logger {
    private static final boolean DEBUG_MODE = false; // Mude para true para ver logs de debug
    private static final boolean LOG_TO_FILE = true; // Salva logs em arquivo
    private static final String LOG_FILE = "task_tracker.log";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Níveis de log
     */
    public enum Level {
        DEBUG("[DEBUG]"),
        INFO("[INFO] "),
        WARN("[WARN] "),
        ERROR("[ERROR]"),
        SUCCESS("[✓]    ");

        private final String prefix;

        Level(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }
    }

    /**
     * Log de informação geral
     */
    public static void info(String mensagem) {
        log(Level.INFO, mensagem);
    }

    /**
     * Log de sucesso (operações concluídas)
     */
    public static void success(String mensagem) {
        log(Level.SUCCESS, mensagem);
    }

    /**
     * Log de aviso (algo inesperado mas não crítico)
     */
    public static void warn(String mensagem) {
        log(Level.WARN, mensagem);
    }

    /**
     * Log de erro (operações que falharam)
     */
    public static void error(String mensagem) {
        log(Level.ERROR, mensagem);
    }

    /**
     * Log de erro com exceção
     */
    public static void error(String mensagem, Exception e) {
        log(Level.ERROR, mensagem + " - " + e.getMessage());
        if (DEBUG_MODE) {
            e.printStackTrace();
        }
    }

    /**
     * Log de debug (apenas se DEBUG_MODE estiver ativo)
     */
    public static void debug(String mensagem) {
        if (DEBUG_MODE) {
            log(Level.DEBUG, mensagem);
        }
    }

    /**
     * Método principal de logging
     */
    private static void log(Level level, String mensagem) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String logMessage = String.format("%s %s %s", timestamp, level.getPrefix(), mensagem);

        // Imprime no console
        System.out.println(logMessage);

        // Salva em arquivo se habilitado
        if (LOG_TO_FILE) {
            salvarEmArquivo(logMessage);
        }
    }

    /**
     * Salva log em arquivo
     */
    private static void salvarEmArquivo(String mensagem) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(mensagem);
        } catch (IOException e) {
            // Se falhar ao salvar log, não fazer nada (evitar loop infinito)
            System.err.println("Falha ao salvar log: " + e.getMessage());
        }
    }

    /**
     * Limpa o arquivo de log
     */
    public static void limparLogs() {
        try (FileWriter fw = new FileWriter(LOG_FILE, false)) {
            fw.write(""); // Limpa o arquivo
            info("Arquivo de log limpo");
        } catch (IOException e) {
            error("Erro ao limpar logs", e);
        }
    }

    /**
     * Log de operação CRUD
     */
    public static void operacao(String tipo, String detalhes) {
        info(String.format("Operação: %s - %s", tipo, detalhes));
    }

    /**
     * Log de início de aplicação
     */
    public static void inicioAplicacao() {
        log(Level.INFO, "========================================");
        log(Level.INFO, "    Task Tracker iniciado");
        log(Level.INFO, "========================================");
    }

    /**
     * Log de fim de aplicação
     */
    public static void fimAplicacao() {
        log(Level.INFO, "========================================");
        log(Level.INFO, "    Task Tracker encerrado");
        log(Level.INFO, "========================================");
    }
}