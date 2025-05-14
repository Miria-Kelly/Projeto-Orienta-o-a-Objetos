package Projeto.gerenciadores;

import Projeto.Arquivo.RepositorioViagemArquivo;
import Projeto.Cliente;
import Projeto.Exception.CidadeNaoEncontradaException;
import Projeto.Motorista;
import Projeto.Respositorios.IRepositorioViagem;
import Projeto.Super.Viagem;
import Projeto.ViagemEntrega;
import Projeto.ViagemPassageiro;
import Projeto.gerenciadores.GerenciarLocal;
import java.util.List;

public class GerenciarViagem {
    private IRepositorioViagem Rviagem;
    private GerenciarLocal gerenciarLocal;
    public GerenciarViagem(){
        this.Rviagem = new RepositorioViagemArquivo();
        this.gerenciarLocal = new GerenciarLocal();
    }

    public void cadastrarViagem(Motorista m, Cliente c, String origem, String destino, String pacote) throws Exception {
        if (pacote == null){
            ViagemPassageiro ViagemPassageiro = new ViagemPassageiro(m, c, origem, destino , pacote);
            Rviagem.adicionar(ViagemPassageiro);
        } else {
            ViagemEntrega viagemEntrega = new ViagemEntrega(m, c, origem, destino, pacote);
            Rviagem.adicionar(viagemEntrega);
        }
        System.out.println("Viagem cadastrada com sucesso");
    }


    public List<Viagem> listarViagemMotorista(String cpf) throws Exception {
        return Rviagem.listarViagemM(cpf);
    }

    public List<Viagem> listarViagemCliente(String cpf) throws Exception {
        return Rviagem.listarViagemC(cpf);
    }
    public void listarViagens() {
        List<Viagem> viagens = Rviagem.carregar();

        if (viagens.isEmpty()) {
            System.out.println("Nenhuma viagem cadastrada.");
        } else {
            for (Viagem v : viagens) {
                System.out.println(v);
            }
        }
    }

}
