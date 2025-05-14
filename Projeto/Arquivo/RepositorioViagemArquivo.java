package Projeto.Arquivo;

import Projeto.Cliente;
import Projeto.Motorista;
import Projeto.Respositorios.IRepositorioViagem;
import Projeto.Super.Veiculo;
import Projeto.Super.Viagem;
import Projeto.gerenciadores.GerenciarCliente;
import Projeto.gerenciadores.GerenciarMotorista;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioViagemArquivo implements IRepositorioViagem {
    String arquivo = "Viagem.ser";
    List<Viagem> viagens;

    public RepositorioViagemArquivo(){
        viagens = carregar();
    }
    @Override
    public void adicionar(Viagem v) {
        viagens.add(v);
        salvarViagem();
    }

    @Override
    public List<Viagem> listarViagemM(String cpf) throws Exception {
        List<Viagem> todas = carregar();
        List<Viagem> VMotorista = new ArrayList<>();

        GerenciarMotorista gerenciarMotorista = new GerenciarMotorista();
        Motorista motorista = gerenciarMotorista.buscarMotorista(cpf);

        for (Viagem v : todas) {
            if (v.getMotorista().getCpf().equals(motorista.getCpf())) {
                VMotorista.add(v);
            }
        }

        return VMotorista;
    }


    @Override
    public List<Viagem> listarViagemC(String cpf) throws Exception {
        List<Viagem> todas = carregar();
        List<Viagem> VCliente = new ArrayList<>();
        GerenciarCliente gerenciarCliente = new GerenciarCliente();
        Cliente cliente = gerenciarCliente.buscarCliente(cpf);

        for (Viagem v : todas) {
            if (v.getCliente().getCpf().equals(cliente.getCpf())) {
                VCliente.add(v);
            }
        }
        return VCliente;
    }

    @Override
    public List<Viagem> carregar(){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Viagem>) in.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void salvarViagem() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) { //salva a lista atualizada no arquivo
            oos.writeObject(viagens);
            oos.flush();
            System.out.println("Viagem salva com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
