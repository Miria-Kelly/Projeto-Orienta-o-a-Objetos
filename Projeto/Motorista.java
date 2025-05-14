package Projeto;

import Projeto.Super.Pessoa;
import Projeto.Super.Veiculo;

public class Motorista extends Pessoa  {
    private static final long serialVersionUID = 2L;
    private boolean validado;
    private boolean emViagem;
    private Veiculo veiculo;

    public Motorista(String nome, String cpf, boolean validado, boolean emViagem,  Veiculo veiculo , int votos) {
        super(nome, cpf , votos);
        this.emViagem = emViagem;
        this.validado = validado;
        this.veiculo = veiculo;
    }


    public boolean getValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public boolean getEmViagem() {
        return emViagem;
    }

    public void setEmViagem(boolean emViagem) {
        this.emViagem = emViagem;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public boolean isEmViagem() {
        return emViagem;
    }

    public boolean isValidado() {
        return validado;
    }


    @Override
    public String toString() {
        return "Nome: " + this.getNome() +
                ", CPF: " + this.getCpf() +
                ", Ocupado:  " + this.getEmViagem() +
                ", Avaliações: " + String.format("%.1f" , this.getEstrelas());
    }
}
