package com.involves.selecao.geradordealerta.api;

import com.involves.selecao.geradordealerta.impl.Produto;

public interface GeradorDeAlerta {
	void gerar();
	void setProduto(Produto produto);
}
