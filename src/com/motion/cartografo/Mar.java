////////////////////////////////////////////////////////////////////////////
//
//	INFORMA��ES GERAIS
//
//	M�dulo: com.motion.cartografo
//
//	Nome do arquivo em reposit�rio: Z:\...\...\...\Mar.java
//
//	Dono: R0B07F4C70RY
//	Projeto: Motion
//	Sistema: Caravela BDT
//	Lista de Autores:
//		Daniel Groxo - gro
//		M�rcio Amadeus - ama
//
//	Log de trabalho
//       Vers�o  Data         Autores      Descri��o
//       1.0     01/jul/2014  gro & ama    Em desenvolvimento
// -------------------------------------------------------------------------
// 
//	ESPECIFICA��ES
// 
//	Descri��o do m�dulo:
//		(Ver Cartografo) Mar � uma classe componente do m�dulo Cart�grafo, que representa apenas 
//		um mar espec�fico.
// 
//	Propriedades a serem observadas por m�dulos clientes:
//		A cria��o de um Mar � feita atrav�s de seu construtor padr�o, que deve ser seguida pela
//		chamada do m�todo setProx(Mar prox), de forma que a inst�ncia seja amarrada � estrutura
//		de dados.
//		Uma classe com acesso ao Mar que deseje encontrar um Mar na estrutura de dados deve usar
//		o m�todo findMarAtual(float lat, float lon, String firstId), enviando como par�metros a
//		latitude e longitude do ponto geogr�fico cujo Mar � procurado e uma string que indica o id
//		do Mar atrav�s da qual o m�todo � chamado.
// 
//	Estrutura dos dados:
//		Mar monta uma lista encadeada sem cabe�a.
//    
////////////////////////////////////////////////////////////////////////////
// 
// -------------------------------------------------------------------------
//
//	M�TODOS
// 
//		Mar
//			- Visibilidade: protegida.
//			- Tipo de retorno: construtor.
//			- Par�metros: String, float, float, float, float.
//			- Descri��o: ???
//
//		setProx
//			- Visibilidade: protegida.
//			- Tipo de retorno: void.
//			- Par�metros: Mar.
//			- Descri��o: ???
//
//		findMarAtual
//			- Visibilidade: protegida.
//			- Tipo de retorno: Mar.
//			- Par�metros: float, float, String.
//			- Descri��o: ???
//
////////////////////////////////////////////////////////////////////////////

package com.motion.cartografo;

import java.util.ArrayList;

public class Mar 
{
	
//----------VARI�VEIS----------//
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
