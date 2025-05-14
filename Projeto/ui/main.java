package Projeto.ui;
import java.util.Scanner;

public class main {

    public static void main(String[] args) throws Exception {

        Scanner input = new Scanner(System.in);
        int resposta1;
        do {
            System.out.println("------- SISTEM DE TRANSPORTE -------");
            System.out.println("1. Motorista");
            System.out.println("2. Cliente");
            System.out.println("0. Encerrar");
            System.out.println("--------------------------------------");
            System.out.println("Resposta: ");
            resposta1 = input.nextInt();
            input.nextLine();
            switch (resposta1) {
                case 1:
                    InterfaceMotorista interfaceMotorista = new InterfaceMotorista();
                    interfaceMotorista.inicio();
                    break;
                case 2:
                    InterfaceCliente interfaceCliente = new InterfaceCliente();
                    interfaceCliente.inicio();
                    break;
                case 0:
                    System.out.println("Saindo do Sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (resposta1 != 0);
    }
}
