package Projeto.Exception;

public class MotoristaNaoEncontradoException extends RuntimeException {
    public MotoristaNaoEncontradoException(String cpf) {

        super("Motorista com CPF " + cpf + " não foi encontrado!");
    }
}
