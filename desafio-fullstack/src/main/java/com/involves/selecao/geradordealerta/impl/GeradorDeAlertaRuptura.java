package com.involves.selecao.geradordealerta.impl;

import org.springframework.stereotype.Component;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.gateway.AlertaGateway;

@Component(value="ruptura")
public class GeradorDeAlertaRuptura extends GeradorDeAlertaAbstrato {
	
	public GeradorDeAlertaRuptura() { super(); }

	public GeradorDeAlertaRuptura(AlertaGateway gateway) {
		super(gateway);
	}

	@Override
	public void gerar() {
		if(NaoDeveGerarAlerta(getProduto().getResposta())){
		    return;
		}
		
		Alerta alerta = new Alerta();
	    alerta.setPontoDeVenda(getProduto().getPontoDeVenda());
	    alerta.setDescricao("Ruptura detectada!");
	    alerta.setProduto(getProduto().getNomeDoProduto());
	    alerta.setFlTipo(1);
	    salvarAlerta(alerta);
	}

	private boolean NaoDeveGerarAlerta(String resposta) {
		return !resposta.equals("Produto ausente na gondola");
	}

	@Override
	protected String getDescricaoCompletaDoAlerta(Alerta alerta) {
		return String.format("%s causa %s", alerta.getProduto(), alerta.getDescricao());
	}

}
