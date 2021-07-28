package JogoOnline;
import java.io.IOException;
import java.net.*;
import java.util.Random;
import Utilizadores.*;
import Jogo.*;
import PrincipalMain.*;
import Aviso.*;

/**
 * Classe responsavel pelo realização de jogo em rede entre dois jogadores, tendo a opção de ser servidor ou cliente no jogo
 * @author Jorge Martins
 *
 */
public class JogoEmRede {
	
	private static Conexao ligacao = null;
	private static int porta;
	private static int x;
	private static int y;
	private static int jogada;
	private static String jogador1;
	private static String jogador2;
	private static boolean serverActivo;
	private static TempoExecucao tempoJogoTotal; //tempo que os jogadores estiveram apenas a jogar
	private static TempoExecucao tempoMinhaJogada; //tempo da jogada em si
	private static TempoExecucao tempoJogadaResp; //tempo que o outro utilizador usou..
	private static int jogadaInterna; //jogada interna do utilizador da aplicação
	
	
	private static long tempoJogada = 0;
	
	
 	/**
 	 * Metodo responsavel pela gestao de jogo sendo o servidor, inicialização do jogo indicando o ip dado pela classe de conexao
 	 * e com o porto dado pelo utilizador para uso
 	 * @param gestorUtilizadores para gravar dados estatisticos do jogador servidor
 	 */
 	public static void servidorON(GestorUtilizadores gestorUtilizadores) {
		int primeiroJogador;
		int transmite = 0;
		serverActivo = true;
		jogadaInterna = 0;
		
		
		if(ligacao==null) { //se esta a null quer dizer que ainda não tem nenhuma....
			porta = Principal.pedeDadosInteiro("Insira a porta/porto que quer usar", DadosEstaticos.teclado);
			ligacao = new Conexao(porta);
			while(true) {
				ligacao.enviaMensagem(enviaMensagemRedeCump("hello"));
				String [] call = conversorMensagem(ligacao.recebeMensagem());
				if(call[1].equals("hello")) {
					DadosEstaticos.utilizador2 = call[0]; // sacar o nome do utilizador remoto
					System.out.println("Ligação Iniciada com o jogador "+ DadosEstaticos.utilizador2 + "\n");
					break; //parar o ciclo que esta para loop infinito
				}
			}
		}
		
		TempoExecucao tempoJogo = new TempoExecucao();
		tempoJogoTotal = new TempoExecucao();
		Random rnd = new Random();
		primeiroJogador = rnd.nextInt(50);
		if(primeiroJogador < 25) {
			primeiroJogador = 1;
			jogador1 = DadosEstaticos.utilizador1; //se for o primeiro a jogar ele é o jogador 1
			jogador2 = DadosEstaticos.utilizador2;
			jogada = 1;
		}else {
			primeiroJogador = 0;
			jogador2 = DadosEstaticos.utilizador1; //sendo o segundo a jogar ele é o jogador 2
			jogador1 = DadosEstaticos.utilizador2;
			jogada = 2;
			tempoJogadaResp = new TempoExecucao(); //começa antes de entrar por causa da validação da primeira jogada, caso fosse invalida poderia reniciar o tempo
		}
		ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("start", Integer.toString(primeiroJogador)));
		if(primeiroJogador==0) {
			System.out.println("Começa o jogador "+ DadosEstaticos.utilizador2 + " a jogar, aguarde a jogada! \n");
			jogador2 = DadosEstaticos.utilizador1; //aqui é quando ele é o jogador2..
			System.out.println("Jogada nº 1 \n");
		}else {System.out.println("Começa você a Jogar\n");}
		Jogo quadro = new Jogo(); //antes de tudo listar o quadro vazio..
		
