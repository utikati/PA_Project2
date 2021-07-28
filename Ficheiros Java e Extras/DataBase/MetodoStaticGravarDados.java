package DataBase;
import Simulador.*;
import Utilizadores.*;
import logs.*;

/**
 * Class com os metodos de gravaçao de dados do programa.
 * @author Jorge Martins
 * Class com os metodos de gravaçao de dados do programa.
 */
public class MetodoStaticGravarDados { 
	/**
	 * Metodo que conjuga todos os metodos de gravação e indica ao utilizador mensagens acerca do sucesso das gravações
	 * @param aGereSistema objecto de gestor de sistema que grava e le os dados
	 * @param gestorJogos objecto com gravação dos jogos realizados na aplicação
	 * @param gestorUtilizadores objecto com dados estatisticos dos utilizadores
	 * @param log dados dos movimentos dos utilizadores
	 */
	public static void gravaDadosSistema(GestorSistema aGereSistema, GestorJogosGravados gestorJogos, GestorUtilizadores gestorUtilizadores, GestorMovimentos log){
		if(aGereSistema.saveDados(gestorJogos)){
			System.out.println("Ficheiros de Sistema gravados com sucesso");
			aGereSistema.fecharFiles();
		}else{
			System.out.println("Erro na gravação do ficheiro de dados de sistema");
			aGereSistema.fecharFiles();
		}
		if(aGereSistema.saveDadosUtilizadores(gestorUtilizadores, log)){
			System.out.println("Ficheiros de Utilizadores gravados com sucesso");
			aGereSistema.fecharFiles();
		}else{
			System.out.println("Erro na gravação do ficheiro de dados de Utilizadores");
			aGereSistema.fecharFiles();
		}
	}
	
	/**
	 * Metodo de fecharFicheirosObjectos 
	 * @param aGereSistema recebe objecto da class GestorSistema
	 */
	public static void fecharFicheirosObjectos(GestorSistema aGereSistema){
		aGereSistema.fecharFiles();
	}
	
	
	
}
