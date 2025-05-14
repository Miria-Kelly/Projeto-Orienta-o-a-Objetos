package Projeto;

import Projeto.Super.Viagem;

import java.io.*;
import java.util.ArrayList;

public class ViagemEntrega extends Viagem implements Serializable{

    private String pacote;

    public ViagemEntrega(Motorista m, Cliente c,  String origem, String destino, String pacote) throws Exception {
        super(m,c, origem, destino, pacote);
        this.pacote = pacote;
    }

    public String getPacote() {
        return pacote;
    }

    public void setPacote(String pacote) {
        this.pacote = pacote;
    }
}

