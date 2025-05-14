package Projeto.gerenciadores;

import Projeto.Arquivo.RepositorioLocaArquivo;
import Projeto.Exception.EntidadeJaExisteException;
import Projeto.Respositorios.IRepositorioLocal;

import java.util.HashMap;
import java.util.Map;

public class GerenciarLocal {
    private IRepositorioLocal rLocal;

    public GerenciarLocal() {
        this.rLocal = new RepositorioLocaArquivo();
    }

    public void cadastrarCidade(String nome, String cep) throws EntidadeJaExisteException {
        if (rLocal.cidadeExiste(nome)) {
            System.out.println("Cidade já cadastrada.");
        } else {
            rLocal.adicionarCidade(nome, cep);
            System.out.println("Cidade " + nome + " cadastrada com sucesso!");
        }
    }

    public boolean verificarCidade(String nome) {
        return rLocal.cidadeExiste(nome);
    }

    public String buscarCep(String nomeCidade) {
        return rLocal.getCep(nomeCidade);
    }
    public Map<String, String> listarCidades() {
        return rLocal.listarCidades(); // Retorna uma cópia do mapa para evitar modificações externas
    }

}