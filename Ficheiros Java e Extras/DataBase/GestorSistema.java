package DataBase;

import java.io.Serializable;
import java.util.ArrayList;
import Simulador.*;
import PrincipalMain.DadosEstaticos;
import Utilizadores.*;
import logs.*;

/**
 * Esta class é responsavel pelo carregamento e gravação de dados dos objectos de conteudo do sistema.
 * @author Jorge Martins
 * 
 * Esta class é responsavel pelo carregamento e gravação de dados dos objectos de conteudo do sistema.
 *
 */
public class GestorSistema implements Serializable{ //para ser gravado e carregado tudo num unico objecto
	
	private GestorFicheiroObjecto gereFichObj;
	private ArrayList listaTudo = new ArrayList();
	private ArrayList listaUtilizadores = new ArrayList();
	
	
	
	/**
	 * Construtor do Objecto de gestor de sistema.
	 */
	public GestorSistema(){
		gereFichObj = new GestorFicheiroObjecto();
	}
	
	
	
	//metodos
	
	/**
	 * Metodo loadDados -> carregar dados para o sistema
	 * 
	 * @return objecto RAW de ArrayList pois contem dois objectos de diferentes classes
	 */
	public ArrayList loadDados(){ 
		if(gereFichObj.abreFicheiroLeitura(DadosEstaticos.caminhoFichObj)){
			try{
				return gereFichObj.leituraFicheiro(); //envia um objecto tipo arrayList
			} catch(Exception e){
				e.printStackTrace();
			}
		}else{
			return null;
		}
		return null;
		
	}
	/**
	 * Metodo loadDados -> carregar dados para o sistema
	 * 
	 * @return objecto RAW de ArrayList pois contem dois objectos de diferentes classes
	 */
	public ArrayList loadDadosUtilizadores(){ 
		if(gereFichObj.abreFicheiroLeitura(DadosEstaticos.caminhoFichObjUtilizadores)){
			try{
				
				return gereFichObj.leituraFicheiro(); //envia um objecto tipo arrayList
			} catch(Exception e){
				e.printStackTrace();
			}
		}else{
			return null;
		}
		return null;
		
	}
	
	/**
	 * Metodo responsavel pela gravação de dados de jogos locais e contra o computador
	 * @param gestorJogos objecto que grava os jogos realizados pelos utilizadores na aplicação
	 * @return booleano com resultado da operação
	 */
	public boolean saveDados(GestorJogosGravados gestorJogos){ //gravar os dados dos objectos 
		if(gereFichObj.abreFicheiroEscrita(DadosEstaticos.caminhoFichObj)){
			try{
				listaTudo.add(DadosEstaticos.quadro);
				listaTudo.add(gestorJogos);
				gereFichObj.escreveFicheiro(listaTudo);
				listaTudo.clear(); //se gravar mais que uma vez esta sempre nesta ordem..
				return true;
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return false;
	}
	/**
	 * Metodo responsavel pela gravação de dados de utilizadores e suas estatisticas
	 * @param gestorUtilizadores objecto que guarda os dados do jogador
	 * @param log objecto com os dados dos movimentos dos utilizadores
	 * @return booleano com resultado da operação
	 */
	public boolean saveDadosUtilizadores(GestorUtilizadores gestorUtilizadores, GestorMovimentos log){ //gravar os dados dos objectos 
		if(gereFichObj.abreFicheiroEscrita(DadosEstaticos.caminhoFichObjUtilizadores)){
			try{
				listaUtilizadores.add(gestorUtilizadores);
				listaUtilizadores.add(log);
				gereFichObj.escreveFicheiro(listaUtilizadores);
				listaUtilizadores.clear(); //se gravar mais que uma vez esta sempre nesta ordem..
				return true;
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return false;
	}
	/**
	 * Metodo para fechar as ligações com ficheiros de objectos
	 */
	public void fecharFiles(){ 
		gereFichObj.fechaFicheiroLeitura();
		gereFichObj.fechaFicheiroEscrita();
	}
	 
}
