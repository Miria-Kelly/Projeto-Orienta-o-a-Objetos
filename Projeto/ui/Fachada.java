package Projeto.ui;

import Projeto.Cliente;
import Projeto.Exception.EntidadeJaExisteException;
import Projeto.Motorista;
import Projeto.Super.FormaDePagamento;
import Projeto.Super.Veiculo;
import Projeto.Super.Viagem;
import Projeto.gerenciadores.*;

import java.lang.reflect.GenericArrayType;
import java.util.List;
import java.util.Map;

public class Fachada {
    private GerenciarMotorista GereMotorista;
    private GerenciarCliente GereCliente;
    private GerenciarViagem gerenciarViagem;
    private GerenciarLocal gerenciarLocal;
    private GerenciarVeiculos gerenciarVeiculos;

    public Fachada(){
        this.GereMotorista = new GerenciarMotorista();
        this.GereCliente = new GerenciarCliente();
        this.gerenciarViagem = new GerenciarViagem();
        this.gerenciarLocal = new GerenciarLocal();
        this.gerenciarVeiculos = new GerenciarVeiculos();

    }

    public void cadastrarMotorista(String nome, String cpf, boolean validado, boolean emViagem,  Veiculo veiculo) throws Exception {
        GereMotorista.cadastrarMotorista(nome, cpf, validado, emViagem, veiculo , 0);
    }

    public Motorista buscarMotorista(String cpf) throws Exception {
        return  GereMotorista.buscarMotorista(cpf);
    }

    public List<Motorista> listarMotoristas(){
        return GereMotorista.listar();
    }

    public void cadastrarCliente(String nome, String cpf , FormaDePagamento formaDePagamento) throws Exception {
        GereCliente.cadastrarCliente(nome, cpf , formaDePagamento, 0);
    }
    public Cliente buscarCliente(String cpf) throws Exception {
        return  GereCliente.buscarCliente(cpf);
    }

    public List<Cliente> listarCliente(){
        return GereCliente.listar();
    }

    public void viajarMotorista(Motorista m) throws Exception {
        gerenciarVeiculos.remover(m.getVeiculo().getPlaca());
        //remove a placa antes pra quando for cadastrar o motorista dnv a placa nao vai ta cadastrada
        GereMotorista.viajarMotorista(m);
    }

    public void cadastrarViagem(Motorista m, Cliente c, String origem, String destino, String pacote) throws Exception {
        gerenciarViagem.cadastrarViagem(m, c, origem, destino, pacote);
    }
    public List<Viagem> listarViagemMotorista(String cpf) throws Exception{
        return gerenciarViagem.listarViagemMotorista(cpf);
    }
    public List<Viagem> listarViagemC(String cpf) throws Exception{
        return  gerenciarViagem.listarViagemCliente(cpf);
    }
    public void cadastrarCidade(String nome, String cep) throws EntidadeJaExisteException {
        gerenciarLocal.cadastrarCidade(nome, cep);
    }
    public boolean verificarCidade(String nome){

        return gerenciarLocal.verificarCidade(nome);
    }
    public Map<String, String> listarCidades() {
        return gerenciarLocal.listarCidades(); // Retorna uma cópia do mapa para evitar modificações externas
    }

    public void adicionarPagamento(String cpf , FormaDePagamento pagamento) throws Exception {
        GereCliente.adicionarPagamento(cpf, pagamento);
    }

    public List<FormaDePagamento> listarPagamentos(String cpf) throws Exception{
        return GereCliente.list(cpf);
    }

    public void atualizarCliente(Cliente cliente) throws Exception {
        GereCliente.atualizarCliente(cliente);
    }

    public void atualizarMotorista(Motorista motorista) throws Exception {
        GereMotorista.atualizarMotorista(motorista);
        gerenciarVeiculos.remover(motorista.getVeiculo().getPlaca());
    }

    public void removerMotorista(String cpf) throws Exception {
        Motorista m = buscarMotorista(cpf);
        gerenciarVeiculos.remover(m.getVeiculo().getPlaca());
        GereMotorista.removerMotorista(m.getCpf());
    }

    public void removerCliente(String cpf) throws Exception {
        GereCliente.removerCliente(cpf);
    }

    public void atualizarVeiculoDoMotorista(String cpf, Veiculo novoVeiculo) throws Exception {
        GereMotorista.atualizarVeiculoDoMotorista(cpf , novoVeiculo);
    }
}
