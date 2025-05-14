package Projeto.Exception;

public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException(String cpf) {
        super("Cliente com cpf "+ cpf + " não foi econtrado!");
    }
}
