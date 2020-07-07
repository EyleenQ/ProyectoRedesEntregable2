/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclientmodificado;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import javax.swing.JFileChooser;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Steven
 */
public class Cliente {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private InputStream fileIn;
    private OutputStream fileOut;
    private static Key key;

    public void iniciarConeccion(String ip, int port) throws IOException, NoSuchAlgorithmException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
           // Generamos una clave de 128 bits adecuada para AES
      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      keyGenerator.init(128);
      key = keyGenerator.generateKey();
      
      // Alternativamente, una clave que queramos que tenga al menos 16 bytes
      // y nos quedamos con los bytes 0 a 15
      key = new SecretKeySpec("entregable2Redes".getBytes(),  0, 16, "AES");
      
    }

    public void enviarMensaje(String msg) throws IOException {
        out.println(msg);
    }

    public String recibirMensaje() throws IOException {
        String respuesta = in.readLine();
        return respuesta;
    }

    public String[] recibirListadoArchivos() throws IOException {
        String respuesta = in.readLine();

        String listadoArchivos[] = respuesta.split("!--!");

        return listadoArchivos;
    }

    public void enviarArchivo(String rutaArchivo, String ip, int puerto, String usuario, String contrasenia) throws FileNotFoundException, IOException, InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException {
        File archivo = new File(rutaArchivo);
   
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
        clientSocket.close();
        System.out.println("Archivo Enviado");

        this.iniciarConeccion(ip, puerto);
        this.enviarMensaje(usuario);
        this.enviarMensaje(contrasenia);
        String respuesta = this.recibirMensaje();
        String listadoArchivos[] = this.recibirListadoArchivos();
    }

    public void recibirArchivo(String archivoADescargar, String rutaDestino, String ip, int puerto, String usuario, String contrasenia) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aes.init(Cipher.DECRYPT_MODE, key);
  
        File fichero = new File (rutaDestino+'/'+archivoADescargar);          
        String tamanio = in.readLine();
        int tam = Integer.parseInt(tamanio);
        System.out.println("Tamanio archivo: "+tam);

        FileOutputStream fos = new FileOutputStream(rutaDestino+'/'+archivoADescargar);
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
        clientSocket.close();
        System.out.println("Recibido");
            
        this.iniciarConeccion(ip, puerto);
        this.enviarMensaje(usuario);
        this.enviarMensaje(contrasenia);
        String respuesta = this.recibirMensaje();
        String listadoArchivos[] = this.recibirListadoArchivos();
    }

     public void CrearArchivo(String rutaArchivo, String nombreArchivo, String ip, int puerto, String usuario, String contrasenia) throws FileNotFoundException, IOException, InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException {
        enviarMensaje("Crear");
        enviarMensaje(nombreArchivo);
        enviarArchivo(rutaArchivo, ip, puerto, usuario, contrasenia);
    }
    
    public void EliminarArchivo(String nombreArchivo, String ip, int puerto, String usuario, String contrasenia) throws FileNotFoundException, IOException, InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException {
        enviarMensaje("Eliminar");
        enviarMensaje(nombreArchivo);
        clientSocket.close();
        
        System.out.println("Eliminado");
            
        this.iniciarConeccion(ip, puerto);
        this.enviarMensaje(usuario);
        this.enviarMensaje(contrasenia);
        String respuesta = this.recibirMensaje();
        String listadoArchivos[] = this.recibirListadoArchivos();
    } 
     
    public void ModificarArchivo(String rutaArchivo, String nombreArchivo,String ip, int puerto, String usuario, String contrasenia) throws FileNotFoundException, IOException, InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException {
        enviarMensaje("Modificar");
        enviarMensaje(nombreArchivo);
        enviarArchivo(rutaArchivo, ip, puerto, usuario, contrasenia);
    }
    
     public void actualizarDirectorio(String[] listaArchivos, String rutaDestino, String ip, int puerto, String usuario, String contrasenia) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException{
        String archivosUsuario = "";
        File carpeta = new File(rutaDestino);       
        File[] archivos = carpeta.listFiles();
        int contador = 0;
        for (int i=0; i< archivos.length; i++) {
            File archivo = archivos[i];
            if(contador == 0){
                archivosUsuario=archivo.getName();
                contador++;
            }else{
                archivosUsuario=archivosUsuario+"!--!"+archivo.getName();
            }
            
        }
        String misArchivos[] = archivosUsuario.split("!--!");
        boolean estado = true;
        if(listaArchivos[0].compareTo("") == 0){
        }else{
            if(misArchivos[0].compareTo("") == 0){   
                for(int i =0; i<listaArchivos.length; i++){
                    enviarMensaje("Recibir");
                    enviarMensaje(listaArchivos[i]); 
                    recibirArchivo(listaArchivos[i],rutaDestino, ip, puerto, usuario, contrasenia);
                }
            }else{
                for(int i =0; i<listaArchivos.length; i++){
                    for(int j= 0; j<misArchivos.length; j++){
                        if(listaArchivos[i].compareToIgnoreCase(misArchivos[j]) == 0){
                            estado = false;
                        }               
                     }
                    if(estado){
                        enviarMensaje("Recibir");
                        enviarMensaje(listaArchivos[i]); 
                        recibirArchivo(listaArchivos[i],rutaDestino, ip, puerto, usuario, contrasenia);
                    }
                    estado = true;
                }
            }
        }
        
    } 
     
     public void actualizarServidor(String[] listaArchivos, String rutaDestino, String ip, int puerto, String usuario, String contrasenia) throws IOException, FileNotFoundException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException{
        String archivosUsuario = "";
        File carpeta = new File(rutaDestino);       
        File[] archivos = carpeta.listFiles();
        int contador = 0;
        for (int i=0; i< archivos.length; i++) {
            File archivo = archivos[i];
            if(contador == 0){
                archivosUsuario=archivo.getName();
                contador++;
            }else{
                archivosUsuario=archivosUsuario+"!--!"+archivo.getName();
            }
            
        }
        String misArchivos[] = archivosUsuario.split("!--!");
        boolean estado = true;
        if(misArchivos[0].compareTo("") == 0){
        }else{
            if(listaArchivos[0].compareTo("") == 0){   
                for(int i =0; i<misArchivos.length; i++){
                    enviarMensaje("Enviar");
                    enviarMensaje(misArchivos[i]);
                    File archiEnviar = new File(rutaDestino+"/"+misArchivos[i]);  
                    enviarArchivo(archiEnviar.getAbsolutePath(), ip, puerto, usuario, contrasenia);
                }
            }else{
                for(int i =0; i<misArchivos.length; i++){
                    for(int j= 0; j<listaArchivos.length; j++){
                        if(misArchivos[i].compareToIgnoreCase(listaArchivos[j]) == 0){
                            estado = false;
                        }               
                     }
                    if(estado){
                        enviarMensaje("Enviar");
                        enviarMensaje(misArchivos[i]);
                        File archiEnviar = new File(rutaDestino+"/"+misArchivos[i]);  
                        enviarArchivo(archiEnviar.getAbsolutePath(), ip, puerto, usuario, contrasenia);
                    }
                    estado = true;
                }
            }
        }
     }
     
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
