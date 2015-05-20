////////////////////////////////////////////////////////////////////////////
//
//	INFORMAÇÕES GERAIS
//
//	Módulo: com.motion.capitao
//
//	Nome do arquivo em repositório: Z:\...\...\...\Capitao.java
//
//	Dono: R0B07F4C70RY
//	Projeto: Motion
//	Sistema: Caravela BDT
//	Lista de Autores:
//		Daniel Groxo - gro
//
//	Log de trabalho
//       Versão  Data         Autores      Descrição
//       1.0     25/set/2014  gro		   Em desenvolvimento
// -------------------------------------------------------------------------
//
//	ESPECIFICAÇÕES
// 
//	Descrição do módulo: test
//		Módulo que controla o início e fim do sistema, e age como intermediário entre agentes 
//		externos e a tripulação. É, portanto, responsável por inicializar todos os outros módulos 
//		e, mais tarde, por desligá-los, e também por desligar o sistema. Adicionalmente, qualquer 
//		contato entre a central e o sistema embarcado deve   obrigatoriamente passar pelo módulo 
//		Capitão, que então delega a mensagem para o módulo responsável. Ele é quem manda e quem dá 
//		as ordens.
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
//		Capitao
//			- Visibilidade: privada.
//			- Tipo de retorno: nenhum (construtor).
//			- Parâmetros: nenhum.
//			- Descrição: ???
//
////////////////////////////////////////////////////////////////////////////

package com.motion.capitao;

import com.motion.bombardeiro.*;
import com.motion.cartografo.*;
//import com.motion.comissario.*;
//import com.motion.escrivao.    *;
import com.motion.navegador.*;

public class Capitao {
	
//----------VARIÁVEIS----------//
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
		
		//aqui entram quaisquer comandos necessários para que o sistema cesse seu funcionamento,
		//como, por exemplo, um comando que indique que é hora do sistema enviar dados de
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
