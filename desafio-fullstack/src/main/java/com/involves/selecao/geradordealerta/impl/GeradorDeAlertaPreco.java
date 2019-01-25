package com.involves.selecao.geradordealerta.impl;

import org.springframework.stereotype.Component;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.gateway.AlertaGateway;
import com.involves.selecao.geradordealerta.util.CalculadoraDeMargem;

@Component(value="preco")
public class GeradorDeAlertaPreco extends GeradorDeAlertaAbstrato {
	
	public GeradorDeAlertaPreco() { super(); }
	
	public GeradorDeAlertaPreco(AlertaGateway alertaGateway) 
	{	
		super(alertaGateway);
	}

	@Override
	public void gerar() {
		int precoColetado = 0;
		int precoEstipulado = 0;
		
		try
		{	
			precoColetado = Integer.parseInt(getProduto().getResposta());
		}
		catch(Exception ex) {
			System.out.println(
					String.format("Não foi possivel converter o preco do produto recebido como resposta para um numero, produto=%s resposta=%s", 
					getProduto().getNomeDoProduto(), 
					getProduto().getResposta()));
			return;
		}
		
		try
		{	
			precoEstipulado = Integer.parseInt(getProduto().getPrecoEstipulado());
		}
		catch(Exception ex) {

			System.out.println(
					String.format("Não foi possivel converter o preco estipulado do produto para um numero, produto=%s preco_estipulado=%s", 
					getProduto().getNomeDoProduto(), 
					getProduto().getPrecoEstipulado()));
			return;
		}
		
		if(precoColetado == precoEstipulado) {
			return;
		}
		
		Alerta alerta = criarAlerta(precoColetado, precoEstipulado);
		
	    salvarAlerta(alerta);
	}

	private Alerta criarAlerta(int precoColetado, int precoEstipulado) {
		Alerta alerta = new Alerta();
	    alerta.setProduto(getProduto().getNomeDoProduto());
	    alerta.setPontoDeVenda(getProduto().getPontoDeVenda());
	    
		int margem = 0;
		if(precoColetado > precoEstipulado){
			margem = CalculadoraDeMargem.Calcula(precoColetado, precoEstipulado);
		    alerta.setDescricao("Preço acima do estipulado!");
		    alerta.setFlTipo(2);
		} else if(precoColetado < precoEstipulado){
			margem = CalculadoraDeMargem.Calcula(precoEstipulado, precoColetado);
		    alerta.setDescricao("Preço abaixo do estipulado!");
		    alerta.setFlTipo(3);
		}
		
		alerta.setMargem(margem);
		return alerta;
	}

	@Override
	protected String getDescricaoCompletaDoAlerta(Alerta alerta) {
		return String.format("%s causa %s", alerta.getProduto(), alerta.getDescricao());
	}

}
