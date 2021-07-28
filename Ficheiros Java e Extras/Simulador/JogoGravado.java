package Simulador;

import java.io.Serializable;
import java.util.*;

import Aviso.Aviso;
import Jogo.Jogo;
import PrincipalMain.DadosEstaticos;
/**
 * Classe responsavel na gravação dos movimentos realizados num jogo local entre utilizadores.
 * @author Jorge Martins
 *
 */
public class JogoGravado implements Serializable{
	private String nick1;
	private String nick2;
	private Date dataJogo;
	/**
	 * ArrayList utilizador para guardar os movimentos de jogo para mais tarde usar em simulação
	 */
	public ArrayList <String[][]> movimentosJogo = new ArrayList <String[][]> ();
	/**
	 * Construtor da classe JogoGravado, é iniciado com os nicks de ambos os jogadores e com a data de realização do jogo
	 * @param aNick1 objeto string com nick do jogador 1
	 * @param aNick2 objecto string com nick do jogador 2
	 * @param aDataJogo objecto data com indicação da data do inicio do jogo
	 */
	public JogoGravado(String aNick1, String aNick2, Date aDataJogo) {
		nick1 = aNick1;
		nick2 = aNick2;
		dataJogo = aDataJogo;
		
	}
	/**
	 * Metodo responsavel por adicionar objectos do tipo array String dentro do array list movimentosJogo que é responsavel por guardar todas as jogadas da partida
	 * @param mov recebe objecto do tipo array de String
	 */
	public void adicionaMovimento(String[][] mov) {
		movimentosJogo.add(mov);
	}
	/**
	 * Metodo responsavel por devolver o nick do jogador 1
	 * @return string com nick do jogador 1
	 */
	public String getNick1() {
		return nick1;
	}
	/**
	 * Metodo responsavel por devolver o nick do jogador 2
	 * @return string com nick do jogador 2
	 */
	public String getNick2() {
		return nick2;
	}
	/**
	 * Metodo responsavel por devolver a data do jogo realizado
	 * @return objecto do tipo data com data completa do jogo realizado
	 */
	public Date getData() {
		return dataJogo;
	}
	/**
	 * Metodo responsavel por devolver os movimentos de jogo como arraylist
	 * @return objecto ArrayList de objectos array String bidimensionais
	 */
	public ArrayList<String[][]> getMovimentos(){
		return movimentosJogo;
	}
	
	
	
}
