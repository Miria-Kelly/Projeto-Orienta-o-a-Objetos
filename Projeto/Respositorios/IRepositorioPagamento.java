package Projeto.Respositorios;

import Projeto.Super.FormaDePagamento;

import java.util.List;

public interface IRepositorioPagamento {
    public void adicionarPagamento(FormaDePagamento pagamento);
    public List<FormaDePagamento> carregarPagamento();
}
