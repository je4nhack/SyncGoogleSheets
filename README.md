# Repositorio de proyecto SyncGoogleSheets

Autor: Jean Pierre Pérez Pinedo (je4npier@gmail.com)

Proyecto Java para el sincronizar resultados de una BD (Oracle o SQL) con Google Sheets usando el API v4.0 de Google.
Requisitos:
- Java JDK 8 (o superior)
- Git
- Maven
	> Se tiene que realizar una configuración adicional para instalar el ojdbc para conectarse a Oracle, se debe descargar el jar de la página oficial ([link](https://www.oracle.com/database/technologies/jdbcdriver-ucp-downloads.html)) y luego instalarlo en el repositorio local de Maven usando el siguiente comando:
		```
		mvn install:install-file -Dfile="<ruta>\ojdbc6.jar"
		-DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0.4 -Dpackaging=jar
		```