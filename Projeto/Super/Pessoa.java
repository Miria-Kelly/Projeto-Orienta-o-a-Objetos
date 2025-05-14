package Projeto.Super;

import java.io.Serializable;

public abstract class Pessoa implements Serializable{
    private static final long serialVersionUID = 1l; //controle de versão feito para segurança e garante a compatibilidade de versões na desserialização
    private String nome;
    private String cpf;
    private double estrelas = 0.0;
    private int votos = 0;


    public Pessoa(String nome, String cpf, int votos) {
        setNome(nome);
        setCpf(cpf);
        setVotos(votos);//quantidade de pessoas que ja votaram
    }

    public Pessoa(String nome, String cpf, Double estrelas) {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public double getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(double estrelasNova) {
        this.estrelas = ((this.estrelas * (this.votos-1)) + estrelasNova) / (this.votos);
    }

    public int getVotos() {
        return this.votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }
}