		while(transmite == 0) {
			transmite = jogoOnlineServer(quadro, ligacao.recebeMensagem(), gestorUtilizadores);
		}
		if(transmite == 1) {
			tempoJogo.setFim();
			servidorON(gestorUtilizadores); //uso de recursividade, problema com a memoria mas é opção para manter tudo contectado e não reniciar de novo...
		}if(transmite == 2) {
			System.out.println("Conexão terminada com sucesso..");
			tempoJogo.setFim();
			ligacao.fechaConexao();
			ligacao = null; //para que se na mesma utilização voltar a usar ele esteja mesmo a null
			
		}if(transmite == -1) {
			tempoJogo.setFim();
			ligacao.fechaConexao();
			System.out.println("Conexão interrompida..");
			ligacao = null;
		}
	}
	
		/**
		 * Gestor do jogo online por parte do Servidor, usa o objecto Jogo e gere a comunicação entre cliente e Server
		 * @param quadro Recebe o objecto Jogo com nome quadro
		 * @param aMensagem Recebe o objecto string com a mensagem que depois é convertida
		 * @return um inteiro que servirá para o metodo continua ou salta para outra opção (ex terminar ligação).
		 */
	private static int jogoOnlineServer(Jogo quadro, String aMensagem, GestorUtilizadores gestorUtilizadores) {
		
		if(aMensagem == null) {return -1;}//se a ligação foi abaixo é preferivel terminar..
		String [] arraySMS = conversorMensagem(aMensagem);
		
		//MENSAGENS COM TAMANHO 2-------------------------------------------------------------------------
		if(arraySMS.length == 2) {
			if(arraySMS[1].equals("ready")) { //apos receber o login ready do client
				Aviso.aguardaEscolha();
				ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("new", "?"));
				return 0;
			}
			if(arraySMS[1].equals("bye")) { //caso especifico de outra aplicação terminar e sair, ou final da app depois do cliente não querer mais
					return 2;
			}
			
			if(arraySMS[1].equals("withdraw")) {
				tempoJogoTotal.setFim(); //termina o tempo de jogo
				Aviso.tempoTotalJogada(tempoJogoTotal.tempoJogada());
				gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, true , tempoJogoTotal.execucao());
				ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("withdraw", "ack"));
				return 0;
			}
			
		}
		
		//--------------------------------------------------------------------------------------------------
		
		//mensagens com tamanho 3-------------------------------
		if(arraySMS.length == 3) {
			if(arraySMS[1].equals("start") && arraySMS[2].equals("1")) { //condicao do cliente
				jogada = 2;
				jogador2 = DadosEstaticos.utilizador1; //caso ele seja o segundo a jogar
				ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("start", "ok"));
				Aviso.aguardaJogada();
				System.out.println("Jogada nº " + (jogada - 1) + "\n");
				tempoJogadaResp = new TempoExecucao();
				return 0;
			}
			if(arraySMS[1].equals("start") && arraySMS[2].equals("0")) { //condicao do cliente <----------------- PRIMEIRA JOGADA
				jogada = 1;
				tempoMinhaJogada = new TempoExecucao();
				while(true) {
					System.out.println("Jogada nº " + jogada + "\n");
					System.out.println(quadro.dica());
					if(continuaJogada()) {
						x = Principal.pedeDadosInteiro("Insira a linha a jogar\n", DadosEstaticos.teclado);
						y = Principal.pedeDadosInteiro("Insira a coluna a jogar\n", DadosEstaticos.teclado);
						if(quadro.verificaDisponibilidade(x-1, y-1)) { //verifico a minha propria disponibilidade
							quadro.inserirDadosX(x-1, y-1); //insiro os dados no objecto
							ligacao.enviaMensagem(enviaMensagemRedeJogada("move", Integer.toString(x), Integer.toString(y)));
							quadro.lista();
							tempoMinhaJogada.setFim();
							Aviso.tempoJogada(tempoMinhaJogada.tempoJogada());
							tempoJogada += tempoMinhaJogada.execucao();
							jogadaInterna++;
							System.out.println("Tempo total das Jogadas do Jogador "+DadosEstaticos.utilizador1+" \n "+ TempoExecucao.conversorTempo(tempoJogada));
							System.out.println("Tempo medio das Jogadas do Jogador "+DadosEstaticos.utilizador1+" \n "+ TempoExecucao.conversorTempo(tempoJogada/jogadaInterna));
							
							Aviso.aguardaJogada();
							return 0;
						}else {Aviso.localOcupado();}
					}else {
						tempoJogoTotal.setFim();
						Aviso.tempoTotalJogada(tempoJogoTotal.tempoJogada());
						gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, false, tempoJogoTotal.execucao());
						ligacao.enviaMensagem(enviaMensagemRedeCump("withdraw"));
						return 0;
					}
					
				}
			}
			
			
			
			if(arraySMS[1].equals("start") && arraySMS[2].equals("ok")) { //caso seja o servidor a jogar primeiro
				tempoMinhaJogada = new TempoExecucao();
				while(true) {
					System.out.println("Jogada nº " + jogada + "\n");
					System.out.println(quadro.dica());
					if(continuaJogada()) {
						x = Principal.pedeDadosInteiro("Insira a linha a jogar\n", DadosEstaticos.teclado);
						y = Principal.pedeDadosInteiro("Insira a coluna a jogar\n", DadosEstaticos.teclado);
						if(quadro.verificaDisponibilidade(x-1, y-1)) { //verifico a minha propria disponibilidade
							quadro.inserirDadosX(x-1, y-1); //insiro os dados no objecto
							
							ligacao.enviaMensagem(enviaMensagemRedeJogada("move", Integer.toString(x), Integer.toString(y)));
							quadro.lista();
							tempoMinhaJogada.setFim();
							Aviso.tempoJogada(tempoMinhaJogada.tempoJogada());
							tempoJogada += tempoMinhaJogada.execucao();
							
							jogadaInterna++;
							System.out.println("Tempo total das Jogadas do Jogador "+DadosEstaticos.utilizador1+" \n "+ TempoExecucao.conversorTempo(tempoJogada));
							System.out.println("Tempo medio das Jogadas do Jogador "+DadosEstaticos.utilizador1+" \n "+ TempoExecucao.conversorTempo(tempoJogada/jogadaInterna));
							Aviso.aguardaJogada();
							tempoJogadaResp = new TempoExecucao();
							return 0;
						}else {Aviso.localOcupado();}
					}else {
						tempoJogoTotal.setFim();
						Aviso.tempoTotalJogada(tempoJogoTotal.tempoJogada());
						gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, false, tempoJogoTotal.execucao());
						ligacao.enviaMensagem(enviaMensagemRedeCump("withdraw"));
						return 0;
					}
					
				}
				
			} 
			if(arraySMS[1].equals("result") && arraySMS[2].equals("valid")) { //podera ser o servidor a validar ou o jogador, caso server pode verificar logo a win e enviar
				if(serverActivo) {
					jogada+=2;
					
					if(quadro.checkaQuadro()) {
						
						if(jogada % 2 == 0 && jogador2.equals(DadosEstaticos.utilizador1)) {
							Aviso.ganhouJogo();
							tempoJogoTotal.setFim();
							Aviso.tempoTotalJogada(tempoJogoTotal.tempoJogada());
							gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, true, tempoJogoTotal.execucao());
							if(Jogo.notificacaoVitoria(gestorUtilizadores.getJogador(DadosEstaticos.utilizador1).getNumeroVitorias()))
								System.out.println("PARABÉNS!! Algomeraste mais 10 vitórias no teu histórico!!\n");
							ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("status", "win")); //vitoria do servidor, envia para o cliente a mensagem lose
						}if(jogada % 2 == 0 && jogador1.equals(DadosEstaticos.utilizador1)) {
							Aviso.perdeuJogo();
							tempoJogoTotal.setFim();
							Aviso.tempoTotalJogada(tempoJogoTotal.tempoJogada());
							gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, false, tempoJogoTotal.execucao());
							ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("status", "lose")); //derrota do servidor e envia o win para o cliente
						}if(jogada % 2 != 0 && jogador1.equals(DadosEstaticos.utilizador1)) {
							Aviso.ganhouJogo();
							tempoJogoTotal.setFim();
							Aviso.tempoTotalJogada(tempoJogoTotal.tempoJogada());
							gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, true, tempoJogoTotal.execucao());
							if(Jogo.notificacaoVitoria(gestorUtilizadores.getJogador(DadosEstaticos.utilizador1).getNumeroVitorias()))
								System.out.println("PARABÉNS!! Algomeraste mais 10 vitórias no teu histórico!!\n");
							ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("status", "win")); //vitoria do servidor, envia para o cliente a mensagem lose
						}if(jogada % 2 != 0 && jogador2.equals(DadosEstaticos.utilizador1)) {
							Aviso.perdeuJogo();
							tempoJogoTotal.setFim();
							Aviso.tempoTotalJogada(tempoJogoTotal.tempoJogada());
							gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, false, tempoJogoTotal.execucao());
							ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("status", "lose")); //derrota do servidor e envia o win para o cliente
						}
					}else {
						if(!quadro.checkboardLines() && serverActivo) {
							System.out.println("Numero de jogadas Esgotadas, Jogo Empatado!\n");
							tempoJogoTotal.setFim();
							Aviso.tempoTotalJogada(tempoJogoTotal.tempoJogada());
							gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, false, tempoJogoTotal.execucao());
							ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("status", "draw")); //empate!!
							return 0;
						}else {
							
							ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("result", "ack"));
							tempoJogadaResp = new TempoExecucao(); //como responde fica a espera da jogada e o timer começa a contar
							return 0;
						}
				}
				}else { //PARTE APENAS PARA O CLIENTE
					jogada+=2;
					
					ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("result", "ack"));
					tempoJogadaResp = new TempoExecucao(); //como responde fica a espera da jogada e o timer começa a contar
					return 0;
					}
				return 0;
			}
			
			if(arraySMS[1].equals("result") && arraySMS[2].equals("notvalid")) { //jogada não foi validada pelo outro
				quadro.apagaJogada(x-1, y-1);
				tempoMinhaJogada = new TempoExecucao();
				while(true) {
					quadro.lista();
					System.out.println(quadro.dica());
					if(continuaJogada()) {
						
						x = Principal.pedeDadosInteiro("Insira a linha a jogar\n", DadosEstaticos.teclado);
						y = Principal.pedeDadosInteiro("Insira a coluna a jogar\n", DadosEstaticos.teclado);
						if(quadro.verificaDisponibilidade(x-1, y-1)) { //verifico a minha propria disponibilidade
							quadro.inserirDadosX(x-1, y-1); //insiro os dados no objecto
							ligacao.enviaMensagem(enviaMensagemRedeJogada("move", Integer.toString(x), Integer.toString(y)));
							quadro.lista();
							tempoMinhaJogada.setFim();
							Aviso.tempoJogada(tempoMinhaJogada.tempoJogada());
							tempoJogada += tempoMinhaJogada.execucao();
							
							jogadaInterna++;
							System.out.println("Tempo total das Jogadas do Jogador "+DadosEstaticos.utilizador1+" \n "+ TempoExecucao.conversorTempo(tempoJogada));
							System.out.println("Tempo medio das Jogadas do Jogador "+DadosEstaticos.utilizador1+" \n "+ TempoExecucao.conversorTempo(tempoJogada/jogadaInterna));
							Aviso.aguardaJogada();
							return 0;
						}else {Aviso.localOcupado();}
					}else {
						tempoJogoTotal.setFim();
						Aviso.tempoTotalJogada(tempoJogoTotal.tempoJogada());
						gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, false, tempoJogoTotal.execucao());
						ligacao.enviaMensagem(enviaMensagemRedeCump("withdraw"));
						return 0;
					}
				}
			}if(arraySMS[1].equals("result") && arraySMS[2].equals("ack")) { //ao receber um ack do client, quer dizer que foi uma jogada dele do cliente
				if(serverActivo) {
					if(quadro.checkaQuadro()) {
						jogada++; //para igualar com a jogada do cliente..
						
						if(jogada % 2 == 0 && jogador2.equals(DadosEstaticos.utilizador1)) {
							Aviso.ganhouJogo();
							tempoJogoTotal.setFim();
							Aviso.tempoTotalJogada(tempoJogoTotal.tempoJogada());
							gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, true, tempoJogoTotal.execucao());
							if(Jogo.notificacaoVitoria(gestorUtilizadores.getJogador(DadosEstaticos.utilizador1).getNumeroVitorias()))
								System.out.println("PARABÉNS!! Algomeraste mais 10 vitórias no teu histórico!!\n");
							ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("status", "win")); //vitoria do servidor, envia para o cliente a mensagem lose
						}if(jogada % 2 == 0 && jogador1.equals(DadosEstaticos.utilizador1)) {
							Aviso.perdeuJogo();
							tempoJogoTotal.setFim();
							Aviso.tempoTotalJogada(tempoJogoTotal.tempoJogada());
							gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, false, tempoJogoTotal.execucao());
							ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("status", "lose")); //derrota do servidor e envia o win para o cliente
						}if(jogada % 2 != 0 && jogador1.equals(DadosEstaticos.utilizador1)) {
							Aviso.ganhouJogo();
							tempoJogoTotal.setFim();
							Aviso.tempoTotalJogada(tempoJogoTotal.tempoJogada());
							gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, true, tempoJogoTotal.execucao());
							if(Jogo.notificacaoVitoria(gestorUtilizadores.getJogador(DadosEstaticos.utilizador1).getNumeroVitorias()))
								System.out.println("PARABÉNS!! Algomeraste mais 10 vitórias no teu histórico!!\n");
							ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("status", "win")); //vitoria do servidor, envia para o cliente a mensagem lose
						}if(jogada % 2 != 0 && jogador2.equals(DadosEstaticos.utilizador1)) {
							Aviso.perdeuJogo();
							tempoJogoTotal.setFim();
							Aviso.tempoTotalJogada(tempoJogoTotal.tempoJogada());
							gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, false, tempoJogoTotal.execucao());
							ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("status", "lose")); //derrota do servidor e envia o win para o cliente
						}
					}else {
						if(!quadro.checkboardLines()) {
							Aviso.empatouJogo();
							System.out.println("Numero de jogadas Esgotadas, Jogo Empatado!\n");
							tempoJogoTotal.setFim();
							Aviso.tempoTotalJogada(tempoJogoTotal.tempoJogada());
							gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, false, tempoJogoTotal.execucao());
							ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("status", "draw")); //empate!!
							return 0;
						}else {
							tempoMinhaJogada = new TempoExecucao();
							while(true) { //envia a sua jogada para o cliente
								System.out.println(quadro.dica());
								if(continuaJogada()) {
									
									x = Principal.pedeDadosInteiro("Insira a linha a jogar\n", DadosEstaticos.teclado);
									y = Principal.pedeDadosInteiro("Insira a coluna a jogar\n", DadosEstaticos.teclado);
									if(quadro.verificaDisponibilidade(x-1, y-1)) { //verifico a minha propria disponibilidade
										quadro.inserirDadosX(x-1, y-1); //insiro os dados no objecto
										ligacao.enviaMensagem(enviaMensagemRedeJogada("move", Integer.toString(x), Integer.toString(y)));
										quadro.lista();
										tempoMinhaJogada.setFim();
										Aviso.tempoJogada(tempoMinhaJogada.tempoJogada());
										tempoJogada += tempoMinhaJogada.execucao();
										
										jogadaInterna++;
										System.out.println("Tempo total das Jogadas do Jogador "+DadosEstaticos.utilizador1+" \n "+ TempoExecucao.conversorTempo(tempoJogada));
										System.out.println("Tempo medio das Jogadas do Jogador "+DadosEstaticos.utilizador1+" \n "+ TempoExecucao.conversorTempo(tempoJogada/jogadaInterna));
										Aviso.aguardaJogada();
										return 0;
									}else {Aviso.localOcupado();}
								}else {
									tempoJogoTotal.setFim();
									Aviso.tempoTotalJogada(tempoJogoTotal.tempoJogada());
									gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, false, tempoJogoTotal.execucao());
									ligacao.enviaMensagem(enviaMensagemRedeCump("withdraw"));
									return 0;
								}
							}
							
						}
					}
				}else { //PARTE APENAS PARA O CLIENTE....................
					tempoMinhaJogada = new TempoExecucao();
					
					
					while(true) { //envia a sua jogada para o cliente
						System.out.println(quadro.dica());
						if(continuaJogada()) {
							
							x = Principal.pedeDadosInteiro("Insira a linha a jogar\n", DadosEstaticos.teclado);
							y = Principal.pedeDadosInteiro("Insira a coluna a jogar\n", DadosEstaticos.teclado);
							if(quadro.verificaDisponibilidade(x-1, y-1)) { //verifico a minha propria disponibilidade
								quadro.inserirDadosX(x-1, y-1); //insiro os dados no objecto
								ligacao.enviaMensagem(enviaMensagemRedeJogada("move", Integer.toString(x), Integer.toString(y)));
								quadro.lista();
								tempoMinhaJogada.setFim();
								Aviso.tempoJogada(tempoMinhaJogada.tempoJogada());
								tempoJogada += tempoMinhaJogada.execucao();
								
								jogadaInterna++;
								System.out.println("Tempo total das Jogadas do Jogador "+DadosEstaticos.utilizador1+" \n "+ TempoExecucao.conversorTempo(tempoJogada));
								System.out.println("Tempo medio das Jogadas do Jogador "+DadosEstaticos.utilizador1+" \n "+ TempoExecucao.conversorTempo(tempoJogada/jogadaInterna));
								Aviso.aguardaJogada();
								return 0;
							}else {Aviso.localOcupado();}
						}else {
							tempoJogoTotal.setFim();
							Aviso.tempoTotalJogada(tempoJogoTotal.tempoJogada());
							gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, false, tempoJogoTotal.execucao());
							ligacao.enviaMensagem(enviaMensagemRedeCump("withdraw"));
							return 0;
						}
					}
				}
				
			}
			if(arraySMS[1].equals("withdraw") && arraySMS[2].equals("ack")) { //caso seja o servidor a desistir..
				if(serverActivo) {
					ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("new", "?"));
					Aviso.aguardaEscolha();
				}
				else {
					ligacao.enviaMensagem(enviaMensagemRedeCump("ready"));
				}
				return 0;
			}
			
			if(arraySMS[1].equals("status") && arraySMS[2].equals("ok")) {
				//pedir para se quer reniciar o jogo
				ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("new", "?")); //verificar se quer continuar a jogar..
				return 0;
			}
			if(arraySMS[1].equals("new") && arraySMS[2].equals("y")) {
				return 1; //significa que vai iniciar novamente no metodo servidor ON de modo recursivo
			}if(arraySMS[1].equals("new") && arraySMS[2].equals("n")) {
				ligacao.enviaMensagem(enviaMensagemRedeCump("bye"));
				return 2; //termina a ligação de rede com o cliente
			}
			
			//*************************parte de resposta para continuação do cliente********************
			
			if(arraySMS[1].equals("new") && arraySMS[2].equals("?")) {
				while(true) {
					System.out.println("1 - Deseja Realizar novo jogo?\n2 - Terminar ligação em rede\n");
					int opcao = Principal.pedeDadosInteiro("Insira a sua opção", DadosEstaticos.teclado);
					switch(opcao) {
						case 1: ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("new", "y")); return 1;
						case 2: ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("new", "n")); return 0;
							default: ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("new", "n")); return 0;
					}
				}
			}
			if(arraySMS[1].equals("status") && arraySMS[2].equals("lose")) {
				Aviso.ganhouJogo();
				tempoJogoTotal.setFim();
				Aviso.tempoTotalJogada(tempoJogoTotal.tempoJogada());
				gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, true, tempoJogoTotal.execucao());
				if(Jogo.notificacaoVitoria(gestorUtilizadores.getJogador(DadosEstaticos.utilizador1).getNumeroVitorias()))
					System.out.println("PARABÉNS!! Algomeraste mais 10 vitórias no teu histórico!!\n");
				ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("status", "ok"));
				return 0;
			}
			if(arraySMS[1].equals("status") && arraySMS[2].equals("win")) {
				Aviso.perdeuJogo();
				tempoJogoTotal.setFim();
				Aviso.tempoTotalJogada(tempoJogoTotal.tempoJogada());
				gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, false, tempoJogoTotal.execucao());
				ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("status", "ok"));
				return 0;
			}
			
			if(arraySMS[1].equals("status") && arraySMS[2].equals("draw")) {
				Aviso.empatouJogo();
				tempoJogoTotal.setFim();
				Aviso.tempoTotalJogada(tempoJogoTotal.tempoJogada());
				gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, false, tempoJogoTotal.execucao());
				ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("status", "ok"));
				return 0;
			}
			
			
			
			
			//******************************************************************************************
			
			
		}
		//------------------------------------------------------------------------------------
		
		//mensagens com tamanho 4 recebidas, isto são os movimentos do cliente
		if(arraySMS.length == 4) {
			if(arraySMS[1].equals("move")) {
				x = Integer.parseInt(arraySMS[2]); //linha
				y = Integer.parseInt(arraySMS[3]); //coluna
				if(quadro.verificaDisponibilidade(x-1, y-1)) { //verifico e insiro os dados no objecto de jogo
					quadro.inserirDadosO(x-1, y-1);
					quadro.lista();
					ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("result", "valid"));
					tempoJogadaResp.setFim();
					Aviso.tempoJogadaResp(tempoJogadaResp.tempoJogada());
					System.out.println("Jogada nº " + jogada + "\n");
					return 0;
				}else {
					ligacao.enviaMensagem(enviaMensagemRedeTipoInfo("result", "notvalid"));
					return 0;
				}
			}
		}
		
		//_------------------------------------------------------------------------
		
		
	return 0;	
	}

	/**
	 * Metodo responsavel pela gestão de jogo do lado de cliente, começando por solicitar o ip e o porto de ligação
	 * @param gestorUtilizadores objecto para gravar e actualizar dados estatisticos relativos ao jogador cliente
	 */
	public static void clienteON(GestorUtilizadores gestorUtilizadores) { //gestor do cliente
			int transmite = 0;
			serverActivo = false;
			jogadaInterna = 0;
			if(ligacao==null) { //se esta a null quer dizer que ainda não tem nenhuma....
				while(true) {
					String ip = Principal.pedeDadosString("Insira o Ip do servidor para conexão", DadosEstaticos.teclado);
					String porta = Principal.pedeDadosString("Insira a porta do servidor para conexão", DadosEstaticos.teclado);
					ligacao = new Conexao(ip, Integer.parseInt(porta));
					if(ligacao.getCliente() != null) {
						break; //significa que a ligação está realizada
					}else {
						Aviso.avisoErro(); //erro para tentar de novo
					}
				}
			
				while(true) {
					String [] call = conversorMensagem(ligacao.recebeMensagem());
					if(call[1].equals("hello")) {
						DadosEstaticos.utilizador2 = call[0]; // sacar o nome do utilizador remoto
						System.out.println("Ligação Iniciada com o Servidor "+ DadosEstaticos.utilizador2 + "\n");
						ligacao.enviaMensagem(enviaMensagemRedeCump("hello"));
						break; //parar o ciclo que esta para loop infinito
					}
					
				}
			}
			TempoExecucao tempoJogo = new TempoExecucao();
			tempoJogoTotal = new TempoExecucao();
			Jogo quadro = new Jogo(); //antes de tudo listar o quadro vazio..
			
			while(transmite == 0) {
				
				transmite = jogoOnlineServer(quadro, ligacao.recebeMensagem(), gestorUtilizadores);
			}
			if(transmite == 1) {
				tempoJogo.setFim();
				clienteON(gestorUtilizadores); //uso de recursividade, problema com a memoria mas é opção para manter tudo contectado e não reniciar de novo...
			}if(transmite == 2) {
				System.out.println("Conexão terminada com sucesso..");
				tempoJogo.setFim();
				ligacao.fechaConexao();
				ligacao = null; //para que se na mesma utilização voltar a usar ele esteja mesmo a null
			}if(transmite == -1) {
				tempoJogo.setFim();
				ligacao.fechaConexao();
				System.out.println("Conexão interrompida..");
				ligacao = null;
			}
		}
			
			
	
	
	/**
	 * Metodo que converte a mensagem para o formato no protocolo pedido, neste caso para 2 campos, sendo o primeiro já preenchido através do nick logado
	 * @param aMensagem mensagem a enviar
	 * @return Objecto String com mensagem já formatada na forma correcta do protocolo de comunicação requisitado
	 */
	private static String enviaMensagemRedeCump(String aMensagem) {
		return "<" + DadosEstaticos.utilizador1 + "> <" + aMensagem + ">;";
	}
	
	/**
	 * Metodo que converte a mensagem para o formato no protocolo pedido, neste caso para 3 campos, sendo o primeiro já preenchido através do nick logado
	 * @param aTipo objecto string com o tipo de mensagem (ex result)
	 * @param aValor objecto com a mensagem a enviar ou valor (ex ack)
	 * @return
	 */
	private static String enviaMensagemRedeTipoInfo(String aTipo, String aValor) {
		return "<" + DadosEstaticos.utilizador1 + "> <" + aTipo + "> <" + aValor + ">;";
	}
	
	/**
	 * Metodo que converte a mensagem para o formato no protocolo pedido, neste caso para 4 campos, sendo o primeiro já preenchido através do nick logado
	 * Este metodo é especificamente usado para o envio de mensagens com as jogadas realizadas
	 * @param aTipo objecto string com o tipo de mensagem (ex move)
	 * @param aLinha objecto string com localização da linha onde jogar
	 * @param aColuna objecto string com localização da coluna onde jogar
	 * @return String com mensagem formatada no protocolo requisitado para enviar de forma correcta
	 */
	private static String enviaMensagemRedeJogada(String aTipo, String aLinha, String aColuna) {
		return "<" + DadosEstaticos.utilizador1 + "> <" + aTipo + "> <" + aLinha + "> <" + aColuna + ">;";
	}
	
	/**
	 * Metodo utilizado para converter a mensagem recebida pelo jogador remoto de forma a que esta seja posta em um array de
	 * strings, estando cada parte da mensagem de forma limpa num dos indexs
	 * @param aMensagem recebe string com mensagem formatada enviada pelo jogador remoto
	 * @return
	 */
	private static String[] conversorMensagem(String aMensagem) {
		
		aMensagem = aMensagem.replace(";","").trim();
		
		String [] envio = aMensagem.split(">");
		for (int i = 0; i < envio.length; i++) {
			envio[i] = envio[i].replace("<", " ").replace(">"," ").trim();
		}
		return envio;
	}
	

	
	/**
	 * Metodo que opciona o utilizador se quer continuar a jogar ou se tenciona desistir do jogo dando a vitoria ao seu adversário
	 * @return booleano com resultado da escolha do utilizador
	 */
	private static boolean continuaJogada() {
		System.out.println("1 - Continuar a Jogar\n2 - Desistir do Jogo\n");
		int opcao = Principal.pedeDadosInteiro("Insira Opcao\n", DadosEstaticos.teclado);
		switch(opcao) {
			case 1: return true; //sim continua
			case 2:	return false; //false se nao quer continuar
				default: return true; //continua por defeito
		}
		
	}
	
	
}
