/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sockets;

import java.io.File;

/**
 *
 * @author Eyleen
 */
public class ManejoArchivos {
    
    public ManejoArchivos(){
    
    }
    
    public String ObtenerArchivos(String nombreUsuario){
        String archivosUsuario = "";
        String sCarpAct = "Archivos/"+nombreUsuario;
        File carpeta = new File(sCarpAct);
        
        File[] archivos = carpeta.listFiles();
    if (archivos == null || archivos.length == 0) {
        System.out.println("No hay elementos dentro de la carpeta actual");
    }
    else {
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        int contador = 0;
        for (int i=0; i< archivos.length; i++) {
            File archivo = archivos[i];
//            System.out.println(String.format("%s",
//                    archivo.getName()
//                    ));
            if(contador == 0){
                archivosUsuario=archivo.getName();
                contador++;
            }else{
                archivosUsuario=archivosUsuario+"!--!"+archivo.getName();
            }
            
        }
    }
    return archivosUsuario;
    }
}
