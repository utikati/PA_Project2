package Utilizadores;

import PrincipalMain.DadosEstaticos;
import Simulador.GestorJogosGravados;
import PrincipalMain.*;
import Aviso.*;
/**
 * Classe responsavel pela interação com o utilizador nos aspectos de manuseamento de dados relativos aos jogadores.
 * @author Jorge Martins
 *
 */
public class MetodosStaticUtilizadores {
		/**
		 * Menu relativo a ordenar listagem de jogadores
		 * @param gestorUtilizadores objecto GestorUtilizadores responsavel pelos dados sobre utilizadores
		 */
	public static void ordenarLista(GestorUtilizadores gestorUtilizadores) {
			int opcao = 0;
			while(opcao != 9) {
				// apresentar menu
				apresentaMenuOrdem();
				// escolha opçao
				opcao = Principal.pedeDadosInteiro("Escolha a opção do menu inserindo um numero inteiro da opçao", DadosEstaticos.teclado);
				//executar opção
				executaOpcaoOrdem(opcao, gestorUtilizadores);
			}
		}

		/**
		 * Metodo de apresentação do conteudo e funcionalidades do menu de ordenação de lista
		 */
		private static void apresentaMenuOrdem() {
			System.out.println("Escolha a Ordem da Listagem\n"
				  + "1 - Ordem Alfabetica Crescente dos Nicks de Jogadores\n" +
					"2 - Ordem Alfabetica Decrescente dos Nicks de Jogadores \n"+
					"3 - Ordem Crescente de tempo de jogo\n"+
					"4 - Ordem Decrescente de tempo de jogo \n"+
					"5 - Ordem Crescente de numero de Jogos \n"+
					"6 - Ordem Decrescente de numero de Jogos \n"+
					"7 - Ordem Crescente de numero de Vitorias \n"+
					"8 - Ordem Decrescente de numero de Vitorias \n"+
					"9 - Sair\n"
					);
					
		}

		/**
		 * Metodo de execução da opção realizada pelo utilizador relativamente ao menu de ordenação da lista
		 * @param aOpcao inteiro com a opção escolhida
		 * @param gestorUtilizadores objecto GestorUtilizadores contem os dados sobre os utilizadores
		 */
		private static void executaOpcaoOrdem(int aOpcao, GestorUtilizadores gestorUtilizadores) {
			switch(aOpcao) {
			case 1: gestorUtilizadores.ordenaJogadores(1, 1); Aviso.operacaoSucesso(); break;
			case 2: gestorUtilizadores.ordenaJogadores(1, -1); Aviso.operacaoSucesso(); break;
			case 3: gestorUtilizadores.ordenaJogadores(2, 1); Aviso.operacaoSucesso(); break;
			case 4: gestorUtilizadores.ordenaJogadores(2, -1); Aviso.operacaoSucesso(); break;
			case 5: gestorUtilizadores.ordenaJogadores(3, 1); Aviso.operacaoSucesso(); break;
			case 6: gestorUtilizadores.ordenaJogadores(3, -1); Aviso.operacaoSucesso(); break;
			case 7: gestorUtilizadores.ordenaJogadores(4, 1); Aviso.operacaoSucesso(); break;
			case 8: gestorUtilizadores.ordenaJogadores(4, -1); Aviso.operacaoSucesso(); break;
			case 9: break;
			default: System.out.println("Opcao Errada!\n");
			}
		}
	
		/**
		 * Metodo responsavel por pedir ao utilizador (interagir) o nick a pesquisar e por conseguinte chamar o metodo da classe GestorUtilizadores
		 * que devolve o resultado perante a string inserida pelo utilizador
		 * @param gestorUtilizadores recebe objecto da classe GestorUtilizadores para manuseamento de dados relativos aos jogadores
		 */
	public static void pesquisaUtilizadorNick(GestorUtilizadores gestorUtilizadores) {
		String procura = Principal.pedeDadosString("Insira o nick a pesquisar", DadosEstaticos.teclado);
		gestorUtilizadores.pesquisarUtilizadores(procura);
	}

}
