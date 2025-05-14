package Projeto.Super;

import java.io.Serializable;
import java.util.Random;

public abstract class Veiculo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String modelo;
    private String placa;
    private static final Random RANDOM = new Random();
    private double valorCorrida;

    public Veiculo(String modelo, String placa) {
        this.modelo = modelo;
        this.placa = placa;
        this.valorCorrida = gerarNovoValor();
    }

    protected abstract double[] getFaixaPreco();

    private double gerarNovoValor() {
        double[] faixa = getFaixaPreco();
        return faixa[0] + RANDOM.nextDouble() * (faixa[1] - faixa[0]);
    }

    public void atualizarValor(){
        this.valorCorrida = gerarNovoValor();
    }

    public double getValorCorrida() {
        return valorCorrida;
    }

    public void setValorCorrida(double valorCorrida) {
        this.valorCorrida = valorCorrida;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
