package Projeto;

import Projeto.Super.FormaDePagamento;

import java.io.Serializable;

public class CartaoCredito extends FormaDePagamento implements Serializable {
    Double limite;
    String CVV;

    public CartaoCredito(Double limite, String CVV) {
        super(true, "Cartão de crédito");
        this.limite = limite;
        this.CVV = CVV;
    }

    public Double getLimite() {
        return limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    public String getCVC() {
        return CVV;
    }

    public void setCVC(String CVC) {
        this.CVV = CVV;
    }
}
