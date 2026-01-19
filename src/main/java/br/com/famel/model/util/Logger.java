package br.com.famel.model.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Sistema de logging refatorado com lambdas e lazy evaluation
 */
public class Logger {
    private static final boolean DEBUG_MODE = false;
    private static final boolean LOG_TO_FILE = true;
    private static final String LOG_FILE = "task_tracker.log";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
     * Log com lazy evaluation - a mensagem só é construída se necessário
     */
    private static void log(Level level, Supplier<String> mensagemSupplier) {
        // Para DEBUG, só processa se estiver ativo
        if (level == Level.DEBUG && !DEBUG_MODE) {
            return;
        }

        String mensagem = mensagemSupplier.get();
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String logMessage = String.format("%s %s %s", timestamp, level.getPrefix(), mensagem);

        System.out.println(logMessage);

        // Salva em arquivo se habilitado
        Optional.of(LOG_TO_FILE)
                .filter(Boolean::booleanValue)
                .ifPresent(flag -> salvarEmArquivo(logMessage));
    }

    /**
     * Sobrecarga para string simples (compatibilidade)
     */
    private static void log(Level level, String mensagem) {
        log(level, () -> mensagem);
    }

    // ========== MÉTODOS PÚBLICOS ==========

    public static void info(String mensagem) {
        log(Level.INFO, mensagem);
    }

    public static void info(Supplier<String> mensagemSupplier) {
        log(Level.INFO, mensagemSupplier);
    }

    public static void success(String mensagem) {
        log(Level.SUCCESS, mensagem);
    }

    public static void success(Supplier<String> mensagemSupplier) {
        log(Level.SUCCESS, mensagemSupplier);
    }

    public static void warn(String mensagem) {
        log(Level.WARN, mensagem);
    }

    public static void warn(Supplier<String> mensagemSupplier) {
        log(Level.WARN, mensagemSupplier);
    }

    public static void error(String mensagem) {
        log(Level.ERROR, mensagem);
    }

    /**
     * Log de erro com exceção usando Optional
     */
    public static void error(String mensagem, Exception e) {
        log(Level.ERROR, () -> mensagem + " - " + e.getMessage());

        // Imprime stack trace apenas se DEBUG_MODE estiver ativo
        Optional.of(DEBUG_MODE)
                .filter(Boolean::booleanValue)
                .ifPresent(mode -> e.printStackTrace());
    }

    /**
     * Log de debug (lazy evaluation - só processa se DEBUG_MODE = true)
     */
    public static void debug(String mensagem) {
        log(Level.DEBUG, () -> mensagem);
    }

    public static void debug(Supplier<String> mensagemSupplier) {
        log(Level.DEBUG, mensagemSupplier);
    }

    /**
     * Salva log em arquivo usando try-with-resources
     */
    private static void salvarEmArquivo(String mensagem) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(mensagem);
        } catch (IOException e) {
            // Evita loop infinito - apenas imprime erro no console
            System.err.println("Falha ao salvar log: " + e.getMessage());
        }
    }

    /**
     * Limpa o arquivo de log usando Optional para tratamento
     */
    public static void limparLogs() {
        try (FileWriter fw = new FileWriter(LOG_FILE, false)) {
            fw.write("");
            info("Arquivo de log limpo");
        } catch (IOException e) {
            error("Erro ao limpar logs", e);
        }
    }

    /**
     * Log de operação CRUD usando lazy evaluation
     */
    public static void operacao(String tipo, String detalhes) {
        info(() -> String.format("Operação: %s - %s", tipo, detalhes));
    }

    /**
     * Log de início de aplicação
     */
    public static void inicioAplicacao() {
        String separador = "========================================";
        log(Level.INFO, separador);
        log(Level.INFO, "    Task Tracker iniciado");
        log(Level.INFO, separador);
    }

    /**
     * Log de fim de aplicação
     */
    public static void fimAplicacao() {
        String separador = "========================================";
        log(Level.INFO, separador);
        log(Level.INFO, "    Task Tracker encerrado");
        log(Level.INFO, separador);
    }

    /**
     * Exemplo de uso avançado - log condicional
     */
    public static void logCondicional(boolean condicao, Level level, Supplier<String> mensagem) {
        Optional.of(condicao)
                .filter(Boolean::booleanValue)
                .ifPresent(c -> log(level, mensagem));
    }

    /**
     * Log de performance - mede tempo de execução
     */
    public static <T> T logPerformance(String operacao, Supplier<T> funcao) {
        long inicio = System.currentTimeMillis();

        T resultado = funcao.get();

        long duracao = System.currentTimeMillis() - inicio;
        debug(() -> String.format("Performance: %s levou %dms", operacao, duracao));

        return resultado;
    }
}