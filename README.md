
SISTEMA ‘NAVIO FANTASMA’ v1.0

Engenharia de Software

Amadeus e Groxo


 
	REQUISITOS FUNCIONAIS

	Sequências de impressões: o sistema deve ser capaz de armazenar e reproduzir sequências de impressões (vídeos, imagens, etc.) de forma automática.

	Zonas: o sistema deve ser capaz de reconhecer as zonas – setores demarcados previamente na geografia local – que condizem com sua rota. As zonas possuem vinculadas a elas sequências de impressões específicas, a serem exibidas pelo sistema.

	Localização: o sistema de deve se comunicar com o hardware de GPS. A informação recebida será usada em tempo de execução para o processamento da escolha das sequências de impressões a serem exibidas, bem como servirá de base para estatísticas, sendo armazenada como dados para uso posterior. Adicionalmente, o sistema deve ser capaz de enviar para o sistema central – via 3G – sua localização a cada instante.

	Adaptabilidade e sensibilidade: o sistema deve ser sensível a modificações das zonas e das sequências de impressões vinculadas a elas, bem como de outras características. Em outras palavras, o sistema embarcado poderá receber atualizações automáticas do sistema central contendo novas sequências de vídeos, novas zonas ou variantes de zonas antigas, e até mesmo modificações de rotas e linhas.

	Geração automática de estatísticas: o sistema deve ser capaz de gerar estatísticas de forma clara, para que os analistas de marketing sejam capazes de traçar estratégias tendo em vista a otimização da exposição de suas impressões. São elas:

	Localização e tempo de mobilidade e parada; 

	Tempo total de exposição por sequência de impressões, além de sua porcentagem comparada ao tempo total de exposição por zona;

	Tempo médio que o ônibus permanece por zona. 

	Comunicação entre sistemas: o sistema deve ser capaz de retornar dados específicos de acordo com requisições de outros sistemas relacionados a ele, como por exemplo o sistema central e o sistema do app.





	MÓDULOS DO SISTEMA

	Módulo Capitão: módulo que controla o início e fim do sistema, e age como intermediário entre agentes externos e a tripulação. É, portanto, responsável por inicializar todos os outros módulos e, mais tarde, por desligá-los, e também por desligar o sistema. Adicionalmente, qualquer contato entre a central e o sistema embarcado deve obrigatoriamente passar pelo módulo Capitão, que então delega a mensagem para o módulo responsável. Ele é quem manda e quem dá as ordens.

	Módulo Cartógrafo: módulo que controla a identificação da zona atual no mapa de zonas, além de ter o registro de todas as zonas por onde a embarcação passa no seu trajeto. Em suma, é o cara que tem os mapas e fica constantemente lendo-o, para saber em que mar estão.

	Módulo Navegador: módulo responsável por ativamente identificar a posição geográfica, controlando o GPS do hardware. Ele é quem pilota o leme.

	Módulo Escrivão: módulo cujo intuito é apenas receber informações ativamente do módulo Cartógrafo e guardá-las de tempos em tempos num arquivo texto em disco. Ele registra todas as informações da viagem para uso posterior.

	Módulo Bombardeiro: módulo responsável pela comunicação e reprodução das sequências de impressões na tela, além do manejamento delas em memória RAM. É o cara que ganha a vida bombardeando as outras embarcações com toda a "propaganda de guerra".

	Módulo Comissário: módulo responsável por manter atualizados os arquivos descritores das zonas por onde o ônibus passa e das sequências de impressões relacionadas à elas. Para tanto, ele possui autonomia para entrar em contato com a central quando chamado, recebendo dados para comparar com os arquivos atuais do sistema e então enviar requests para download de novos arquivos, se necessário. É responsável também por retirar sequências de vídeos obsoletas.







	ROTINA DE INICIALIZAÇÃO DO SISTEMA

1.	O método main inicializa o módulo Capitão.

2.	O módulo Capitão inicializa o módulo Cartógrafo, que envia as informações das zonas em disco para a memória RAM.

3.	O módulo Capitão inicializa o módulo Navegador, que passa a ativamente perceber a localização geográfica do ônibus.

4.	O módulo Capitão inicializa o módulo Escrivão. Ele passa a agir como observador do módulo Cartógrafo, pegando informações tanto do Cartógrafo quanto do próprio Navegador através do Cartógrafo.

5.	O módulo Cartógrafo envia requisição para o módulo Navegador e recebe a localização, a partir da qual descobre e guarda a zona atual do ônibus. A partir desse momento, o módulo Cartógrafo irá observar a variação da localização geográfica ativamente e assim saberá quando a zona muda.

6.	O módulo Capitão inicializa o módulo Bombardeiro, que envia requisição para o módulo Cartógrafo para saber qual sequência de impressões deve ser reproduzida. A partir desse momento, o módulo Bombardeiro irá observar a variação de zonas ativamente e assim saberá quando a sequência de impressões muda.

7.	O módulo Bombardeiro inicia a reprodução da sequência de impressões de acordo com a zona atual.












	FUNCIONAMENTO DO SISTEMA

1.	O módulo Navegador estará ativamente armazenando a posição geográfica atual do ônibus, e por observação o módulo Cartógrafo receberá suas informações, através das quais realiza o cálculo necessário para prever a chegada da transição de zonas. Adicionalmente, o módulo Escrivão estará observando as informações do módulo Cartógrafo e guardando-as em disco.

2.	Quando o módulo Cartógrafo conclui que é chegada uma transição de zonas, o módulo Bombardeiro processa tal mudança, carregando uma nova sequência de vídeos na memória RAM e então reproduzindo-a.

3.	A qualquer momento do dia, o sistema central poderá enviar uma notificação push para o módulo Capitão alertando-o que é necessária uma atualização (tal horário é específico do próprio sistema central e para casos incomuns apenas, uma vez que isso provavelmente gerará um tempo de inatividade na apresentação das sequências de impressões). O módulo Capitão, então, ativa o módulo Comissário, realizando assim o "item 2" da rotina de desligamento do sistema.
















	ROTINA DE DESLIGAMENTO DO SISTEMA

1.	O módulo Capitão recebe uma mensagem de desligamento, e então a delega aos módulos Cartógrafo, Navegador, Escrivão e Bombardeiro. Tais módulos concluem suas atividades em andamento, guardando quaisquer informações necessárias em disco e então encerrando seu funcionamento.

2.	O módulo Capitão inicializa o módulo Comissário, que requisita do sistema central, via 3G, os arquivos textos contendo a relação entre zonas. Através dessas informações, o módulo Comissário envia requisições para a central e recebe as sequências de impressões que ele não possua no HD e que estejam incluídas nas suas zonas, além de apagar as sequências de impressões que não estejam mais incluídas. O módulo Comissário, então, é desativado.

3.	O módulo Capitão envia um comando para que o hardware seja desativado.

