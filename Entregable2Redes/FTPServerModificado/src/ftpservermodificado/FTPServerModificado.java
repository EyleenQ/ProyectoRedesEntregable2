/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpservermodificado;

import GUI.VistaPrincipal ;
import Sockets.Server;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
/**
 *
 * @author Eyleen
 */
public class FTPServerModificado {
    public static VistaPrincipal vp;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException { 
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
        
         Server server = new Server();
        vp = new VistaPrincipal ();
        vp.setVisible(true);
        server.start(5555);       
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
        MenuItem nombreItem = new MenuItem("Servidor");
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
                 vp.setVisible(true); 
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
        URL imageURL = FTPServerModificado.class.getResource(path);
        
        if (imageURL == null) {
            System.err.println("Recurso no encontrado: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }

}
