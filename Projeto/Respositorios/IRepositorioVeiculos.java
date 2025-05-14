package Projeto.Respositorios;

import Projeto.Exception.EntidadeJaExisteException;
import Projeto.Motorista;
import Projeto.Super.Veiculo;

import java.util.List;

public interface IRepositorioVeiculos {
    void adicionar(Veiculo veiculo) throws EntidadeJaExisteException;
    Veiculo buscarPorPlaca(String cpf);
    List<Veiculo> listar();
    void removerVeiculo(String placa);
}



