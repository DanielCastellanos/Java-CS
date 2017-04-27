/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricar
 */
public class Interfaces {
    private ArrayList<NetworkInterface> interfaces=new ArrayList<>();
    public Interfaces()
    {
        try {
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                try {
                    NetworkInterface iface = (NetworkInterface) e.nextElement();
                    if (!iface.isLoopback() && !iface.getDisplayName().contains("Virtual") && !iface.getDisplayName().contains("Teredo") && iface.getMTU() >= 1450) {
                        this.interfaces.add(iface);
                    }
                } catch (SocketException ex) {
                    Logger.getLogger(Interfaces.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(Interfaces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ArrayList<NetworkInterface> getFisicalInterfaces()
    {
        return interfaces;
    }
    public NetworkInterface getFirstActiveInterface()
    {
        NetworkInterface netInterface=null;
        for (NetworkInterface iface : interfaces) {
            try {
                if(iface.isUp())
                {
                    netInterface=iface;
                    break;
                }
                    } catch (SocketException ex) {
                Logger.getLogger(Interfaces.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return netInterface;
    }
    public String getMAC()
    {
        String directions="";
        for (int i = 0; i < interfaces.size(); i++) {
                directions+=String.format("%s%s",byteToHex(interfaces.get(i)),(i<(interfaces.size()-1))?"|":"");
        }
        return (directions.isEmpty()? null:directions);
    }
    public static String byteToHex(NetworkInterface ni)
    {
       
            String hex="[";
        try {
            byte array[]=ni.getHardwareAddress();
            for (int i = 0; i < array.length; i++) {
            hex+=String.format("%02X%s%s",array[i],(i<array.length-1)?"-":"",((i+1)==array.length)?"]":"");
            }
        } catch (SocketException ex) {
            Logger.getLogger(Interfaces.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (hex.equals("[")? null:hex);
    }
    
}
