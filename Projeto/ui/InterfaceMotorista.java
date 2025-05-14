package Projeto.ui;

import Projeto.*;
import Projeto.Exception.EntidadeJaExisteException;
import Projeto.Super.Veiculo;
import Projeto.Super.Viagem;
import Projeto.veiculos.Economico;
import Projeto.veiculos.Luxo;
import Projeto.veiculos.Motocicleta;
import Projeto.veiculos.SUV;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InterfaceMotorista {
    Scanner input = new Scanner(System.in);
    static Fachada fachada = new Fachada();

    public void inicio() throws Exception {
        System.out.println("1. Cadastrar");
        System.out.println("2. Listar viagens anteriores");
        System.out.println("3. Listar clientes");
        System.out.println("4. Listar cidades cadastradas");
        System.out.println("5. Remover cadastro");
        System.out.println("6. Atualizar veiculo");
        System.out.println("0. Voltar");
        System.out.println("Sua opcao: ");
        int opcao = input.nextInt();
        input.nextLine();
            switch (opcao) {
                case 1:
                    CadastrarMotorista();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    listarCliente();
                    break;
                case 4:
                    listarCidade();
                    break;
                case 5:
                    removerM();
                    break;
                case 6:
                    atualizarVeiculoMotorista();
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
    }
    public void CadastrarMotorista() throws Exception {
        System.out.println("Digite seu nome : ");
        String nome = input.nextLine();
        System.out.println("Digite seu CPF : ");
        String cpf = input.nextLine();
        if (cpf.length() != 11) {
            throw new IllegalArgumentException("CPF digitado invàlido!");
        }
        if (fachada.buscarMotorista(cpf) != null) {
            throw new EntidadeJaExisteException("Já existe um cliente com esse CPF.");
        }
        System.out.println("Tipo do carro");
        System.out.println("1. Economico");
        System.out.println("2. Luxo");
        System.out.println("3. SUV");
        System.out.println("4. Motocicleta");
        System.out.println("Digite o número: ");
        int tipo;
        if (input.hasNextInt()) {
            tipo = input.nextInt();
            input.nextLine();
        } else {
            System.out.println("Digite apenas o numero!");
            tipo = input.nextInt();
            input.nextLine();
        }



        System.out.println("Modelo: ");
        String modelo = input.nextLine();
        System.out.println("Placa (Digite sem espaços e simbolos): ");
        String placa = input.nextLine();
        if(placa.length() != 7){
            throw new IllegalArgumentException("Placa digitada inválida!");
        }
        System.out.println("Confirme seus dados: ");
        System.out.println("nome: "+nome);
        System.out.println("CPF: " +cpf);
        System.out.println("Veiculo: " + modelo + " - " + placa);
        System.out.println("confimar seus dados? ");
        System.out.println("1. sim");
        System.out.println("2. nao");
        int confirmar = input.nextInt();
        if (confirmar == 1) {
            switch (tipo) {
                case 1:
                    Veiculo veiculo = new Economico(modelo, placa);
                    fachada.cadastrarMotorista(nome, cpf, true, false, veiculo);
                    System.out.println("Motorista cadastrado!");
                    break;
                case 2:
                    Veiculo veiculo1 = new Luxo(modelo, placa);
                    fachada.cadastrarMotorista(nome, cpf, true, false, veiculo1);
                    System.out.println("Motorista cadastrado!");
                    break;
                case 3:
                    Veiculo veiculo2 = new SUV(modelo, placa);
                    fachada.cadastrarMotorista(nome, cpf, true, false, veiculo2);
                    System.out.println("Motorista cadastrado!");
                    break;
                case 4:
                    Veiculo veiculo3 = new Motocicleta(modelo, placa);
                    fachada.cadastrarMotorista(nome, cpf, true, false, veiculo3);
                    System.out.println("Motorista cadastrado!");
                    break;
                default:
                    System.out.println("Tipo de carro inválido");
            }
        }
        else{
            System.out.println("Dados não confirmados");
        }
    }

    public void listar() throws Exception {
        System.out.println("Digite seu CPF : ");
        String cpf1 = input.nextLine();
        if(cpf1.length() != 11){
            throw new IllegalArgumentException("CPF digitado invàlido!");
        }
        List<Viagem> viagem = fachada.listarViagemMotorista(cpf1);
        if(!viagem.isEmpty()) {
            for (Viagem v : viagem) {
                System.out.println(v);
            }
        }else{
            System.out.println("Nenhum viagem encontrada!");
        }
    }

    public void listarCliente() {
        System.out.println("Clientes Cadastrados");
        for (Cliente c : fachada.listarCliente()) {
            System.out.println(c);
        }
    }

    public static void listarCidade(){
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

    public void removerM() throws Exception {
        System.out.println("Digite o CPF do motorista a ser removido  : ");
        String cpf1 = input.nextLine();
        if(cpf1.length() != 11){
            throw new IllegalArgumentException("CPF digitado invàlido!");
        }
        if (fachada.buscarMotorista(cpf1) != null) {
            fachada.removerMotorista(cpf1);
            System.out.println("Cadastro removido!");
        }else{
        System.out.println("Cliente não cadastrado!");
        }
    }

    public void atualizarVeiculoMotorista() throws Exception {
        System.out.println("Digite seu CPF : ");
        String cpf = input.nextLine();
        if (cpf.length() != 11) {
            throw new IllegalArgumentException("CPF digitado inválido!");
        }

        Motorista motorista = fachada.buscarMotorista(cpf);
        if (motorista == null) {
            System.out.println("Motorista não encontrado.");
            return;
        }

        System.out.println("Digite os novos dados do veículo:");
        System.out.println("1. Econômico");
        System.out.println("2. Luxo");
        System.out.println("3. SUV");
        System.out.println("4. Motocicleta");
        System.out.println("Digite o número do tipo do veículo:");
        int tipo = input.nextInt();
        input.nextLine();

        System.out.println("Modelo: ");
        String modelo = input.nextLine();

        System.out.println("Placa (sem espaços ou símbolos): ");
        String placa = input.nextLine();
        if (placa.length() != 7) {
            throw new IllegalArgumentException("Placa inválida!");
        }

        Veiculo novoVeiculo;
        switch (tipo) {
            case 1:
                novoVeiculo = new Economico(modelo, placa);
                break;
            case 2:
                novoVeiculo = new Luxo(modelo, placa);
                break;
            case 3:
                novoVeiculo = new SUV(modelo, placa);
                break;
            case 4:
                novoVeiculo = new Motocicleta(modelo, placa);
                break;
            default:
                System.out.println("Tipo de veículo inválido.");
                return;
        }

        fachada.atualizarVeiculoDoMotorista(cpf, novoVeiculo);
        System.out.println("Veículo atualizado com sucesso!");
    }
}

