package Jogo;

import java.io.Serializable;

/**
 * Classe que � o gerador do jogo, atrav�s do objecto desta que todo o jogo � realizado, suas verifica��es e listagem
 * do quadro de jogo
 * @author Jorge Martins
 *
 */
public class Jogo implements Serializable{
	private String [][] quadro = {{"","",""},{"","",""},{"","",""}};
	/**
	 * Construtor vazio para inicializa��o de um novo jogo, imediatamente � listado o quadro em vazio
	 */
	public Jogo() {
		lista();
	}
	/**
	 * Construtor especificamente usado para o simulador que j� recebe quadros com jogadas j� realizadas e assim as listar
	 * @param aQuadro objecto de array estatico de string com jogadas
	 */
	public Jogo(String[][] aQuadro) {
		for(int i = 0; i<3; ++i) {
			for(int a=0; a<3; ++a) {
				quadro[i][a] = aQuadro[i][a];
			}
		}
	}
	
	/**
	 * Metodo responsavel pela listagem dos dados (jogadas) inseridas no quadro do objecto da classe
	 */
	public void lista() {
		for(int i=0; i<3; ++i) {
			for(int z=0; z<3; ++z) {
				if(z==2) {
					if(i!=2) {
						System.out.printf("%1s", quadro[i][z]);
						System.out.printf("\n_+_+_\n");
					}else {
						System.out.printf("%1s", quadro[i][z]);
					}
				}else {
					if(z!=2)
						System.out.printf("%1s", quadro[i][z]);
						System.out.printf("|");
				}	
			}
		}
		System.out.println("\n\n\n");
	}
	/**
	 * Metodo estatico da classe que verifica se o jogador obteve 10 vitorias 
	 * @param num recebe numero de vitorias do jogador
	 * @return booleano com resultado da opera��o
	 */
	public static boolean notificacaoVitoria(int num) {
		if(num % 10 == 0 && num != 0)
			return true;
		return false;
	}
	
 	/**
 	 * Metodo de inser��o de dados para o caracter X
 	 * @param x linha onde vai inserir no quadro
 	 * @param y coluna onde vai inseri no quadro
 	 */
 	public void inserirDadosX(int x, int y) {
		quadro[x][y] = "X";
	}
 	/**
 	 * Metodo de inser��o de dados para o caracter O
 	 * @param x linha onde vai inserir no quadro
 	 * @param y coluna onde vai inseri no quadro
 	 */
	public void inserirDadosO(int x, int y) {
		quadro[x][y] = "O";
	}
	
	/**
	 * Metodo que apaga jogada realizada na localiza��o x,y do quadro
	 * usada para jogo em rede pelo qual se o jogador remoto indicar que a jogada n�o � valida a mesma tem de ser
	 * apagada do quadro para que o utilizador fique sincronizado com o outro jogador remoto
	 * @param x linha onde vai apagar no quadro
	 * @param y coluna onde vai apagar no quadro
	 */
	public void apagaJogada(int x, int y) {
		quadro[x][y] = "";
	}
	
	/**
	 * Metodo que � responsavel por verificar a disponibilidade do campo escolhido, este verifica se o espa�o se encontra vazio
	 * ou seja n�o est� j� ocupado
	 * @param x linha 
	 * @param y coluna
	 * @return boleano com resultado da opera��o
	 */
	public boolean verificaDisponibilidade(int x, int y) {
		if(x <3 && x >= 0 && y < 3 && y >= 0) {
		if(quadro[x][y].length()==0) {
			return true;
		}}
		return false;
	}
	
	/**
	 * Metodo responsavel por verificar se existem nas linhas do quadro 3 elementos iguais que significa vitoria
	 * @return booleano com resultado da opera��o
	 */
	private boolean checkaLinhas() {
		for(int li=0; li<3; ++li) {
			if(!quadro[li][0].equals("") && !quadro[li][1].equals("") && !quadro[li][2].equals("")) {
				if(quadro[li][0].equals(quadro[li][1]) && quadro[li][1].equals(quadro[li][2])) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Metodo responsavel por verificar se existem nas colunas do quadro 3 elementos iguais que significa vitoria
	 * @return booleano com resultado da opera��o
	 */
	private boolean checkaColunhas() {
		for(int col=0; col<3; ++col) {
			if(!quadro[0][col].equals("") && !quadro[1][col].equals("") && !quadro[2][col].equals("")) {
				if(quadro[0][col].equals(quadro[1][col]) && quadro[1][col].equals(quadro[2][col])) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Metodo responsavel por verificar se existem nas diagonais do quadro 3 elementos iguais que significa vitoria
	 * @return booleano com resultado da opera��o
	 */
	private boolean checkaDiagonais() {
		if(!quadro[0][0].equals("") && !quadro[1][1].equals("") && !quadro[2][2].equals("")) {
			if(quadro[0][0].equals(quadro[1][1]) && quadro[1][1].equals(quadro[2][2])) {
				return true;
			}
		}
		if(!quadro[0][2].equals("") && !quadro[1][1].equals("") && !quadro[2][0].equals("")) {
			if(quadro[0][2].equals(quadro[1][1]) && quadro[1][1].equals(quadro[2][0])) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Metodo que chama os tres metodos de verifica��o, caso algum teste positivo � devolvido true indicando que se verificou
	 * uma vitoria no quadro
	 * @return booleano com resultado da opera��o
	 */
	public boolean checkaQuadro() {
		if(checkaLinhas()) {return true;}
		if(checkaColunhas()) {return true;}
		if(checkaDiagonais()) {return true;}
		return false;
	}
	
	/**
	 * Metodo de dica dado pelo computador, apenas indica um local que se encontra disponivel para jogar
	 * Metodo considerado Dummy, n�o contem algoritmo de inteligencia ou resolu��o para vitoria
	 * @return string com indica��o da dica ao utilizador
	 */
	public String dica() { //dica dummy....
		boolean test = true;
		int x = 0;
		int y = 0;
		do {
			for(int i=1; i<4; ++i) {
				for(int z=1; z<4; ++z) {
					x = i;
					y = z;
					if(verificaDisponibilidade(x-1, y-1)) { // para n�o estar a gerar mensagens ao computador de repetir
						test = false;
						break;
					}
				}
				if(!test) {
					break;
				}
			}
				
		}while(test);
		return "**DICA** -> *O espa�o na linha "+x+" e coluna "+y+" encontra-se disponivel para jogar \n";
	}
	/**
	 * Metodo que verifica se o quadro se encontra ainda com espa�os vazios, ou seja se este se encontra disponivel para ainda se jogar
	 * @return booleano com resultado da opera��o
	 */
	public boolean checkboardLines() {
		for(int i=0; i<3; ++i) {
			for(int z=0; z<3; ++z) {
				if(quadro[i][z].equals("")) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Metodo para enviar o objecto de string quadro 
	 * @return objeto array string do quadro de jogo
	 */
	public String[][] getQuadro(){
		return quadro;
	}
	
	/**
	 * Metodo utilizado para modificar o quadro presente no objecto
	 * @param aQuadro objecto array de string para substituir no quadro de jogo
	 */
	public void setQuadro(String[][] aQuadro){
		quadro = aQuadro;
	}
	
}
