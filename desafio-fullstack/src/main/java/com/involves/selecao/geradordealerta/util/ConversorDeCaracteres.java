package com.involves.selecao.geradordealerta.util;

public class ConversorDeCaracteres {
	public static Conversao converterParaInteiro(String caracteres) {
		Conversao conversao = new Conversao();
		try
		{	
			conversao.setValorEmInteiro(Integer.parseInt(caracteres));
			conversao.valorConvertidoComSucesso();
		}
		catch(Exception ex) {
			conversao.valorConvertidoComErro();
		}
		return conversao;
	}
}
