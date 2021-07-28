package Jogo;
import PrincipalMain.*;
import java.util.*;
import Simulador.*;
import Utilizadores.*;
/**
 * Class utilizada para a realização de jogos com natureza local, utilizador vs utilizador e utilizador vs comp
 * @author Jorge Martins
 *	Class utilizada para a realização de jogos com natureza local, utilizador vs utilizador e utilizador vs comp
 */
public class JogarLocal {
	
	/**
	 * Metodo responsavel para jogo local de utilizador vs utilizador
	 * @param gestorJogos objecto responsavel para guardar jogos para simulador
	 * @param gestorUtilizadores objecto responsavel por guardar e actualizar dados estatisticos do nick/jogador
	 * @return booleano com resultado da operação
	 */
	public static boolean jogarLocal(GestorJogosGravados gestorJogos, GestorUtilizadores gestorUtilizadores) {
		Jogo quadro = new Jogo();
		loginUtilizador2();
		if(!DadosEstaticos.utilizador2.equals("Anonimo")) {
			if(!gestorUtilizadores.verificaNick(DadosEstaticos.utilizador2)) {
				gestorUtilizadores.addUtilizadores(new Utilizadores(DadosEstaticos.utilizador2));
			}
		}
		JogoGravado gravador = new JogoGravado(DadosEstaticos.utilizador1, DadosEstaticos.utilizador2, new Date());
		System.out.println("Jogador 1: "+DadosEstaticos.utilizador1+" joga com X "+
		"Jogador 2: "+DadosEstaticos.utilizador2+" joga com O");
		int jogada = 1; // jogada impar para o X jogada par para o O
		System.out.printf("Primeira jogada é com X\n");
		TempoExecucao tempoJogo = new TempoExecucao();
		long tempo1 = 0;
		long tempo2 = 0;
		int jogada1 = 1, jogada2 = 1;
		while(true) {
			TempoExecucao tempoJogada = new TempoExecucao();
			System.out.println("Jogada nº"+jogada+"\n");
			int x = Principal.pedeDadosInteiro("Insira a linha a jogar\n", DadosEstaticos.teclado);
			int y = Principal.pedeDadosInteiro("Insira a coluna a jogar\n", DadosEstaticos.teclado);
			tempoJogada.setFim();
			if(x <=3 && x > 0 && y <= 3 && x > 0) {
				if(quadro.verificaDisponibilidade(x-1, y-1)) {
					if(jogada % 2 == 0) {
						quadro.inserirDadosO(x-1, y-1);
						Jogo quadroPar = new Jogo(quadro.getQuadro());
						gravador.adicionaMovimento(quadroPar.getQuadro());
						tempo1 += tempoJogada.execucao();
						System.out.println("Tempo total das Jogadas do Jogador "+DadosEstaticos.utilizador2+" \n "+ TempoExecucao.conversorTempo(tempo1));
						System.out.println("Tempo medio das Jogadas do Jogador "+DadosEstaticos.utilizador2+" \n "+ TempoExecucao.conversorTempo(tempo1/jogada1));
						jogada1++;
						
					}else {
						quadro.inserirDadosX(x-1, y-1);
						Jogo quadroImpar = new Jogo(quadro.getQuadro());
						gravador.adicionaMovimento(quadroImpar.getQuadro());
						tempo2 += tempoJogada.execucao();
						System.out.println("Tempo total das Jogadas do Jogador "+DadosEstaticos.utilizador2+" \n "+ TempoExecucao.conversorTempo(tempo2));
						System.out.println("Tempo medio das Jogadas do Jogador "+DadosEstaticos.utilizador2+" \n "+ TempoExecucao.conversorTempo(tempo2/jogada2));
						jogada2++;
					}
					jogada++;
				}else {
					System.out.println("Localização Ocupada por Outro jogador - Jogue Novamente!\n");
				}
			}
			System.out.println("Tempo da Jogada \n"+tempoJogada.tempoJogada()+"\n"); //para demonstrar info acerca do tempo que demorou a realizar a jogada
			quadro.lista();
			if(quadro.checkaQuadro()) {
				if((jogada-1) % 2 == 0) {
					tempoJogo.setFim();
					System.out.println("Jogador "+DadosEstaticos.utilizador2+" ganhou!\n");
					gestorJogos.adicionarJogoGravado(gravador);
					if(gestorUtilizadores.verificaNick(DadosEstaticos.utilizador1))
						gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, false, tempoJogo.execucao()); //actualizar os dados do utilizador
					if(gestorUtilizadores.verificaNick(DadosEstaticos.utilizador2))
						gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador2, true, tempoJogo.execucao());
					if(Jogo.notificacaoVitoria(gestorUtilizadores.getJogador(DadosEstaticos.utilizador2).getNumeroVitorias()))
						System.out.println("PARABÉNS!! Algomeraste mais 10 vitórias no teu histórico!!\n");
					System.out.println("Tempo de Jogo"+TempoExecucao.conversorTempo(tempoJogo.execucao())+"\n");
					return true;
				}else {
					tempoJogo.setFim();
					System.out.println("Jogador "+DadosEstaticos.utilizador1+" ganhou!\n");
					gestorJogos.adicionarJogoGravado(gravador);
					if(gestorUtilizadores.verificaNick(DadosEstaticos.utilizador1))
						gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, true, tempoJogo.execucao()); //actualizar os dados do utilizador
					if(gestorUtilizadores.verificaNick(DadosEstaticos.utilizador2))
						gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador2, false, tempoJogo.execucao());
					if(Jogo.notificacaoVitoria(gestorUtilizadores.getJogador(DadosEstaticos.utilizador1).getNumeroVitorias()))
						System.out.println("PARABÉNS!! Algomeraste mais 10 vitórias no teu histórico!!\n");
					System.out.println("Tempo de Jogo"+TempoExecucao.conversorTempo(tempoJogo.execucao())+"\n");
					return true;
				}
			}
			if(jogada == 10) {
				System.out.println("Numero de jogadas Esgotadas, Jogo Empatado!\n");
				gestorJogos.adicionarJogoGravado(gravador);
				gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador1, false, tempoJogo.execucao());
				gestorUtilizadores.actualizarDados(DadosEstaticos.utilizador2, false, tempoJogo.execucao());
				System.out.println("Tempo de Jogo"+TempoExecucao.conversorTempo(tempoJogo.execucao())+"\n");
				return true;
			}
		
			
		}
	}

	/**
	 * Metodo responsavel pela jogabilidade entre utilizador e computador.
	 * @return resultado booleano da operação
	 */
	public static boolean jogarPC() {
		Jogo quadro = new Jogo();
		int jogada = 1; // jogada impar para o X jogada par para o O
		if(verificarJogoGuardado()) {
			System.out.println("Tem um jogo a decorrer, deseja continuar?");
			if(continuaJogada()) {
				quadro.setQuadro(DadosEstaticos.quadro);
				quadro.lista();
			}
		}
		System.out.printf("A jogada com X é do Jogador\n");
		while(true) {
			int x = 0;
			int y = 0;
			if(jogada % 2 != 0) {
				if(continuaJogada()) {
					x = Principal.pedeDadosInteiro("Insira a linha a jogar\n", DadosEstaticos.teclado);
				 	y = Principal.pedeDadosInteiro("Insira a coluna a jogar\n", DadosEstaticos.teclado);
				}else {
					DadosEstaticos.quadro = quadro.getQuadro();
					return true;
				}
				}else {
					System.out.println("Jogada do Computador");
					boolean test = true;
					do {
						for(int i=1; i<4; ++i) {
							for(int z=1; z<4; ++z) {
								x = i;
								y = z;
								if(quadro.verificaDisponibilidade(x-1, y-1)) { // para não estar a gerar mensagens ao computador de repetir
									test = false;
									break;
								}
							}
							if(!test) {
								break;
							}
						}
							
					}while(test);
			}
			if(x <=3 && x > 0 && y <= 3 && y > 0) {
				if(quadro.verificaDisponibilidade(x-1, y-1)) {
					if(jogada % 2 == 0) {
						quadro.inserirDadosO(x-1, y-1);
					}else {
						quadro.inserirDadosX(x-1, y-1);
					}
					jogada++;
				}else {
					System.out.println("Localização Ocupada por Outro jogador - Jogue Novamente!\n");
				}
			}
			quadro.lista();
			if(quadro.checkaQuadro()) {
				if((jogada-1) % 2 == 0) {
					System.out.println("Computador ganhou!\n");
					reniciarArray();
					return true;
				}else {
					System.out.println("Jogador X ganhou!\n");
					reniciarArray();
					return true;
				}
			}
			if(jogada == 10) {
				System.out.println("Numero de jogadas Esgotadas, Jogo Empatado!\n");
				reniciarArray();
				return true;
			}
		
			
		}
	}

	/**
	 * Metodo responsavel para reniciar o array, deixando este vazio..
	 */
	private static void reniciarArray() {
		for(int i=0; i<3; ++i) {
			DadosEstaticos.quadro[i][0]="";
			DadosEstaticos.quadro[i][1]="";
			DadosEstaticos.quadro[i][2]="";
		}
	}
	
	/**
	 * Metodo que implementa um menu para a cada jogada questionar o utilizador se este deseja desistir do jogo ou continuar a jogar
	 * @return resultado da operação 
	 */
	private static boolean continuaJogada() {
		System.out.println("1 - Continuar a Jogar\n2 - Sair\n");
		int opcao = Principal.pedeDadosInteiro("Insira Opcao\n", DadosEstaticos.teclado);
		switch(opcao) {
			case 1: return true; //sim continua
			case 2:	return false; //false se nao quer continuar
				default: return true; //continua por defeito
		}
		
	}
	
	/**
	 * Metodo que verifica se existe algum jogo guardado entre utilizador e computador para que este continue a jogar 
	 * se assim o desejar
	 * @return resultado da operação em booleano
	 */
	private static boolean verificarJogoGuardado() {
		for(int i=0; i<3; ++i) {
			if(DadosEstaticos.quadro[i][0].length()>0 || DadosEstaticos.quadro[i][1].length()>0 || DadosEstaticos.quadro[i][2].length()>0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Metodo responsavel pelo login do segundo utilizador para que este seja logado no jogo local de jogador vs jogador
	 */
	private static void loginUtilizador2() {
		System.out.println("LOGIN DO UTILIZADOR 2...\n1 - Entrar com o seu Nick \n2 - Entrar como anonimo");
		int opcao = Principal.pedeDadosInteiro("Insira a sua opcao", DadosEstaticos.teclado);
		switch(opcao) {
		case 1: DadosEstaticos.utilizador2 = Principal.pedeDadosString("Insira o seu Nick", DadosEstaticos.teclado); break;
		case 2: DadosEstaticos.utilizador2 = "Anonimo"; break;
			default: DadosEstaticos.utilizador2 = "Anonimo"; break;
		}
	}
	
	

}
