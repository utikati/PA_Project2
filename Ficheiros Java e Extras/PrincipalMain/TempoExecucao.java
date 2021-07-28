package PrincipalMain;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Classe que calcula o tempo de execução do software desde que ligou ate encerrar
 * @author Jorge Martins
 * Classe que calcula o tempo de execução do software desde que ligou ate encerrar
 */
public class TempoExecucao {
	private static Date inicio;
	private static Date fim;
	private Calendar cal1 = Calendar.getInstance();
	private Calendar cal2 = Calendar.getInstance();
	private static Calendar cal3 = Calendar.getInstance();
	private String format1;
	private String format2;
	private String dia [] = {" ","Domingo","Segunda - Feira","Terça - Feira","Quarta - Feira","Quinta - Feira",
					"Sexta - Feira", "Sabado"};
	
	/**
	 * Construtor da classe inicia logo com a gravação da data pelo qual iniciou o sistema
	 */
	public TempoExecucao() {
		inicio = new Date();
		cal1.setTime(inicio);
		format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(inicio);
	}
	/**
	 * Metodo estatico para devolver apenas a data, uso para os logs
	 * @return string com a data
	 */
	public static String setData() {
		fim = new Date();
		cal3.setTime(fim);
		return new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(fim);
	}
	
	/**
	 * Metodo estatico da classe para retornar a hora, uso para os logs
	 * @return string com a hora do momento
	 */
	public static String setHora() {
		fim = new Date();
		cal3.setTime(fim);
		return new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(fim);
	}
	
	/**
	 * A data de encerramento tem de ser dada pelo set fim para indicar com precisão a data final 
	 * e usar a mesma classe sem estar a usar dois objectos (por isso sao static)
	 */
	public void setFim() {
		fim = new Date();
		cal2.setTime(fim);
		format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(fim);
	}
	
	/**
	 * @return resultado em milisegundos da diferença entre o inicio e o fim do programa
	 */
	public long execucao() {
		return cal2.getTimeInMillis() - cal1.getTimeInMillis();
	}
	
	/**
	 * Metodo usado para dar o tempo de jogada, apenas usando os minutos, hora e segundos, deixando de lado os dias.
	 * @return string com os valores de tempo de jogada como segundos, minutos e horas
	 */
	public String tempoJogada() {
		return "Tempo de execução: "+execucao()+" Milisegundos ("+ (int) Math.round((execucao()*0.001))+" Segundos "+ 
			(int) Math.round((execucao()*0.000016666666666667))+
			" Minutos "+(int) Math.round(((execucao()*2.7777777778*(Math.pow(10, -7)))))+" Horas )";
	}
	/**
	 * Metodo para converter o tempo que é guardado em milisegundos (mais facil tambem para o somar)
	 * @param tempo recebe o long com os milisegundos para depois os converter em horas, minutos e segundos
	 * @return string com o resultado da conversao
	 */
	public static String conversorTempo(long tempo) {
		return ""+tempo+" Milisegundos ("+ (int) Math.round((tempo*0.001))+" Segundos "+ 
				(int) Math.round((tempo*0.000016666666666667))+
				" Minutos "+(int) Math.round(((tempo*2.7777777778*(Math.pow(10, -7)))))+" Horas )";
	}
	
	
	/**
	 * Metodo generico de toString que indica as informaçoes do inicio do programa com dia da semana
	 * hora e data e fim do processo do programa
	 * Indica também o tempo de execução do mesmo (diferença entre inicio e fim)
	 */
	public String toString() {
		
		return "Inicio do Processo : "+dia[cal1.get(Calendar.DAY_OF_WEEK)]+", "+format1+
			 "\nFim do Processo :    "+dia[cal1.get(Calendar.DAY_OF_WEEK)]+", "+format2+"\n"+
			   "Tempo de execução:   "+execucao()+" Milisegundos ("+ (int) Math.round((execucao()*0.001))+" Segundos "+ 
			 (int) Math.round((execucao()*0.000016666666666667))+" Minutos "+(int) Math.round(((execucao()*2.7777777778*(Math.pow(10, -7)))))+" Horas)\n\n";
	
	}
	
}
