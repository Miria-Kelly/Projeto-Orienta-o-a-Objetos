package Projeto;

import Projeto.Super.FormaDePagamento;
import Projeto.Super.Pessoa;
import java.io.*;
import java.util.ArrayList;

public class Cliente extends Pessoa implements Serializable {
    ArrayList<FormaDePagamento> pagamentos = new ArrayList<>();
    private static final long serialVersionUID = 1L;

    public Cliente(String nome, String cpf  , FormaDePagamento formaDePagamento, int votos) {
        super(nome, cpf, votos);
        setPagamentos(formaDePagamento);
    }

    public ArrayList<FormaDePagamento> getPagamentos() {
        return pagamentos;
    }

    public FormaDePagamento setPagamentos(FormaDePagamento pagamento) {

        this.pagamentos.add(pagamento);
        return pagamento;
    }


    @Override
    public String toString(){
        return "Nome: " + this.getNome() +
                ", CPF: " + this.getCpf() +
                ", avaliações: "+ String.format("%.1f" , this.getEstrelas());

    }
}

