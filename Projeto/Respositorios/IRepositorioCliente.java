package Projeto.Respositorios;

import Projeto.Cliente;
import Projeto.Super.FormaDePagamento;

import java.util.List;

public interface IRepositorioCliente {
        void adicionar(Cliente cliente);
        Cliente buscarPorCpf(String cpf) throws Exception;
        List<Cliente> listar();
        List<FormaDePagamento> list(String cpf) throws Exception;
        void salvar();
        void Atualizar(Cliente cliente) throws Exception;
        void removerCliente(String cpf);
}
