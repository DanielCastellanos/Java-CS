/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import servidor.Hint;
import servidor.Ordenes;

public class Apagar extends javax.swing.JFrame {

    private Ordenes orden=new Ordenes();
    private String hostname; 
    private boolean individual=false;
    public Apagar(String grupo) {
        initComponents();
        this.setTitle("Apagar");
        tiempo.setUI(new Hint("Tiempo"));
        tiempo.setEnabled(false);
        medida.setEnabled(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setIconImage(Principal.getLogo());
        this.hostname=grupo;
    }
    
    public Apagar(String nombre,String ip ) {
        initComponents();
        this.setTitle("Apagar "+nombre);
        tiempo.setUI(new Hint("Tiempo"));
        tiempo.setEnabled(false);
        medida.setEnabled(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setIconImage(Principal.getLogo());
        this.aviso1.setText("El equipo '"+nombre+"' se apagará inmediatamente");
        this.aviso2.setText("Ingrese el tiempo que tardará '"+nombre+"' para apagarse");
        individual=true;
        hostname=ip;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        check1 = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        check2 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        aceptar = new javax.swing.JButton();
        aviso1 = new javax.swing.JLabel();
        medida = new javax.swing.JComboBox();
        tiempo = new javax.swing.JTextField();
        aviso2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        check1.setSelected(true);
        check1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check1ActionPerformed(evt);
            }
        });

        check2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Apagar Inmediatamente");

        jLabel2.setText("Programar Apagado");

        aceptar.setText("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });

        aviso1.setText("Todas las computadoras dentro del grupo se apagarán");

        medida.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Horas", "Minutos" }));

        aviso2.setText("Apagar todas las computadoras dentro de...");

        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 226, Short.MAX_VALUE)
                        .addComponent(check2)
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(check1)
                        .addGap(29, 29, 29))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(aviso1, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(aceptar))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(medida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addComponent(aviso2))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(check1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(14, 14, 14)
                .addComponent(aviso1)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(check2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(aviso2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(medida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aceptar)
                    .addComponent(jButton1))
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void check1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check1ActionPerformed
        check2.setSelected(false);
        medida.setEnabled(false);
        tiempo.setEnabled(false);
    }//GEN-LAST:event_check1ActionPerformed

    private void check2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check2ActionPerformed
            check1.setSelected(false);
           medida.setEnabled(true);
        tiempo.setEnabled(true);
    }//GEN-LAST:event_check2ActionPerformed

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
        if (individual) {//Si la instrucción va a una sola PC
            if (check1.isSelected()) {
                //Envia instrucción de apagado
                orden.apagar(hostname);
            } else if (check2.isSelected()) {
                //Envia instrucción de apagado programado
                orden.autoApagado(hostname,tiempo.getText());
            }
        } else //Si la instrucción es grupal
            if (check1.isSelected()) {
                orden.apagar(hostname);
            //Envia instrucción de apagado
        } else if (check2.isSelected()) {
            orden.autoApagado(hostname,tiempo.getText());
            //Envia instrucción de apagado programado
        }
        this.dispose();
    }//GEN-LAST:event_aceptarActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

        this.dispose();
    }//GEN-LAST:event_formWindowClosed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

       this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Apagar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Apagar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Apagar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Apagar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Apagar("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptar;
    private javax.swing.JLabel aviso1;
    private javax.swing.JLabel aviso2;
    private javax.swing.JCheckBox check1;
    private javax.swing.JCheckBox check2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JComboBox medida;
    private javax.swing.JTextField tiempo;
    // End of variables declaration//GEN-END:variables
}
