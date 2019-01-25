package com.involves.selecao.gerador.teste;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.alerta.mock.AlertaGatewayMock;
import com.involves.selecao.gateway.AlertaGateway;
import com.involves.selecao.gerador.teste.auxiliar.BuscadorDeAlertaAuxiliar;
import com.involves.selecao.geradordealerta.impl.GeradorDeAlertaRuptura;
import com.involves.selecao.geradordealerta.impl.Produto;

public class GeradorDeAlertaRupturaTeste {

	private static final String PONTO_DE_VENDA_A_ALERTAR = "Loja Somzera";

	private static final String PRODUTO_A_ALERTAR = "CD do Metalica";

	private static final String RESPOSTA_POSITIVA = "Produto ausente na gondola";
	
	private static final String RESPOSTA_NEGATIVA = "Produto esta na gondola";
	
	@Test
	public void geraAlertaParaRespostaPositiva() {		
		AlertaGateway alertaGateway = gerarAlertaParaResposta(RESPOSTA_POSITIVA);
		
		List<Alerta> alertaSalvos = alertaGateway.buscarTodos();
		Alerta alerta = alertaSalvos.stream()
				.filter(BuscadorDeAlertaAuxiliar.buscaPorProdutoEPontoDeVenda(PRODUTO_A_ALERTAR, PONTO_DE_VENDA_A_ALERTAR))
				.findFirst()
				.get();
		
		Assert.assertEquals(alerta.getProduto(), PRODUTO_A_ALERTAR);
	}
	
	@Test
	public void naoGeraAlertaParaRespostaNegativa() {
		AlertaGateway alertaGateway = gerarAlertaParaResposta(RESPOSTA_NEGATIVA);
		
		List<Alerta> alertaSalvos = alertaGateway.buscarTodos();

		Assert.assertEquals(0, alertaSalvos.size());
	}

	private AlertaGateway gerarAlertaParaResposta(String resposta) {
		AlertaGateway alertaGateway = new AlertaGatewayMock();
		
		Produto produto = new Produto();
		produto.setNomeDoProduto(PRODUTO_A_ALERTAR);
		produto.setPontoDeVenda(PONTO_DE_VENDA_A_ALERTAR);
		produto.setResposta(resposta);
		
		GeradorDeAlertaRuptura geradorDeAlertaRuptura = new GeradorDeAlertaRuptura(alertaGateway);
		geradorDeAlertaRuptura.setProduto(produto);
		geradorDeAlertaRuptura.gerar();
		return alertaGateway;
	}

}
