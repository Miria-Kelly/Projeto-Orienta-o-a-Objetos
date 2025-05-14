package Projeto.veiculos;

import Projeto.Super.Veiculo;

import java.util.Random;


public class Economico extends Veiculo {
    private static final long serialVersionUID = 1L;
    private Random random = new Random();
    public Economico(String modelo, String placa) {
        super(modelo, placa);
    }

    @Override
    protected double[] getFaixaPreco(){
        return new double[]{10.0 , 15.0};
    }
}
