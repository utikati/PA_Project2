package PrincipalMain;
import java.util.*;
import DataBase.*;
import Jogo.*;
import JogoOnline.JogoEmRede;
import Simulador.*;
import Utilizadores.*;
import logs.*;
/**
 * Classe Principal do Software
 * @author Jorge Martins
 *
 */
public class Principal {
	/**
	 * Metodo principal do software, sendo esta o metodo Main
	 * @param args argumentos externos 
	 */
	public static void main(String[] args) {
		TempoExecucao tempoMaquina = new TempoExecucao();
		PrimeiraCamada.primeiraCamada();
		System.out.println("Bem Vindo "+DadosEstaticos.utilizador1);
		
		//---------------------------------------------------------------------
		
		GestorSistema systemManager = new GestorSistema();
		GestorJogosGravados gestorJogos = new GestorJogosGravados();
		GestorUtilizadores gestorUtilizadores = new GestorUtilizadores();
		GestorMovimentos log = new GestorMovimentos();
		
		ArrayList jogosG = systemManager.loadDados();
		systemManager.fecharFiles();
		ArrayList utilizadoresG = systemManager.loadDadosUtilizadores();
		
		systemManager.fecharFiles();
		if(jogosG != null) {
			DadosEstaticos.quadro = (String[][]) jogosG.get(0);
			if(jogosG.size() > 1)
				gestorJogos = (GestorJogosGravados) jogosG.get(1);
		}else {
			jogosG = new ArrayList();
		}
		if(utilizadoresG != null) {
			if(utilizadoresG.get(0)!=null)
				gestorUtilizadores = (GestorUtilizadores) utilizadoresG.get(0);
			if(utilizadoresG.get(1)!= null) {
				log = (GestorMovimentos) utilizadoresG.get(1);
			}

		}else {
			utilizadoresG = new ArrayList();
		}
		
		
		
		//--------------------------------------------------------------------
		
		
		if(!DadosEstaticos.utilizador1.equals("Anonimo")) {
			if(!gestorUtilizadores.verificaNick(DadosEstaticos.utilizador1)) {
				gestorUtilizadores.addUtilizadores(new Utilizadores(DadosEstaticos.utilizador1));
			}
				if(gestorUtilizadores.verificaMax()) {
					System.out.println("PARAB�NS �s o PLAYER com mais vitorias!!!\n");
				}
			
		}
		log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Inicio do Software"));
		log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Entrou no Menu Principal"));
		menu(gestorJogos, gestorUtilizadores, log);
		log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Saiu do Software"));
		MetodoStaticGravarDados.gravaDadosSistema(systemManager, gestorJogos, gestorUtilizadores, log);
		MetodoStaticGravarDados.fecharFicheirosObjectos(systemManager);
		tempoMaquina.setFim();
		System.out.println(tempoMaquina);
		System.out.println("Adeus "+DadosEstaticos.utilizador1);
	}
	/**
	 * Menu Principal do Programa
	 * @param gestorJogos recebe o objecto do tipo gestorJogos
	 * @param gestorUtilizadores recebe o objecto do tipo GestorUtilizadores
	 * @param log recebe o objecto do tipo Log
	 */
	public static void menu(GestorJogosGravados gestorJogos, GestorUtilizadores gestorUtilizadores, GestorMovimentos log){
		int opcao = 0;
		while(opcao != 4) {
			// apresentar menu
			apresentaMenu();
			// escolha op�ao
			opcao = pedeDadosInteiro("Escolha a op��o do menu inserindo um numero inteiro da op�ao", DadosEstaticos.teclado);
			//executar op��o
			executaOpcaoUtilizadores(opcao, gestorJogos, gestorUtilizadores, log);
		}
	}

	//LISTAGEM DE OPCOES
	/**
	 * Listagem das op��es do Menu Principal
	 */
	private static void apresentaMenu() {
		System.out.println("1 - Iniciar um Jogo\n" +
				"2 - Listar \n"+
				"3 - Pesquisa Avancada por Nick\n"+
				"4 - Sair\n"
				
				);
				
	}

	//OPCOES
	/**
	 * @param aOpcao
	 * Metodo onde s�o executadas as op��es do utilizador para o Menu Principal
	 */
	private static void executaOpcaoUtilizadores(int aOpcao, GestorJogosGravados gestorJogos, GestorUtilizadores gestorUtilizadores, GestorMovimentos log) {
		switch(aOpcao) {
		case 1: menuJogo(gestorJogos, gestorUtilizadores, log); log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Entrou Menu de Jogos")); break;
		case 2: menuListagens(gestorUtilizadores, log); log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Entrou no Menu de Listagens")); break;
		case 3: MetodosStaticUtilizadores.pesquisaUtilizadorNick(gestorUtilizadores); log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Pesquisou Utilizador por Nick")); break;
		case 4: break;
		default: System.out.println("Opcao Errada!\n");
		}
	}
	
	
	
