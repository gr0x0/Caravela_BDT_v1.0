////////////////////////////////////////////////////////////////////////////
//
//	INFORMAÇÕES GERAIS
//
//	Módulo: com.motion.navegador
//
//	Nome do arquivo em repositório: Z:\...\...\...\Navegador.java
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
//		Módulo responsável por ativamente identificar a posição geográfica, controlando o GPS do 
//		hardware. Ele é quem pilota o leme.
// 
//	Propriedades a serem observadas por módulos clientes:
//		O acesso ao módulo Navegador se dá pelo método estático getNavegador(), seguindo as 
//		diretrizes do design pattern Singleton adotado.
//		Adicionalmente, este módulo é observável através do design pattern Observador. Para receber
//		as informações de GPS que ele guarda, módulos clientes devem se inscrever como observadores
//		através do método add(ObservadorIF o), enviando-se como o parâmetro 'o'. Perceba que, para
//		tanto, o módulo cliente deve importar a classe interface de suporte ObservadorIF, seguindo 
//		assim o design pattern Observador, e implementar as classes apresentadas por ela.
// 
//	Estrutura dos dados:
//		Este módulo se apresenta como um Singleton.
//		Este módulo é observável via design pattern Observador.
//    
////////////////////////////////////////////////////////////////////////////
// 
// -------------------------------------------------------------------------
//
//	MÉTODOS
// 
//		Navegador
//			- Visibilidade: privada.
//			- Tipo de retorno: nenhum (construtor).
//			- Parâmetros: nenhum.
//			- Descrição: ???
//
//		getNavegador
//			- Visibilidade: publica.
//			- Tipo de retorno: Navegador.
//			- Parâmetros: nenhum.
//			- Descrição: ???
//
//		setLat
//			- Visibilidade: privada.
//			- Tipo de retorno: void.
//			- Parâmetros: String.
//			- Descrição: ???
//
//		getLat
//			- Visibilidade: privada.
//			- Tipo de retorno: float.
//			- Parâmetros: nenhum.
//			- Descrição: ???
//
//		setLon
//			- Visibilidade: privada.
//			- Tipo de retorno: void.
//			- Parâmetros: String.
//			- Descrição: ???
//
//		getLon
//			- Visibilidade: privada.
//			- Tipo de retorno: float.
//			- Parâmetros: nenhum.
//			- Descrição: ???
//
//		setVel
//			- Visibilidade: privada.
//			- Tipo de retorno: void.
//			- Parâmetros: String.
//			- Descrição: ???
//
//		getVel
//			- Visibilidade: privada.
//			- Tipo de retorno: float.
//			- Parâmetros: nenhum.
//			- Descrição: ???
//
//		add
//			- Visibilidade: pública
//			- Tipo de retorno: void.
//			- Parâmetros: ObservadorIF.
//			- Descrição: ???
//
//		remove
//			- Visibilidade: pública.
//			- Tipo de retorno: void.
//			- Parâmetros: ObservadorIF.
//			- Descrição: ???
//
//		public void atualizaObservadores(ObservadoIF o)
//			- Visibilidade: pública.
//			- Tipo de retorno: void.
//			- Parâmetros: ObservadoIF.
//			- Descrição: ???
//
//		get
//			- Visibilidade: pública.
//			- Tipo de retorno: Object (float[]).
//			- Parâmetros: void.
//			- Descrição: ???
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

//----------VARIÁVEIS----------//
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
		// Os dados de latitude, longitude e velocidade devem ser armazenados nas variáveis
		// correspondentes utilizando-se os métodos set privados.
		// Atualmente e com intenção de teste, tais dados estão sendo pegos de um arquivo texto chamado
		// "GPS_Data_Test.txt", contido no diretório 'data'.
		
		try
		{	
			//Abrindo arquivo texto de posições de GPS
			ArchiveManager archiveManager = new ArchiveManager("data\\GPS_Data_Test.txt");
			final ArrayList<String> data = archiveManager.openArchive();
				
			//Inicializando o iterador temporal
			Timer timer = new Timer();
			/*TEST PRINT*/System.out.print("------Início da iteração com Timer no Navegador------\n");
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
	
//----------MÉTODOS PÚBLICOS----------//
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

//----------PADRÃO OBSERVADOR----------//
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
