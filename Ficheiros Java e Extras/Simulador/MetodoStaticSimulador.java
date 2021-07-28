package Simulador;

import Jogo.JogarLocal;
import PrincipalMain.*;
import Aviso.*;
import logs.*;
/**
 * Classe responsavel pela intera��o com o utilizador no submenu de Simulador de jogo
 * @author Jorge Martins
 *
 */
public class MetodoStaticSimulador {
	/**
	 * Metodo responsavel por agregar os metodos de menu de Simulador
	 * @param gestorJogos objecto GestorJogosGravados que contem as informa��es de simula��es de jogos
	 * @param log objecto GestorMovimentos para gravar e actualizar os movimentos do utilizador
	 */
	public static void menuSimulador(GestorJogosGravados gestorJogos, GestorMovimentos log) {
			int opcao = 0;
			while(opcao != 3) {
				// apresentar menu
				apresentaMenuJogo();
				// escolha op�ao
				opcao = Principal.pedeDadosInteiro("Escolha a op��o do menu inserindo um numero inteiro da op�ao", DadosEstaticos.teclado);
				//executar op��o
				executaOpcaoSimulador(opcao, gestorJogos, log);
			}
		}

		/**
		 * Metodo responsavel por demonstrar as op��es disponiveis ao utilizador
		 */
		private static void apresentaMenuJogo() {
			System.out.println("1 - Escolher Simula��o\n" +
					"2 - Listar Simula��es \n"+
					"3 - Sair"
					);
					
		}
		/**
		 * Metodo responsavel por executar a op��o escolhida pelo utilizador no menu de simulador
		 * @param aOpcao inteiro com a op��o escolhida pelo utilizador
		 * @param gestorJogos objecto responsavel pelo tratamento de dados relativos a jogos gravados
		 * @param log objecto responsavel pela adi��o e actualiza��o dos movimentos de utilizador no software
		 */
		private static void executaOpcaoSimulador(int aOpcao, GestorJogosGravados gestorJogos, GestorMovimentos log) {
			switch(aOpcao) {
			case 1: int numJogo = Principal.pedeDadosInteiro("Insira o numero de jogo a Simular", DadosEstaticos.teclado);
					if(gestorJogos.verificaJogo(numJogo)) {
						gestorJogos.listarMovimentosJogo(numJogo);
					}else {Aviso.avisoErro();} log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Visualizar uma Simulacao de Jogo")); break;
			case 2: gestorJogos.listarJogos(); log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Listar jogos gravados no Sistema")); break;
			case 3: log.addMovimentos(new logs(DadosEstaticos.utilizador1, TempoExecucao.setData(), TempoExecucao.setHora(), "Saiu do menu Simulador")); break;
			default: System.out.println("Opcao Errada!\n");
			}
		}
	}


