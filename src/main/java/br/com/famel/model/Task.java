package br.com.famel.model;

import java.time.LocalDate;

public class Task {
    int id;
    String nome;
    String descricao;
    String status;
    LocalDate dataDeCriacao;
    LocalDate dataDeConclusao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(LocalDate dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    public LocalDate getDataDeConclusao() {
        return dataDeConclusao;
    }

    public void setDataDeConclusao(LocalDate dataDeConclusao) {
        this.dataDeConclusao = dataDeConclusao;
    }

    @Override
    public String toString() {
        return "task{" +
                "id=" + id +
                " nome=" + nome + "\n" +
                " descricao=" + descricao + "\n" +
                " status=" + status + "\n" +
                " dataDeCriacao=" + dataDeCriacao + "\n" +
                " dataDeConclusao=" + dataDeConclusao +
                '}';
    }
}
