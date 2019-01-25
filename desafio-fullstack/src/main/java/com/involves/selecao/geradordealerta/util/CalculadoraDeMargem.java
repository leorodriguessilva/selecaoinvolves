package com.involves.selecao.geradordealerta.util;

public class CalculadoraDeMargem {
	public static int Calcula(int maiorPreco, int menorPreco) {
		int margem = maiorPreco - menorPreco;
		return margem;
	}
}
