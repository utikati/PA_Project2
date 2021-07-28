package Simulador;

import Jogo.JogarLocal;
import PrincipalMain.*;
import Aviso.*;
import logs.*;
/**
 * Classe responsavel pela interação com o utilizador no submenu de Simulador de jogo
 * @author Jorge Martins
 *
 */
public class MetodoStaticSimulador {
	/**
	 * Metodo responsavel por agregar os metodos de menu de Simulador
	 * @param gestorJogos objecto GestorJogosGravados que contem as informações de simulações de jogos
	 * @param log objecto GestorMovimentos para gravar e actualizar os movimentos do utilizador
	 */
	public static void menuSimulador(GestorJogosGravados gestorJogos, GestorMovimentos log) {
			int opcao = 0;
			while(opcao != 3) {
				// apresentar menu
				apresentaMenuJogo();
				// escolha opçao
				opcao = Principal.pedeDadosInteiro("Escolha a opção do menu inserindo um numero inteiro da opçao", DadosEstaticos.teclado);
				//executar opção
				executaOpcaoSimulador(opcao, gestorJogos, log);
			}
		}

		/**
		 * Metodo responsavel por demonstrar as opções disponiveis ao utilizador
		 */
		private static void apresentaMenuJogo() {
			System.out.println("1 - Escolher Simulação\n" +
					"2 - Listar Simulações \n"+
					"3 - Sair"
					);
					
		}
		/**
		 * Metodo responsavel por executar a opção escolhida pelo utilizador no menu de simulador
		 * @param aOpcao inteiro com a opção escolhida pelo utilizador
		 * @param gestorJogos objecto responsavel pelo tratamento de dados relativos a jogos gravados
		 * @param log objecto responsavel pela adição e actualização dos movimentos de utilizador no software
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


