package com.involves.selecao.alerta.mock;

import java.util.ArrayList;
import java.util.List;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.gateway.AlertaGateway;

public class AlertaGatewayMock implements AlertaGateway {

	private List<Alerta> alertasSalvos;
	
	public AlertaGatewayMock() {
		this.alertasSalvos = new ArrayList<Alerta>();
	}

	@Override
	public void salvar(Alerta alerta) {
		alertasSalvos.add(alerta);
	}

	@Override
	public List<Alerta> buscarTodos() {
		return alertasSalvos;
	}

}
