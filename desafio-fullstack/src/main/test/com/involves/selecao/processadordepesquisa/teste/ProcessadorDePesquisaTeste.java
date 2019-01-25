package com.involves.selecao.processadordepesquisa.teste;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.alerta.Pesquisa;
import com.involves.selecao.alerta.Resposta;
import com.involves.selecao.alerta.mock.AlertaGatewayMock;
import com.involves.selecao.gateway.AlertaGateway;
import com.involves.selecao.geradordealerta.api.GeradorDeAlerta;
import com.involves.selecao.geradordealerta.impl.GeradorDeAlertaParticipacao;
import com.involves.selecao.geradordealerta.impl.GeradorDeAlertaPreco;
import com.involves.selecao.geradordealerta.impl.GeradorDeAlertaRuptura;
import com.involves.selecao.processadordepesquisa.impl.ProcessadorDePequisaReal;;

public class ProcessadorDePesquisaTeste {
	
	private AlertaGateway alertaGatewayMock = new AlertaGatewayMock();
	
	private GeradorDeAlertaRuptura geradorDeAlertaRuptura = new GeradorDeAlertaRuptura(alertaGatewayMock);
	
	private GeradorDeAlertaPreco geradorDeAlertaPreco = new GeradorDeAlertaPreco(alertaGatewayMock);
	
	private GeradorDeAlertaParticipacao geradorDeAlertaParticipacao = new GeradorDeAlertaParticipacao(alertaGatewayMock);

	private ProcessadorDePequisaReal processadorDePesquisa;
	
	@Before
    public void inicializaMocks() {
		HashMap<String, GeradorDeAlerta> geradoresDeAlerta = new HashMap<String, GeradorDeAlerta>();
		geradoresDeAlerta.put("Ruptura?", geradorDeAlertaRuptura);
		geradoresDeAlerta.put("Preco?", geradorDeAlertaPreco);
		geradoresDeAlerta.put("Share?", geradorDeAlertaParticipacao);
		
		processadorDePesquisa = new ProcessadorDePequisaReal(geradoresDeAlerta);
    }
	
	@Test
	public void pesquisaSobreRupturaDeveRetornarUmAlerta() {
		Pesquisa pesquisa = CriaPesquisa();
		
		List<Resposta> respostas = new ArrayList<Resposta>(2);
		
		Resposta respostaUm = new Resposta();
		respostaUm.setPergunta("Ruptura?");
		respostaUm.setResposta("Produto ausente na gondola");
		
		Resposta respostaDois = new Resposta();
		respostaDois.setPergunta("Ruptura?");
		respostaDois.setResposta("Produto presente na gondola");
		
		respostas.add(respostaUm);
		respostas.add(respostaDois);
		
		pesquisa.setRespostas(respostas);
		
		processadorDePesquisa.processar(pesquisa);
		
		List<Alerta> alertas = alertaGatewayMock.buscarTodos();
		
		Assert.assertEquals(1, alertas.size());
	}
	
	@Test
	public void pesquisaSobreRupturaDeveRetornarDoisAlertas() {
		Pesquisa pesquisa = CriaPesquisa();
		
		List<Resposta> respostas = new ArrayList<Resposta>(2);
		
		Resposta respostaUm = new Resposta();
		respostaUm.setPergunta("Ruptura?");
		respostaUm.setResposta("Produto ausente na gondola");
		
		Resposta respostaDois = new Resposta();
		respostaDois.setPergunta("Ruptura?");
		respostaDois.setResposta("Produto ausente na gondola");
		
		respostas.add(respostaUm);
		respostas.add(respostaDois);
		
		pesquisa.setRespostas(respostas);
		
		processadorDePesquisa.processar(pesquisa);
		
		List<Alerta> alertas = alertaGatewayMock.buscarTodos();
		
		Assert.assertEquals(2, alertas.size());
	}
	
