package br.com.famel.model;

import java.time.LocalDate;

public class Task {
    private int id;
    private String nome;
    private String descricao;
    private String status;
    private LocalDate dataDeCriacao;
    private LocalDate dataDeFinalizacao;

    private static int contadorId = 0;

    public Task() {
    }

    public Task(int id, String nome, String descricao, String status, String dataCriacao, String dataFinalizacao) {
    }

    public void gerarId() {
        contadorId ++;
        this.id = contadorId;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public  void setStatus(String status) {
            if ("1".equals(status)) {
                this.status = "Para fazer";
            } else if ("2".equals(status)) {
                this.status = "Fazendo";
            } else if ("3".equals(status)) {
                this.status = "Pronta";
                this.dataDeFinalizacao = LocalDate.now();
            } else {
                this.status = status;
            }
    }

    public LocalDate getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(LocalDate dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    public LocalDate getDataDeFinalizacao() {
        return dataDeFinalizacao;
    }

    @Override
    public String toString() {
        return "Task\n" +
                "Id = " + id + "\n" +
                "Nome = " + nome + "\n" +
                "Descrição = " + descricao + "\n" +
                "Status = " + status + "\n" +
                "Data de criação = " + dataDeCriacao + "\n" +
                "Data de Atualização = " + dataDeFinalizacao + "\n";
    }
}
