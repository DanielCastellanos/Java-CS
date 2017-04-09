/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import javax.swing.JOptionPane;
import servidor.Hint;
import servidor.Ordenes;

/**
 *
 * @author PC11
 */
public class Bloquear extends javax.swing.JFrame {

    String nombre; //nombre del usuario
    String ip;     //direccion del usuario
    Ordenes ordenes=new Ordenes();
    
    public Bloquear(String grupo) {
        initComponents();
        //ponemos el titulo a la ventana
        this.setTitle("Bloquear");
        iniciarVentana();
        //guardamos la direccion recibida
        this.ip=grupo;
    }

    public Bloquear(String nombre, String ip) {
        initComponents();
        //ponemos el titulo a la ventana
        this.setTitle("Bloquear");
        iniciarVentana();
        //capturamos la información recibida
        this.nombre=nombre;
        this.ip=ip;
        //personalizamos las etiquetas con el nombre del usuario
        this.aviso1.setText("El equipo '"+nombre+"' será bloqueado");
        this.aviso2.setText("Ingrese el tiempo que durará bloqueado '"+nombre+"'");
    }
    private void iniciarVentana()
    {
        //centramos la ventana
        this.setLocationRelativeTo(null);
        //colocamos el icono
        this.setIconImage(Principal.getLogo());
        //dejamos que la ventana no se pueda redimensionar
        this.setResizable(false);
        //hacemos la ventana visible
        this.setVisible(true);
        //seleccionamos la opción login ppor defecto
        login.setSelected(true);
        //agregamos pista/Hint al campo de texto de tiempo
        tiempo.setUI(new Hint("Tiempo"));
        //deshabilitamos los campos de bloqueo
        tiempo.setEnabled(false);
        medida.setEnabled(false);
    }
    //metodo para calcular las horas o minutos
    private String calcularTiempo()
    {
        //obtenemos el texto del campo "Texto"
        int cantidad=Integer.getInteger(this.tiempo.getText());
        //Verificamos la opción seleccionada
        if(medida.getSelectedIndex() == 0)
        {
            //si fueron minutos tomamos la cantidad y la multiplicamos por
            //60000 que es un minuto en milisegundos
            cantidad=cantidad*60000;
        }
        else
        {
            //si la seleccion fue de Horas multiplicaremos la cantidad por
            //3600000 que es una hora en milisegundos
            cantidad=cantidad*3600000;
        }
        return cantidad+"";
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        aviso1 = new javax.swing.JLabel();
        aceptar = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        login = new javax.swing.JCheckBox();
        tiempo = new javax.swing.JTextField();
        medida = new javax.swing.JComboBox();
        aviso2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        aviso1.setText("Todas los equipos dentro del grupo se bloquearán");

        aceptar.setText("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });

        cancelar.setText("Cancelar");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });

        login.setText("Login");
        login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });

        medida.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Minutos", "Horas" }));

        aviso2.setText("Tiempo de bloqueo sin login habilitado");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(197, Short.MAX_VALUE)
                        .addComponent(cancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(aceptar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(aviso1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(medida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(aviso2)
                            .addComponent(login))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(aviso1)
                .addGap(21, 21, 21)
                .addComponent(login)
                .addGap(10, 10, 10)
                .addComponent(aviso2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(medida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aceptar)
                    .addComponent(cancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelarActionPerformed

    private void loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginActionPerformed
        if (!login.isSelected()) {
            tiempo.setEnabled(true);
            medida.setEnabled(true);
        }else{
            tiempo.setEnabled(false);
            medida.setEnabled(false);
        }
    }//GEN-LAST:event_loginActionPerformed

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
        //Bloqueo sin contraseña
        if (!login.isSelected()) {
            try {
                //verificamos que el tiempo de bloqueo no sobrepase las 12 horas
                if ((Integer.parseInt(tiempo.getText()) > 12 && medida.getSelectedIndex() == 1) || (Integer.parseInt(tiempo.getText()) > 720 && medida.getSelectedIndex() == 0)) {
                    JOptionPane.showMessageDialog(null, "No puede establecer un bloqueo por más de 12 horas", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    //mandamos orden de bloqueo y el tiempo que durara bloqueada
                        ordenes.bloqueo(this.ip,tiempo.getText());
                        //cerramos la ventana
                        this.dispose();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "El valor '" + tiempo.getText() + "' no es un valor numérico", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        //bloqueo con contraseña
        else {
            //mandamos orden de login
            ordenes.login(this.ip);
            //cerramos la ventana
            this.dispose();
        }
    }//GEN-LAST:event_aceptarActionPerformed

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
            java.util.logging.Logger.getLogger(Bloquear.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Bloquear.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Bloquear.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Bloquear.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Bloquear("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptar;
    private javax.swing.JLabel aviso1;
    private javax.swing.JLabel aviso2;
    private javax.swing.JButton cancelar;
    private javax.swing.JCheckBox login;
    private javax.swing.JComboBox medida;
    private javax.swing.JTextField tiempo;
    // End of variables declaration//GEN-END:variables
}
