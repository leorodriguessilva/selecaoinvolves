package com.involves.selecao.geradordealerta.util;

public final class Conversao {
	private int valorEmInteiro;
	private boolean valorConvertidoComErro;
	
	Conversao() {
		super();
	}
	
	public int getValorEmInteiro() {
		return valorEmInteiro;
	}
	public void setValorEmInteiro(int valorEmInteiro) {
		this.valorEmInteiro = valorEmInteiro;
	}
	public boolean isValorConvertidoComErro() {
		return valorConvertidoComErro;
	}
	public void valorConvertidoComSucesso() {
		this.valorConvertidoComErro = false;
	}
	public void valorConvertidoComErro() {
		this.valorConvertidoComErro = true;
	}
	
}
