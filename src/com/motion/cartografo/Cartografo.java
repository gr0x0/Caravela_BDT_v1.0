////////////////////////////////////////////////////////////////////////////
//
//	INFORMAÇÕES GERAIS
//
//	Módulo: com.motion.cartografo
//
//	Nome do arquivo em repositório: Z:\...\...\...\Cartografo.java
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
//		Módulo que controla a identificação da mar atual no mapa de mars, além de ter o registro 
//		de todas as mars por onde a embarcação passa no seu trajeto. Em suma, é o cara que tem os
//		mapas e fica constantemente lendo-o, para saber em que mar estão.
// 
//	Propriedades a serem observadas por módulos clientes:
//		O acesso ao módulo Cartógrafo se dá pelo método estático getNavegador(), seguindo as 
//		diretrizes do design pattern Singleton adotado.
//		???
// 
//	Estrutura dos dados:
//		Este módulo se apresenta como um Singleton.
//		???
//    
////////////////////////////////////////////////////////////////////////////
// 
// -------------------------------------------------------------------------
//
//	MÉTODOS
// 
//		Cartografo
//			- Visibilidade: privada.
//			- Tipo de retorno: nenhum (construtor).
//			- Parâmetros: nenhum.
//			- Descrição: ???
//
//		getCartografo
//			- Visibilidade: pública.
//			- Tipo de retorno: Cartografo.
//			- Parâmetros: nenhum.
//			- Descrição: ???
//
//		notifyObservador
//			- Visibilidade: pública.
//			- Tipo de retorno: void.
//			- Parâmetros: ObservadorIF.
//			- Descrição: ???
//
////////////////////////////////////////////////////////////////////////////

package com.motion.cartografo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
///*INTERFACE CODE*/import java.util.Iterator;
import java.util.ListIterator;
import com.motion.navegador.Navegador;
import com.motion.supportClasses.ArchiveManager;
import com.motion.supportClasses.ObservadorIF;
import com.motion.supportClasses.ObservadoIF;

public class Cartografo implements ObservadorIF, ObservadoIF {

	//----------VARIÁVEIS----------//
	private 	static Cartografo pCartografo = null;
	protected 	static MarList listaMares = new MarList();
	private 	static Mar marAtual = null;	
	private 	ArrayList<ObservadorIF> listaObservadores = new ArrayList<ObservadorIF>();

	private double 	latMinima = 1000, lonMinima = 1000, latMaxima = -1000, lonMaxima = -1000;

	//----------CONSTRUTOR----------//
	private Cartografo(){

		// Aqui entra a rotina de try catch pra pegar os dados das zonas relativas ao ônibus.
		// Atualmente e com intenção de teste, tais dados estão sendo pegos de um arquivo texto chamado
		// "Zones_Data_Test.txt", contido no diretório 'data'.

		try
		{	
			//Abrindo arquivo texto de Mares
			ArchiveManager archiveManager = new ArchiveManager("data\\Zones_Data_Test.txt");
			ArrayList<String> data = archiveManager.openArchive();

//			/*TEST PRINT*/System.out.print("------Início da leitura de zonas e alocação de Mares------\n");

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

				//Guardando as lat e lon mínimas e máximas
				if(this.latMinima>lat0)
					latMinima = lat0;
				if(this.lonMinima>lon0)
					lonMinima = lon0;
				if(this.latMaxima<latF)
					latMaxima = latF;
				if(this.lonMaxima<lonF)
					lonMaxima = lonF;
			}			

//			/*TEST PRINT*/System.out.print("------Fim da leitura de zonas e alocação de Mares------\n");
		}

		catch (IOException e){
			System.out.printf(e.getMessage());
		}

		//Inscrevendo-se como observador junto ao módulo Navegador
		Navegador.getNavegador().add(this);
	}

	//----------MÉTODOS PÚBLICOS----------//
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

	//----------PADRÃO OBSERVADOR----------//
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






