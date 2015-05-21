////////////////////////////////////////////////////////////////////////////
//
//	INFORMAÇÕES GERAIS
//
//	Módulo: com.motion.bombardeiro
//
//	Nome do arquivo em repositório: Z:\...\...\...\Bombardeiro.java
//
//	Dono: R0B07F4C70RY
//	Projeto: Motion
//	Sistema: Caravela BDT
//	Lista de Autores:
//		Daniel Groxo - gro
//		Marcio Amadeus - ama
//
//	Log de trabalho
//       Versão  Data         Autores      Descrição
//       1.0     06/mar/2015  gro		   Em desenvolvimento
// -------------------------------------------------------------------------
// 
//	ESPECIFICAÇÕES
// 
//	Descrição do módulo:
//		Módulo responsável pela comunicação e reprodução das sequências de impressões na tela, 
//		além do manejamento delas em memória RAM. É o cara que ganha a vida bombardeando as 
//		outras embarcações com toda a "propaganda de guerra".
// 
//	Propriedades a serem observadas por módulos clientes:
//		???
// 
//	Estrutura dos dados:
//		???
//    
////////////////////////////////////////////////////////////////////////////
// 
// -------------------------------------------------------------------------
//
//	MÉTODOS
// 
//		Bombardeiro
//			- Visibilidade: privada.
//			- Tipo de retorno: nenhum (construtor).
//			- Parâmetros: nenhum.
//			- Descrição: ???
//
////////////////////////////////////////////////////////////////////////////

package com.motion.bombardeiro;

import com.motion.cartografo.Cartografo;
import com.motion.navegador.Navegador;
import com.motion.supportClasses.InterfaceManager;
import com.motion.supportClasses.ObservadoIF;
import com.motion.supportClasses.ObservadorIF;

public class Bombardeiro implements ObservadorIF {
   
	//----------VARIÁVEIS----------//
	static private Bombardeiro pBombardeiro = null;	 
	private InterfaceManager pInterfaceManager 	= null;
	private String marAtual = null;
	
//----------CONSTRUTOR----------//
	private Bombardeiro() 
	{		
		//Utilizando o InterfaceManager para gerar uma interface de amostragem de protótipo
		pInterfaceManager = InterfaceManager.getInterfaceManager(
				Cartografo.getCartografo().getMaresCoordinates());
		
		// aqui entra a rotina de inicialização da tela e da reprodução contínua das impressões
		// ...
		
		//Inscrevendo-se como observador junto aos módulos Navegador e Cartógrafo
		Navegador.getNavegador().add(this);
		Cartografo.getCartografo().add(this);
	}
	
//----------MÉTODOS PÚBLICOS----------//
	static public Bombardeiro getBombardeiro() 
	{
		if (pBombardeiro == null){
			pBombardeiro = new Bombardeiro();
			return pBombardeiro;
		}
		
		else {
			return pBombardeiro;
		}
		
	}

	@Override
	public void notifyObservador(ObservadoIF o) 
	{
		if(o.getClass().equals(Cartografo.class))
		{
			this.marAtual = (String)o.get();
		}
		else if (o.getClass().equals(Navegador.class))
		{
			double[] coordinates = (double[])o.get();
			int x = (int)(coordinates[0]*100);
			int y = (int)(coordinates[1]*100);
			
			int[] minLimits = Cartografo.getCartografo().getMaresMinimumLimits();
			
			pInterfaceManager.setAgentCoord(x-minLimits[0], y-minLimits[1]);
			pInterfaceManager.notifyRepaint();
		}		
	}
	
}
