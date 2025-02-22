import java.io.*;
import java.net.*;
import java.security.KeyStore;

import javax.net.*;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;

/*********************************************************************
 * ClassFileServer.java -- a simple file server that can server
 * Http get request in both clear and secure channel
 *
 * The ClassFileServer implements a ClassServer that
 * reads files from the file system. See the
 * doc for the "Main" method for how to run this
 * server.
 ********************************************************************/

 public class ClassFileServer extends ClassServer {

    private String     		docroot;
    private static int 		DefaultServerPort = 9001;
	private static String 	raiz = "/home/dani/compartida/KeyStore/";

	//	ks.load(new FileInputStream("c:/comun/escuela/seguridad_bolonia/practica2013/cliente/testkeys.jks"), passphrase);

    /**********************************************************
     * Constructs a ClassFileServer.
     *
     * @param path the path where the server locates files
     **********************************************************/
    public ClassFileServer(ServerSocket ss, String docroot) throws IOException
    {
		super(ss);
		this.docroot = docroot;
    }

    /**********************************************************
    * getBytes -- Retorna un array de bytes con el contenido del fichero. 
    *    representado por el argumento <b>path</b>.
    *
    *  @return the bytes for the file
    *  @exception FileNotFoundException si el fichero 
    *      <b>path</b> no existe
    *********************************************************/
    public byte[] getBytes(String path)  
    	                throws IOException, FileNotFoundException     {

	    String fichero = docroot + File.separator + path;

	    File f = new File(fichero);
		int length = (int)(f.length());

		System.out.println("leyendo: " + fichero);
		
		if (length == 0) {
		    throw new IOException("La longitud del fichero es cero: " + path);
		} 
		else 
		{
		    FileInputStream fin = new FileInputStream(f);
		    DataInputStream in  = new DataInputStream(fin);
	
		    byte[] bytecodes = new byte[length];
	
		    in.readFully(bytecodes);
		    return bytecodes;
		}
    }

    /** Main *********************************************
     * Main method to create the class server that reads
     * files. This takes two command line arguments, the
     * port on which the server accepts requests and the
     * root of the path. To start up the server: <
     *
     *   java ClassFileServer <port> <path>
     * 
     *
     * <code>   new ClassFileServer(port, docroot);
     * </code>
     *****************************************************/
    public static void main(String args[])
    {
		System.out.println(
		    "USAGE: java ClassFileServer port docroot [TLS [true]]");
		System.out.println("");
		System.out.println(
		    "If the third argument is TLS, it will start as\n" +
		    "a TLS/SSL file server, otherwise, it will be\n"   +
		    "an ordinary file server. \n"                      +
		    "If the fourth argument is true,it will require\n" +
		    "client authentication as well.");
	
		definirKeyStores();
	
		int port = DefaultServerPort;
		String docroot = "";
	
		if (args.length >= 1) {
		    port = Integer.parseInt(args[0]);
		}
	
		if (args.length >= 2) {
		    docroot = args[1];
		}
		
		String type = "PlainSocket";
		if (args.length >= 3) {
		    type = args[2];
		}
	
		try {
		    ServerSocketFactory ssf = ClassFileServer.getServerSocketFactory(type);
	
		    ServerSocket ss = ssf.createServerSocket(port);
	
		    if (args.length >= 4 && 
		    	args[3].equals("true")) {
	
		    	((SSLServerSocket)ss).setNeedClientAuth(true);
		    }
		
		    new ClassFileServer(ss, docroot);
		
		} catch (IOException e) {
		    System.out.println("Unable to start ClassServer: " + e.getMessage());
		    e.printStackTrace();
		}
    }

    /************************************************************
    	getServerSocketFactory(String type) 
    ************************************************************/
    private static ServerSocketFactory getServerSocketFactory(String type) {

    if (type.equals("TLS")) 
    {
    	SSLServerSocketFactory ssf = null;
	    
    	try {
			
    		// Establecer el keymanager para la autenticacion del servidor

    			SSLContext 			ctx;
			KeyManagerFactory 		kmf;
			KeyStore 			ks;
			char[] 				contrasena = "1234".toCharArray();
	
			ctx = SSLContext.getInstance("TLS");
			kmf = KeyManagerFactory.getInstance("SunX509");

			ks  = KeyStore.getInstance("JCEKS");
			ks.load(new FileInputStream(raiz + "keyStoreServer.jce"), contrasena);

			kmf.init(ks, contrasena);
			
			ctx.init(kmf.getKeyManagers(), null, null);
	
			ssf = ctx.getServerSocketFactory();

			return ssf;
	    } 
	    catch (Exception e) {

	    	   e.printStackTrace();
	    }
	
    }  
    else 
    {
    	System.out.println("Usando la Factoria socket por defecto (no SSL)");

    	return ServerSocketFactory.getDefault();
	}
	
    return null;
    }

    /******************************************************
		definirKeyStores()
    *******************************************************/
	private static void definirKeyStores()
	{
	    // Almacen de claves
		
	    System.setProperty("javax.net.ssl.keyStore",           raiz + "keyStoreServer.jce");
	    System.setProperty("javax.net.ssl.keyStoreType",     "JCEKS");
	    System.setProperty("javax.net.ssl.keyStorePassword", "1234");
	
	    // Almacen de confianza
	    
	    System.setProperty("javax.net.ssl.trustStore",         raiz + "trustStoreServer.jce");
	    System.setProperty("javax.net.ssl.trustStoreType",     "JCEKS");
	    System.setProperty("javax.net.ssl.trustStorePassword", "1234");
	}

}


