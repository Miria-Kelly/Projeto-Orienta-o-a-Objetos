package Projeto.Super;

import java.io.Serializable;

public class FormaDePagamento  implements Serializable {
    private boolean valido;
    private String tipo;

    public FormaDePagamento(boolean valido, String tipo){
        this.valido = valido;
        this.tipo = tipo;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString(){
        return "tipo: " +tipo;
    }
}
