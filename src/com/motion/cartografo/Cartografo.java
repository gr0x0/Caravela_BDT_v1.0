////////////////////////////////////////////////////////////////////////////
//
//	INFORMA��ES GERAIS
//
//	M�dulo: com.motion.cartografo
//
//	Nome do arquivo em reposit�rio: Z:\...\...\...\Cartografo.java
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
//		M�dulo que controla a identifica��o da mar atual no mapa de mars, al�m de ter o registro 
//		de todas as mars por onde a embarca��o passa no seu trajeto. Em suma, � o cara que tem os
//		mapas e fica constantemente lendo-o, para saber em que mar est�o.
// 
//	Propriedades a serem observadas por m�dulos clientes:
//		O acesso ao m�dulo Cart�grafo se d� pelo m�todo est�tico getNavegador(), seguindo as 
//		diretrizes do design pattern Singleton adotado.
//		???
// 
//	Estrutura dos dados:
//		Este m�dulo se apresenta como um Singleton.
//		???
//    
////////////////////////////////////////////////////////////////////////////
// 
// -------------------------------------------------------------------------
//
//	M�TODOS
// 
//		Cartografo
//			- Visibilidade: privada.
//			- Tipo de retorno: nenhum (construtor).
//			- Par�metros: nenhum.
//			- Descri��o: ???
//
//		getCartografo
//			- Visibilidade: p�blica.
//			- Tipo de retorno: Cartografo.
//			- Par�metros: nenhum.
//			- Descri��o: ???
//
//		notifyObservador
//			- Visibilidade: p�blica.
//			- Tipo de retorno: void.
//			- Par�metros: ObservadorIF.
//			- Descri��o: ???
//
////////////////////////////////////////////////////////////////////////////

package com.motion.cartografo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
///*INTERFACE CODE*/import java.util.Iterator;
import java.util.ListIterator;

import com.motion.navegador.Navegador;

import org.jdom2.Element;
import org.jdom2.JDOMException;

import com.motion.supportClasses.XMLParser;
import com.motion.supportClasses.ObservadorIF;
import com.motion.supportClasses.ObservadoIF;

public class Cartografo implements ObservadorIF, ObservadoIF {

	//----------VARI�VEIS----------//
	private 	static Cartografo pCartografo = null;
	protected 	static MarList listaMares = new MarList();
	private 	static Mar marAtual = null;	
	private 	ArrayList<ObservadorIF> listaObservadores = new ArrayList<ObservadorIF>();

	final String xmlZonesPath 		= "resources\\CarroInfoTeste.xml";
	final String schemaZonesPath	= "resources\\CarroSchema.xsd";

	private double 	latMinima = 1000, lonMinima = 1000, latMaxima = -1000, lonMaxima = -1000;

	//----------CONSTRUTOR----------//
	private Cartografo(){

		//Abrindo arquivo texto de Mares
		XMLParser parser;
		try {
			parser = new XMLParser(xmlZonesPath, schemaZonesPath);
			List<org.jdom2.Element> elementsList = parser.openArchive();

			/*TEST PRINT*/System.out.print("------In�cio da leitura de zonas e aloca��o de Mares------\n");

			//Instanciando todos os Mares contidos no arquivo xml
			for (org.jdom2.Element zoneElement : elementsList) {
				String id = zoneElement.getAttributeValue("id");
				double lat0 = Double.parseDouble(zoneElement.getChildText("lat0"));
				double latF = Double.parseDouble(zoneElement.getChildText("latF"));
				double lon0 = Double.parseDouble(zoneElement.getChildText("lon0"));
				double lonF = Double.parseDouble(zoneElement.getChildText("lonF"));

				List<Element> impressoesElements = zoneElement.getChild("impressoes").getChildren();
				ArrayList<String> listaImpressoes = new ArrayList<String>();
				for(int i = 0; impressoesElements.get(i)!=null; i++)
				{
					listaImpressoes.add(impressoesElements.get(i).getText());
					/*TEST PRINT*/System.out.print("Variaveis "+i+" = "+"id "+id+"| lon0 "+lon0+"| lonF "+lonF+"| lat0 "+lat0+"| latF "+latF+"\n");
				}

				Mar novoMar = new Mar(id, lat0, latF, lon0, lonF, listaImpressoes);
				Cartografo.listaMares.add(novoMar);

				//Guardando as lat e lon m�nimas e m�ximas
				if(this.latMinima>lat0)
					latMinima = lat0;
				if(this.lonMinima>lon0)
					lonMinima = lon0;
				if(this.latMaxima<latF)
					latMaxima = latF;
				if(this.lonMaxima<lonF)
					lonMaxima = lonF;
			}

		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Inscrevendo-se como observador junto ao m�dulo Navegador
		Navegador.getNavegador().add(this);
	}

	//----------M�TODOS P�BLICOS----------//
	public static Cartografo getCartografo() {
		if (pCartografo == null){
			pCartografo = new Cartografo();
			return pCartografo;
		}

		else {
			return pCartografo;
		}		
	}

	/*INTERFACE CODE BEGIN*/
	//	public int[][] getMaresCoordinates() 
	//	{	
	//		int[][] mares = new int[Cartografo.listaMares.size()][4];
	//		Mar marAux;
	//		Iterator<Mar> iterator = Cartografo.listaMares.iterator();
	//		for(int i = 0; iterator.hasNext(); i++)
	//		{
	//			marAux = iterator.next();
	//			mares[i][0] = (int)marAux.getLonFim();
	//			mares[i][1] = (int)marAux.getLatFim();
	//			mares[i][2] = (int)marAux.getLonIni();
	//			mares[i][3] = (int)marAux.getLatIni();
	//		}
	//		return mares;
	//	}
	/*INTERFACE CODE END*/

	public int[] getMaresMinimumLimits()
	{
		int[] MinimumLimits = {(int)(this.latMinima*100), (int)(this.lonMinima*100)};
		return MinimumLimits;
	}

	//----------PADR�O OBSERVADOR----------//
	public void notifyObservador(ObservadoIF o)
	{
		double[] getList = (double[])o.get();
		double latitude = getList[0];
		double longitude = getList[1];

		marAtual = Cartografo.listaMares.findMar(latitude, longitude);
		//		/*TEST CODE*/if(marAtual!=null)
		//			/*TEST PRINT*/System.out.print("marAtual = "+marAtual.getId()+"\n");
		//		/*TEST CODE*/else System.out.print("marAtual = "+marAtual+"\n");
	}

	@Override
	public void add(ObservadorIF o) 
	{
		listaObservadores.add(o);		
	}

	@Override
	public void remove(ObservadorIF o) 
	{
		listaObservadores.remove(o);	
	}

	@Override
	public void atualizaObservadores(ObservadoIF o) 
	{
		ListIterator<ObservadorIF> helpList = listaObservadores.listIterator();
		while(helpList.hasNext())
		{
			helpList.next().notifyObservador(this);
		}
	}

	@Override
	public Object get() 
	{
		return Cartografo.marAtual.getId();
	}	
}






