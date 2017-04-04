/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricar
 */
public class Interfaces {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while ( e.hasMoreElements() ) {
                try {
                    NetworkInterface iface = (NetworkInterface) e.nextElement();
                    if ( !iface.isLoopback() && !iface.getDisplayName().contains("Virtual") && iface.isUp() && !iface.getDisplayName().contains("Teredo")) {
                        byte[] macAddress = iface.getHardwareAddress();
                        String message = String.format(
                                "%s %s %s parent: %s",
                                iface.getDisplayName(),
                                iface.getName(),
                                Arrays.toString(macAddress ),
                                iface.getParent() );
                        System.out.println( message );
                    }       } catch (SocketException ex) {
                        Logger.getLogger(Interfaces.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }       } catch (SocketException ex) {
            Logger.getLogger(Interfaces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
