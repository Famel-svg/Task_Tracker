package br.com.famel.model;

import java.time.LocalDate;
import java.util.Objects;

public class Task {
    private int id;
    private String nome;
    private String descricao;
    private String status;
    private LocalDate dataDeCriacao;
    private LocalDate dataDeConclusao;

    private static int contadorId = 0;

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

    public void setStatus(int status) {
        setStatus(String.valueOf(status));
    }

    public void setStatus(String status) {
        if (Objects.equals(status,  "1")) {
            setStatus("Para fazer");
        } else if (Objects.equals(status, "2")) {
            setStatus("Fazendo");
        } else {
            setStatus("Pronta");
        }
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
        return "Task\n" +
                "Id = " + id + "\n" +
                "Nome = " + nome + "\n" +
                "Descrição = " + descricao + "\n" +
                "Status = " + status + "\n" +
                "Data de criação = " + dataDeCriacao + "\n" +
                "Data de conclusão = " + dataDeConclusao;
    }
}
