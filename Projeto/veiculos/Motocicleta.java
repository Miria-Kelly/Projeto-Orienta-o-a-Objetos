package Projeto.veiculos;

import Projeto.Super.Veiculo;

import java.util.Random;

public class Motocicleta extends Veiculo {
    private Random random = new Random();
    public Motocicleta(String modelo, String placa){
        super(modelo, placa);
    }

    @Override
    protected double[] getFaixaPreco(){
        return new double[]{5.0 , 12.0};
    }
}
