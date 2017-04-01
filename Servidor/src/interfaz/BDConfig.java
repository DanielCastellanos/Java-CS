/*
 * GUI para configurar la conexión a la base de datos
 */
package interfaz;

import hibernate.HibernateUtil;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import org.hibernate.HibernateException;
import servidor.ArchivoConf;

public class BDConfig extends javax.swing.JFrame {

    //Variables de clase
    private String URL;
    private String usr;
    private String pass;
    ArchivoConf settingsFile;
    ImageIcon icon = new ImageIcon("src/iconos/logo chico.png");

    public BDConfig(ArchivoConf conf) {
        initComponents();
        this.setIconImage(icon.getImage());
        this.settingsFile= conf;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        UrlField = new javax.swing.JTextField();
        passField = new javax.swing.JTextField();
        usrField = new javax.swing.JTextField();
        btnDone = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        errorLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Conectar a Base de Datos");

        jLabel2.setText("URL");

        jLabel3.setText("Usuario");

        jLabel4.setText("Constraseña");

        UrlField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UrlFieldActionPerformed(evt);
            }
        });

        passField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passFieldActionPerformed(evt);
            }
        });

        usrField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usrFieldActionPerformed(evt);
            }
        });

        btnDone.setText("Listo");
        btnDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoneActionPerformed(evt);
            }
        });

        jButton1.setText("Trabajar sin base de datos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        errorLabel.setForeground(new java.awt.Color(255, 51, 51));
        errorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(101, 101, 101)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passField)
                            .addComponent(usrField)
                            .addComponent(UrlField, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(btnDone)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(30, 30, 30))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95))
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(errorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(UrlField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usrField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(passField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDone)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(errorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void UrlFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UrlFieldActionPerformed
        submit();
    }//GEN-LAST:event_UrlFieldActionPerformed

    private void usrFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usrFieldActionPerformed
        submit();
    }//GEN-LAST:event_usrFieldActionPerformed

    private void passFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passFieldActionPerformed
        submit();
    }//GEN-LAST:event_passFieldActionPerformed

    private void btnDoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoneActionPerformed
        submit();
    }//GEN-LAST:event_btnDoneActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void submit() {

        editable(false);

        URL = UrlField.getText();
        usr = usrField.getText();
        pass = passField.getText();

        if (URL.length() * usr.length() != 0) {
            try {
                HibernateUtil.buildSessionFactory(URL, usr, pass);
                settingsFile.setURLBD(URL);
                settingsFile.setUserBD(usr);
                settingsFile.setPassBD(pass);
                JOptionPane.showMessageDialog(null, "Conexión con BD establecida", "Conexión establecida", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();

            } catch (HibernateException ex) {
                errorLabel.setText("Error de conexión: Verifique sus datos");
                editable(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Valores inválidos", "Error de valores nulos", JOptionPane.WARNING_MESSAGE);
            editable(true);
        }
    }

    private void editable(boolean flag) {
        UrlField.setEditable(flag);
        usrField.setEditable(flag);
        passField.setEditable(flag);
        btnDone.setEnabled(flag);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField UrlField;
    private javax.swing.JButton btnDone;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField passField;
    private javax.swing.JTextField usrField;
    // End of variables declaration//GEN-END:variables
}
