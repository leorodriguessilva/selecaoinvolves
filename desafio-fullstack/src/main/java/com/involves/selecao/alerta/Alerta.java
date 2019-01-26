package com.involves.selecao.alerta;

import java.util.Date;

public class Alerta {
	
	private String pontoDeVenda;
	private String descricao;
	private String produto;
	private String categoria;
	private Integer flTipo;
	private Integer margem;
	private Date respondidaEm;
	
	public String getPontoDeVenda() {
		return pontoDeVenda;
	}
	public void setPontoDeVenda(String pontoDeVenda) {
		this.pontoDeVenda = pontoDeVenda;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getProduto() {
		return produto;
	}
	public void setProduto(String produto) {
		this.produto = produto;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public Integer getFlTipo() {
		return flTipo;
	}
	public void setFlTipo(Integer flTipo) {
		this.flTipo = flTipo;
	}
	public Integer getMargem(){
		return margem;
	}
	public void setMargem(Integer margem){
		this.margem = margem;
	}
	public Date getRespondidaEm() {
		return respondidaEm;
	}
	public void setRespondidaEm(Date respondidaEm) {
		this.respondidaEm = respondidaEm;
	}
	
}