	//MENU JOGO---------------------------------------------------------------------

	/**
	 * SubMenu do jogo, usado para aceder �s op��es para jogo local e online
	 * @param gestorJogos recebe objecto para gravar jogos
	 * @param gestorUtilizadores recebe objecto para gravar as estatisticas de jogo
	 * @param log recebe objecto para guardar dados de log
	 */
	public static void menuJogo(GestorJogosGravados gestorJogos, GestorUtilizadores gestorUtilizadores, GestorMovimentos log){
		int opcao = 0;
		while(opcao != 5) {
			// apresentar menu
			apresentaMenuJogo();
			// escolha op�ao
			opcao = pedeDadosInteiro("Escolha a op��o do menu inserindo um numero inteiro da op�ao", DadosEstaticos.teclado);
			//executar op��o
			executaOpcaoUtilizadoresJogo(opcao, gestorJogos, gestorUtilizadores, log);
		}
	}

	/**
	 * Apresenta apenas o menu de jogo
	 */
	private static void apresentaMenuJogo() {
		System.out.println("1 - Jogar Local contra Utilizador\n" +
				"2 - Jogar Local contra Computador \n"+
				"3 - Jogar Online\n"+
				"4 - Simulador\n"+
				"5 - Sair\n"
				
				);
				
	}

	/**
	 * Metodo que executa a opcao do utilizador do seu menu, neste caso o menu de jogo, recebe os objectos para grava�ao de jogo, actualizar as estatisticas
	 * de utilizadores que est�o activos
	 * @param aOpcao inteiro com op��o a executar
	 * @param gestorJogos objecto para gravar os jogos
	 * @param gestorUtilizadores objecto para actualizar dados estatisticos
	 * @param log objecto para gravar movimentos
	 */
	private static void executaOpcaoUtilizadoresJogo(int aOpcao, GestorJogosGravados gestorJogos, GestorUtilizadores gestorUtilizadores, GestorMovimentos log) {
		switch(aOpcao) {
		case 1: JogarLocal.jogarLocal(gestorJogos, gestorUtilizadores); log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Jogou um Jogo Local")); break;
		case 2: JogarLocal.jogarPC(); log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Jogou Contra o Computador")); break;
		case 3: menuJogoOnline(gestorJogos, gestorUtilizadores, log); log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Entrou Menu Jogo Online")); break;
		case 4: MetodoStaticSimulador.menuSimulador(gestorJogos, log); log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Entrou no menu Simulador")); break;
		case 5: log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Saiu Para o Menu Principal")); break;
		default: System.out.println("Opcao Errada!\n");
		}
	}
	
	
	//FINAL MENU JOGO-------------------------------------------------------------
	
	//MENU JOGO ONLINE-----------------------------------------------------------------------------------
	/**
	 * Menu especifico para o Jogo Online / em rede
	 * @param gestorJogos objecto gestor de jogos para gravar os jogos
	 * @param gestorUtilizadores objecto gestor de utilizadores para actualizar dados estatisticos
	 * @param log objecto log para guardar movimentos de utilizador
	 */
	public static void menuJogoOnline(GestorJogosGravados gestorJogos, GestorUtilizadores gestorUtilizadores, GestorMovimentos log){
		int opcao = 0;
		while(opcao != 3) {
			// apresentar menu
			apresentaMenuJogoOnline();
			// escolha op�ao
			opcao = pedeDadosInteiro("Escolha a op��o do menu inserindo um numero inteiro da op�ao", DadosEstaticos.teclado);
			//executar op��o
			executaOpcaoUtilizadoresJogoOnline(opcao, gestorJogos, gestorUtilizadores, log);
		}
	}

	/**
	 * Apresenta o menu especifico do Jogo em Rede
	 */
	private static void apresentaMenuJogoOnline() {
		System.out.println("1 - Jogar como Servidor\n" +
				"2 - Jogar como Cliente \n"+
				"3 - sair\n"
				);
				
	}

