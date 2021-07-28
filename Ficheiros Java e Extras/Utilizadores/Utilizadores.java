package Utilizadores;

import java.io.Serializable;
import java.util.*;

/**
 * Classe responsavel por conter informações acerca dos jogadores/utilizadores desta aplicação, sendo que em cada objecto desta classe estão guardados 
 * alem dos nomes(nicks) também se encontram os seus dados estatiticos relativos aos jogos realizado na aplicação
 * @author Jorge Martins
 *
 */
public class Utilizadores implements Comparable <Utilizadores>, Serializable{
	private String nick;
	private long tempoJogo = 0;
	private int numeroJogos = 0;
	private int numeroVitorias = 0;
	
	private static int tipoOrdena = 1;
	private static int ordemCrescimento = 1;
	/**
	 * Construtor da classe que instancia a String nick
	 * @param nick recebe objecto String com nome do jogador
	 */
	public Utilizadores(String nick) {
		this.nick = nick;
	}
	/**
	 * Metodo responsavel por devolver o nome de jogador 
	 * @return string com nome/nick do jogador 
	 */
	public String getNick() {
		return nick;
	}
	/**
	 * Metodo responsavel por devolver o total de tempo de jogo que o jogador tem.
	 * @return objecto string com os dados relativos ao tempo de jogo acumulado
	 */
	public String getTempoJogo() {
		
		return "Tempo de Jogo: "+tempoJogo+" Milisegundos ("+ (int) Math.round((tempoJogo*0.001))+" Segundos "+ 
				(int) Math.round((tempoJogo*0.000016666666666667))+
				" Minutos "+(int) Math.round(((tempoJogo*2.7777777778*(Math.pow(10, -7)))))+" Horas )";
	}
	/**
	 * Metodo que devolve o tempo de jogo acumulado em formato long (milisegundos)
	 * @return long com milisegundos acumulados
	 */
	public long getTempo() {
		return tempoJogo;
	}
	/**
	 * Metodo que devolve o numero de jogos realizados pelo utilizador
	 * @return inteiro com o numero de jogos realizados
	 */
	public int getNumeroJogos() {
		return numeroJogos;
	}
	
	/**
	 * Metodo que devolve o numero de vitorias que o utilizador tem
	 * @return inteiro com o numero de vitorias
	 */
	public int getNumeroVitorias() {
		return numeroVitorias;
	}
	/**
	 * Metodo que adiciona tempo acumulado ao jogador
	 * @param aTempo recebe long com os milisegundos a adicionar ao jogador
	 */
	public void adicionaTempo(long aTempo) {
		tempoJogo += aTempo;
	}
	/**
	 * Metodo que incrementa o numero de jogos realizados pelo jogador
	 */
	public void adicionaNumeroJogo() {
		numeroJogos++;
	}
	/**
	 * Metodo que incrementa o numero de vitorias do jogador
	 */
	public void adicionaNumeroVitorias() {
		numeroVitorias++;
	}
	/**
	 * Metodo standard da classe toString para que se esta for chamada a ser escrita devolva um objecto String com os seus dados
	 */
	public String toString() {
		return "Nick: "+nick+" Numero de Jogos: "+numeroJogos+" Numero vitorias: "+numeroVitorias+" Tempo de Jogo: "+tempoJogo+" ";
	}
	
	/**
	 * Metodo que indica a ordenação que o ArrayList vai ter
	 * @param novaOrdem inteiro com a opção da nova ordem
	 */
	public static void setTipoOrdena(int novaOrdem){ 
		tipoOrdena = novaOrdem;
	}
	
	/**
	 * Metodo responsavel pela decisão do tipo de crescimento que terá o ArrayList (crescente ou decrescente)
	 * @param novoCresce inteiro com 1 ou -1 para ser multiplicado assim manipulando a listagem 
	 */
	public static void setTipoCrescimento(int novoCresce){ //para ser a crescer ou decrescer
		ordemCrescimento = novoCresce;
	}
	
	/**
	 * Metodo responsavel pelo sort do ArrayList, nele se encontram as opções de ordenação dos objectos.
	 */
	public int compareTo(Utilizadores aObj) {    // 2.º passo ordenamento (esta a por por ordem crescente)
	    switch(tipoOrdena) {
	    case 1: return ((nick.compareToIgnoreCase(aObj.getNick()))*ordemCrescimento); 
	    
	    case 2: if(tempoJogo > aObj.getTempo()) {
	    			return (1 * ordemCrescimento);
	    		}else {return (-1 * ordemCrescimento);}
	    
	    case 3:  if(numeroJogos > aObj.getNumeroJogos()) {
					return (1 * ordemCrescimento);
				}else {return (-1 * ordemCrescimento);}
	    
	    case 4: if(numeroVitorias > aObj.getNumeroVitorias()) {
					return (1 * ordemCrescimento);
				}else {return (-1 * ordemCrescimento);}
	    }
	    return 1;
	   
	  }
	
	
	

}
