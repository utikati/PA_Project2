package Utilizadores;
import java.io.Serializable;
import java.util.*;
import Aviso.*;
import PrincipalMain.DadosEstaticos;

/**
 * Classe responsavel pela gestão de utilizadores/jogadores da aplicação atraves da manipulação de objectos da classe Utilizadores
 * @author Jorge Martins
 *
 */
public class GestorUtilizadores implements Serializable{
	/**
	 * Arraylist responsavel por guardar todos os jogadores da aplicação
	 */
	public ArrayList <Utilizadores> listaJogadores = new ArrayList <Utilizadores>();
	/**
	 * Metodo responsavel por adicionar utilizadores no ArrayList listaJogadores da classe GestorUtilizadores
	 * @param utilizador objecto da classe Utilizadores
	 * @return booleano com resultado da operação
	 */
	public boolean addUtilizadores(Utilizadores utilizador){ //adicionar funcionarios
		if(listaJogadores != null){
			if(!DadosEstaticos.utilizador1.equals("Anonimo")) {
				return listaJogadores.add(utilizador);
			}else {return false;}
			
		}else{
			return false;
		}
	}
	/**
	 * Metodo responsavel por verificar a existencia de nick nos dados já guardados, para se seja decidido se este é adicionado ao ArrayList
	 * ou se o mesmo não é necessário adicionar
	 * @param aNick objecto string com nick do jogador 
	 * @return booleano com resultado da operação
	 */
	public boolean verificaNick(String aNick){ //verifica se já existe codigo interno de funcionario
		if(listaJogadores != null){
			Iterator <Utilizadores> tabela = listaJogadores.iterator();
			
			while(tabela.hasNext()) {
		        if(tabela.next().getNick().equals(aNick)){
		        	return true; //se existir devolve verdade
		      }
		    }
		}
	    return false;	 
	}
	/**
	 * Metodo responsavel por devolver um jogador presente no ArrayList listaJogadores da classe GestorUtilizadores
	 * @param aNick recebe objecto com nick do jogador a pesquisar
	 * @return objecto Utilizadores com resultado da pesquisa
	 */
	public Utilizadores getJogador(String aNick) {
		if(listaJogadores != null){
			Iterator <Utilizadores> tabela = listaJogadores.iterator();
			Utilizadores envio;
			while(tabela.hasNext()) {
				envio = tabela.next();
		        if(envio.getNick().equals(aNick)){
		        	return envio;
		      }
		    }
		}
		return new Utilizadores("100Nome");
	}
	
	/**
	 * Metodo responsavel por deixar a lista de jogadores por ordem descrecente pelo seu numero de vitorias assim percecionando qual jogador tem mais 
	 * vitorias para ao iniciar o software seja notificado
	 * @return booleano com resultado da operação
	 */
	public boolean verificaMax() {
		ordenaJogadores(4, -1); //ao meter em ordem decrescente nas vitorias o primeiro (index 0) é o que tem mais vitorias!
		if(listaJogadores.get(0).getNick().equals(DadosEstaticos.utilizador1) && listaJogadores.get(0).getNumeroVitorias() > 0) {
			return true;
		}
	    return false;
	}
	
	/**
	 * 
	 * Listar utilizadores sem o filtro do seu tipo de conta, aparecendo apenas os seus dados atraves
	 * de uma condição 
	 */
	public void listarUtilizadores() {
		
		if(listaJogadores != null){
			if(listaJogadores.size() != 0) {
			Iterator <Utilizadores> tabela = listaJogadores.iterator();
			String envio = "";
			Utilizadores auxiliar;
			int contador = 0;
		       while(tabela.hasNext()) {
			       auxiliar = tabela.next();
			       envio += "Nick: "+auxiliar.getNick() + " Numero de Jogos: "+auxiliar.getNumeroJogos()+" Vitorias: "+auxiliar.getNumeroVitorias()+" Tempo de Jogo: "+auxiliar.getTempoJogo()+"\n";
			       contador++;		
			    
			       if(contador == 10) {
			    	   DadosEstaticos.listadorMaster(" Lista Jogadores \n" + envio + "\n", contador); 
			    	   contador = 0;
			    	   envio = "";
			       }
		       }
		       if(contador > 0) {
		    	   DadosEstaticos.listadorMaster(" Lista Jogadores \n" + envio + "\n", contador);
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
	 * Metodo responsavel pela actualizado dos dados estatisticos de jogador
	 * @param nick objecto string com nick do jogador
	 * @param Vitoria booleano indicando se trata de vitoria (true) ou derrota (false)
	 * @param tempo long com o tempo em milisegundos para que este seja adicionado ao jogador 
	 */
	public void actualizarDados(String nick, boolean Vitoria, long tempo) { //adicionar pelo nick é melhor que criar um objecto apenas para o actualizar
		if(listaJogadores != null){
			Iterator <Utilizadores> tabela = listaJogadores.iterator();
			
			while(tabela.hasNext()) {
		        Utilizadores player = tabela.next();
		        if(player.getNick().equals(nick)){
		        	if(Vitoria) {
		        		player.adicionaNumeroVitorias();
		        	}
		        	player.adicionaNumeroJogo();
		        	player.adicionaTempo(tempo);
		      }
		    }
		} 
	}
	/**
	 * Metodo responsavel por ordenar o ArrayList listaJogadores de forma a que este fique ordenado como o utilizador decidiu
	 * @param aNovaOrdem inteiro com a escolha do case do switch que ordena na classe utilizadores
	 * @param aOrdemCrescimento inteiro com o qual vai multiplicar (1 para crescer e -1 para decrescer)
	 * @return booleano com resultado da operação
	 */
	public boolean ordenaJogadores(int aNovaOrdem, int aOrdemCrescimento) {
	    if (listaJogadores != null && listaJogadores.size() > 1) {
	      Utilizadores.setTipoOrdena(aNovaOrdem);
	      Utilizadores.setTipoCrescimento(aOrdemCrescimento);
	      Collections.sort(listaJogadores);     // 4.º passo ordenamento
	      return true;
	    }
	    return false;
	  }

	/**
	 * Metodo responsavel pela pesquisa de Utilizadores no ArrayList listaJogares da classe GestorUtilizadores
	 * @param nick recebe objecto string com nick do jogador a pesquisar
	 */
	public void pesquisarUtilizadores(String nick) {
		if(listaJogadores != null){
			if(listaJogadores.size() != 0) {
			Iterator <Utilizadores> tabela = listaJogadores.iterator();
			String envio = "";
			Utilizadores auxiliar;
			int contador = 0;
		       while(tabela.hasNext()) {
			       auxiliar = tabela.next();
			       if(auxiliar.getNick().contains(nick)) {
			    	   envio += "Nick: "+auxiliar.getNick() + " Numero de Jogos: "+auxiliar.getNumeroJogos()+" Vitorias: "+auxiliar.getNumeroVitorias()+" Tempo de Jogo: "+auxiliar.getTempoJogo()+"\n";
			    	   contador++;		
			       }
			       if(contador == 10) {
			    	   DadosEstaticos.listadorMaster(" Lista Jogadores \n" + envio + "\n", contador); 
			    	   contador = 0;
			    	   envio = "";
			       }
		       }
		       if(contador > 0) {
		    	   DadosEstaticos.listadorMaster(" Lista Jogadores \n" + envio + "\n", contador);
		       }else {
		    	   Aviso.semDados();
		    	   Aviso.continua(DadosEstaticos.teclado);
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
	
}
