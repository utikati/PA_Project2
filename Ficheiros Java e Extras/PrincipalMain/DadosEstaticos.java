package PrincipalMain;


import java.util.Scanner;
import Aviso.*;
/**
 * Classe responsavel por manter os dados estaticos do software, estes dados são utilizados para guardar informação importante
 * para a sessao da aplicação.
 * @author Jorge Martins
 *
 */
public class DadosEstaticos {
	/**
	 * variavel estatica de teclado
	 */
	public static Scanner teclado = new Scanner(System.in);
	/**
	 * Variavel estatica que guarda o utilizador que esta logado
	 */
	public static String utilizador1 = new String();
	/**
	 * Variavel estatica que guarda o nick do jogador adversario
	 */
	public static String utilizador2 = new String();
	/**
	 * Variavel estatica de array de string para guardar o jogo que pode ficar a meio contra o computador
	 */
	public static String [][] quadro = {{"","",""},{"","",""},{"","",""}};
	/**
	 * Variavel estatica com o caminho relativo do ficheiro de objecto
	 */
	public static String caminhoFichObj = "dados_Jog.dat";
	/**
	 * Variavel estatica com o caminho relativo do ficheiro de objecto
	 */
	public static String caminhoFichObjUtilizadores = "dados_Uti.dat";
	
	/**
	 * Metodo responsavel por listar os dados em string e filtrar o aviso para continuação na proxima pagina ou se a lista chegou ao fim
	 * @param lista objecto string com o conteudo a lista
	 * @param tamanho inteiro com o numero de linhas a listar
	 */
	public static void listadorMaster(String lista, int tamanho) {
		if(lista != null || tamanho > 0)
			System.out.println(lista);
		else
			Aviso.semDados();
		if(tamanho < 10) {
			Aviso.continuaListaFim(teclado);
		}else {
			Aviso.continuaLista(teclado);
		}
		
	}

}
