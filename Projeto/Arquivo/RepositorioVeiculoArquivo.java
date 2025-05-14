package Projeto.Arquivo;

import Projeto.Exception.EntidadeJaExisteException;
import Projeto.Exception.MotoristaNaoEncontradoException;
import Projeto.Motorista;
import Projeto.Respositorios.IRepositorioVeiculos;
import Projeto.Super.Veiculo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioVeiculoArquivo implements IRepositorioVeiculos {
    private final String arquivo = "Veiculo.ser";
    private List<Veiculo> veiculos;

    public RepositorioVeiculoArquivo(){
        veiculos = carregar();
    }

    @Override
    public void adicionar(Veiculo veiculo) throws EntidadeJaExisteException{
        if (buscarPorPlaca(veiculo.getPlaca()) == null) {
            veiculos.add(veiculo);
            salvar();
            return;
        }
    }

    @Override
    public Veiculo buscarPorPlaca(String placa) {
        for (Veiculo v: veiculos){
            if(v.getPlaca().equalsIgnoreCase(placa)){
                return v;
            }
        }
        return null;
    }

    @Override
    public List<Veiculo> listar() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Veiculo>) in.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    public void salvar(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))){
            out.writeObject(veiculos);
            System.out.println("Veiculo cadastrado!");
        }catch(IOException e){
            System.out.println("Erro ao salvar o motorista  " + e.getMessage());
            e.printStackTrace();
        }
    }
    public List<Veiculo> carregar(){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Veiculo>) in.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    @Override
    public void removerVeiculo(String placa) {
        for (Veiculo v: veiculos){
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                veiculos.remove(v);
                salvar();
                System.out.println("Placa removida");//salva no arquivo a nova lista
                return;
            }
        }
    }
}
