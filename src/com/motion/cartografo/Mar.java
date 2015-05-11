////////////////////////////////////////////////////////////////////////////
//
//	INFORMAÇÕES GERAIS
//
//	Módulo: com.motion.cartografo
//
//	Nome do arquivo em repositório: Z:\...\...\...\Mar.java
//
//	Dono: R0B07F4C70RY
//	Projeto: Motion
//	Sistema: Caravela BDT
//	Lista de Autores:
//		Daniel Groxo - gro
//		Márcio Amadeus - ama
//
//	Log de trabalho
//       Versão  Data         Autores      Descrição
//       1.0     01/jul/2014  gro & ama    Em desenvolvimento
// -------------------------------------------------------------------------
// 
//	ESPECIFICAÇÕES
// 
//	Descrição do módulo:
//		(Ver Cartografo) Mar é uma classe componente do módulo Cartógrafo, que representa apenas 
//		um mar específico.
// 
//	Propriedades a serem observadas por módulos clientes:
//		A criação de um Mar é feita através de seu construtor padrão, que deve ser seguida pela
//		chamada do método setProx(Mar prox), de forma que a instância seja amarrada à estrutura
//		de dados.
//		Uma classe com acesso ao Mar que deseje encontrar um Mar na estrutura de dados deve usar
//		o método findMarAtual(float lat, float lon, String firstId), enviando como parâmetros a
//		latitude e longitude do ponto geográfico cujo Mar é procurado e uma string que indica o id
//		do Mar através da qual o método é chamado.
// 
//	Estrutura dos dados:
//		Mar monta uma lista encadeada sem cabeça.
//    
////////////////////////////////////////////////////////////////////////////
// 
// -------------------------------------------------------------------------
//
//	MÉTODOS
// 
//		Mar
//			- Visibilidade: protegida.
//			- Tipo de retorno: construtor.
//			- Parâmetros: String, float, float, float, float.
//			- Descrição: ???
//
//		setProx
//			- Visibilidade: protegida.
//			- Tipo de retorno: void.
//			- Parâmetros: Mar.
//			- Descrição: ???
//
//		findMarAtual
//			- Visibilidade: protegida.
//			- Tipo de retorno: Mar.
//			- Parâmetros: float, float, String.
//			- Descrição: ???
//
////////////////////////////////////////////////////////////////////////////

package com.motion.cartografo;

import java.util.ArrayList;

public class Mar 
{
	
//----------VARIÁVEIS----------//
	private String id;
	private double lat0;
	private double lon0;
	private double latF;
	private double lonF;
	private ArrayList<String> listaImpressoes = new ArrayList<String>();
	
//----------CONSTRUTOR----------//
	protected Mar(String id, double lat0, double latF, double lon0, double lonF, 
			ArrayList<String> listaImpressoes)
	{
		this.id = id;
		this.lat0 = lat0;
		this.latF = latF;
		this.lon0 = lon0;
		this.lonF = lonF;
		this.listaImpressoes = listaImpressoes;
	}

//----------GETs----------//	
	
	protected String getId() 
	{
		return id;
	}
	
	protected double getLatIni() 
	{
		return lat0;
	}
	
	protected double getLatFim() 
	{
		return latF;
	}
	
	protected double getLonIni() 
	{
		return lon0;
	}
	
	protected double getLonFim() 
	{
		return lonF;
	}
	
	protected ArrayList<String> getImpressoes() 
	{
		return listaImpressoes;
	}
	
}
