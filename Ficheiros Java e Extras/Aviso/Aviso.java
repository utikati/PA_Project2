package Aviso;

import java.util.Scanner;

import PrincipalMain.DadosEstaticos;
import PrincipalMain.*;

/**
 * Classe que apenas contem metodos de envio de aviso com System.out.println
 * @author Jorge Martins
 * Classe que apenas contem metodos de envio de aviso com System.out.println
 */
public class Aviso {
	/**
	 * Metodo de Aviso que indica que o local do quadro esta já preenchido
	 */
	public static void localOcupado() {
		System.out.println("O local que escolheu já se encontra preenchido \n");
	}
	/**
	 * Metodo de Aviso que indica o tempo total de jogo realizado
	 * @param tempoJogada objeto string com o valor do tempo jogado
	 */
	public static void tempoTotalJogada(String tempoJogada) {
		System.out.println("Tempo total de jogo "+ tempoJogada +"\n");
	}
	/**
	 * Aviso sobre o tempo que ja esta acumulado do jogador
	 * @param tempo objecto tipo string convertido já o tempo
	 */
	public static void tempoAcumulado(String tempo) {
		System.out.println("Tempo acumulado de jogadas "+ tempo +"\n");
	}
	
	/**
	 * Metodo de Aviso que indica o tempo de jogada contado
	 * @param tempoJogada string com o valor do tempo de jogada
	 */
	public static void tempoJogada(String tempoJogada) {
		System.out.println("Tempo de jogada "+ tempoJogada +"\n");
	}
	/**
	 * Metodo de Aviso que indica o tempo que em jogo em rede o utilizador remoto demorou a jogar
	 * @param tempoJogada objecto string com o valor do tempo que o jogador remoto demorou a jogar
	 */
	public static void tempoJogadaResp(String tempoJogada) {
		System.out.println("Tempo de jogada de resposta "+ tempoJogada +"\n");
	}
	
	
	/**
	 * Metodo de Aviso que indica ao utilizador que esta a aguardar a jogada do jogador remoto
	 */
	public static void aguardaJogada() {
		System.out.println("Aguarda jogada de "+DadosEstaticos.utilizador2+"...\n\n");
	}
	
	/**
	 * Metodo de Aviso que indica ao utilizador que está aguardar o jogador remoto pela sua escolha, caso queira continuar ou não o jogo, etc
	 */
	public static void aguardaEscolha() {
		System.out.println("Aguarda escolha de "+DadosEstaticos.utilizador2+"...\n\n");
	}
	/**
	 * Metodo de Aviso que indica no jogo em rede que o utilizador online na aplicação ganhou o jogo
	 */
	public static void ganhouJogo() {
		System.out.println("Ganhou o Jogo!!...\n\n");
	}
	/**
	 * Metodo de Aviso que indica no jogo em rede que o utilizador online na aplicação que empatou o jogo
	 */
	public static void empatouJogo() {
		System.out.println("Empatou o Jogo!!...\n\n");
	}
	/**
	 * Metodo de Aviso que indica no jogo em rede que o utilizador online na aplicação que perdeu o jogo
	 */
	public static void perdeuJogo() {
		System.out.println("Perdeu o Jogo!!..");
	}
	
	/**
	 * Aviso de erro
	 */
	public static void avisoErro() {
		System.out.println("Erro! Nao foi possivel realizar a sua acao!");
	}
	
	//mensagem de sucesso e insucesso
	  /**
	 * Aviso de operacao sucesso
	 */
	public static void operacaoSucesso(){
		  System.out.println("Operacao realizada com Sucesso\n");
	  }
	  
	  /**
	 * Aviso de operacao insucesso
	 */
	public static void operacaoInsucesso(){
		  System.out.println("Aconteceu um erro no decorrer da operaï¿½ï¿½o\n");
	  }
	  
	  /**
	 * Aviso sem dados
	 */
	public static void semDados(){
		  System.out.println("Sem dados\n");
	  }
	  
	  
	 //Metodo para continuar, faz uma pausa..
	 /**
	  * Aviso continuar para menu
	 * @param aTeclado recebe objecto da classe scanner
	 * Aviso continuar para menu
	 */
	public static void continua(Scanner aTeclado){
		 System.out.println("Clique enter para voltar ao menu");
			aTeclado.nextLine();
	 }
	 
	 /**
	  * Aviso proxima pagina
	 * @param aTeclado recebe objecto da classe scanner
	 * Aviso proxima pagina
	 */
	public static void continuaLista(Scanner aTeclado){
		 System.out.println("Clique enter para visualizar a proxima pagina");
			aTeclado.nextLine();
	 }
	 
	 /**
	  * Aviso fim de lista
	 * @param aTeclado recebe objecto da classe scanner
	 * Aviso fim de lista
	 */
	public static void continuaListaFim(Scanner aTeclado){
		 System.out.println("Fim de Lista clique enter..");
			aTeclado.nextLine();
	 }

}
