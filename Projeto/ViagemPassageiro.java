package Projeto;

import Projeto.Super.Viagem;
import Projeto.ui.Fachada;

import java.io.*;
import java.util.ArrayList;

public class ViagemPassageiro extends Viagem implements Serializable{
    public ViagemPassageiro(Motorista m , Cliente c, String origem , String destino, String pacote) throws Exception {
        super(m, c, origem, destino, null);

    }

}
