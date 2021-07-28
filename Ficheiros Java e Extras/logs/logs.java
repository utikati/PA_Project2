package logs;

import java.io.Serializable;
/**
 * Classe responsavel pela informação armazenada nos logs / movimentos do utilizador
 * @author Jorge Martins
 *
 */
public class logs implements Serializable{
	
	private String nome;
	private String data;
	private String hora;
	private String accao;
	/**
	 * Construtor da classe logs
	 * @param aNome recebe objecto string com o nome do utilizador ou nick
	 * @param aData recebe objecto string com a data da realização do movimento
	 * @param aHora recebe objecto string com a hora ao qual o movimento foi realizado
	 * @param aAccao recebe objecto string com a acção realizada pelo utilizador
	 */
	public logs(String aNome, String aData, String aHora, String aAccao) {
		nome = aNome;
		data = aData;
		hora = aHora;
		accao = aAccao;
	}
	/**
	 * Metodo standard toString do objecto para que este seja escrito a quando a sua chamada na listagem
	 */
	public String toString() {
		return "<"+data+"> <"+hora+"> <"+nome+"> <"+accao+">\n";
	}
	/**
	 * Metodo get nome usado para fazer a pesquisa do nome de utilizador e filtrar os seus movimentos
	 * @return string com o nome de utilizador no objecto desta classe
	 */
	public String getNome() {
		return nome;
	}

}
