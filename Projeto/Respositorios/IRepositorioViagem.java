package Projeto.Respositorios;
import Projeto.Super.Viagem;

import java.util.List;

public interface IRepositorioViagem {
    void adicionar(Viagem cliente);

    List<Viagem> listarViagemM(String cpf) throws Exception;

    List<Viagem> listarViagemC(String cpf) throws Exception;

    List<Viagem> carregar();
}
