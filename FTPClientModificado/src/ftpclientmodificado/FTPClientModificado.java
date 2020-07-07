/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclientmodificado;

import java.io.IOException;
import java.util.*;
import java.io.*;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

/**
 *
 * @author Steven
 */
public class FTPClientModificado {

    /**
     * @param args the command line arguments
     */
    public static InterfazCliente interfazCliente;
            
    public static void main(String[] args) throws IOException {     
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        //Schedule a job for the event-dispatching thread:
        //adding TrayIcon.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
        
        interfazCliente = new InterfazCliente();
        interfazCliente.setVisible(true);    
    }
    
    private static void createAndShowGUI() {
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            System.out.println("Tray no soportado");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        Image img = createImage("FLOR.gif", "tray icon");
        final TrayIcon trayIcon = new TrayIcon(img);
        final SystemTray tray = SystemTray.getSystemTray();
        
        // Create a popup menu components
        MenuItem nombreItem = new MenuItem("Cliente");
        MenuItem openItem = new MenuItem("Abrir");
        MenuItem exitItem = new MenuItem("Salir");
        
        //Add components to popup menu
        popup.add(nombreItem);
        popup.add(openItem);
        popup.add(exitItem);
        
        trayIcon.setPopupMenu(popup);
        
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon no se añadio.");
            return;
        }
        
        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Tray ejecutandose");
            }
        });
        
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 interfazCliente.setVisible(true);    
            }
        });
        
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
    }
    
    //Obtener URL
    protected static Image createImage(String path, String description) {
        URL imageURL = FTPClientModificado.class.getResource(path);
        
        if (imageURL == null) {
            System.err.println("Recurso no encontrado: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }

}