	@Test
	public void pesquisaSobrePrecoMaiorDeveRetornarUmAlerta() {
		Pesquisa pesquisa = CriaPesquisa();
		
		pesquisa.setPreco_estipulado("100");
		
		List<Resposta> respostas = new ArrayList<Resposta>(2);
		
		Resposta respostaUm = new Resposta();
		respostaUm.setPergunta("Preco?");
		respostaUm.setResposta("110");
		
		Resposta respostaDois = new Resposta();
		respostaDois.setPergunta("Preco?");
		respostaDois.setResposta("100");
		
		respostas.add(respostaUm);
		respostas.add(respostaDois);
		
		pesquisa.setRespostas(respostas);
		
		processadorDePesquisa.processar(pesquisa);
		
		List<Alerta> alertas = alertaGatewayMock.buscarTodos();
		
		Assert.assertEquals(1, alertas.size());
	}
	
	@Test
	public void pesquisaSobrePrecoMaiorEMenorDeveRetornarDoisAlertas() {
		Pesquisa pesquisa = CriaPesquisa();
		
		pesquisa.setPreco_estipulado("100");
		
		List<Resposta> respostas = new ArrayList<Resposta>(2);
		
		Resposta respostaUm = new Resposta();
		respostaUm.setPergunta("Preco?");
		respostaUm.setResposta("110");
		
		Resposta respostaDois = new Resposta();
		respostaDois.setPergunta("Preco?");
		respostaDois.setResposta("90");
		
		respostas.add(respostaUm);
		respostas.add(respostaDois);
		
		pesquisa.setRespostas(respostas);
		
		processadorDePesquisa.processar(pesquisa);
		
		List<Alerta> alertas = alertaGatewayMock.buscarTodos();
		
		Assert.assertEquals(2, alertas.size());
	}
	
	@Test
	public void pesquisaSobreParticipacaoMaiorDeveRetornarUmAlerta() {
		Pesquisa pesquisa = CriaPesquisa();
		
		pesquisa.setParticipacao_estipulada("50");
		
		List<Resposta> respostas = new ArrayList<Resposta>(2);
		
		Resposta respostaUm = new Resposta();
		respostaUm.setPergunta("Share?");
		respostaUm.setResposta("51");
		
		Resposta respostaDois = new Resposta();
		respostaDois.setPergunta("Share?");
		respostaDois.setResposta("50");
		
		respostas.add(respostaUm);
		respostas.add(respostaDois);
		
		pesquisa.setRespostas(respostas);
		
		processadorDePesquisa.processar(pesquisa);
		
		List<Alerta> alertas = alertaGatewayMock.buscarTodos();
		
		Assert.assertEquals(1, alertas.size());
	}
	
	@Test
	public void pesquisaSobreParticipacaoMaiorEMenorDeveRetornarDoisAlertas() {
		Pesquisa pesquisa = CriaPesquisa();
		
		pesquisa.setParticipacao_estipulada("50");
		
		List<Resposta> respostas = new ArrayList<Resposta>(2);
		
		Resposta respostaUm = new Resposta();
		respostaUm.setPergunta("Share?");
		respostaUm.setResposta("51");
		
		Resposta respostaDois = new Resposta();
		respostaDois.setPergunta("Share?");
		respostaDois.setResposta("49");
		
		respostas.add(respostaUm);
		respostas.add(respostaDois);
		
		pesquisa.setRespostas(respostas);
		
		processadorDePesquisa.processar(pesquisa);
		
		List<Alerta> alertas = alertaGatewayMock.buscarTodos();
		
		Assert.assertEquals(2, alertas.size());
	}
	
	@Test
	public void pesquisaNenhumaRespostaAtendidaNaoDeveRetornarAlerta() {
		Pesquisa pesquisa = CriaPesquisa();
		
		pesquisa.setPreco_estipulado("100");
		
		List<Resposta> respostas = new ArrayList<Resposta>(2);
		
		Resposta respostaUm = new Resposta();
		respostaUm.setPergunta("Ruptura?");
		respostaUm.setResposta("Produto presente na gondola");
		
		Resposta respostaDois = new Resposta();
		respostaDois.setPergunta("Preco?");
		respostaDois.setResposta("100");
		
		respostas.add(respostaUm);
		respostas.add(respostaDois);
		
		pesquisa.setRespostas(respostas);
		
		processadorDePesquisa.processar(pesquisa);
		
		List<Alerta> alertas = alertaGatewayMock.buscarTodos();
		
		Assert.assertEquals(0, alertas.size());
	}
	
