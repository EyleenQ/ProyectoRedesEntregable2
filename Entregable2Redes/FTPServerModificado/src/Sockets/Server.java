/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sockets;

import ConexionBaseDatos.UsuarioData;
import Domain.Usuario;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.sql.SQLException;
//para encriptar
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


/**
 *
 * @author Eyleen
 */
public class Server {
     private ServerSocket serverSocket;
     private static Key key;
 
    public void start(int port) throws IOException, NoSuchAlgorithmException {
        serverSocket = new ServerSocket(port);
        // Generamos una clave de 128 bits adecuada para AES
      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      keyGenerator.init(128);
      key = keyGenerator.generateKey();
      
      // Alternativamente, una clave que queramos que tenga al menos 16 bytes
      // y nos quedamos con los bytes 0 a 15
      key = new SecretKeySpec("entregable2Redes".getBytes(),  0, 16, "AES");
      
        while (true)
            new ClientHandler(serverSocket.accept()).start();
    }
 
    public void stop() throws IOException {
        serverSocket.close();
    }
 
    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        
        private OutputStream outArchivo;
        private InputStream inArchivo;
        private String nombreArchivo="";
 
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
 
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException ex) {
                //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                //System.out.println("1.Error al establecer conexion");
            }
             
            String inputLine;
            try {
                int contador = 0;
                
                boolean enviar=false;
                boolean recibir=false;
                Usuario u =new Usuario();
                UsuarioData ud = new UsuarioData();
                String archivosUsuario = "";
                
                while ((inputLine = in.readLine()) != null ) {
                    //validar sesion
                    if(contador==0){
                        u.setNombreUsuario(inputLine);
                    }
                    if(contador == 1){
                        u.setContrasenna(inputLine);
                        Usuario recibido = ud.extraerUsuario(u.getNombreUsuario());
                        if(recibido.getContrasenna()!= null && recibido.getContrasenna().equals(u.getContrasenna())){
                            out.println("Correcto");
                            ManejoArchivos ma = new ManejoArchivos();
                            archivosUsuario = ma.ObtenerArchivos(u.getNombreUsuario());
                            out.println(archivosUsuario);
                            System.out.println("El usuario "+u.getNombreUsuario()+" esta conectado");
                        }else{
                            out.println("Incorrecto");
                        }
                    }
                    contador= contador+1;
                   //fin validar sesion
                   
                   //Si el msj del cliente=Enviar, significa que voy a recibir algo
                   if(inputLine.equals("Enviar")){
                        nombreArchivo = in.readLine();
                        recibirArchivo(u.getNombreUsuario(),nombreArchivo);
                   }
                   
                   //Si el msj del cliente=Recibir, significa que voy a enviar algo
                   if(inputLine.equals("Recibir")){
                       nombreArchivo = in.readLine();
                       enviarArchivo(u.getNombreUsuario(),nombreArchivo);
                   }
                   
                   if(inputLine.equals("Crear")){
                       nombreArchivo = in.readLine();
                       recibirArchivo(u.getNombreUsuario(),nombreArchivo);
                   }
                   
                   if(inputLine.equals("Eliminar")){
                       nombreArchivo = in.readLine();
                       eliminarArchivo(u.getNombreUsuario(), nombreArchivo);
                   }
                   
                   if(inputLine.equals("Modificar")){
                       nombreArchivo = in.readLine();
                       recibirArchivo(u.getNombreUsuario(),nombreArchivo);
                   }
                }
            } catch (IOException ex) {
                //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Conexion finalizada");
            } catch (SQLException ex) {
                //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);               
                //System.out.println("Conexion perdida con Base de datos");
            } catch (NoSuchAlgorithmException ex) {
             //   Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchPaddingException ex) {
             //   Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalBlockSizeException ex) {
           //     Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
             //   Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
             //   Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
 
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException ex) {
                //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                //System.out.println("2.Error al establecer conexion");
            }
           
        }
        
        public void enviarArchivo(String nombreUsuario, String nombreArchivo) throws FileNotFoundException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException{
            File archivoEnviar = new File("Archivos/"+nombreUsuario+"/"+nombreArchivo);
            File archivo = new File(archivoEnviar.getAbsolutePath());
           // Se obtiene un cifrador AES
            Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");  

            aes.init(Cipher.ENCRYPT_MODE, key);
            byte[] encriptado = aes.doFinal(Files.readAllBytes(archivo.toPath()));
            BufferedOutputStream bos = new BufferedOutputStream(clientSocket.getOutputStream());

            int tamanio = (int)encriptado.length;  
            out.println(tamanio);
            System.out.println("Tamanio archivo: "+tamanio);

            for(int i=0; i<encriptado.length; i++){
                bos.write(encriptado[i]);
            }

            bos.close();
            System.out.println("Archivo Enviado");

        }
        
        public void recibirArchivo(String nombreUsuario, String nombreArchivo) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{        
            Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
            aes.init(Cipher.DECRYPT_MODE, key);
            
            File fichero = new File ("Archivos/"+nombreUsuario+"/"+nombreArchivo);
            String tamanio = in.readLine();
            int tam = Integer.parseInt(tamanio);
            System.out.println("Tamanio archivo: "+tam);
            
            FileOutputStream fos = new FileOutputStream("Archivos/"+nombreUsuario+"/"+nombreArchivo);
            BufferedInputStream inF = new BufferedInputStream(clientSocket.getInputStream());
            
            byte[] encriptado = new byte[tam];
            for(int i=0; i < encriptado.length; i++){
                encriptado[i] = (byte) inF.read();
            }
            String noseperostring = Base64.encode(encriptado);
            byte[] encryptedBytes=Base64.decode(noseperostring.replace("\n", "") );
            byte[] desencriptado = aes.doFinal(encryptedBytes);
            fos.write(desencriptado);

            fos.close();
            System.out.println("Recibido");          
        }
        
        public void eliminarArchivo(String nombreUsuario, String nombreArchivo){
            File fichero = new File("Archivos/"+nombreUsuario+"/"+nombreArchivo);
            if (fichero.delete())
                System.out.println("Archivo "+nombreArchivo+" borrado");
             else
                System.out.println("Archivo no borrado");
        }
    }
}
