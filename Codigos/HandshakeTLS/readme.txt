
Pasos para la ejecucion de los archivos del HandshakeTLS:
- Tenemos que comprobar primero si las rutas de los almacenes este bien definidas junto con los nombres de los 
  KeyStore y TrustStore
- El codigo del server es el mismo para los dos casos, en cliente autenticado y no autenticado
- Tenemos que ejecutar primero el servidor y despues el cliente
- Este servicio se basa en un servidor de ficheros levantado en una ubicacion que especificamos y el cliente
  solicita uno de los archivos que el servidor ofrece, todo esto bajo una conaxion TLS segura

    Ejecucion:
    +   Cliente no autenticado:
        #   java ClassFileServer [port] [docroot] TLS
            java ClassFileServer 9001 ~/compartida/conexion_Cli-Ser/ TLS

        #   java ClienteNoAutenticado [server_host] [port] [file]
            java ClienteNoAutenticado 127.0.0.1 9001 texto1.txt

    +   Cliente Autenticado:
        #   java ClassFileServer [port] [docroot] TLS true
            java ClassFileServer 9001 ~/compartida/conexion_Cli-Ser/ TLS true

        #   java ClienteAutenticado [server_host] [port] [file]
            java ClienteAutenticado 127.0.0.1 9001 texto1.txt
