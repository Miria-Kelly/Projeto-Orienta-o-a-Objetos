package Projeto.ui;

import Projeto.*;
import Projeto.Exception.EntidadeJaExisteException;
import Projeto.Super.FormaDePagamento;
import Projeto.Super.Viagem;
import Projeto.gerenciadores.GerenciarViagem;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InterfaceCliente {
    Scanner input = new Scanner(System.in);
    Fachada fachada = new Fachada();
    InterfaceViagem viagem = new InterfaceViagem();
    public void inicio() throws Exception {
        System.out.println("1. Cadastrar");
        System.out.println("2. Cadastrar nova forma de pagamento");
        System.out.println("3. Cadastrar nova cidade");
        System.out.println("4. Iniciar viagem");
        System.out.println("5. Listar viagens anteriores");
        System.out.println("6. Listar motoristas");
        System.out.println("7. Listar cidades cadastradas");
        System.out.println("8. Listar formas de pagamento cadastradas");
        System.out.println("9. Remover cadastro");
        System.out.println("0. Voltar");
        System.out.println("Sua resposta: ");
        int opcao = input.nextInt();
        input.nextLine();
        switch (opcao){
            case 0:
                break;
            case 1:
               cadastrar();
               break;
            case 2 :
                cadastrarPag();
                break;
            case 3:
                cadastrarCidade();
                break;
            case 4:
                viagem.inicio();
                break;
            case 5:
                listar();
                break;
            case 6:
                listarMotorista();
                break;
            case 7:
                listarCidade();
                break;
            case 8:
                listarPag();
                break;
            case 9:
                remover();
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }

    public void cadastrar() throws Exception {
        System.out.println("Digite seu nome : ");
        String nome = input.nextLine();
        System.out.println("Digite seu CPF : ");
        String cpf = input.nextLine();
        if(cpf.length() != 11){
            throw new IllegalArgumentException("CPF digitado invàlido");
        }
        FormaDePagamento cad = cadastrarPagamento();
        fachada.cadastrarCliente(nome, cpf, cad);
        System.out.println("Cliente cadastrado!");
    }

    public void listar() throws Exception {
        System.out.println("Digite seu CPF : ");
        String cpf2 = input.nextLine();
        if(cpf2.length() != 11){
            throw new IllegalArgumentException("CPF digitado invàlido!");
        }
        if(fachada.buscarCliente(cpf2) != null){
            List<Viagem> viagem = fachada.listarViagemC(cpf2);
            if(!viagem.isEmpty()) {
                for (Viagem v : viagem) {
                    System.out.println(v);
                }
            } else {
                System.out.println("Nenhuma viagem encontrada!");
            }
        }else{
            System.out.println("Cliente não cadastrado!");
            }
    }

    public void listarMotorista(){
        System.out.println("Motoristas Cadastrados : ");
        for (Motorista m : fachada.listarMotoristas()) {
            System.out.println(m);
        }
    }

    public void listarCidade(){
        Map<String, String> cidades = fachada.listarCidades();
        if (cidades.isEmpty()) {
            System.out.println("Nenhuma cidade cadastrada.");
        } else {
            System.out.println("Cidades cadastradas:");
            for (Map.Entry<String, String> cidade : cidades.entrySet()) {
                System.out.println("Nome: " + cidade.getKey() + ", CEP: " + cidade.getValue());
            }
        }
    }

    public boolean cadastrarPag() throws Exception {
        System.out.println("Digite seu CPF : ");
        String cpf = input.nextLine();
        if(cpf.length() != 11){
            throw new IllegalArgumentException("CPF digitado invàlido!");
        }
        System.out.println("Escolha uma forma de pagamento : ");
        System.out.println("1. Dinheiro");
        System.out.println("2. Pix");
        System.out.println("3. Cartão de crédito");
        System.out.println("Sua resposta : ");
        FormaDePagamento pagamento;
        int opcao = input.nextInt();
        input.nextLine();
        switch (opcao) {
            case 1:
                pagamento = new Dinheiro(true);
                fachada.adicionarPagamento(cpf, pagamento);
                break;
            case 2:
                System.out.println("Digite sua chave pix : ");
                String chave = input.nextLine();
                pagamento = new Pix(chave);
                fachada.adicionarPagamento(cpf, pagamento);
                break;
            case 3:
                System.out.println("Digite seu limite: ");
                Double limite = input.nextDouble();
                input.nextLine();
                System.out.println("Digite seu CVV : ");
                String cvv = input.nextLine();
                if(cvv.length() != 3){
                    System.out.println("CVV inválido!");
                    return false;
                }
                pagamento = new CartaoCredito(limite, cvv);
                fachada.adicionarPagamento(cpf, pagamento);
                break;
            default:
                System.out.println("Opção invalida!");
        }
        return true;
    }

    public void listarPag() throws Exception {
        System.out.println("Digite seu CPF : ");
        String cpf = input.nextLine();
        if(cpf.length() != 11){
            throw new IllegalArgumentException("CPF digitado invàlido!");
        }
        if(fachada.buscarCliente(cpf) != null) {
            List<FormaDePagamento> pagamentos = fachada.listarPagamentos(cpf);
            if (!pagamentos.isEmpty()) {
                for (FormaDePagamento f : pagamentos) {
                    System.out.println(f);
                }
            } else {
                System.out.println("Nenhuma forma de pagamento encontrada!");
            }
        }else{
            System.out.println("Cliente não cadastrado!");
        }
    }

    public FormaDePagamento cadastrarPagamento() {
        FormaDePagamento pagamento = null;
        System.out.println("Escolha uma forma de pagamento : ");
        int opcao;
        System.out.println("1. Dinheiro");
        System.out.println("2. Pix");
        System.out.println("3. Cartão de crédito");
        System.out.println("Sua resposta : ");
        opcao = input.nextInt();
        input.nextLine();
        do {
            switch (opcao) {
                case 1:
                    pagamento = new Dinheiro(true);
                    break;
                case 2:
                    System.out.println("Digite sua chave pix : ");
                    String chave = input.nextLine();
                    pagamento = new Pix(chave);
                    break;
                case 3:
                    System.out.println("Digite o limite do seu cartão : ");
                    double limite = input.nextDouble();
                    input.nextLine();
                    System.out.println("Digite seu CVV : ");
                    String cvv = input.nextLine();
                    pagamento = new CartaoCredito(limite, cvv);
                    break;
                default:
                    System.out.println("Digite uma opção válida!");
                    break;
            }
        }while (opcao != 1 && opcao != 2 && opcao != 3);
        return pagamento;
    }

    public void cadastrarCidade() throws EntidadeJaExisteException {
        System.out.println("Digite o nome da cidade : ");
        String nome = input.nextLine();
        System.out.println("Digite o CEP da cidade(somente numeros): ");
        String cep = input.nextLine();
        if(cep.length() != 8){
            throw new IllegalArgumentException("CEP digitado inválido!");
        }
        fachada.cadastrarCidade(nome, cep);
    }

    public void remover() throws Exception {
        System.out.println("Digite o CPF do cliente a ser removido : ");
        String cpf = input.nextLine();
        if(cpf.length() != 11){
            throw new IllegalArgumentException("CPF digitado invàlido!");
        }
        fachada.removerCliente(cpf);
        System.out.println("Cliente Removido!");
    }

}
