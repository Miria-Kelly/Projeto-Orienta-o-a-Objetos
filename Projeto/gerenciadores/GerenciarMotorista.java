package Projeto.gerenciadores;

import Projeto.Arquivo.RepositorioMotoristaArquivo;
import Projeto.Exception.EntidadeJaExisteException;
import Projeto.Motorista;
import Projeto.Respositorios.IRepositorioMotorista;
import Projeto.Super.Veiculo;

import java.util.List;

public class GerenciarMotorista {
    private IRepositorioMotorista rMotorista;
    private GerenciarVeiculos Gveiculo;

    public GerenciarMotorista(){
        this.rMotorista = new RepositorioMotoristaArquivo();
        this.Gveiculo = new GerenciarVeiculos();
    }

    public void cadastrarMotorista(String nome, String cpf, boolean validado, boolean emViagem,  Veiculo veiculo , int votos) throws Exception {
        if(rMotorista.buscarPorCpf(cpf) != null){
            throw new EntidadeJaExisteException("Já existe um cliente com esse CPF.");
        }
        Motorista m = new Motorista(nome, cpf, validado, emViagem, veiculo, votos);
        rMotorista.adicionar(m);
        Gveiculo.adicionar(veiculo);
    }

    public Motorista buscarMotorista(String cpf) throws Exception {
        return rMotorista.buscarPorCpf(cpf);
    }

    public List<Motorista> listar(){
        return  rMotorista.listar();
    }

    public void removerMotorista(String Cpf){
        rMotorista.removerMotorista(Cpf);
    }

    public void viajarMotorista(Motorista m) throws Exception {
        removerMotorista(m.getCpf());
        m.setEmViagem(true);
        cadastrarMotorista(m.getNome(), m.getCpf(), m.getValidado(), m.getEmViagem(), m.getVeiculo(), m.getVotos());
    }

    public void atualizarMotorista(Motorista motoristaNovo) throws Exception {
        rMotorista.Atualizar(motoristaNovo);
        return;
    }

    public void atualizarVeiculoDoMotorista(String cpf, Veiculo novoVeiculo) throws Exception {
        Motorista motorista = buscarMotorista(cpf);
        if (motorista != null) {
            motorista.setVeiculo(novoVeiculo);
            Gveiculo.adicionar(novoVeiculo);
            atualizarMotorista(motorista);
            System.out.println("Veículo do motorista atualizado com sucesso.");
        } else {
            throw new Exception("Motorista com CPF " + cpf + " não encontrado.");
        }
    }
}