	/**
	 * Metodo que executa a op��o dada pelo utilizador no menu especifico de jogo online, neste caso se escolheu jogar como servidor ou como cliente
	 * @param aOpcao opcao do utilizador 
	 * @param gestorJogos objecto para gravar jogos
	 * @param gestorUtilizadores objecto para gerir e actualizar dados estatisticos 
	 * @param log objecto para adicionar e gravar os movimentos no software
	 */
	private static void executaOpcaoUtilizadoresJogoOnline(int aOpcao, GestorJogosGravados gestorJogos, GestorUtilizadores gestorUtilizadores, GestorMovimentos log) {
		switch(aOpcao) {
		case 1: JogoEmRede.servidorON(gestorUtilizadores); log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Jogou Online como Servidor")); break;
		case 2: JogoEmRede.clienteON(gestorUtilizadores); log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Jogou Online como Cliente")); break;
		case 3: log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Saiu Para o Menu de Jogo")); break;
		default: System.out.println("Opcao Errada!\n");
		}
	}
	
	//FINAL MENU JOGO ONLINE *************************+++++++++++++++++++++++++++++++**********************-------
	
	//INICIO MENU LISTAGENS--------------------------------------------------------
	/**
	 * Metodos do menu das listagens, apenas chama os metodos que constroiem este menu
	 * @param gestorUtilizadores recebe objecto gestorUtilizadores para aceder aos dados, para as listagens
	 * @param log objecto para adicionar os movimentos realizados no software
	 */
	public static void menuListagens(GestorUtilizadores gestorUtilizadores, GestorMovimentos log){
		int opcao = 0;
		while(opcao != 5) {
			// apresentar menu
			apresentaMenuListagens();
			// escolha op�ao
			opcao = pedeDadosInteiro("Escolha a op��o do menu inserindo um numero inteiro da op�ao", DadosEstaticos.teclado);
			//executar op��o
			executaOpcaoListagens(opcao, gestorUtilizadores, log);
		}
	}

	/**
	 * Metodo de apresenta��o das op��es do menu das listagens
	 */
	private static void apresentaMenuListagens() {
		System.out.println("1 - Listar todos os Jogadores\n" +
				"2 -  Ordenar a Lista de Jogadores\n"+
				"3 - Listar os Logs de Sistema\n"+
				"4 - Listar logs de Utilizador\n"+
				"5 - Sair\n"
				
				);
				
	}

	/**
	 * Metodo que executa a op��o realizada pelo utilizador no menu de Listagens
	 * @param aOpcao opcao escolhida pelo utilizador
	 * @param gestorUtilizadores objecto para aceder aos dados estatisticos dos utilizadores / jogadores
	 * @param log objecto para actualizar e adicionar os movimentos do utilizador no software
	 */
	private static void executaOpcaoListagens(int aOpcao, GestorUtilizadores gestorUtilizadores, GestorMovimentos log) {
		switch(aOpcao) {
		case 1: gestorUtilizadores.listarUtilizadores(); log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Listou Todos os Jogadores")); break;
		case 2: MetodosStaticUtilizadores.ordenarLista(gestorUtilizadores); log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Ordenou a Lista")); break;
		case 3: log.listarMovimentos(); log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Listou os Movimentos ou Logs de Sistema")); break;
		case 4: String nome = pedeDadosString("Insira o nick a pesquisar para Listagem", DadosEstaticos.teclado);
				log.pesquisaMoviNick(nome); log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Listagem Condicionada por Nick"));
				break;
		case 5: log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Saida do Menu de Listagens")); break;
		default: System.out.println("Opcao Errada!\n");
		}
	}
	
	
	//FINAL MENU LISTAGENS----------------------------------------------------------
	/**
	 * Metodo standard usado no software para pedir dados ao utilizador, neste caso dados em string
	 * @param aTexto frase que sera visualizada pelo utilizador, algo como os dados que queremos que o utlizador insira
	 * @param aTeclado objecto scanner para que o utilizador use o input por defeito do sistema, neste caso o teclado
	 * @return string com o input realizado pelo utilizador
	 */
	public static String pedeDadosString(String aTexto, Scanner aTeclado) {
		System.out.println(aTexto);
		return aTeclado.nextLine();
	}
	
	/**
	 * Metodo standard usado no software para pedir dados ao utilizador mas na forma de inteiro apenas, n�o deixando que o inteiro seja maior que 9 unidades e verifica se � mesmo um 
	 * numero inteiro que foi inserido
	 * @param aTexto frase ou texto que indica os dados que ser�o para inserir
	 * @param aTeclado objecto scanner para o utilizar realizar o input atrav�s do teclado
	 * @return inteiro com o input dado pelo utilizador
	 */
	public static int pedeDadosInteiro(String aTexto, Scanner aTeclado) { 
		while(true){
			try{
				return (Integer.parseInt(pedeDadosString(aTexto, aTeclado))); //assim j� asseguro ser inteiro..
			}catch(NumberFormatException nfe){
				System.out.println("N�o � um inteiro ou maior que 9 unidades, insira novamente");
			}
		}
	}
	
	

}
