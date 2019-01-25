package com.involves.selecao.gerador.teste;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.alerta.mock.AlertaGatewayMock;
import com.involves.selecao.gateway.AlertaGateway;
import com.involves.selecao.gerador.teste.auxiliar.BuscadorDeAlertaAuxiliar;
import com.involves.selecao.geradordealerta.impl.GeradorDeAlertaParticipacao;
import com.involves.selecao.geradordealerta.impl.Produto;

public class GeradorDeAlertaParticipacaoTeste {

	private static final String PONTO_DE_VENDA_A_ALERTAR = "Loja Somzera";

	private static final String PRODUTO_A_ALERTAR = "CD do Metalica";

	private static final String RESPOSTA_PARTICIPACAO_MAIOR = "51";
	
	private static final String RESPOSTA_PARTICIPACAO_MENOR = "49";
	
	private static final String PARTICIPACAO_ESTIPULADA = "50";

	@Test
	public void geraAlertaParaRespostaDeParticipacaoMaiorQueOEstipulada() {
		AlertaGateway alertaGateway = gerarAlertaParaResposta(RESPOSTA_PARTICIPACAO_MAIOR, PARTICIPACAO_ESTIPULADA);
		
		List<Alerta> alertaSalvos = alertaGateway.buscarTodos();
		Alerta alerta = alertaSalvos.stream()
				.filter(BuscadorDeAlertaAuxiliar.buscaPorProdutoEPontoDeVenda(PRODUTO_A_ALERTAR, PONTO_DE_VENDA_A_ALERTAR))
				.findFirst()
				.get();

		Assert.assertSame(1, alerta.getMargem());
	}
	
	@Test
	public void geraAlertaParaRespostaDeParticiapacaoMenorQueAEstipulada() {
		AlertaGateway alertaGateway = gerarAlertaParaResposta(RESPOSTA_PARTICIPACAO_MENOR, PARTICIPACAO_ESTIPULADA);
		
		List<Alerta> alertaSalvos = alertaGateway.buscarTodos();
		Alerta alerta = alertaSalvos.stream()
				.filter(BuscadorDeAlertaAuxiliar.buscaPorProdutoEPontoDeVenda(PRODUTO_A_ALERTAR, PONTO_DE_VENDA_A_ALERTAR))
				.findFirst()
				.get();

		Assert.assertSame(1, alerta.getMargem());
	}
	
	@Test
	public void naoGeraAlertaParaRespostaDeParticipacaoQueNaoEstejaNoFormatoDeNumero() {
		AlertaGateway alertaGateway = gerarAlertaParaResposta("valor errado", "55");
		
		List<Alerta> alertaSalvos = alertaGateway.buscarTodos();

		Assert.assertEquals(0, alertaSalvos.size());
	}
	
	@Test
	public void naoGeraAlertaParaParticipacaoEstipuladaQueNaoEstejaNoFormatoDeNumero() {
		AlertaGateway alertaGateway = gerarAlertaParaResposta("55", "valor errado");
		
		List<Alerta> alertaSalvos = alertaGateway.buscarTodos();

		Assert.assertEquals(0, alertaSalvos.size());
	}
	
	@Test
	public void naoGeraAlertaParaRespostaDeParticipacaoIgualAEstipulada() {
		AlertaGateway alertaGateway = gerarAlertaParaResposta(PARTICIPACAO_ESTIPULADA, PARTICIPACAO_ESTIPULADA);
		
		List<Alerta> alertaSalvos = alertaGateway.buscarTodos();

		Assert.assertEquals(0, alertaSalvos.size());
	}

	private AlertaGateway gerarAlertaParaResposta(String resposta, String participacaoEstipulada) {
		AlertaGateway alertaGateway = new AlertaGatewayMock();
		
		Produto produto = new Produto();
		produto.setNomeDoProduto(PRODUTO_A_ALERTAR);
		produto.setPontoDeVenda(PONTO_DE_VENDA_A_ALERTAR);
		produto.setResposta(resposta);
		produto.setParticipacaoEstipulada(participacaoEstipulada);
		
		GeradorDeAlertaParticipacao geradorDeAlertaParticipacao = new GeradorDeAlertaParticipacao(alertaGateway);
		geradorDeAlertaParticipacao.setProduto(produto);
		geradorDeAlertaParticipacao.gerar();
		return alertaGateway;
	}
}
