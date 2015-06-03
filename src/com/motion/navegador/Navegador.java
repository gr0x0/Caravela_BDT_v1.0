////////////////////////////////////////////////////////////////////////////
//
//	INFORMA��ES GERAIS
//
//	M�dulo: com.motion.navegador
//
//	Nome do arquivo em reposit�rio: Z:\...\...\...\Navegador.java
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
//		M�dulo respons�vel por ativamente identificar a posi��o geogr�fica, controlando o GPS do 
//		hardware. Ele � quem pilota o leme.
// 
//	Propriedades a serem observadas por m�dulos clientes:
//		O acesso ao m�dulo Navegador se d� pelo m�todo est�tico getNavegador(), seguindo as 
//		diretrizes do design pattern Singleton adotado.
//		Adicionalmente, este m�dulo � observ�vel atrav�s do design pattern Observador. Para receber
//		as informa��es de GPS que ele guarda, m�dulos clientes devem se inscrever como observadores
//		atrav�s do m�todo add(ObservadorIF o), enviando-se como o par�metro 'o'. Perceba que, para
//		tanto, o m�dulo cliente deve importar a classe interface de suporte ObservadorIF, seguindo 
//		assim o design pattern Observador, e implementar as classes apresentadas por ela.
// 
//	Estrutura dos dados:
//		Este m�dulo se apresenta como um Singleton.
//		Este m�dulo � observ�vel via design pattern Observador.
//    
////////////////////////////////////////////////////////////////////////////
// 
// -------------------------------------------------------------------------
//
//	M�TODOS
// 
//		Navegador
//			- Visibilidade: privada.
//			- Tipo de retorno: nenhum (construtor).
//			- Par�metros: nenhum.
//			- Descri��o: ???
//
//		getNavegador
//			- Visibilidade: publica.
//			- Tipo de retorno: Navegador.
//			- Par�metros: nenhum.
//			- Descri��o: ???
//
//		setLat
//			- Visibilidade: privada.
//			- Tipo de retorno: void.
//			- Par�metros: String.
//			- Descri��o: ???
//
//		getLat
//			- Visibilidade: privada.
//			- Tipo de retorno: float.
//			- Par�metros: nenhum.
//			- Descri��o: ???
//
//		setLon
//			- Visibilidade: privada.
//			- Tipo de retorno: void.
//			- Par�metros: String.
//			- Descri��o: ???
//
//		getLon
//			- Visibilidade: privada.
//			- Tipo de retorno: float.
//			- Par�metros: nenhum.
//			- Descri��o: ???
//
//		setVel
//			- Visibilidade: privada.
//			- Tipo de retorno: void.
//			- Par�metros: String.
//			- Descri��o: ???
//
//		getVel
//			- Visibilidade: privada.
//			- Tipo de retorno: float.
//			- Par�metros: nenhum.
//			- Descri��o: ???
//
//		add
//			- Visibilidade: p�blica
//			- Tipo de retorno: void.
//			- Par�metros: ObservadorIF.
//			- Descri��o: ???
//
//		remove
//			- Visibilidade: p�blica.
//			- Tipo de retorno: void.
//			- Par�metros: ObservadorIF.
//			- Descri��o: ???
//
//		public void atualizaObservadores(ObservadoIF o)
//			- Visibilidade: p�blica.
//			- Tipo de retorno: void.
//			- Par�metros: ObservadoIF.
//			- Descri��o: ???
//
//		get
//			- Visibilidade: p�blica.
//			- Tipo de retorno: Object (float[]).
//			- Par�metros: void.
//			- Descri��o: ???
//
////////////////////////////////////////////////////////////////////////////

package com.motion.navegador;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;
import com.motion.supportClasses.ArchiveManager;
import com.motion.supportClasses.ObservadorIF;
import com.motion.supportClasses.ObservadoIF;

public class Navegador implements ObservadoIF 
{

//----------VARI�VEIS----------//
	private static 	Navegador 	pNavegador = null;	
	private static 	int 		refreshTimer = 1;
	private 		String 		latitude;
	private 		String 		longitude;
	private 		String 		velocidade;	
	private ArrayList<ObservadorIF> listaObservadores = new ArrayList<ObservadorIF>();   
	
//----------CONSTRUTOR----------//
	private Navegador() 
	{
		// Aqui entra a rotina de try catch pra pegar os dados de GPS a cada instante.
		// Os dados de latitude, longitude e velocidade devem ser armazenados nas vari�veis
		// correspondentes utilizando-se os m�todos set privados.
		// Atualmente e com inten��o de teste, tais dados est�o sendo pegos de um arquivo texto chamado
		// "GPS_Data_Test.txt", contido no diret�rio 'data'.
		
		try
		{	
			//Abrindo arquivo texto de posi��es de GPS
			ArchiveManager archiveManager = new ArchiveManager("data\\GPS_Data_Test.txt");
			final ArrayList<String> data = archiveManager.openArchive();
				
			//Inicializando o iterador temporal
			Timer timer = new Timer();
			/*TEST PRINT*/System.out.print("------In�cio da itera��o com Timer no Navegador------\n");
			timer.schedule(new TimerTask(){

				int i = 0;
				
				@Override
				public void run() {
					if(i < data.size())
					{
						String stringData[] = data.get(i).toString().split(",");
						setLat(stringData[0]);
						setLon(stringData[1]);
						//setVel(stringData[2]);
						setVel("50.0");
						i++;
						getNavegador().atualizaObservadores(getNavegador());
						/*TEST PRINT*/System.out.print("Instante = "+i+" ---> "+getLat()+"; "+getLon()+"; "+getVel()+"\n");
					}
					else i = 0;
				}
			}, 0, Navegador.refreshTimer*1000);
			
		}
		
		catch (IOException e){
			System.out.printf(e.getMessage());
		}
	}
	
//----------M�TODOS P�BLICOS----------//
	public static Navegador getNavegador() 
	{
		if (pNavegador == null){
			pNavegador = new Navegador();
			return pNavegador;
		}
		
		else {
			return pNavegador;
		}
		
	}

//----------SETs & GETs----------//	
	
	private void setLat(String latitude) 
	{
		this.latitude = latitude;
	}
	
	private double getLat() 
	{
		return Double.parseDouble(latitude);
	}
	
	private void setLon(String longitude) 
	{
		this.longitude = longitude;
	}
	
	private double getLon() 
	{
		return Double.parseDouble(longitude);
	}
	
	private void setVel(String velocidade) 
	{
		this.velocidade = velocidade;
	}
	
	private double getVel() 
	{
		return Double.parseDouble(velocidade);
	}

//----------PADR�O OBSERVADOR----------//
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
		double[] getList = { this.getLat(), this.getLon(), this.getVel() };
		return getList;
	}	
}
