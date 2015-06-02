////////////////////////////////////////////////////////////////////////////
//
//	INFORMA��ES GERAIS
//
//	M�dulo: com.motion.bombardeiro
//
//	Nome do arquivo em reposit�rio: Z:\...\...\...\Bombardeiro.java
//
//	Dono: R0B07F4C70RY
//	Projeto: Motion
//	Sistema: Caravela BDT
//	Lista de Autores:
//		Daniel Groxo - gro
//		Marcio Amadeus - ama
//
//	Log de trabalho
//       Vers�o  Data         Autores      Descri��o
//       1.0     06/mar/2015  gro		   Em desenvolvimento
// -------------------------------------------------------------------------
// 
//	ESPECIFICA��ES
// 
//	Descri��o do m�dulo:
//		M�dulo respons�vel pela comunica��o e reprodu��o das sequ�ncias de impress�es na tela, 
//		al�m do manejamento delas em mem�ria RAM. � o cara que ganha a vida bombardeando as 
//		outras embarca��es com toda a "propaganda de guerra".
// 
//	Propriedades a serem observadas por m�dulos clientes:
//		???
// 
//	Estrutura dos dados:
//		???  
//    
////////////////////////////////////////////////////////////////////////////
// 
// -------------------------------------------------------------------------
//
//	M�TODOS
// 
//		Bombardeiro
//			- Visibilidade: privada.
//			- Tipo de retorno: nenhum (construtor).
//			- Par�metros: nenhum.
//			- Descri��o: ???
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
   
	//----------VARI�VEIS----------//
	private static 	Bombardeiro pBombardeiro 	= null;
	private 		String 		marAtual 		= null;
//	/*INTERFACE CODE*/private InterfaceManager pInterfaceManager 	= null;
	
//----------CONSTRUTOR----------//
	private Bombardeiro() 
	{		

		//Utilizando o InterfaceManager para gerar uma interface de amostragem de prot�tipo

//		pInterfaceManager = InterfaceManager.getInterfaceManager(null,null,
//				Cartografo.getCartografo().getMaresCoordMatrix());


		//Utilizando o InterfaceManager para gerar uma interface de amostragem de prot�tipo


//		pInterfaceManager = InterfaceManager.getInterfaceManager(null,null,
//				Cartografo.getCartografo().getMaresCoordMatrix());


//		/*INTERFACE CODE*/pInterfaceManager = InterfaceManager.getInterfaceManager(
//				Cartografo.getCartografo().getMaresCoordinates());
		
		// aqui entra a rotina de inicializa��o da tela e da reprodu��o cont�nua das impress�es
		// ...
		
		//Inscrevendo-se como observador junto aos m�dulos Navegador e Cart�grafo
		Navegador.getNavegador().add(this);
		Cartografo.getCartografo().add(this);
	}
	
//----------M�TODOS P�BLICOS----------//
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
	

