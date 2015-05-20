////////////////////////////////////////////////////////////////////////////
//
//	INFORMA��ES GERAIS
//
//	M�dulo: com.motion.capitao
//
//	Nome do arquivo em reposit�rio: Z:\...\...\...\Capitao.java
//
//	Dono: R0B07F4C70RY
//	Projeto: Motion
//	Sistema: Caravela BDT
//	Lista de Autores:
//		Daniel Groxo - gro
//
//	Log de trabalho
//       Vers�o  Data         Autores      Descri��o
//       1.0     25/set/2014  gro		   Em desenvolvimento
// -------------------------------------------------------------------------
//
//	ESPECIFICA��ES
// 
//	Descri��o do m�dulo: test
//		M�dulo que controla o in�cio e fim do sistema, e age como intermedi�rio entre agentes 
//		externos e a tripula��o. �, portanto, respons�vel por inicializar todos os outros m�dulos 
//		e, mais tarde, por deslig�-los, e tamb�m por desligar o sistema. Adicionalmente, qualquer 
//		contato entre a central e o sistema embarcado deve   obrigatoriamente passar pelo m�dulo 
//		Capit�o, que ent�o delega a mensagem para o m�dulo respons�vel. Ele � quem manda e quem d� 
//		as ordens.
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
//		Capitao
//			- Visibilidade: privada.
//			- Tipo de retorno: nenhum (construtor).
//			- Par�metros: nenhum.
//			- Descri��o: ???
//
////////////////////////////////////////////////////////////////////////////

package com.motion.capitao;

import com.motion.bombardeiro.*;
import com.motion.cartografo.*;
//import com.motion.comissario.*;
//import com.motion.escrivao.    *;
import com.motion.navegador.*;

public class Capitao {
	
//----------VARI�VEIS----------//
	Cartografo 		pCartografo	 = null;
	//Comissario 	pComissario	 = null;
	//Escrivao 		pEscrivao	 = null;
	Navegador 		pNavegador	 = null;            
	Bombardeiro 	pBombardeiro = null;
	
//----------CONSTRUTOR----------//
	private Capitao(){		    
	} 
	
	private void startSystem(){
		
		pNavegador		= Navegador.getNavegador();	
		pCartografo		= Cartografo.getCartografo();
		//pComissario	= Comissario.getComissario();
		//pEscrivao		= Bombardeiro.getEscrivao();
		pBombardeiro	= Bombardeiro.getBombardeiro();
	}
	
	@SuppressWarnings("unused")
	private void stopSystem(){
		
		//aqui entram quaisquer comandos necess�rios para que o sistema cesse seu funcionamento,
		//como, por exemplo, um comando que indique que � hora do sistema enviar dados de
		//funcionamento para a central, ou salvar algum dado importante. Seria algo assim:
		
		//pEscrivao.sendFeedback();
		//pBombardeiro.stopBombing();
		//pComissario.checkVersion();
		
	}
    /// testa ver se esta comitando 
	public static void main(String [ ] args)
	{
	      Capitao capitao = new Capitao();
	      capitao.startSystem();
	}
}
