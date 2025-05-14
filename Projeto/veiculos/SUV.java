package Projeto.veiculos;

import Projeto.Super.Veiculo;

import java.util.Random;

public class SUV extends Veiculo {
    private static final long serialVersionUID = 1L;
    private Random random = new Random();
    public SUV(String modelo, String placa){
        super(modelo, placa);
    }

    @Override
    protected double[] getFaixaPreco(){
        return new double[]{13.0 , 20.0};
    }
}
