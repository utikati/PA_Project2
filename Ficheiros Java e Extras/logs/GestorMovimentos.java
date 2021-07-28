package logs;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import Aviso.Aviso;
import PrincipalMain.DadosEstaticos;
import Utilizadores.Utilizadores;

/**
 * Esta class ao contrario do habitual neste software não está a gerir uma outra class mas sim directamente a gerir os movimentos do utilizador, foi criada uma Arraylist
 * para que fosse adicionado cada movimento sempre no inicio do Array e assim ficar de forma da mais actual para a mais antiga.
 * A opção do uso de um arraylist é devido à sua utilidade para uso e manipulação de dados.
 * @author Jorge Martins
 * 
 * Esta class ao contrario do habitual neste software não está a gerir uma outra class mas sim directamente a gerir os movimentos do utilizador, foi criada uma Arraylist
 * para que fosse adicionado cada movimento sempre no inicio do Array e assim ficar de forma da mais actual para a mais antiga.
 * A opção do uso de um arraylist é devido à sua utilidade para uso e manipulação de dados.
 *
 */

public class GestorMovimentos implements Serializable{
	
	private ArrayList <logs> logMovimentos;
	
	/**
	 * Construtor de iniciação do Gestor de Movimentos inicia o seu arraylist vazio
	 */
	public GestorMovimentos(){
		logMovimentos = new ArrayList <logs>();
	}
	
	
	/**
	 * Metodo addMovimentos
	 * @param aMovimento recebe objecto da class String com descrição do movimento
	 */
	public void addMovimentos(logs aMovimento){
		logMovimentos.add(0, aMovimento); //adiciona no inicio da arrayList de strings o movimento feito
	}
	
	/**
	 * Metodo Listar Movimentos mostra de 10 em 10 paginas
	 * 
	 */
	public void listarMovimentos(){
		if(logMovimentos != null){
			if(logMovimentos.size() != 0) {
				Iterator <logs> tabela = logMovimentos.iterator();
				logs aux;
				String envio = "";
				int contador = 0;
				while(tabela.hasNext()) {
					aux = tabela.next();
				       envio +=  aux;
				       contador++;		
				       if(contador == 10) {
				    	   DadosEstaticos.listadorMaster(" Lista de Movimentos \n" + envio + "\n", contador); 
				    	   contador = 0;
				    	   envio = "";
				       }
			       }
			       if(contador > 0) {
			    	   DadosEstaticos.listadorMaster(" Lista de Movimentos \n" + envio + "\n", contador);
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
	 * Metodo responsavel pela listagem/pesquisa dos movimentos de um determinado utilizador
	 * @param aNick recebe um objecto string com o nome a pesquisar no arraylist de movimentos 
	 */
	public void pesquisaMoviNick(String aNick) {
		if(logMovimentos != null){
			if(logMovimentos.size() != 0) {
				Iterator <logs> tabela = logMovimentos.iterator();
				logs aux;
				String envio = "";
				int contador = 0;
				while(tabela.hasNext()) {
					aux = tabela.next();
					if(aux.getNome().equals(aNick)) {
				       envio +=  aux;
				       contador++;		
				       if(contador == 10) {
				    	   DadosEstaticos.listadorMaster(" Lista de Movimentos de "+aNick+" \n" + envio + "\n", contador); 
				    	   contador = 0;
				    	   envio = "";
				       }
			       }
				}
				if(contador > 0) {
			    	   DadosEstaticos.listadorMaster(" Lista de Movimentos "+aNick+" \n" + envio + "\n", contador);
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
