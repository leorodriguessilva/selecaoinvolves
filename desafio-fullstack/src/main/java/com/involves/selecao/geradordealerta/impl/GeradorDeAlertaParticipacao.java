package com.involves.selecao.geradordealerta.impl;

import org.springframework.stereotype.Component;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.gateway.AlertaGateway;
import com.involves.selecao.geradordealerta.util.CalculadoraDeMargem;
import com.involves.selecao.geradordealerta.util.Conversao;
import com.involves.selecao.geradordealerta.util.ConversorDeCaracteres;

@Component(value="participacao")
public class GeradorDeAlertaParticipacao extends GeradorDeAlertaAbstrato {
	
	public GeradorDeAlertaParticipacao() { super();	}
	
	public GeradorDeAlertaParticipacao(AlertaGateway alertaGateway) 
	{	
		super(alertaGateway);
	}
	
	@Override
	public void gerar() {
		Conversao conversaoParticipacaoColetada = ConversorDeCaracteres.converterParaInteiro(getProduto().getResposta());
		Conversao conversaoParticipacaoEstipulada = ConversorDeCaracteres.converterParaInteiro(getProduto().getParticipacaoEstipulada());
		
		if(conversaoParticipacaoColetada.isValorConvertidoComErro()) {
			System.out.println(
					String.format("Não foi possivel converter a participacao do produto recebido como resposta para um numero, produto=%s resposta=%s", 
					getProduto().getNomeDoProduto(), 
					getProduto().getResposta()));
			return;
		}
		
		if(conversaoParticipacaoEstipulada.isValorConvertidoComErro()) {

			System.out.println(
					String.format("Não foi possivel converter a participacao estipulada do produto para um numero, produto=%s participacao_estipulada=%s", 
					getProduto().getNomeDoProduto(), 
					getProduto().getParticipacaoEstipulada()));
			return;
		}
		
		if(conversaoParticipacaoColetada.getValorEmInteiro() == conversaoParticipacaoEstipulada.getValorEmInteiro()) {
			return;
		}
		
		Alerta alerta = criarAlerta(conversaoParticipacaoColetada.getValorEmInteiro(), conversaoParticipacaoEstipulada.getValorEmInteiro());
		
	    salvarAlerta(alerta);
	}

	private Alerta criarAlerta(int participacaoColetada, int participacaoEstipulada) {
		Alerta alerta = new Alerta();
	    alerta.setCategoria(getProduto().getNomeDaCategoria());
	    alerta.setPontoDeVenda(getProduto().getPontoDeVenda());
	    
		int margem = 0;
		if(participacaoColetada > participacaoEstipulada){
			margem = CalculadoraDeMargem.Calcula(participacaoColetada, participacaoEstipulada);
		    alerta.setDescricao("Participação superior ao estipulado!");
		    alerta.setFlTipo(4);
		} else if(participacaoColetada < participacaoEstipulada){
			margem = CalculadoraDeMargem.Calcula(participacaoEstipulada, participacaoColetada);
		    alerta.setDescricao("Participação inferior ao estipulado!");
		    alerta.setFlTipo(5);
		}
		
		alerta.setMargem(margem);
		return alerta;
	}

	@Override
	protected String getDescricaoCompletaDoAlerta(Alerta alerta) {
		return String.format("%s causa %s", alerta.getCategoria(), alerta.getDescricao());
	}

}
