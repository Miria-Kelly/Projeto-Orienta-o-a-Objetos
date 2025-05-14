package Projeto.gerenciadores;

import Projeto.Arquivo.RepositorioVeiculoArquivo;
import Projeto.Exception.EntidadeJaExisteException;
import Projeto.Respositorios.IRepositorioVeiculos;
import Projeto.Super.Veiculo;

import java.util.List;

public class GerenciarVeiculos {
    private IRepositorioVeiculos rVeiculos;

    public GerenciarVeiculos() {
        rVeiculos = new RepositorioVeiculoArquivo();
    }

    public void adicionar(Veiculo v) throws EntidadeJaExisteException {
        rVeiculos.adicionar(v);
    }

    public Veiculo buscarPorPlaca(String placa) throws EntidadeJaExisteException{
        Veiculo v = rVeiculos.buscarPorPlaca(placa);
        if (v != null){
            return v;
        }else{
            throw new EntidadeJaExisteException("Esse veiculo já esta cadastrado!.");
        }
    }

    public List<Veiculo> listar() {
        return rVeiculos.listar();
    }

    public void remover(String placa){
        rVeiculos.removerVeiculo(placa);
    }


}
