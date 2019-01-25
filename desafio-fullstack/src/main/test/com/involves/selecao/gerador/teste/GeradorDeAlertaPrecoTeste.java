package com.involves.selecao.gerador.teste;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.alerta.mock.AlertaGatewayMock;
import com.involves.selecao.gateway.AlertaGateway;
import com.involves.selecao.gerador.teste.auxiliar.BuscadorDeAlertaAuxiliar;
import com.involves.selecao.geradordealerta.impl.GeradorDeAlertaPreco;
import com.involves.selecao.geradordealerta.impl.Produto;

public class GeradorDeAlertaPrecoTeste {

	private static final String PONTO_DE_VENDA_A_ALERTAR = "Loja Somzera";

	private static final String PRODUTO_A_ALERTAR = "CD do Metalica";

	private static final String RESPOSTA_PRECO_MAIOR = "110";
	
	private static final String RESPOSTA_PRECO_MENOR = "90";
	
	private static final String PRECO_ESTIPULADO = "100";
	
	@Test
	public void geraAlertaParaRespostaDePrecoMaiorQueOEstipulado() {
		AlertaGateway alertaGateway = gerarAlertaParaResposta(RESPOSTA_PRECO_MAIOR, PRECO_ESTIPULADO);
		
		List<Alerta> alertaSalvos = alertaGateway.buscarTodos();
		Alerta alerta = alertaSalvos.stream()
				.filter(BuscadorDeAlertaAuxiliar.buscaPorProdutoEPontoDeVenda(PRODUTO_A_ALERTAR, PONTO_DE_VENDA_A_ALERTAR))
				.findFirst()
				.get();

		Assert.assertSame(10, alerta.getMargem());
	}
	
	@Test
	public void geraAlertaParaRespostaDePrecoMenorQueOEstipulado() {
		AlertaGateway alertaGateway = gerarAlertaParaResposta(RESPOSTA_PRECO_MENOR, PRECO_ESTIPULADO);
		
		List<Alerta> alertaSalvos = alertaGateway.buscarTodos();
		Alerta alerta = alertaSalvos.stream()
				.filter(BuscadorDeAlertaAuxiliar.buscaPorProdutoEPontoDeVenda(PRODUTO_A_ALERTAR, PONTO_DE_VENDA_A_ALERTAR))
				.findFirst()
				.get();

		Assert.assertSame(10, alerta.getMargem());
	}
	
	@Test
	public void naoGeraAlertaParaRespostaDePrecoQueNaoEstejaNoFormatoDeNumero() {
		AlertaGateway alertaGateway = gerarAlertaParaResposta("valor errado", "100");
		
		List<Alerta> alertaSalvos = alertaGateway.buscarTodos();

		Assert.assertEquals(0, alertaSalvos.size());
	}
	
	@Test
	public void naoGeraAlertaParaPrecoEstipuladoQueNaoEstejaNoFormatoDeNumero() {
		AlertaGateway alertaGateway = gerarAlertaParaResposta("100", "valor errado");
		
		List<Alerta> alertaSalvos = alertaGateway.buscarTodos();

		Assert.assertEquals(0, alertaSalvos.size());
	}
	
	@Test
	public void naoGeraAlertaParaRespostaDePrecoIgualAoEstipulado() {
		AlertaGateway alertaGateway = gerarAlertaParaResposta(PRECO_ESTIPULADO, PRECO_ESTIPULADO);
		
		List<Alerta> alertaSalvos = alertaGateway.buscarTodos();

		Assert.assertEquals(0, alertaSalvos.size());
	}

	private AlertaGateway gerarAlertaParaResposta(String resposta, String precoEstipulado) {
		AlertaGateway alertaGateway = new AlertaGatewayMock();
		
		Produto produto = new Produto();
		produto.setNomeDoProduto(PRODUTO_A_ALERTAR);
		produto.setPontoDeVenda(PONTO_DE_VENDA_A_ALERTAR);
		produto.setResposta(resposta);
		produto.setPrecoEstipulado(precoEstipulado);
		
		GeradorDeAlertaPreco geradorDeAlertaPreco = new GeradorDeAlertaPreco(alertaGateway);
		geradorDeAlertaPreco.setProduto(produto);
		geradorDeAlertaPreco.gerar();
		return alertaGateway;
	}
}
