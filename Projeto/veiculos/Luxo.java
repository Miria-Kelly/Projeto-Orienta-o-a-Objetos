package Projeto.veiculos;

import Projeto.Super.Veiculo;

import java.util.Random;

public class Luxo extends Veiculo {
    private static final long serialVersionUID = 1L;
    private Random random = new Random();
    public Luxo(String modelo, String placa) {
        super(modelo, placa);
    }

    @Override
    protected double[] getFaixaPreco(){
        return new double[]{20.0 , 50.0};
    }
}
