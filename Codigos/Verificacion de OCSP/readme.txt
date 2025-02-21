
Pasos para la ejecucion de la verificiacion de no revocacion con OCSP
Lo podemos dividir en dos partes:
    - OCSP de cliente:

        Tenemos que comprobar si tenemos bien la raiz de los almacenes de claves en los codigos que vamos a ejecutar

        Para esto solo tenemos que usar el codigo de cliente: Cliente_autenticado_con_OCSP_cliente.java y para
        el servidor usamos el mismo que en el HandshakeTLS

        + Ejecutamos el OCSP Responder: (situado en root-ca)
            
        #    openssl ocsp -port 9080 -index db/index -rsigner root-ocsp.crt -rkey private/root-ocsp.key -CA root-ca.crt -text
        
        (El cliente y servidor los ejecutamos en la MV de ser-cli)
        + Iniciamos el servidor:

        #   java ClassFileServer [port] [docroot] TLS
            java ClassFileServer 9001 ~/compartida/conexion_Cli-Ser/ TLS
        
        + Ejecutamos el cliente:

        #   java Cliente_autenticado_con_OCSP_cliente [server_host] [port] [file]
            java Cliente_autenticado_con_OCSP_cliente 127.0.0.1 9001 texto1.txt

        De esta forma el cliente verifica el estado de revocacion del certificado del servidor al que nos vamos a conectar

    - OCSP Stapling:

        Igual que en el anterior tenemos que comprobar que tengamos bien definidas la reiz de los almacenes en los codigos

        Para usar OCSP Stapling tenemos que usar un servidor especifico que sera ClassFileServer_con_OCSPStapling.java el cual
        usa el fichero ClassServer.java (como el servidor anterior) y tambien tenemos que tener un codigo java especifico que
        es Cliente_autenticado_con_OCSP_Stapling.java

        Para el uso de OCSP Stapling tenemos que modificar el codigo del servidor definiendo que metodo vamos a usar para indicar
        la direccion IP y el puerto del servidor OCSP Responder

        *Metodo1:
            La direccion del OCSP Responder se indica en el certificado del servidor

            En este caso tenemos que generar en el KeyStoreServer un servertls.crs y certificarlo de nuevo en el openssl root-ca
            ya que en el root-ca.conf se ha modificado icluyendo en la extension de server_ext para decir la IP del OSCP Responder,
            y volver a importar el certificado al KeyStoreServer

        *Metodo2:
            La direccion del OCSP Responder se indica explicitamente en el codigo java del servidor

        Ejecucion:
            + Ejecutamos el OCSP Responder: (situado en root-ca)
            
            #    openssl ocsp -port 9080 -index db/index -rsigner root-ocsp.crt -rkey private/root-ocsp.key -CA root-ca.crt -text

            (El cliente y servidor los ejecutamos en la MV de ser-cli)
            + Iniciamos el servidor:

            #   java ClassFileServer [port] [docroot] TLS
                java ClassFileServer 9001 ~/compartida/conexion_Cli-Ser/ TLS
            
            + Ejecutamos el cliente:

            #   java Cliente_autenticado_con_OCSP_cliente [server_host] [port] [file]
                java Cliente_autenticado_con_OCSP_cliente 127.0.0.1 9001 texto1.txt