	@Test
	public void pesquisaPrecoMaiorParticipacaoMaiorERupturaDeveRetornarTresAlertas() {
		Pesquisa pesquisa = CriaPesquisa();
		
		pesquisa.setPreco_estipulado("100");
		pesquisa.setParticipacao_estipulada("50");
		
		List<Resposta> respostas = new ArrayList<Resposta>(3);
		
		Resposta respostaUm = new Resposta();
		respostaUm.setPergunta("Ruptura?");
		respostaUm.setResposta("Produto ausente na gondola");
		
		Resposta respostaDois = new Resposta();
		respostaDois.setPergunta("Preco?");
		respostaDois.setResposta("120");
		
		Resposta respostaTres = new Resposta();
		respostaTres.setPergunta("Share?");
		respostaTres.setResposta("51");
		
		respostas.add(respostaUm);
		respostas.add(respostaDois);
		respostas.add(respostaTres);
		
		pesquisa.setRespostas(respostas);
		
		processadorDePesquisa.processar(pesquisa);
		
		List<Alerta> alertas = alertaGatewayMock.buscarTodos();
		
		Assert.assertEquals(3, alertas.size());
	}
	
	@Test
	public void pesquisaPrecoMaiorERupturaDeveRetornarDoisAlertas() {
		Pesquisa pesquisa = CriaPesquisa();
		
		pesquisa.setPreco_estipulado("100");
		pesquisa.setParticipacao_estipulada("50");
		
		List<Resposta> respostas = new ArrayList<Resposta>(3);
		
		Resposta respostaUm = new Resposta();
		respostaUm.setPergunta("Ruptura?");
		respostaUm.setResposta("Produto ausente na gondola");
		
		Resposta respostaDois = new Resposta();
		respostaDois.setPergunta("Preco?");
		respostaDois.setResposta("120");
		
		Resposta respostaTres = new Resposta();
		respostaTres.setPergunta("Share?");
		respostaTres.setResposta("50");
		
		respostas.add(respostaUm);
		respostas.add(respostaDois);
		respostas.add(respostaTres);
		
		pesquisa.setRespostas(respostas);
		
		processadorDePesquisa.processar(pesquisa);
		
		List<Alerta> alertas = alertaGatewayMock.buscarTodos();
		
		Assert.assertEquals(2, alertas.size());
	}
	
	@Test
	public void pesquisaPrecoMaiorERupturaDeveRetornarUmAlerta() {
		Pesquisa pesquisa = CriaPesquisa();
		
		pesquisa.setPreco_estipulado("100");
		
		List<Resposta> respostas = new ArrayList<Resposta>(2);
		
		Resposta respostaUm = new Resposta();
		respostaUm.setPergunta("Ruptura?");
		respostaUm.setResposta("Produto presente na gondola");
		
		Resposta respostaDois = new Resposta();
		respostaDois.setPergunta("Preco?");
		respostaDois.setResposta("120");
		
		Resposta respostaTres = new Resposta();
		respostaTres.setPergunta("Share?");
		respostaTres.setResposta("50");
		
		respostas.add(respostaUm);
		respostas.add(respostaDois);
		respostas.add(respostaTres);
		
		pesquisa.setRespostas(respostas);
		
		processadorDePesquisa.processar(pesquisa);
		
		List<Alerta> alertas = alertaGatewayMock.buscarTodos();
		
		Assert.assertEquals(1, alertas.size());
	}

	private Pesquisa CriaPesquisa() {
		Pesquisa pesquisa = new Pesquisa();
		pesquisa.setId(0);
		pesquisa.setNotificante("John Doe");
		pesquisa.setPonto_de_venda("Loja Somzera");
		pesquisa.setProduto("CD do Metalica");
		pesquisa.setRotulo("123");
		return pesquisa;
	}

}
