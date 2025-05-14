package Projeto.Respositorios;

import Projeto.Exception.EntidadeJaExisteException;
import Projeto.Super.Local;

import java.util.List;
import java.util.Map;

public interface IRepositorioLocal {
    void adicionarCidade(String nome, String cep) throws EntidadeJaExisteException;
    boolean cidadeExiste(String nomeCidade);
    String getCep(String nomeCidade);
    Map<String, String> listarCidades();
}
