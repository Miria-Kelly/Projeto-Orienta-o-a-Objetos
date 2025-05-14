package Projeto;

import Projeto.Super.FormaDePagamento;

import java.io.Serializable;

public class Pix extends FormaDePagamento implements Serializable {
    String chavePix;

    public Pix(String chavePix) {
        super(true, "Pix");
        this.chavePix = chavePix;
    }

    public String getChavePix() {
        return chavePix;
    }

    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }
}

