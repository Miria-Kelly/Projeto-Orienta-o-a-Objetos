package Projeto.Arquivo;
import Projeto.Exception.EntidadeJaExisteException;
import Projeto.Exception.MotoristaNaoEncontradoException;
import Projeto.Motorista;
import Projeto.Respositorios.IRepositorioMotorista;
import Projeto.Super.Veiculo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioMotoristaArquivo implements IRepositorioMotorista {
    private final String arquivo = "motoristas.ser";
    private List<Motorista> motoristas;

    public RepositorioMotoristaArquivo(){
        motoristas = carregar();
    }

    @Override
    public void adicionar(Motorista motorista) throws Exception {
        if (buscarPorCpf(motorista.getCpf()) != null) {
            throw new EntidadeJaExisteException("Motorista já cadastrado");
        }
        motoristas.add(motorista);
        salvar();
    }

    @Override
    public Motorista buscarPorCpf(String cpf)  {//throws exception é usado para se caso algo dê errado a exceção seja lançada
        for (Motorista m: motoristas) { //busca pelo cliente certo
            if (m.getCpf().equalsIgnoreCase(cpf)) {
                return m;
            }
        }
        return null;
    }

    @Override
    public List<Motorista> listar() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Motorista>) in.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    @Override
    public void salvar(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))){
            out.writeObject(motoristas);
            out.flush(); //força a escrita
        }catch(IOException e){
            System.out.println("Erro ao salvar o motorista  " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public void Atualizar(Motorista motorista) throws Exception {
        if (buscarPorCpf(motorista.getCpf()) != null) {
            removerMotorista(motorista.getCpf());
            motoristas.add(motorista);
            salvar();
        }
    }

    public List<Motorista> carregar(){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Motorista>) in.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
     public void removerMotorista(String cpf) throws MotoristaNaoEncontradoException {
        for (Motorista m : motoristas){
            if (m.getCpf().equalsIgnoreCase(cpf)) {
                motoristas.remove(m);
                salvar(); //salva no arquivo a nova lista
                return;
            }
        }
        throw new MotoristaNaoEncontradoException(cpf);
    }

    @Override
    public void atualizarVeiculoDoMotorista(String cpf, Veiculo novoVeiculo) throws Exception {
        Motorista motorista = buscarPorCpf(cpf);
        motorista.setVeiculo(novoVeiculo);
        if (motorista != null) {
            for (int i = 0; i < motoristas.size(); i++) {
                if (motoristas.get(i).getCpf().equalsIgnoreCase(motorista.getCpf())) {
                    motoristas.set(i, motorista); // atualiza direto na lista
                    salvar();
                    return;
                }
            }
        }else {
            throw new MotoristaNaoEncontradoException("Motorista com CPF " + cpf + " não encontrado.");
        }
    }
}
