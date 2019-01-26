package com.involves.selecao.geradordealerta.impl;

import java.util.Date;

public class Produto {

	private String resposta;
	
	private String pontoDeVenda;
	
	private String nomeDoProduto;
	
	private String nomeDaCategoria;
	
	private String precoEstipulado;
	
	private String participacaoEstipulada;
	
	private Date respondidaEm;

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public String getPontoDeVenda() {
		return pontoDeVenda;
	}

	public void setPontoDeVenda(String pontoDeVenda) {
		this.pontoDeVenda = pontoDeVenda;
	}

	public String getNomeDoProduto() {
		return nomeDoProduto;
	}

	public void setNomeDoProduto(String nomeDoProduto) {
		this.nomeDoProduto = nomeDoProduto;
	}

	public String getNomeDaCategoria() {
		return nomeDaCategoria;
	}

	public void setNomeDaCategoria(String nomeDaCategoria) {
		this.nomeDaCategoria = nomeDaCategoria;
	}

	public String getPrecoEstipulado() {
		return precoEstipulado;
	}

	public void setPrecoEstipulado(String precoEstipulado) {
		this.precoEstipulado = precoEstipulado;
	}

	public String getParticipacaoEstipulada() {
		return participacaoEstipulada;
	}

	public void setParticipacaoEstipulada(String participacaoEstipulada) {
		this.participacaoEstipulada = participacaoEstipulada;
	}

	public Date getRespondidaEm() {
		return respondidaEm;
	}

	public void setRespondidaEm(Date respondidaEm) {
		this.respondidaEm = respondidaEm;
	}
	
}
