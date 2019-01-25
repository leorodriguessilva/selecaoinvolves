package com.involves.selecao.geradordealerta.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.gateway.AlertaGateway;
import com.involves.selecao.geradordealerta.api.GeradorDeAlerta;

public abstract class GeradorDeAlertaAbstrato implements GeradorDeAlerta {

	private Produto produto;

	@Autowired
	@Qualifier("mongoGateway")
	private AlertaGateway alertaGateway;

	public GeradorDeAlertaAbstrato() { }

	public GeradorDeAlertaAbstrato(AlertaGateway alertaGateway) {
		this.alertaGateway = alertaGateway;
	}
	
	@Override
	public abstract void gerar();

	@Override
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	protected Produto getProduto() {
		return produto;
	}
	
	protected void salvarAlerta(Alerta alerta) {
		alertaGateway.salvar(alerta);
		informeCadastroDoAlerta(alerta);
	}
	
	protected void informeCadastroDoAlerta(Alerta alerta) {
		System.out.println(String.format("[%s] - Registrando alerta para %s", getClass().getName(), getDescricaoCompletaDoAlerta(alerta)));
	}
	
	protected abstract String getDescricaoCompletaDoAlerta(Alerta alerta);
}
