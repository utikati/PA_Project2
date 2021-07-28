package PrincipalMain;
/**
 * Classe responsavel pela primeira interação com o utilizador ao ligar o software, aqui é onde ele escolhe entrar com o seu nick ou entrar
 * no modo anonimo.
 * @author Jorge Martins
 *
 */
public class PrimeiraCamada {
	
	/**
	 *Metodo responsavel pela primeira interação com o utilizador ao ligar o software, aqui é onde ele escolhe entrar com o seu nick ou entrar
	 * no modo anonimo.
	 */
	public static void primeiraCamada() {
		System.out.println("1 - Entrar com o seu Nick \n2 - Entrar como anonimo");
		int opcao = Principal.pedeDadosInteiro("Insira a sua opcao", DadosEstaticos.teclado);
		switch(opcao) {
		case 1: DadosEstaticos.utilizador1 = Principal.pedeDadosString("Insira o seu Nick", DadosEstaticos.teclado); break;
		case 2: DadosEstaticos.utilizador1 = "Anonimo"; break;
			default: DadosEstaticos.utilizador1 = "Anonimo"; break;
		}
		
	}
	
	
}
