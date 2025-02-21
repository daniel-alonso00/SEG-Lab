# 🔒 Seguridad en TLS y Certificados - Pruebas

## 📌 Descripción
Este repositorio contiene código y archivos relacionados con la implementación y prueba de conexiones seguras utilizando TLS, validación de certificados con OCSP y jerarquías de Autoridades de Certificación (CA). Se incluyen ejemplos de clientes y servidores que intercambian archivos de forma segura, validando certificados y asegurando la autenticidad de las claves utilizadas en la conexión.

---
## 📂 Estructura del Repositorio
```
├── Keystore
│
├── Codigos
│   ├── HandshakeTLS
│   ├── Verificacion de OSCP
│   ├── Jerarquias de CA
│   │   ├── Prueba Handshake
│   │   ├── Prueba OCSP
│   ├── texto1.txt
│   ├── texto2.txt
│   ├── texto3.txt
│
├── README.md
```

## Estructura del Repositorio

### `KeyStore/`
Contiene todos los certificados, almacenes de confianza y demás archivos relacionados con la gestión de claves y certificados. Para manejar estos archivos, se utiliza la aplicación `KeyStore`.

### `Codigos/`
Carpeta principal que contiene las implementaciones en código divididas en diferentes subdirectorios:

  #### `- HandshakeTLS/`
  - Contiene el código de una conexión TLS básica entre un cliente y un servidor.
  - Implementa autenticación mutua utilizando certificados almacenados en `KeyStore` y `TrustStore`.
    - *Cliente:* Se conecta al servidor y solcita archivos
    - *Servidor:* Aloja archivos y los envia al cliente tras validar su certificado 
  
  #### `- Verificacion_OCSP/`
  - Implementa la verificación de no revocacion de certificados mediante **OCSP**.
  - Incluye código para:
    - **OCSP Cliente**: El cliente valida el certificado del servidor a través de un OCSP responder.
    - **OCSP Stapling**: El servidor proporciona la validación OCSP en la respuesta TLS, reduciendo la carga en el cliente.
  
  #### `- Jerarquias_CA/`
  - Implementación de TLS y OCSP utilizando una **jerarquía de autoridades de certificación**.
  - Contiene dos subdirectorios:
  
    - **`prueba_handshake/`**: Código de cliente y servidor para una conexión TLS básica con certificados de una subCA.
    - **`prueba_OCSP/`**: Código de cliente y servidor con OCSP de cliente y OCSP Stapling, certificados por una subCA y comprobado su estado de no revocacion con subOCSP responder.


### 📜 Archivos de Prueba
Se incluyen tres archivos de texto ( `texto1.txt`, `texto2.txt` , `texto3.txt` ) que funcionan como archivos de ejemplo para probar la transmisión segura de datos entre el cliente y el servidor. Estos archivos se utilizan para simular solicitudes de documentos y verificar que la comunicación TLS funciona correctamente. El servidor aloja los archivos, y el cliente los solicita a través de una conexión segura, la cual solo se establece si ambos están certificados por la misma autoridad.

---

## ⚙️ Consideraciones de Implementación
 Aunque cliente y servidor se ejecutan en la misma máquina, la **Autoridad de Certificación (rootCA)** debería estar en una máquina separada.
 La `rootCA` se encarga de:
  -  Certificar claves con `OpenSSL`.
  -  Servir como `OCSP responder` en los casos que se use la verificación de no revocacion.

---

## 🛠 Requisitos y Configuración
1.  **OpenSSL**: Para la generación y validación de certificados.
2.  **KeyStore**: Para la gestión de certificados y claves.
3.  **Configuración del OCSP responder** en la máquina que actúa como `rootCA`.

---
#### Este repositorio es parte de un proyecto academico y esta diseñado para fines educativos
