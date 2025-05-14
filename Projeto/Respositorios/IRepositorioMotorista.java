package Projeto.Respositorios;

import Projeto.Exception.EntidadeJaExisteException;
import Projeto.Motorista;
import Projeto.Super.Veiculo;

import java.util.List;

public interface IRepositorioMotorista {
    void adicionar(Motorista motorista) throws Exception;
    Motorista buscarPorCpf(String cpf) throws Exception;
    List<Motorista> listar();
    void removerMotorista(String Cpf);
    void salvar();
    void Atualizar(Motorista motorista) throws Exception;
    void atualizarVeiculoDoMotorista(String cpf, Veiculo novoVeiculo) throws Exception;
}
