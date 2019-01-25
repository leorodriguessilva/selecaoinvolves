package com.involves.selecao.gerador.teste.auxiliar;

import java.util.function.Predicate;

import com.involves.selecao.alerta.Alerta;

public class BuscadorDeAlertaAuxiliar {
	public static Predicate<? super Alerta> buscaPorProdutoEPontoDeVenda(String produto, String pontoDeVenda) {
		return alertaSalvo -> alertaSalvo.getProduto().equals(produto) && alertaSalvo.getPontoDeVenda().equals(pontoDeVenda);
	}
}
