package Projeto.Super;

import java.io.Serializable;

public class Local implements Serializable {
    private String nome;
    private String cep;

    public Local(String nome , String cep){
        setNome(nome);
        setCep(cep);
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
