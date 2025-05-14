package Projeto.Arquivo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import Projeto.Cliente;
import Projeto.Exception.ClienteNaoEncontradoException;
import Projeto.Motorista;
import Projeto.Respositorios.IRepositorioCliente;
import Projeto.Super.FormaDePagamento;

public class RepositorioClienteArquivo implements IRepositorioCliente {
    private final String arquivo = "clientes.ser";
    private List<Cliente> clientes;

    public RepositorioClienteArquivo() {
        clientes = carregar();
    }

    public void adicionar(Cliente cliente) {
        clientes.add(cliente);
        salvar();
    }

    public Cliente buscarPorCpf(String cpf)  { //throws exception é usado para se caso algo dê errado a exceção seja lançada
        List<Cliente> clientes1 = listar();
        for(Cliente c: clientes1){
            if (c.getCpf().equalsIgnoreCase(cpf)) {
                return c;
            }
        }
        return null;
    }
    public List<Cliente> listar(){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Cliente>) in.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<FormaDePagamento> list(String cpf) throws ClienteNaoEncontradoException{
       Cliente cliente = buscarPorCpf(cpf);
       List<FormaDePagamento> lista = cliente.getPagamentos();
       return lista;
    }

    private List<Cliente> carregar() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Cliente>) in.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void salvar() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(clientes);
            out.flush();// força a escrita
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void Atualizar(Cliente cliente) throws Exception {
        if (buscarPorCpf(cliente.getCpf()) != null) {
            removerCliente(cliente.getCpf());
            clientes.add(cliente);
            salvar();
        }
    }
    @Override
    public void removerCliente(String cpf) throws ClienteNaoEncontradoException {
        for (Cliente c : clientes){
            if (c.getCpf().equalsIgnoreCase(cpf)) {
                clientes.remove(c);
                salvar(); //salva no arquivo a nova lista
                return;
            }
        }
    }
}