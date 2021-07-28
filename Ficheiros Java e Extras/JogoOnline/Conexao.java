package JogoOnline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import Aviso.*;
/**
 * Classe responsavel pela ligação de sockets entre aplicações.
 * @author Jorge Martins
 *
 */
public class Conexao {
	
		private ServerSocket server;
		private Socket client;
		private PrintWriter envia;
		private BufferedReader receptor;
		private String enderecoIp;

	
		/** 
		 * Metodo construtor para ligação como servidor
		 * @param aPorta recebe o valor do porto a utilizador pelo sistema
		 */
		public Conexao(int aPorta) { //construtor da ligação a ser servidor..
			try {
				server = new ServerSocket(aPorta);
				enderecoIp = obterIp();
				System.out.println("Servidor conectado com IP "+ enderecoIp +":"+server.getLocalPort() + "\nServidor a escuta...\n");
				client = server.accept(); //servidor à espera da conexao por parte do cliente
				System.out.println("Conexao estabelecida com cliente: " + client.getRemoteSocketAddress().toString().replace("/","."));
				envia = new PrintWriter(client.getOutputStream(), true);
				receptor = new BufferedReader(new InputStreamReader(client.getInputStream()));
			} catch (IOException ioe) {
				Aviso.avisoErro();
				ioe.printStackTrace();
			}
		}

		
		/**
		 * Metodo Construtor para ligação como cliente
		 * @param aIp recebe o ip privado do cliente 
		 * @param aPorta recebe o porto de ligação do cliente
		 */
		public Conexao(String aIp, int aPorta) { //construtor a ser ligação como o cliente....
			try {
				server = null;
				client = new Socket(aIp, aPorta);
				System.out.println("Conexao estabelecida com servidor:  " + aIp + ":" + aPorta);

				envia = new PrintWriter(client.getOutputStream(), true);
				receptor = new BufferedReader(new InputStreamReader(client.getInputStream()));
			}catch (UnknownHostException uhe) {
				System.err.println("Erro: Servidor desconhecido: "+aIp+":"+aPorta);
				client = null;
			}catch (ConnectException ce) {
				System.err.println("Erro: Sem resposta do servidor: "+aIp+":"+aPorta);
				client = null;
			}catch (IOException ioe) {
				Aviso.avisoErro();
				ioe.printStackTrace();
			}
		}

		
		/**
		 * Metodo de leitura do socket de resposta
		 * @return retorna a mensagem recebida em string
		 */
		public String recebeMensagem() { //recebe o socket
			try {
				return receptor.readLine();
			} catch (IOException ioe) {
				return null; //na excepção envia null para na comunicação interpretar como interrompida
			}
		}

		
		/**
		 * Metodo que envia mensagem /socket pela rede
		 * @param aMensagem recebe string com a mensagem a enviar
		 */
		public void enviaMensagem(String aMensagem) { //envia o socket
			
			envia.println(aMensagem);
		}

		
		/**
		 * Metodo que termina a ligação em rede com segurança
		 */
		public void fechaConexao(){
			try {
			    if (receptor != null)
			    	receptor.close();
			    if (envia != null)
			    	envia.close();
			    if (client != null)
			    	client.close();
			    if (server != null)
			    	server.close();
			} catch (Exception e) { // aqui trata tanto da UnknowHost tal como da IOE Exception
				e.printStackTrace();
			} finally { // assegurar que ficam mesmo close !
				if(receptor != null) {
					try{receptor.close();} catch(Exception e) {e.printStackTrace();}
				}if(envia != null) {
					try{envia.close();} catch(Exception e) {e.printStackTrace();}
				}if(client != null) {
					try{client.close();} catch(Exception e) {e.printStackTrace();}
				}if(server != null) {
					try{server.close();} catch(Exception e) {e.printStackTrace();}
				}
			}
		}

		
		/**
		 * Metodo responsavel por obter o ip privado do servidor
		 * @return string com o ip local do utilizador, caso falhe envia null
		 */
		public String obterIp() {
			try {
				return InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
				return null;
			}
		}

		
		/**Metodo que retorna o socket de ligação de cliente
		 * @return socket cliente
		 */
		public Socket getCliente() {
			return client;
		}
		
		
		/** Metodo que retorn o socket de ligação de servidor
		 * @return socket servidor
		 */
		public ServerSocket getServer() {
			return server;
		}
	}


