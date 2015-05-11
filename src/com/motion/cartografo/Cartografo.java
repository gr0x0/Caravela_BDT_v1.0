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
import java.util.Arrays;
import java.util.ListIterator;
import com.motion.navegador.Navegador;
import com.motion.supportClasses.ArchiveManager;
import com.motion.supportClasses.Matrix;
import com.motion.supportClasses.ObservadorIF;
import com.motion.supportClasses.ObservadoIF;

public class Cartografo implements ObservadorIF, ObservadoIF {

	//----------VARI�VEIS----------//
	static private Cartografo pCartografo = null;
	static protected MarList listaMares = new MarList();
	static private Mar marAtual = null;	
	private ArrayList<ObservadorIF> listaObservadores = new ArrayList<ObservadorIF>();

	private double 	latMinima = 1000, lonMinima = 1000, latMaxima = -1000, lonMaxima = -1000;
	private int 	matrixRows = 10, matrixColumns = 15;

	//----------CONSTRUTOR----------//
	private Cartografo(){

		// Aqui entra a rotina de try catch pra pegar os dados das zonas relativas ao �nibus.
		// Atualmente e com inten��o de teste, tais dados est�o sendo pegos de um arquivo texto chamado
		// "Zones_Data_Test.txt", contido no diret�rio 'data'.

		try
		{	
			//Abrindo arquivo texto de Mares
			ArchiveManager archiveManager = new ArchiveManager(
			"C:/Users/Daniel/Google Drive/Arquivos de Programa��o/JAVA no Eclipse/workspace/Caravela_BDT_v1.0/data/Zones_Data_Test.txt");
			ArrayList<String> data = archiveManager.openArchive();

//			/*TEST PRINT*/System.out.print("------In�cio da leitura de zonas e aloca��o de Mares------\n");

			//Instanciando todos os Mares contidos no arquivo texto
			for(int i = 0; i < data.size(); i++)
			{
				String stringData[] = data.get(i).trim().toString().split(",");

//				/*TEST PRINT*/System.out.print("Data      "+i+" = "+data.get(i)+"\n");

				String id = stringData[0];
				double lat0 = Double.parseDouble(stringData[1]);
				double latF = Double.parseDouble(stringData[2]);
				double lon0 = Double.parseDouble(stringData[3]);
				double lonF = Double.parseDouble(stringData[4]);
				String stringImpressoes[] = stringData[5].split(";");
				ArrayList<String> listaImpressoes = new ArrayList<String>(Arrays.asList(stringImpressoes));;

//				/*TEST PRINT*/System.out.print("Variaveis "+i+" = "+"id "+id+"| lon0 "+lon0+"| lonF "+lonF+"| lat0 "+lat0+"| latF "+latF+"\n");

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

//			/*TEST PRINT*/System.out.print("------Fim da leitura de zonas e aloca��o de Mares------\n");
		}

		catch (IOException e){
			System.out.printf(e.getMessage());
		}

		//Inscrevendo-se como observador junto ao m�dulo Navegador
		Navegador.getNavegador().add(this);
	}

	//----------M�TODOS P�BLICOS----------//
	static public Cartografo getCartografo() {
		if (pCartografo == null){
			pCartografo = new Cartografo();
			return pCartografo;
		}

		else {
			return pCartografo;
		}		
	}

	public Matrix getMaresCoordMatrix() 
	{		
		int[][] matrix = new int[matrixColumns][matrixRows];
		ArrayList<String> zonaTypes = new ArrayList<String>();

		double unidadeLat = (this.latMaxima - this.latMinima)/this.matrixColumns;
		double unidadeLon = (this.lonMaxima - this.lonMinima)/this.matrixRows;

		for(int i=0; i<matrixColumns; i++)
		{
			for(int j=0; j<matrixRows; j++)
			{
				try
				{
//					int idx = zonaTypes.indexOf(listaMares.findMar((this.latMinima + i*unidadeLat),
//							(this.lonMinima + j*unidadeLon)).getId());
					int idx = zonaTypes.indexOf(listaMares.findMar(-22.84,-43.37).getId());
					
					if(idx == -1)
						//Se n�o tiver passado por essa zona ainda...
					{
						Mar mar = listaMares.findMar((this.latMinima + i*unidadeLat),
								(this.lonMinima + j*unidadeLon));
						zonaTypes.add(mar.getId());
						matrix[i][j] = Integer.getInteger(zonaTypes.get(zonaTypes.size()-1));
						/*TEST PRINT*/System.out.print("      Valor da zoneId em int = "+matrix[i][j]+"\n");
					}
					else
						//Se j� tiver passado, ou seja, ela est� na lista zonaTypes...
					{
						matrix[i][j] = Integer.getInteger(zonaTypes.get(idx));
						/*TEST PRINT*/System.out.print("      Valor da zoneId em int = "+matrix[i][j]+"\n");
					}						
				}
				catch(NullPointerException e)
				{
					matrix[i][j] = 0;
					/*TEST PRINT*/System.out.print("      Valor da zoneId em int = "+matrix[i][j]+"\n");
				}
			}
		}

		return new Matrix(matrix);
	}

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






