package org.example;

public class Pessoa {
    private String nome;
    private Data dataNascimento;
    private Cpf cpf;

    Pessoa(String nome, Data dataNascimento, Cpf cpf) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public Data getDataNascimento() {
        return dataNascimento;
    }

    public Cpf getCpf() {
        return cpf;
    }
}
