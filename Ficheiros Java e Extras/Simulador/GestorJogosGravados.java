package Simulador;
import java.io.Serializable;
import java.util.*;

import PrincipalMain.DadosEstaticos;
import Aviso.*;
import Jogo.*;
/**
 * Classe responsavel por gravar os jgoos realizados localmente entre utilizadores, esta classe gere objectos do tipo da classe JogoGravado
 * que armazena as jogadas realizadas numa partida.
 * @author Jorge Martins
 *
 */
public class GestorJogosGravados implements Serializable{
	
	private ArrayList <JogoGravado> listaJogosGravados;
	/**
	 * Construtor da classe de forma a instanciar o ArrayList que armazena os objectos da classe JogoGravado
	 */
	public GestorJogosGravados() {
		listaJogosGravados = new ArrayList <JogoGravado> ();
	}
	/**
	 * Metodo responsavel por adicionar novos objectos da classe JogoGravado no ArrayList
	 * @param aJogo recebe um objecto do tipo JogoGravado para ser adicionado no ArrayList
	 */
	public void adicionarJogoGravado(JogoGravado aJogo) {
		listaJogosGravados.add(aJogo);
	}
	
	/**
	 * Metodo responsavel por listar os objectos da classe JogoGravado presentes no ArrayList da classe GestorJogosGravados 
	 */
 public void listarJogos() {
		if(listaJogosGravados != null){
			if(listaJogosGravados.size() != 0) {
			Iterator <JogoGravado> tabela = listaJogosGravados.iterator();
			String envio = "";
			JogoGravado auxiliar;
			int contador = 0, jogo = 1;
		       while(tabela.hasNext()) {
			       auxiliar = tabela.next();
			       envio += "Jogo: "+jogo+" Jogador 1: "+auxiliar.getNick1()+ " Jogador 2: "+auxiliar.getNick2()+"Data: "+auxiliar.getData()+"\n";
			       contador++;
			       jogo++;
			    
			       if(contador == 10) {
			    	   DadosEstaticos.listadorMaster(" Lista Jogos \n" + envio + "\n", contador); 
			    	   contador = 0;
			    	   envio = "";
			       }
		       }
		       if(contador > 0) {
		    	   DadosEstaticos.listadorMaster(" Lista Jogos \n" + envio + "\n", contador);
		       }
		}else {
			Aviso.semDados();
			Aviso.continua(DadosEstaticos.teclado);
		}
		}else {
			Aviso.semDados();
			Aviso.continua(DadosEstaticos.teclado);
		}
	}
	
 /**
  * Metodo responsavel por listar cada jogada realizada no jogo, assim simulando o mesmo.
  * @param numJogo recebe o inteiro indicando o numero do jogo a listar
  */
 public void listarMovimentosJogo(int numJogo) {
	 JogoGravado jogo = listaJogosGravados.get((numJogo-1)); //na lista começam em 1 e no array estao no indice 0
	 if(jogo.getMovimentos() != null){
			Iterator <String[][]> tabela = jogo.getMovimentos().iterator();
			System.out.println("Jogador 1: "+jogo.getNick1()+" joga com X "+
	    			"Jogador 2: "+jogo.getNick2()+" joga com O \n");
			String[][] auxiliar;
			Jogo quadro = new Jogo();
			int contador = 1;
		       while(tabela.hasNext()) {
			       auxiliar = tabela.next();
			       quadro.setQuadro(auxiliar);
			       System.out.println("Jogada nº: "+contador+"\n");
			       quadro.lista();
			       contador++;
			       Aviso.continuaLista(DadosEstaticos.teclado);
		       }
		}
		
 }
 
 /**
  * Metodo responsavel para verificar se o jogo esta dentro da lista, menor que 0 ou maior que o tamanho do array indica que o numero escolhido pelo
  * utilizador está fora dos indices do array.
  * @param numJogo recebe inteiro com o numero de jogo a verificar
  * @return booleano com resultado da operação
  */
 public boolean verificaJogo(int numJogo) {
	 if(numJogo < 0) {
		 return true;
	 }
	 if(listaJogosGravados.size() >= numJogo) {
		 return true;
	 }return false;
 }

}
