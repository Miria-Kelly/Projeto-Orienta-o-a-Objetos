package Projeto;

import Projeto.Super.FormaDePagamento;
import Projeto.ui.InterfaceCliente;

import java.io.Serializable;

public class Dinheiro extends FormaDePagamento implements Serializable {
    public Dinheiro(boolean valido) {
        super(valido, "Dinheiro");
    }
}
