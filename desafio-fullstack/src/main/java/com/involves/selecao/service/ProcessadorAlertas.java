package com.involves.selecao.service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.involves.selecao.alerta.Pesquisa;
import com.involves.selecao.processadordepesquisa.api.ProcessadorDePesquisa;

@Service
public class ProcessadorAlertas {
	
	@Autowired
	private ProcessadorDePesquisa processadorDePesquisa;
	
	public void processa() throws IOException {
		URL url = new URL("https://selecao-involves.agilepromoter.com/pesquisas");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		BufferedReader in = new BufferedReader(
		  new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer content = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
		    content.append(inputLine);
		}
		in.close();

		Gson gson = new Gson();
		Pesquisa[] ps =  gson.fromJson(content.toString(), Pesquisa[].class);
		processaPesquisa(ps);
	}

	private void processaPesquisa(Pesquisa[] ps) {
		for (int i = 0; i < ps.length; i++){
			Pesquisa pesquisa = ps[i];
			processadorDePesquisa.processar(pesquisa);
		}
	}
}

