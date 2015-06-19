////////////////////////////////////////////////////////////////////////////
//
//	INFORMAï¿½ï¿½ES GERAIS
//
//	Mï¿½dulo: com.motion.bombardeiro
//
//	Nome do arquivo em repositï¿½rio: Z:\...\...\...\Bombardeiro.java
//
//	Dono: R0B07F4C70RY
//	Projeto: Motion
//	Sistema: Caravela BDT
//	Lista de Autores:
//		Daniel Groxo - gro
//		Marcio Amadeus - ama
//
//	Log de trabalho
//       Versï¿½o  Data         Autores      Descriï¿½ï¿½o
//       1.0     06/mar/2015  gro		   Em desenvolvimento
// -------------------------------------------------------------------------
// 
//	ESPECIFICAï¿½ï¿½ES
// 
//	Descriï¿½ï¿½o do mï¿½dulo:
//		Mï¿½dulo responsï¿½vel pela comunicaï¿½ï¿½o e reproduï¿½ï¿½o das sequï¿½ncias de impressï¿½es na tela, 
//		alï¿½m do manejamento delas em memï¿½ria RAM. ï¿½ o cara que ganha a vida bombardeando as 
//		outras embarcaï¿½ï¿½es com toda a "propaganda de guerra".
// 
//	Propriedades a serem observadas por mï¿½dulos clientes:
//		???
// 
//	Estrutura dos dados:
//		???  
//    
////////////////////////////////////////////////////////////////////////////
// 
// -------------------------------------------------------------------------
//
//	Mï¿½TODOS
// 
//		Bombardeiro
//			- Visibilidade: privada.
//			- Tipo de retorno: nenhum (construtor).
//			- Parï¿½metros: nenhum.
//			- Descriï¿½ï¿½o: ???
//
////////////////////////////////////////////////////////////////////////////

package com.motion.bombardeiro;

import com.motion.cartografo.Cartografo;
import com.motion.navegador.Navegador;
///*INTERFACE CODE*/import com.motion.supportClasses.InterfaceManager;
import com.motion.supportClasses.ObservadoIF;
import com.motion.supportClasses.ObservadorIF;
       ///
public class Bombardeiro implements ObservadorIF {
   
	//----------VARIï¿½VEIS----------//
	private static 	Bombardeiro pBombardeiro 	= null;
	private 		String 		marAtual 		= null;
//	/*INTERFACE CODE*/private InterfaceManager pInterfaceManager 	= null;
	
//----------CONSTRUTOR----------//
	private Bombardeiro() 
	{		

		//Utilizando o InterfaceManager para gerar uma interface de amostragem de protï¿½tipo

//		/*INTERFACE CODE*/pInterfaceManager = InterfaceManager.getInterfaceManager(
//				Cartografo.getCartografo().getMaresCoordinates());
		
		// aqui entra a rotina de inicializaï¿½ï¿½o da tela e da reproduï¿½ï¿½o contï¿½nua das impressï¿½es
		// lembrando que: as impressões estão limitadas a 12 por zona, cada uma de 10 segundos, para um
		// loop total de 2 minutos. Dessas 12 impressões, 2 serão default: a impressão da Motion e a 
		// impressão do governo - e por isso no .xsd a quantidade mínima de impressões é 2 (e a máxima
		// é 12)
		
		//Inscrevendo-se como observador junto aos mï¿½dulos Navegador e Cartï¿½grafo
		Navegador.getNavegador().add(this);
		Cartografo.getCartografo().add(this);
	}
	
//----------Mï¿½TODOS Pï¿½BLICOS----------//
	public static Bombardeiro getBombardeiro() 
	{
		if (pBombardeiro == null){
			pBombardeiro = new Bombardeiro();
			return pBombardeiro;
		}
		
		else {
			return pBombardeiro; 
		}
		
	}
	
	public String getMarAtual()
	{
		return this.marAtual;
	}

	@Override
	public void notifyObservador(ObservadoIF o) 
	{
		if(o.getClass().equals(Cartografo.class))
		{
			this.marAtual = (String)o.get();
		}
/*INTERFACE CODE BEGIN*/
//		else if (o.getClass().equals(Navegador.class))
//		{
//			double[] coordinates = (double[])o.get();
//			int x = (int)(coordinates[0]*100);
//			int y = (int)(coordinates[1]*100);
			
//			int[] minLimits = Cartografo.getCartografo().getMaresMinimumLimits();
			
//			pInterfaceManager.setAgentCoord(x-minLimits[0], y-minLimits[1]);
//		}		



			//pInterfaceManager.setAgentCoord(x-minLimits[0], y-minLimits[1]);
		//}		


//			pInterfaceManager.setAgentCoord(x-minLimits[0], y-minLimits[1]);
//			pInterfaceManager.notifyRepaint();
//		}
/*INTERFACE CODE END*/

	}
	
}
	

