/*
 * GUI para configurar la conexión a la base de datos
 */
package interfaz;

import hibernate.HibernateUtil;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import servidor.Archivos;
import servidor.Configuracion;

public class BDConfig extends javax.swing.JFrame {

    //Variables de clase
    private String URL;
    private String usr;
    private String pass;
    Configuracion settingsFile;
    ImageIcon icon = new ImageIcon("src/iconos/logo chico.png");

    public BDConfig(Configuracion conf) {
        initComponents();
        this.setIconImage(icon.getImage());
        this.settingsFile= conf;
    }
    
    //Este constructor tiene el propósito de mostrar al usuario configuración previamente definida
    public BDConfig(Configuracion conf, String ur, String u, String p) {
        
        initComponents();
        this.setIconImage(icon.getImage());
        this.settingsFile= conf;
        
        UrlField.setText(ur);
        usrField.setText(u);
        passField.setText(p);
        
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
        btnNoBD = new javax.swing.JButton();
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

        btnNoBD.setText("Trabajar sin base de datos");
        btnNoBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNoBDActionPerformed(evt);
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
                        .addComponent(btnNoBD)
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
                    .addComponent(btnNoBD))
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

    private void btnNoBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNoBDActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnNoBDActionPerformed

    private void submit() {

        //inhabilita la edición de los campos y botones
        editable(false);
        
        //Obtiene información de los campos
        URL = UrlField.getText();
        usr = usrField.getText();
        pass = passField.getText();

        if (URL.length() * usr.length() != 0) {                 /*Si no estan vacíos los campos de usuario o url
                                                            Intentará realizar la conexión con esos datos
            */
            try {
                connect(URL, usr, pass);

            } catch (HibernateException ex) {           /*Si la conexión falla
                                                Avisamos al usuario y le pedimos que rectifique los datos
                */
                errorLabel.setText("Error de conexión: Verifique sus datos");
                //Habilitamos los controles para que el usuario vuelva a ingresar datos.
                editable(true);
            }
        } else {                //Advertimos al usuario sobre los valores vacíos
            JOptionPane.showMessageDialog(null, "Valores inválidos", "Error de valores nulos", JOptionPane.WARNING_MESSAGE);
            //Habilitamos los controles.
            editable(true);
        }
    }

    private void connect(String url, String user, String pass)throws HibernateException{
        
        HibernateUtil.buildSessionFactory(url, user, pass);          /*Invoca al método de construir la sesión
                                        Si en este punto no se ha obtenido una HibernateException significa que se
                                        creó exitosamente la session factory con los datos ingresados, así que los escribiremos
                                        en el objeto de configuración
                */
                settingsFile.setURLBD(url);
                settingsFile.setUserBD(user);
                settingsFile.setPassBD(pass);       
                Archivos.guardarConf(settingsFile);        //Y le diremos que guarde esa configuración en el archivo
                //Después notificaremos al usuario que la conexión fue realizada
                JOptionPane.showMessageDialog(null, "Conexión con BD establecida", "Conexión establecida", JOptionPane.INFORMATION_MESSAGE);
                //Y se cerrará este frame.
                this.dispose();
    }
    
    private void editable(boolean flag) {
        UrlField.setEditable(flag);
        usrField.setEditable(flag);
        passField.setEditable(flag);
        btnDone.setEnabled(flag);
        btnNoBD.setEnabled(flag);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField UrlField;
    private javax.swing.JButton btnDone;
    private javax.swing.JButton btnNoBD;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField passField;
    private javax.swing.JTextField usrField;
    // End of variables declaration//GEN-END:variables
}
