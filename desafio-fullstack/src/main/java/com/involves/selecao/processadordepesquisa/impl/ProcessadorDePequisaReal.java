package com.involves.selecao.processadordepesquisa.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.involves.selecao.alerta.Pesquisa;
import com.involves.selecao.alerta.Resposta;
import com.involves.selecao.geradordealerta.api.GeradorDeAlerta;
import com.involves.selecao.geradordealerta.impl.Produto;
import com.involves.selecao.processadordepesquisa.api.ProcessadorDePesquisa;

@Component
public class ProcessadorDePequisaReal implements ProcessadorDePesquisa {

	private final HashMap<String, GeradorDeAlerta> geradoresDeAlertas;
	
	@Autowired
	@Qualifier("ruptura")
	private GeradorDeAlerta geradorDeAlertaRuptura;
	
	@Autowired
	@Qualifier("preco")
	private GeradorDeAlerta geradorDeAlertaPreco;
	
	@Autowired
	@Qualifier("participacao")
	private GeradorDeAlerta geradorDeAlertaParticipacao;
	
	public ProcessadorDePequisaReal() {
		this.geradoresDeAlertas = new HashMap<String, GeradorDeAlerta>();
	}
	
	public ProcessadorDePequisaReal(HashMap<String, GeradorDeAlerta> geradoresDeAlertas) {
		this.geradoresDeAlertas = geradoresDeAlertas;
	}

	private void RegistreGeradoresDeAlertas() {
		geradoresDeAlertas.put("Qual a situação do produto?", geradorDeAlertaRuptura);
		geradoresDeAlertas.put("Qual o preço do produto?", geradorDeAlertaPreco);
		geradoresDeAlertas.put("%Share", geradorDeAlertaParticipacao);
	}

	public void inicializarGeradoresDeAlertasSeNecessario() {
		if(geradoresDeAlertas.isEmpty()) {
			RegistreGeradoresDeAlertas();
		}
	}

	@Override
	public void processar(Pesquisa pesquisa) {
		inicializarGeradoresDeAlertasSeNecessario();
		
		List<Resposta> respostas = pesquisa.getRespostas();
		for (int i = 0; i < respostas.size(); i++){
			Resposta resposta = respostas.get(i);
			
			if(!geradoresDeAlertas.containsKey(resposta.getPergunta())) {
				System.out.println("Alerta ainda não implementado!");
				continue;
			}
			
			GeradorDeAlerta geradorDeAlerta = geradoresDeAlertas.get(resposta.getPergunta());
			
			Produto produto = CriarProdutoBaseadoNaPesquisa(pesquisa, resposta);
			
			geradorDeAlerta.setProduto(produto);
			
			geradorDeAlerta.gerar();
		} 
	}

	private Produto CriarProdutoBaseadoNaPesquisa(Pesquisa pesquisa, Resposta resposta) {
		Produto produto = new Produto();
		produto.setNomeDoProduto(pesquisa.getProduto());
		produto.setPontoDeVenda(pesquisa.getPonto_de_venda());
		produto.setPrecoEstipulado(pesquisa.getPreco_estipulado());
		produto.setParticipacaoEstipulada(pesquisa.getParticipacao_estipulada());
		produto.setResposta(resposta.getResposta());
		produto.setNomeDaCategoria(pesquisa.getCategoria());
		return produto;
	}

}
