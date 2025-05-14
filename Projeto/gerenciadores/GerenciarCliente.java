package Projeto.gerenciadores;

import Projeto.Arquivo.RepositorioClienteArquivo;
import Projeto.Arquivo.RepositorioPagamentoArquivo;
import Projeto.Cliente;
import Projeto.Exception.EntidadeJaExisteException;
import Projeto.Motorista;
import Projeto.Respositorios.IRepositorioCliente;
import Projeto.Respositorios.IRepositorioPagamento;
import Projeto.Super.FormaDePagamento;

import java.util.List;

public class GerenciarCliente {
    private IRepositorioCliente rCliente;
    private IRepositorioPagamento rPagamentos;

    public GerenciarCliente(){
        this.rCliente = new RepositorioClienteArquivo();
        this.rPagamentos = new RepositorioPagamentoArquivo();
    }

    public void cadastrarCliente(String nome, String cpf , FormaDePagamento formaDePagmento, int votos) throws Exception {
        if(rCliente.buscarPorCpf(cpf) != null){
            throw new EntidadeJaExisteException("Já existe um cliente com esse CPF.");
        }
        Cliente c = new Cliente(nome, cpf, formaDePagmento, votos);
        rCliente.adicionar(c);
        rPagamentos.adicionarPagamento(formaDePagmento);
    }

    public void adicionarPagamento(String cpf , FormaDePagamento pagamento) throws Exception{
        List<Cliente> clientes = listar();
        for (Cliente cliente : clientes) {//busca pelo cliente certo
            if (cliente.getCpf().equalsIgnoreCase(cpf)) {
                for (FormaDePagamento p : cliente.getPagamentos()) {
                    System.out.println(p.getTipo());
                    if (!(p.getTipo().equalsIgnoreCase(pagamento.getTipo()))) {
                        cliente.setPagamentos(pagamento);
                        rPagamentos.adicionarPagamento(pagamento);
                        atualizarCliente(cliente);
                        System.out.println("Forma de pagamento adicionada!");
                        return;
                    } else {
                        System.out.println("Forma de pagamento já cadastrada!");
                        return;
                    }
                }
            }
        }
    }
    public void removerCliente(String cpf) {
        rCliente.removerCliente(cpf);
    }

    public Cliente buscarCliente(String cpf) throws Exception {
        return rCliente.buscarPorCpf(cpf);
    }

    public List<Cliente> listar(){
        return  rCliente.listar();
    }

    public List<FormaDePagamento> list(String cpf) throws Exception{
        return rCliente.list(cpf);
    }

    public void atualizarCliente(Cliente clienteNovo) throws Exception {
        rCliente.Atualizar(clienteNovo);
    }
}
