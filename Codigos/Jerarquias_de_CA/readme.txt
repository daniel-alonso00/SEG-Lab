
Pasos para la ejecucion de de los codigos:

En este caso tenemos un sistema de jerarquias de autoridades de certificacion las cuales descienden de la root-ca y las llamamos sub-ca-server, estas certifican las claves tanto del cliente como del servidor en lugar de ser certificadas por la root-ca Para todo esto tenemos que volver a generar tanto en el cliente como servidor los archivos .csr y certificarlos la sub-ca-server e importarlos de nuevo en los KeyStore y TrustStore de cada uno. Al ser una autoridad de certificacion secundaria tenemos que encadenar el certificado del sub-ca-server y del root-ca haciendo una Jerarquia de autoridades de certificación.

Al modificar los codigos tenemos que tener en cuenta como siempre las ubicaciones de los almacenes

Para este tipo de jerarquias de certificacion vamos a hacer dos pruebas:

- Conexion HandshakeTLS: 

    Usamos los codigos de HandshakeTLS para hacer una prueba de conexion TLS usando las claves certificadas
    por una autoridad de certificacion secundaria

    Usamos los mismos comandos de terminal:

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

- Verificacion de no revocacion con OCSP Stapling:
    Creamos un OCSP Responder secundario para el sub-ca-server, funciona igual que el normal pero esta al 
    mismo nivel secundario

    En este caso vamos a usar el metodo2 de Stapling que es indicar la direccion del OCSP Responder en el codigo java
    por lo que tenemos que comprobar que este indicado asi en el ClassFileServer_con_OCSPStapling.java

    Este OCSP Responder se ejecuta en otro puerto (9081) por que suelen estar ejecutándose simultáneamente el root-ocsp y el sub-ocsp, para que asi puedan estar ejecutándose a la vez sin interferirse entre ellos

    La ejecucion es la misma que en el OCSP Stapling:

    Ejecucion:
            + Ejecutamos el OCSP Responder: (situado en el sub-ca-server)
            
            #    openssl ocsp -port 9081 -index db/index -rsigner sub-ocsp.crt -rkey private/sub-ocsp.key -CA sub-ca-server.crt -text -validity_period 300

            (El cliente y servidor los ejecutamos en la MV de ser-cli)
            + Iniciamos el servidor:

            #   java ClassFileServer [port] [docroot] TLS
                java ClassFileServer 9001 ~/compartida/conexion_Cli-Ser/ TLS
            
            + Ejecutamos el cliente:

            #   java Cliente_autenticado_con_OCSP_cliente [server_host] [port] [file]
                java Cliente_autenticado_con_OCSP_cliente 127.0.0.1 9001 texto1.txt

