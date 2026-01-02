package br.com.famel.model.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public LocalDate getDataDeFinalizacao() {
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
     * Aceita tanto código ("1", "2", "3") quanto descrição ("Para fazer", etc)
     */
    public void setStatus(String statusStr) {
        try {
            this.status = Status.fromString(statusStr);

            // Se mudou para Pronta e não tinha data de finalização, define agora
            if (this.status == Status.PRONTA && this.dataDeFinalizacao == null) {
                this.dataDeFinalizacao = LocalDate.now();
            }

            // Se mudou de Pronta para outro status, remove data de finalização
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

    /**
     * Marca a task como pronta
     */
    public void marcarComoPronta() {
        setStatus(Status.PRONTA);
    }

    /**
     * Marca a task como em progresso
     */
    public void marcarComoEmProgresso() {
        setStatus(Status.FAZENDO);
    }

    /**
     * Marca a task como pendente
     */
    public void marcarComoPendente() {
        setStatus(Status.PARA_FAZER);
    }

    /**
     * Verifica se a task está pronta
     */
    public boolean isPronta() {
        return status == Status.PRONTA;
    }

    /**
     * Verifica se a task está em progresso
     */
    public boolean isEmProgresso() {
        return status == Status.FAZENDO;
    }

    /**
     * Verifica se a task está pendente
     */
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

        if (dataDeFinalizacao != null) {
            sb.append(String.format("║ Finalizada em: %-23s ║\n", dataDeFinalizacao));
        } else {
            sb.append("║ Finalizada em: Não finalizada        ║\n");
        }

        sb.append("╚════════════════════════════════════════╝");

        return sb.toString();
    }

    /**
     * Versão simplificada do toString para listagens
     */
    public String toStringSimples() {
        return String.format("ID: %d | %s | Status: %s",
                id, nome, status.getDescricao());
    }
}