package DataBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;



/**
 * Classe responsavel pela leitura e grava��o dos dados em ficheiros de objecto
 * @author Jorge Martins
 * Classe responsavel pela leitura e grava��o dos dados em ficheiros de objecto
 */
public class GestorFicheiroObjecto implements Serializable{
	
	  private File ficheiroLeitura;
	  private File ficheiroEscrita;
	  // leitura do ficheiro
	  private FileInputStream fis; // substitui FileReader
	  private ObjectInputStream ois; // substitui BufferedReader
	  // escrita do ficheiro
	  private FileOutputStream fos; // substitui FileWriter
	  private ObjectOutputStream oos; // substitui BufferedWriter

	  //ABERTURA DE FICHEIRO DE OBJECTO----------------------------------------------------------
	  /** Metodo usado para abertura do ficheiro de objeto para que este seja lido
	 * @param aCaminho string com o caminho relativo do objecto
	 * @return booleano com resultado da opera��o
	 */
	public boolean abreFicheiroLeitura(String aCaminho) {

	    if (aCaminho != null && aCaminho.length() > 0) {
	      ficheiroLeitura = new File(aCaminho);
	      if (ficheiroLeitura.exists()) {
	        try {
	        	fis = new FileInputStream(ficheiroLeitura);
	            ois = new ObjectInputStream(fis);
	          return true;
	        } catch (IOException ioe) {
	          ioe.printStackTrace();
	        } 
	      }
	    }
	    return false;
	  }
	  

	  /**
	   * Metodo responsavel para abrir o ficheiro para escrever os dados do software
	 * @param aCaminho string com caminho relativo do ficheiro .dat
	 * @return booleano com o resultado da opera��o
	 */
	public boolean abreFicheiroEscrita(String aCaminho) {
	    if (aCaminho != null && aCaminho.length() > 0) {
	      ficheiroEscrita = new File(aCaminho);
	      try {
	        if (!ficheiroEscrita.exists()) {
	          fos = new FileOutputStream(ficheiroEscrita);
	          oos = new ObjectOutputStream(fos);
	        } else {
	          ficheiroEscrita.delete();
	          fos = new FileOutputStream(new File(aCaminho)); //n�o reutilizo o ficheiroEscrita para ter a certeza que n�o corrompe o header de qualquer forma
	          oos = new ObjectOutputStream(fos);
	        }
	        return true;
	      } catch (IOException ioe) {
	        ioe.printStackTrace();
	      }
	    }
	    return false;
	  }

	  //FECHO DE FICHEIROS DE OBJECTO---------------------------------------------------------------------------
	  /**
	   * Metodo para terminar a liga��o com o ficheiro de leitura, para que a liga��o n�o fique ligada todo o tempo
	 * @return booleano com resultado da opera��o
	 */
	public boolean fechaFicheiroLeitura() {

	    try {
	      if (ois != null) {
	        ois.close();
	      }
	      
	      if (fis != null) {
	          fis.close();
	          return true;
	      }
	    } catch (IOException ioe) {
	      ioe.printStackTrace();
	    }
	    return false;
	  }

	  /**
	   * Metodo para terminar a liga��o com o ficheiro de escrita (poder� ser o mesmo de leitura) para que ao terminar o programa
	   * a liga��o seja antes terminada e n�o fique no SO um processo de liga��o aberto
	 * @return booleano com o resultado deste metodo
	 */
	public boolean fechaFicheiroEscrita() {
	    try {
	      if (oos != null) {
	        oos.close();
	      }

	      if (fos != null) {
	          fos.close();
	          return true;
	      }
	    } catch (IOException ioe) {
	      ioe.printStackTrace();
	    }
	    return false;

	  }
	  
	  //Para o ficheiro de objectos do conteudo do sistema

	 //LEITURA DE FICHEIRO DE OBJECTOS-------------------------------------------------------------------------
	  /** Metodo de leitura de um ficheiro de objectos, este metodo retorna um arrayList raw pois est�o objectos diferentes dentro do mesmo
	   * assim reutilizando o mesmo ficheiro para v�rias op��es
	 * @return ArrayList raw com dois objectos diferentes
	 */
	public ArrayList leituraFicheiro() {

	    if (ois != null) {
	      try {
	    	  
	        return (ArrayList) ois.readObject();
	      } catch (IOException ioe) {
	        ioe.printStackTrace();
	      } catch (ClassNotFoundException cnfe) {
	        cnfe.printStackTrace();
	      }
	    }
	    return null;
	  }
//	ESCRITA DE FICHEIRO DE OBJECTOS--------------------------------------------------------------------------
	  
	  /**
	   * Metodo de escrita de ficheiro de objecto, recebe um ArrayList raw com objectos diferentes e s�o escritos no ficheiro
	 * @param aGI objecto ArrayList RAW
	 * @return boolean com resultado da opera��o
	 */
	public boolean escreveFicheiro(ArrayList aGI) {

	    if (oos != null) {
	      if (aGI != null) {
	        try {	
	          oos.writeObject(aGI);
	          oos.flush();
	          return true;
	        } catch (IOException ioe) {
	          ioe.printStackTrace();
	        }
	      }
	    }
	    return false;
	  }
}
