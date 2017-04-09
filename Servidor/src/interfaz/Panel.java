/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.Component;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import servidor.OrdenarTareas;

public class Panel extends javax.swing.JPanel {

    List<String []> tareas;     //Lista de tareas
    InetAddress ip;     //direccion del cliente
    
    public Panel(ArrayList<String []> datos,InetAddress ip) {
        initComponents();
        //guardamos la lista de tareas
        tareas=datos;
        //guardamos la direccion del cliente
        this.ip=ip;
        //ponemos la barra verticar de desplazamiento mas rapida
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(10);
        //agregamos los procesos
        agregarProcesos(0);
    }
    
public void agregarProcesos(int orden){
    //removemos todos los paneles de tareas
        jPanel1.removeAll();
        //reordenamos las tareas 
        Collections.sort(tareas,new OrdenarTareas(orden));
        //volvemos a mostrar las tareas
        for (String[] dato : tareas) {
            jPanel1.add(new proceso(dato[3],dato[0],dato[1],dato[2],ip));
        }
        //actualizamos la interfaz grafica de los componentes
        this.updateUI();
        jPanel1.updateUI();
        jScrollPane1.updateUI();
    }

    public  void eliminarProceso(Component p){
        //removemos el panel del cual se pidio su cierre
        this.remove(p);
        //actualizamos la interfaz grafica
        this.updateUI();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        jPanel1.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
