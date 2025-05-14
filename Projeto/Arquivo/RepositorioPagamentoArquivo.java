package Projeto.Arquivo;

import Projeto.CartaoCredito;
import Projeto.Exception.EntidadeJaExisteException;
import Projeto.Motorista;
import Projeto.Pix;
import Projeto.Respositorios.IRepositorioPagamento;
import Projeto.Super.FormaDePagamento;
import Projeto.Super.Veiculo;
import Projeto.veiculos.Economico;
import Projeto.veiculos.Luxo;
import Projeto.veiculos.SUV;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositorioPagamentoArquivo implements IRepositorioPagamento {
    private static final String arquivo = "pagamentos.ser.";
    private static List<FormaDePagamento> pagamentos = new ArrayList<>();
    public RepositorioPagamentoArquivo(){
        carregarPagamento();
    }

    @Override
    public List<FormaDePagamento> carregarPagamento() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<FormaDePagamento>) in.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void adicionarPagamento(FormaDePagamento pagamento) {
        if (pagamento instanceof CartaoCredito){
            if (buscarPorCVV(((CartaoCredito) pagamento).getCVC()) != null) {
                pagamentos.add(pagamento);
                salvar();
            }
        }else if(pagamento instanceof Pix) {
            if (buscarPorpix(((Pix) pagamento).getChavePix()) != null) {
                pagamentos.add(pagamento);
                salvar();
            }
        }else{
            pagamentos.add(pagamento);
            salvar();
        }
    }

    private FormaDePagamento buscarPorCVV(String cvv) {
        for (FormaDePagamento f : pagamentos) {
            if (f instanceof CartaoCredito) {
                CartaoCredito cc = (CartaoCredito) f;
                if(cc.getCVC().equalsIgnoreCase(cvv)){
                    return cc;
                }
                System.out.println("CVV: " + cvv);
            }
        }
        return null;
    }
    private FormaDePagamento buscarPorpix(String pix) {
        for (FormaDePagamento f : pagamentos) {
            if (f instanceof Pix) {
                Pix p = (Pix) f;
                if(p.getChavePix().equalsIgnoreCase(pix)){
                    System.out.println("Forma de pagamento ja cadastrada");
                    return p;
                }
                System.out.println("Pix : " +pix);
            }
        }
        return null;
    }

    private void salvar() {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))){
            out.writeObject(pagamentos);
            out.flush();
            System.out.println("Pagamento cadastrado!");
        }catch(IOException e){
            System.out.println("Erro ao salvar o pagamento " + e.getMessage());
            e.printStackTrace();
        }
    }
}
