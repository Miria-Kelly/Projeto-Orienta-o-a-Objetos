package Projeto.Super;

import Projeto.Cliente;
import Projeto.LocalDestino;
import Projeto.LocalOrigem;
import Projeto.ui.Fachada;
import Projeto.Motorista;

import java.io.Serializable;

public class Viagem implements Serializable{
    private static final long serialVersionUID = 1l;
    private Cliente cliente;
    private Motorista motorista;
    private String origem;
    private String destino;
    private String pacote;
    private transient Fachada  fachada = new Fachada();


    public Viagem(Motorista m, Cliente c, String origem,  String destino, String pacote) throws Exception {
        if (!fachada.verificarCidade(origem)){
            throw new IllegalArgumentException("Cidade nao cadastrada!.");
        }
        if (!fachada.verificarCidade(destino)){
            throw new IllegalArgumentException("Cidade nao cadastrada!.");
        }
        if (m == null) {
            throw new IllegalArgumentException("Motorista não encontrado.");
        }
        if (c == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        this.motorista = m;
        this.cliente = c;
        this.origem = origem;
        this.destino = destino;
        this.pacote = pacote;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }
    @Override
    public String toString() {
        return "Viagem{" +
                "Motorista='" + motorista.getNome() + '\'' +
                ", Cliente='" + cliente.getNome() + '\'' +
                ", Origem='" +  origem+ '\'' +
                ", Destino='" + destino+ '\'' +
                ", Veículo='" + motorista.getVeiculo().getModelo() + '\'' +
                (pacote != null && !pacote.isBlank() ? " , Pacote '" + pacote+ '\'' : "") +
                '}';
    }

}
