# ProyectoRedesEntregable2

Este proyecto cuenta con dos modulos separados que son un servidor y un cliente, los cuales permiten establecer una conexion FTP en ellos, esto con el fin de transpasar y almacenar archivos.

Software Requerido Para el cliente JAVA 1.8.0 -Versión IDE de Java JDK Java 8 - Version del JDK NetBeans 8.2 - Versión del IDE

Para el server JAVA 1.8.0 -Versión IDE de Java JDK Java 8 - Version del JDK NetBeans 8.2 - Versión del IDE MySQL Workbench 8.0 - Versión de la base de datos

Clonar Proyecto Utilizando la información previa, se instalan las herramientas. Una vez están listas se puede clonar el repositorio.

Abrir NetBeans ir a Team->Git->Clone En el campo que dice Url del repositorio se ingresa https://github.com/EyleenQ/ProyectoRedesEntregable2.git En los campo para usuario y clave se ingresar las credenciales correctas. Después dar clic al botón de siguiente y se debe seleccionar la casilla que dice master.


## Instalar Proyecto

Una vez se tenga el proyecto clonado en NetBeans se debe hacer lo siguiente: Abrir el proyecto que desea ejecutar(FTPServer o FTPCliente) Buscar la raíz del proyecto y posicionarse sobre esta, específicamente sobre el nombre Revisar que tenga los requerimientos asociados mencionados. Colocarse en la raíz del proyecto. Seleccionar clic derecho sobre el proyecto>Run.

## Setup Base de datos

Abrir MySQL Workbench 8.0 Selecciona la opción de agregar nueva conexión. Para crear la base de datos, solamente debe seguir la estructura que se muestra [aquí](https://github.com/EyleenQ/ProyectoRedesEntregable2/wiki/Script-Base-de-Datos).
Una vez creada, se debe acceder a la carpeta del proyecto>Source Packages>ConexionBaseDatos>ConexionSQL e ingresar las credenciales necesarias para que pueda realizar la conexión con la base de datos de acuerdo con la dirección del servidor de base de datos que esté utilizando.

## Ejecutar Aplicación

Cuando todos los requerimientos estén instalados y listos, se podrá ejecutar la aplicación siguiendo los siguientes pasos:

1. Abrir el proyecto en NetBeans 8.2 2. 

2. Clic derecho sobre el proyecto que fue anteriormente descargado.

3. Seleccionar el botón de Run.

## Resultados

Cuando el aplicación esta corriendo

### El Servidor:

Podremos ingresar los datos de un nuevo usuario y registrarlos en la base de datos y crear su carpeta asociada. También se podrá permitir la conexión con los clientes, así como enviar, recibir y eliminar archivos del cliente conectado.

### El Cliente:

Tendremos la opción de ingresar los datos para establecer conexión con el servidor. Una vez establecida la conexión, si no hay una carpeta en el cliente con el usuario que se conecto, entonces se creara una con el nombre del usuario y se cargaran los archivos que estan en el servidor en su carpeta asociada. En el caso contrario de que si exista una carpeta asociada, se comprobaran los archivos de ambas para que los datos del servidor y el cliente sean iguales, para esto se enviaran y recibirán los archivos necesarios.

Una vez finalizado completamente la conexión inicial, si le cliente realiza algún cambio en su carpeta, como añadir, eliminar o modificar un archivo, automáticamente estos cambios serán realizados en la carpeta asociada en el servidor.
