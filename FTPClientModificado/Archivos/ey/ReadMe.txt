
Este proyecto cuenta con dos modulos separados que son un servidor y un cliente, los cuales permiten establecer una conexion FTP en ellos, esto con el fin de transpasar y alamcenar archivos.

Software Requerido
Para el cliente
JAVA 1.8.0 -Version IDE de Java
NetBeans 8.2 - Version del IDE

Para el server
JAVA 1.8.0 -Version IDE de Java
NetBeans 8.2 - Version del IDE
MySQL Workbench 8.0 - Version de la base de datos

Clonar Proyecto
Utilizando la informacion previa, se instalan las herramientas. Una vez estan listas se puede clonar el repositorio.

Abrir NetBeans ir a Team->Git->Clone
En el campo que dice url del repositorio se ingresa https://github.com/EyleenQ/ProyectoRedesEntregable1.git	
En los campo para usuario y clave se ingresar las credenciales correctas
Despues dar clic al boton de siguiente y se debe seleccionar la casilla que dice master

////de aqui para arriba ya esta en git



Instalar Proyecto

Una vez se tenga el proyecto clonado en Netbeans se debe hacer lo siguiente:
Abrir el proyecto que desea ejecutar(FTPServer o FTPCliente)
Buscar la raiz del proyecto y posicionarse sobre esta, especificamente sobre el nombre
Revisar que tenga los requerimientos asociados mecionados
Colocarse en la raiz del proyecto
Seleccionar clic derecho sobre el proyecto>Run


Setup Base de datoss

Abrir MySQL Workbench 8.0
Selecciona la opcion de agregar nueva conexion.
Ingresar las credenciales que s eencuentran guardadas en el proyecto, para obtenerlas:
	Abrir el proyecto>Source Packages>ConexionBaseDatos>ConexionSQL
Una vez conectado busca la base de datos nombrada en el proyecto, ubicados en la misma seccion mencionada.


Ejecutar Aplicacion

Cuando todos los requerimeintos esten instalados y listos, se podra ejecutar la aplicacion siguiendo los siguentes pasos: 
1. Abrir NetBeans 8.2
2.Clic derecho sobre el proyecto que fue anteriormente descargardo
3. Selecionar el boton de Run

Resultados

Cuando el aplicacion esta corriendo

El Servidor:

Podremos ingresar los datos de un nuevo usuario y registrarlos en la base de datos y crear su carpeta asociada.
Tambien se podra permitir la conexion con los clientes, asi como enviar y resibir archivos del cliente conectado.

El Cliente:

Tendremos la opcion de ingresar los datos para establecer conexion con el servidor.
Una vez establecidad la conexion se tendra la opcion de enviar archivos al servidor para guardalos en su carpeta asociada.
Tambien se podra recibir archivos que se encuentren guardados en la carpeta asociada en el servidor.