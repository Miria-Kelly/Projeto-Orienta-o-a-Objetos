package Projeto.Arquivo;

import Projeto.Exception.EntidadeJaExisteException;
import Projeto.Respositorios.IRepositorioLocal;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class RepositorioLocaArquivo implements IRepositorioLocal {
    private static final String arquivo = "cidades.ser";
    private static final Map<String, String> cidades = new HashMap<>();

     public RepositorioLocaArquivo(){
        carregarCidades();
    }

    private void carregarCidades() {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 2) {
                    cidades.put(partes[0].trim().toLowerCase(), partes[1].trim());
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Override
    public void adicionarCidade(String nome, String cep) throws EntidadeJaExisteException {
        if (cidades.containsValue(cep)){
            throw new EntidadeJaExisteException("Cidade com esse CEP já foi cadastrada!");
        }else{
            cidades.put(nome, cep);
            salvarCidade(nome, cep);
        }
    }
    @Override
    public boolean cidadeExiste(String nomeCidade) {
        return cidades.containsKey(nomeCidade.toLowerCase());
    }
    @Override
    public String getCep(String nomeCidade) {
        return cidades.getOrDefault(nomeCidade.toLowerCase(), "CEP desconhecido");
    }
    @Override
    public Map<String, String> listarCidades() {
        return new HashMap<>(cidades); // Retorna uma cópia do mapa para evitar modificações externas
    }
    private void salvarCidade(String nome, String cep) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write(nome + "," + cep);
            writer.newLine(); // Adiciona uma nova linha para a próxima cidade
        } catch (IOException e) {
            System.out.println("Erro ao salvar a cidade: " + e.getMessage());
        }
    }
}

