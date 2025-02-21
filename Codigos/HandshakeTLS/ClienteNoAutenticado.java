//package ClienteWeb;

import java.net.*;
import java.io.*;
import javax.net.ssl.*;

/*
 *  Argumentos:
 *      - server
 *      - port
 *      - file
 */

public class ClienteNoAutenticado {	

	private static String 	raiz     = "/home/dani/compartida/KeyStore/";

	
    public static void main(String[] args) throws Exception {
	try {		

		definirKeyStores();

	    
	    SSLSocketFactory factory =
	    		(SSLSocketFactory)SSLSocketFactory.getDefault();

	    System.out.println ("Crear socket");
	    SSLSocket socket =
	    		(SSLSocket)factory.createSocket(args[0], Integer.parseInt(args[1]));
	 
	    // Ver las suites SSL disponibles

	    System.out.println ("CypherSuites");
	    SSLContext context = SSLContext.getDefault();
	    SSLSocketFactory sf = context.getSocketFactory();
	    
	    String[] cipherSuites = sf.getSupportedCipherSuites();

	    for (int i=0; i<cipherSuites.length; i++) 
	    		;//System.out.println (cipherSuites[i]);
	    
	    
	    
	    System.out.println ("Comienzo SSL Handshake");

	    socket.startHandshake();
	    
	    System.out.println ("Fin SSL Handshake");

	    PrintWriter out = new PrintWriter(
							  new BufferedWriter(
							  new OutputStreamWriter(
									  socket.getOutputStream())));

	    out.println("GET " + "/" + args[2]  + " "  + " HTTP/1.0");
	    out.println();
	    out.flush();

	    System.out.println("GET " + "/" + args[2]  + " " + "HTTP/1.0");
	    /*
	     * Make sure there were no surprises
	     */
	    if (out.checkError())
			System.out.println("SSLSocketClient:  java.io.PrintWriter error");

	    /* Leer respuesta */
	    BufferedReader in = new BufferedReader(
							    new InputStreamReader(
							    		socket.getInputStream()));

	    String inputLine;
	    while ((inputLine = in.readLine()) != null)
		System.out.println(inputLine);

	    in.close();
	    out.close();
	    socket.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    /******************************************************
		definirKeyStores()
    *******************************************************/
	private static void definirKeyStores()
	{
		
		// Almacen de confianza
	  
		System.setProperty("javax.net.ssl.trustStore",          raiz + "trustStoreClient1.jce");
		System.setProperty("javax.net.ssl.trustStoreType",     "JCEKS");
		System.setProperty("javax.net.ssl.trustStorePassword", "1234");

	}


	}

