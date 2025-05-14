package Projeto.ui;

import Projeto.*;
import Projeto.Exception.CidadeNaoEncontradaException;
import Projeto.Exception.EntidadeJaExisteException;
import Projeto.Super.FormaDePagamento;
import Projeto.Super.Veiculo;
import Projeto.gerenciadores.GerenciarViagem;
import Projeto.veiculos.Economico;
import Projeto.veiculos.Luxo;
import Projeto.veiculos.Motocicleta;
import Projeto.veiculos.SUV;


import java.util.List;
import java.util.Scanner;

public class InterfaceViagem {
    Scanner input = new Scanner(System.in);
    static Fachada fachada = new Fachada();

    public void inicio() throws Exception {
        System.out.println("Qual tipo de viagem você deseja fazer : ");
        System.out.println("1. Viagem");
        System.out.println("2. Entrega de pacote");
        System.out.println("0. Voltar");
        System.out.println("Sua resposta : ");
        int opcao = input.nextInt();
        input.nextLine();
        switch (opcao) {
            case 0:
                break;
            case 1:
                Viajar();
                break;
            case 2:
                viagemPacote();
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    public void viagemPacote() throws Exception {
        System.out.println("Digite seu CPF : ");
        String cpf = input.nextLine();
        if(cpf.length() != 11){
            throw new IllegalArgumentException("CPF digitado invàlido!");
        }
        System.out.println("Digite qual pacote a ser enviado : ");
        String pacote = input.nextLine();
        Cliente c = fachada.buscarCliente(cpf);
        viajarPacote(c, pacote);
    }

    public void viajarPacote(Cliente cliente, String pacote) throws Exception {
        String origem = null;
        String destino = null;
        boolean cidadesValidas = false;

        do {
            System.out.println("Nome da cidade de origem: ");
            origem = input.nextLine();
            System.out.println("Nome da cidade de destino: ");
            destino = input.nextLine();

            boolean origemValida = fachada.verificarCidade(origem);
            boolean destinoValida = fachada.verificarCidade(destino);

            if (!origemValida || !destinoValida) {
                System.out.println("Uma ou ambas as cidades não estão cadastradas!");

                if (!origemValida) {
                    System.out.println("Cidade de origem inválida: " + origem);
                    cadastrarNovaCidade();
                }

                if (!destinoValida) {
                    System.out.println("Cidade de destino inválida: " + destino);
                    cadastrarNovaCidade();
                }
            } else {
                cidadesValidas = true;
            }

        } while (!cidadesValidas);

        System.out.println("Veículos Disponíveis:");
        listarVeiculosDisponiveisPacote();

        System.out.println("Qual tipo de veículo você escolhe: ");
        String veiculo = input.nextLine();

        Motorista motoristaEscolhido = escolherMotorista(veiculo);

        if (motoristaEscolhido != null) {
            fachada.viajarMotorista(motoristaEscolhido);
            System.out.println("Viagem aceita");
            System.out.println("Seu motorista: " + motoristaEscolhido.getNome());
            System.out.println("Veículo: " + motoristaEscolhido.getVeiculo().getModelo() + " - " + motoristaEscolhido.getVeiculo().getPlaca());


            double valorCorrida = calcularPreco(motoristaEscolhido);
            System.out.printf("Valor: %.2f%n", valorCorrida);

            GerenciarViagem viagem = new GerenciarViagem();
            viagem.cadastrarViagem(motoristaEscolhido, cliente, origem, destino, pacote);

            int opcao;
            boolean pagamentoConcluido = false;
            do{
                System.out.println("Deseja pagar e finalizar a corrida?");
                System.out.println("1. Sim");
                System.out.println("2. Ainda não");
                System.out.println("Sua respota : ");
                opcao = input.nextInt();
                input.nextLine();

                if(opcao == 1) {
                    boolean apareceu = false;
                    System.out.println("Qual forma de pagamento? ");
                    System.out.println("Pix");
                    System.out.println("Cartão de crédito");
                    System.out.println("Dinheiro");
                    System.out.println("Sua resposta: ");
                    String pagamento = input.nextLine().replace("ã", "a").replace("é", "e");
                    if (pagamento.equalsIgnoreCase("pix") || pagamento.equalsIgnoreCase("cartao de credito") || pagamento.equalsIgnoreCase("dinheiro")) {
                        if(pagamento.equalsIgnoreCase("cartao de credito")){
                            pagamento = "Cartão de crédito";
                        }
                        List<FormaDePagamento> pagamentos = fachada.listarPagamentos(cliente.getCpf());
                        if(pagamentos != null){
                            for (FormaDePagamento p : pagamentos) {
                                if (pagamento.equalsIgnoreCase(p.getTipo())) {
                                    apareceu = true;
                                    if (pagamento.equalsIgnoreCase("cartão de crédito")) {
                                        if ((((CartaoCredito) p)).getLimite() < valorCorrida) {
                                            System.out.println("Você está sem limite em seu cartão, tente com outro pagamento!");
                                            boolean pagou = false;
                                            while(!pagou){
                                                if(cadastrarNovoPagamento(cliente.getCpf())){
                                                    pagou = true;
                                                }
                                            }
                                        }
                                    }
                                    break;
                                }
                            }
                        }

                        if (!apareceu) {
                            System.out.println("Forma de pagamento não cadastrada!");
                            boolean pagou = false;
                            while(!pagou){
                                if(cadastrarNovoPagamento(cliente.getCpf())){
                                    pagou = true;
                                }
                            }

                        }

                        //toda vez que for finalizar uma corrida incrementa os votos;
                        if(apareceu) {
                            boolean avaliouMotorista = false;
                            boolean avaliouCliente = false;
                            do {
                                System.out.println("Pagamento concluido!Viagem finalizada!");
                                System.out.println("Motorista avalie seu cliente de 0  a 5 estrelas : ");
                                double estrelaCliente = input.nextDouble();
                                input.nextLine();
                                if (estrelaCliente < 0 || estrelaCliente > 5) {
                                    System.out.println("Número digitado inválido!");
                                } else {
                                    cliente.setVotos(cliente.getVotos() + 1);
                                    cliente.setEstrelas(estrelaCliente);
                                    avaliouCliente = true;
                                }
                            } while (!avaliouCliente);

                            do {
                                System.out.println("Cliente avalie seu motorista de 0 a 5 estrelas : ");
                                double estrelasMotorista = input.nextDouble();
                                input.nextLine();
                                if (estrelasMotorista < 0 || estrelasMotorista > 5) {
                                    System.out.println("Número digitado inválido");
                                } else {
                                    motoristaEscolhido.setVotos(motoristaEscolhido.getVotos() + 1);
                                    motoristaEscolhido.setEstrelas(estrelasMotorista);

                                    avaliouMotorista = true;
                                }
                            } while (!avaliouMotorista);
                            //depois de finalizar, a gente atualiza cliente e motorista com as avaliacoes
                            motoristaEscolhido.setEmViagem(false);
                            fachada.atualizarMotorista(motoristaEscolhido);
                            fachada.atualizarCliente(cliente);
                            pagamentoConcluido = true;
                        }
                    }else{
                        System.out.println("Forma de pagamento digitada inválida!");
                    }
                }
            }while (!pagamentoConcluido);
        } else {
            System.out.println("Nenhum motorista disponível para essa categoria.");
        }
    }

    private void listarVeiculosDisponiveisPacote() {
        boolean economico = false, suv = false, luxo = false;
        List<Motorista> motoristas = fachada.listarMotoristas();
        if (motoristas == null){
            System.out.println("Nenhum motorista disponivel");
            return;
        }
        for (Motorista m : motoristas) {
            if (!m.getEmViagem()) {
                if (m.getVeiculo() instanceof Economico && !economico) {
                    System.out.println("Econômico");
                    economico = true;
                } else if (m.getVeiculo() instanceof SUV && !suv) {
                    System.out.println("SUV");
                    suv = true;
                } else if (m.getVeiculo() instanceof Luxo && !luxo) {
                    System.out.println("Luxo");
                    luxo = true;
                }
            }
        }
    }

    public void Viajar() throws Exception {
        System.out.println("Digite seu CPF: ");
        String cpf = input.nextLine();
        Cliente cliente = fachada.buscarCliente(cpf);
        if (cliente == null) {
            System.out.println("É necessário o cadastro! Cliente não cadastrado!");
        } else {
            viajar(cliente);
        }
    }

    public void viajar(Cliente cliente) throws Exception {
        String origem = null;
        String destino = null;
        boolean cidadesValidas = false;

        do {
            System.out.println("Nome da cidade de origem: ");
            origem = input.nextLine();
            System.out.println("Nome da cidade de destino: ");
            destino = input.nextLine();

            boolean origemValida = fachada.verificarCidade(origem);
            boolean destinoValida = fachada.verificarCidade(destino);

            if (!origemValida || !destinoValida) {
                System.out.println("Uma ou ambas as cidades não estão cadastradas!");

                if (!origemValida) {
                    System.out.println("Cidade de origem inválida: " + origem);
                    cadastrarNovaCidade();
                }

                if (!destinoValida) {
                    System.out.println("Cidade de destino inválida: " + destino);
                    cadastrarNovaCidade();
                }
            } else {
                cidadesValidas = true;
            }

        } while (!cidadesValidas);

        System.out.println("Veículos Disponíveis:");
        listarVeiculosDisponiveis();

        System.out.println("Qual tipo de veículo você escolhe: ");
        String veiculo = input.nextLine();

        Motorista motoristaEscolhido = escolherMotorista(veiculo);

        if (motoristaEscolhido != null) {
            fachada.viajarMotorista(motoristaEscolhido);
            System.out.println("Viagem aceita");
            System.out.println("Seu motorista: " + motoristaEscolhido.getNome());
            System.out.println("Veículo: " + motoristaEscolhido.getVeiculo().getModelo() + " - " + motoristaEscolhido.getVeiculo().getPlaca());


            double valorCorrida = calcularPreco(motoristaEscolhido);
            System.out.printf("Valor: %.2f%n", valorCorrida);

            GerenciarViagem viagem = new GerenciarViagem();
            viagem.cadastrarViagem(motoristaEscolhido, cliente, origem, destino, null);

            int opcao;
            boolean pagamentoConcluido = false;
            do{
                System.out.println("Deseja pagar e finalizar a corrida?");
                System.out.println("1. Sim");
                System.out.println("2. Ainda não");
                System.out.println("Sua resposta : ");
                opcao = input.nextInt();
                input.nextLine();

                if(opcao == 1) {
                    boolean apareceu = false;
                    System.out.println("Qual forma de pagamento? ");
                    System.out.println("Pix");
                    System.out.println("Cartão de crédito");
                    System.out.println("Dinheiro");
                    System.out.println("Sua resposta: ");
                    String pagamento = input.nextLine().replace("ã", "a").replace("é","e");
                    if (pagamento.equalsIgnoreCase("pix") || pagamento.equalsIgnoreCase("cartao de credito") || pagamento.equalsIgnoreCase("dinheiro")) {
                        if(pagamento.equalsIgnoreCase("cartao de credito")){
                            pagamento = "Cartão de crédito";
                        }
                        List<FormaDePagamento> pagamentos = fachada.listarPagamentos(cliente.getCpf());
                        if(pagamentos != null){
                            for (FormaDePagamento p : pagamentos) {
                                if (pagamento.equalsIgnoreCase(p.getTipo())) {
                                    apareceu = true;
                                    if (pagamento.equalsIgnoreCase("cartão de crédito")) {
                                        if ((((CartaoCredito) p)).getLimite() < valorCorrida) {
                                            System.out.println("Você está sem limite em seu cartão!");
                                            boolean pagou = false;
                                            while(!pagou){
                                                if(cadastrarNovoPagamento(cliente.getCpf())){
                                                    pagou = true;
                                                }
                                            }
                                        }
                                    }
                                    break;
                                }
                            }
                        }

                        if (!apareceu) {
                            System.out.println("Forma de pagamento não cadastrada!");
                            boolean pagou = false;
                            while(!pagou){
                                if(cadastrarNovoPagamento(cliente.getCpf())){
                                    pagou = true;
                                }
                            }

                        }

                                //toda vez que for finalizar uma corrida incrementa os votos;
                        if(apareceu) {
                            boolean avaliouMotorista = false;
                            boolean avaliouCliente = false;
                            do {
                                System.out.println("Pagamento concluido!Viagem finalizada!");
                                System.out.println("Motorista avalie seu cliente de 0  a 5 estrelas : ");
                                double estrelaCliente = input.nextDouble();
                                input.nextLine();
                                if (estrelaCliente < 0 || estrelaCliente > 5) {
                                    System.out.println("Número digitado inválido!");
                                } else {
                                    cliente.setVotos(cliente.getVotos() + 1);
                                    cliente.setEstrelas(estrelaCliente);
                                    avaliouCliente = true;
                                }
                            } while (!avaliouCliente);

                            do {
                                System.out.println("Cliente avalie seu motorista de 0 a 5 estrelas : ");
                                double estrelasMotorista = input.nextDouble();
                                input.nextLine();
                                if (estrelasMotorista < 0 || estrelasMotorista > 5) {
                                    System.out.println("Número digitado inválido");
                                } else {
                                    motoristaEscolhido.setVotos(motoristaEscolhido.getVotos() + 1);
                                    motoristaEscolhido.setEstrelas(estrelasMotorista);

                                    avaliouMotorista = true;
                                }
                            } while (!avaliouMotorista);
                            //depois de finalizar, a gente atualiza cliente e motorista com as avaliacoes
                            motoristaEscolhido.setEmViagem(false);
                            fachada.atualizarMotorista(motoristaEscolhido);
                            fachada.atualizarCliente(cliente);
                            pagamentoConcluido = true;
                        }
                    }else{
                        System.out.println("Forma de pagamento digitada inválida!");
                    }
                }
            }while (!pagamentoConcluido);
        } else {
            System.out.println("Nenhum motorista disponível para essa categoria.");
        }
    }

    private void listarVeiculosDisponiveis() {
        boolean economico = false, suv = false, moto = false, luxo = false;
        List<Motorista> motoristas = fachada.listarMotoristas();
        if (motoristas == null){
            System.out.println("Nenhum motorista disponivel");
            return;
        }
        for (Motorista m : motoristas ) {
            if (!m.getEmViagem()) {
                if (m.getVeiculo() instanceof Economico && !economico) {
                    System.out.println("Econômico");
                    economico = true;
                } else if (m.getVeiculo() instanceof SUV && !suv) {
                    System.out.println("SUV");
                    suv = true;
                } else if (m.getVeiculo() instanceof Motocicleta && !moto) {
                    System.out.println("Motocicleta");
                    moto = true;
                } else if (m.getVeiculo() instanceof Luxo && !luxo) {
                    System.out.println("Luxo");
                    luxo = true;
                }
            }
        }
    }

    private Motorista escolherMotorista(String tipoVeiculo) {
        for (Motorista m : fachada.listarMotoristas()) {
            if (!m.getEmViagem()) {
                if ((tipoVeiculo.equalsIgnoreCase("Economico") && m.getVeiculo() instanceof Economico) ||
                        (tipoVeiculo.equalsIgnoreCase("SUV") && m.getVeiculo() instanceof SUV) ||
                        (tipoVeiculo.equalsIgnoreCase("Motocicleta") && m.getVeiculo() instanceof Motocicleta) ||
                        (tipoVeiculo.equalsIgnoreCase("Luxo") && m.getVeiculo() instanceof Luxo)) {
                    return m;
                }
            }
        }
        return null;
    }

    private double calcularPreco(Motorista motorista) {
        return motorista.getVeiculo().getValorCorrida();
    }

    public boolean cadastrarNovoPagamento(String cpf) throws Exception {
        System.out.println("Deseja cadastrar uma nova forma de pagamento?");
        System.out.println("1. Sim");
        System.out.println("2. Não");
        System.out.println("Sua resposta : ");
        int opcao = input.nextInt();
        input.nextLine();
        switch (opcao) {
            case 1:
                if(!cadastrarNovoPag(cpf)){
                    System.out.println("Erro ao cadastrar nova forma de pagamento!");
                    return false;
                }
                return true;
            case 2:
                System.out.println("Escolha uma forma de pagamento cadastrada!");
                break;
            default:
                System.out.println("Escolha inválida!");
                break;
        }
        return false;

    }

    public boolean cadastrarNovoPag(String cpf) throws Exception {
        FormaDePagamento pagamento;
        System.out.println("Escolha uma forma de pagamento : ");
        System.out.println("1. Dinheiro");
        System.out.println("2. Pix");
        System.out.println("3. Cartão de crédito");
        System.out.println("Sua resposta : ");
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
                pagamento= new Pix(chave);
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
                System.out.println("Escolha inválida!");
                break;
        }
        return true;
    }

    public void cadastrarNovaCidade() throws EntidadeJaExisteException {
        System.out.println("Deseja cadastrar uma nova cidade?");
        System.out.println("1. Sim");
        System.out.println("2. Não");
        System.out.println("Sua resposta : ");
        int resposta = input.nextInt();
        input.nextLine();
        switch (resposta) {
            case 1:
                System.out.println("Digite o nome da cidade : ");
                String nome = input.nextLine();
                System.out.println("Digite o CEP : ");
                String cep = input.nextLine();
                fachada.cadastrarCidade(nome , cep);
                break;
            case 2:
                System.out.println("Digite uma cidade já cadastrada!");
                fachada.listarCidades();
                break;
            default:
                System.out.println("Digite uma opção válida!");
                break;
        }
    }
}
