/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import servidor.Ordenes;

/**
 *
 * @author Ricar
 */
public class CompartirPagina extends javax.swing.JFrame {

    String hostName;
    public CompartirPagina(String hostName) {
        initComponents();
        //guardamos los datos recibidos
        this.hostName=hostName;
        iniciarVentana();
        //ponemos el titulo de la ventana
        this.setTitle("Compartir con todos los Equipos");
    }
    public CompartirPagina(String nombre,String hostname)
    {
        initComponents();
        //guardamos los datos recibidos
        this.hostName=hostname;
        iniciarVentana();
        //ponemos el titulo a la ventana
        this.setTitle("Compartir con "+ nombre);
    }
    private void iniciarVentana()
    {
        //centramos la ventana
        this.setLocationRelativeTo(null);
        //hacemos visible la ventana
        this.setVisible(true);
        //Hacemos que no se pueda redimencionar la ventana
        this.setResizable(false);
        //ponemos el icono a la ventana
        this.setIconImage(Principal.getLogo());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_Pagina = new javax.swing.JTextField();
        btn_Enviar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Compartir Pagina");

        btn_Enviar.setText("Enviar");
        btn_Enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EnviarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_Pagina)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(173, Short.MAX_VALUE)
                .addComponent(btn_Enviar)
                .addGap(164, 164, 164))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_Pagina, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_Enviar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_EnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EnviarActionPerformed
       //enviamos la orden de compartir o enviar pagina
        new Ordenes().enviarPagina(hostName, txt_Pagina.getText());
    }//GEN-LAST:event_btn_EnviarActionPerformed

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
            java.util.logging.Logger.getLogger(CompartirPagina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CompartirPagina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CompartirPagina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CompartirPagina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CompartirPagina("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Enviar;
    private javax.swing.JTextField txt_Pagina;
    // End of variables declaration//GEN-END:variables
}
