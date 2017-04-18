/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.Image;
import javax.swing.ImageIcon;
import servidor.Clientes;

/**
 *
 * @author Ricar
 */
public class PropiedadesCliente extends javax.swing.JPanel {

    /**
     * Creates new form PropiedadesCliente
     */
    public PropiedadesCliente(Image icono,Clientes cliente) {
        initComponents();
        lblIcono.setIcon(new ImageIcon(icono.getScaledInstance(lblIcono.getWidth(), lblIcono.getHeight(), Image.SCALE_DEFAULT)));
        lblNombre.setText(lblNombre.getText()+" "+cliente.getNombre());
        lblHost.setText(lblHost.getText()+" "+cliente.getHostname());
        lblMarca.setText(lblMarca.getText()+" "+cliente.getMarca());
        lblModelo.setText(lblModelo.getText()+" "+cliente.getModelo());
        lblNumeroSerie.setText(lblNumeroSerie.getText()+" "+cliente.getNumeroSerie());
        lblMAC.setText(lblMAC.getText()+" "+cliente.getMac());
        lblProcesador.setText(lblProcesador.getText()+" "+cliente.getProcesador());
        lblHDD.setText(lblHDD.getText()+" "+cliente.getHdd());
        lblRAM.setText(lblRAM.getText()+" "+cliente.getRam());
        lblSistema.setText(lblSistema.getText()+" "+cliente.getSo());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNombre = new javax.swing.JLabel();
        lblHost = new javax.swing.JLabel();
        lblIcono = new javax.swing.JLabel();
        lblModelo = new javax.swing.JLabel();
        lblNumeroSerie = new javax.swing.JLabel();
        lblHDD = new javax.swing.JLabel();
        lblRAM = new javax.swing.JLabel();
        lblMAC = new javax.swing.JLabel();
        lblProcesador = new javax.swing.JLabel();
        lblMarca = new javax.swing.JLabel();
        lblSistema = new javax.swing.JLabel();

        lblNombre.setText("Nombre Cliente:");

        lblHost.setText("Hostname: ");

        lblModelo.setText("Modelo:");

        lblNumeroSerie.setText("N° Serie:");

        lblHDD.setText("HDD: ");

        lblRAM.setText("RAM: ");

        lblMAC.setText("direcciones MAC: ");

        lblProcesador.setText("Procesador: ");

        lblMarca.setText("Marca: ");

        lblSistema.setText("S.O : ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIcono, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNombre)
                            .addComponent(lblHost)
                            .addComponent(lblHDD)
                            .addComponent(lblRAM)
                            .addComponent(lblNumeroSerie)
                            .addComponent(lblMAC)
                            .addComponent(lblProcesador)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblModelo)
                                .addGap(47, 47, 47)))
                        .addGap(19, 156, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMarca)
                            .addComponent(lblSistema))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcono, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHost)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMarca)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblModelo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNumeroSerie)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMAC)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProcesador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHDD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRAM)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSistema)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblHDD;
    private javax.swing.JLabel lblHost;
    private javax.swing.JLabel lblIcono;
    private javax.swing.JLabel lblMAC;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblModelo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNumeroSerie;
    private javax.swing.JLabel lblProcesador;
    private javax.swing.JLabel lblRAM;
    private javax.swing.JLabel lblSistema;
    // End of variables declaration//GEN-END:variables
}