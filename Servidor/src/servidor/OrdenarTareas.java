/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.util.Comparator;

/**
 *
 * @author Ricar
 */
public class OrdenarTareas implements Comparator<String[]>{
    private int opc;
public OrdenarTareas(int tipo){
    opc=tipo;
    }
    @Override
    public int compare(String[] t, String[] t1) {
        return t[opc].compareToIgnoreCase(t1[opc]);
    }
}
