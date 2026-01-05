package br.com.famel.model.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Task {
    private int id;
    private String nome;
    private String descricao;
    private Status status;
    private LocalDate dataDeCriacao;
    private LocalDate dataDeFinalizacao;

    private static int contadorId = 0;

    /**
     * Construtor vazio
     */
    public Task() {
    }

    /**
     * Construtor completo usado para carregar do JSON
     */
    public Task(int id, String nome, String descricao, String status, String dataCriacao, String dataFinalizacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;

        // Converte string para Enum
        try {
            this.status = Status.fromString(status);
        } catch (IllegalArgumentException e) {
            System.err.println("Status inválido ao carregar task: " + status + ". Usando 'Para fazer' como padrão.");
            this.status = Status.PARA_FAZER;
        }

        // Converte as strings de data para LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        if (dataCriacao != null && !dataCriacao.isEmpty() && !dataCriacao.equals("null")) {
            try {
                this.dataDeCriacao = LocalDate.parse(dataCriacao, formatter);
            } catch (Exception e) {
                System.err.println("Erro ao parsear data de criação: " + dataCriacao);
                this.dataDeCriacao = LocalDate.now();
            }
        } else {
            this.dataDeCriacao = LocalDate.now();
        }

        if (dataFinalizacao != null && !dataFinalizacao.isEmpty() && !dataFinalizacao.equals("null")) {
            try {
                this.dataDeFinalizacao = LocalDate.parse(dataFinalizacao, formatter);
            } catch (Exception e) {
                System.err.println("Erro ao parsear data de finalização: " + dataFinalizacao);
            }
        }

        // Atualiza o contador para evitar IDs duplicados
        if (id > contadorId) {
            contadorId = id;
        }
    }

    /**
     * Construtor privado para o Builder
     */
    private Task(Builder builder) {
        this.nome = builder.nome;
        this.descricao = builder.descricao;
        this.status = builder.status;
        this.dataDeCriacao = builder.dataDeCriacao;
        this.dataDeFinalizacao = builder.dataDeFinalizacao;
        gerarId();
    }

    /**
     * Gera um novo ID único para a task
     */
    public void gerarId() {
        contadorId++;
        this.id = contadorId;
    }

    // ========== GETTERS ==========

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDate getDataDeCriacao() {
        return dataDeCriacao;
    }

    /**
     * Retorna a data de finalização usando Optional
     */
    public Optional<LocalDate> getDataDeFinalizacao() {
        return Optional.ofNullable(dataDeFinalizacao);
    }

    /**
     * Retorna a data de finalização (versão sem Optional para compatibilidade)
     */
    public LocalDate getDataDeFinalizacaoRaw() {
        return dataDeFinalizacao;
    }

    // ========== SETTERS ==========

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia");
        }
        this.descricao = descricao;
    }

    /**
     * Define o status usando String (para compatibilidade com código antigo)
     */
    public void setStatus(String statusStr) {
        try {
            this.status = Status.fromString(statusStr);

            if (this.status == Status.PRONTA && this.dataDeFinalizacao == null) {
                this.dataDeFinalizacao = LocalDate.now();
            }

            if (this.status != Status.PRONTA && this.dataDeFinalizacao != null) {
                this.dataDeFinalizacao = null;
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao definir status: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Define o status usando o Enum diretamente
     */
    public void setStatus(Status status) {
        this.status = status;

        if (this.status == Status.PRONTA && this.dataDeFinalizacao == null) {
            this.dataDeFinalizacao = LocalDate.now();
        }

        if (this.status != Status.PRONTA && this.dataDeFinalizacao != null) {
            this.dataDeFinalizacao = null;
        }
    }

    public void setDataDeCriacao(LocalDate dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    public void setDataDeFinalizacao(LocalDate dataDeFinalizacao) {
        this.dataDeFinalizacao = dataDeFinalizacao;
    }

    // ========== MÉTODOS DE CONVENIÊNCIA ==========

    public void marcarComoPronta() {
        setStatus(Status.PRONTA);
    }

    public void marcarComoEmProgresso() {
        setStatus(Status.FAZENDO);
    }

    public void marcarComoPendente() {
        setStatus(Status.PARA_FAZER);
    }

    public boolean isPronta() {
        return status == Status.PRONTA;
    }

    public boolean isEmProgresso() {
        return status == Status.FAZENDO;
    }

    public boolean isPendente() {
        return status == Status.PARA_FAZER;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔════════════════════════════════════════╗\n");
        sb.append(String.format("║ Task #%-32d ║\n", id));
        sb.append("╠════════════════════════════════════════╣\n");
        sb.append(String.format("║ Nome: %-32s ║\n", nome));
        sb.append(String.format("║ Descrição: %-27s ║\n", descricao));
        sb.append(String.format("║ Status: %-30s ║\n", status.getDescricao()));
        sb.append(String.format("║ Criada em: %-27s ║\n", dataDeCriacao));

        // Usando Optional
        getDataDeFinalizacao().ifPresentOrElse(
                data -> sb.append(String.format("║ Finalizada em: %-23s ║\n", data)),
                () -> sb.append("║ Finalizada em: Não finalizada        ║\n")
        );

        sb.append("╚════════════════════════════════════════╝");

        return sb.toString();
    }

    public String toStringSimples() {
        return String.format("ID: %d | %s | Status: %s",
                id, nome, status.getDescricao());
    }

    // ========== BUILDER PATTERN ==========

    /**
     * Retorna um novo Builder para criar Task
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder Pattern para criar Tasks de forma fluente
     */
    public static class Builder {
        private String nome;
        private String descricao;
        private Status status = Status.PARA_FAZER; // Status padrão
        private LocalDate dataDeCriacao = LocalDate.now(); // Data atual por padrão
        private LocalDate dataDeFinalizacao;

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder dataDeCriacao(LocalDate data) {
            this.dataDeCriacao = data;
            return this;
        }

        public Builder dataDeFinalizacao(LocalDate data) {
            this.dataDeFinalizacao = data;
            return this;
        }

        /**
         * Cria a task marcando como pronta
         */
        public Builder pronta() {
            this.status = Status.PRONTA;
            this.dataDeFinalizacao = LocalDate.now();
            return this;
        }

        /**
         * Cria a task marcando como em progresso
         */
        public Builder emProgresso() {
            this.status = Status.FAZENDO;
            return this;
        }

        /**
         * Constrói a Task
         */
        public Task build() {
            // Validações
            if (nome == null || nome.trim().isEmpty()) {
                throw new IllegalStateException("Nome é obrigatório");
            }
            if (descricao == null || descricao.trim().isEmpty()) {
                throw new IllegalStateException("Descrição é obrigatória");
            }

            // Lógica de validação de status
            if (status == Status.PRONTA && dataDeFinalizacao == null) {
                dataDeFinalizacao = LocalDate.now();
            }

            return new Task(this);
        }
    }
}