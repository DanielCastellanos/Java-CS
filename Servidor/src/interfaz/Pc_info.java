/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import servidor.Ordenes;

public class Pc_info extends javax.swing.JPanel {

    ImageIcon verde = new ImageIcon(new ImageIcon("src/iconos/verde.png").getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
    ImageIcon rojo = new ImageIcon(new ImageIcon("src/iconos/rojo.png").getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
    private String nombre = "Nombre";
    private String hostname;
    Principal p;
    Socket conexion;
    Ordenes ordenes=new Ordenes();
    //constructor recive toda la infomacion del usario 
    public Pc_info(String nombre ,String hostname) {
        initComponents();
        Color color = new Color(255, 255, 255, 255);
        barEnvio.setVisible(false);
        this.setBackground(color);
        this.nombre=nombre;
        this.hostname=hostname;
        label.setText(nombre);
        iconos();
    }
    public void conexion()
    {
        try {
            conexion=new Socket();
            //intentamos la coneccion a la direccion ip y puerto con un tiempo maximo de 200milisegundos
            SocketAddress sa=new InetSocketAddress(hostname, 4401);
            conexion.connect(sa,200);
            //si hay conexion con el destino colocamos el icono verde
            estado.setIcon(verde);
            
        } catch (IOException e) {
            //si el tiempo de conexion se agoto ponemos el inono rojo
            estado.setIcon(rojo);
        }
        //al final cerramos el Socket
        finally
        {
            try {
                
                conexion.close();
            } catch (IOException ex) {
                System.err.println("Error al cerrar coneccion en Pc_info Linea 52");
            }
        }
    }
    public void estadoRojo() {
        label.setIcon(rojo);
    }
    public void estadoVerde() {
        label.setIcon(verde);
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
        label.setText(nombre);
    }
    public void bloquearEnviar()
    {
        Enviar.setEnabled(false);
    }
    public void desBloqEnviar()
    {
        Enviar.setEnabled(true);
    }
    public boolean estaEnviarBloq()
    {
        return !Enviar.isEnabled();
    }
    public String getNombre() {
        return nombre;
    }
    public void iconos(){
    ImageIcon configuracion = new ImageIcon(new ImageIcon("src/iconos/configuracion.png").getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
    conf.setIcon(configuracion);
    
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu = new javax.swing.JPopupMenu();
        Enviar = new javax.swing.JMenuItem();
        Apagar = new javax.swing.JMenuItem();
        Bloquear = new javax.swing.JMenuItem();
        desbloquear = new javax.swing.JMenuItem();
        Cpagina = new javax.swing.JMenuItem();
        reiniciar = new javax.swing.JMenuItem();
        Tareas = new javax.swing.JMenuItem();
        estado = new javax.swing.JLabel();
        conf = new javax.swing.JLabel();
        label = new javax.swing.JLabel();
        barEnvio = new javax.swing.JProgressBar();

        Enviar.setText("Enviar Archivo");
        Enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnviarActionPerformed(evt);
            }
        });
        menu.add(Enviar);

        Apagar.setText("Apagar...");
        Apagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApagarActionPerformed(evt);
            }
        });
        menu.add(Apagar);

        Bloquear.setText("Bloquear");
        Bloquear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BloquearActionPerformed(evt);
            }
        });
        menu.add(Bloquear);

        desbloquear.setText("Desbloquear");
        desbloquear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                desbloquearActionPerformed(evt);
            }
        });
        menu.add(desbloquear);

        Cpagina.setText("Compartir Pagina");
        Cpagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CpaginaActionPerformed(evt);
            }
        });
        menu.add(Cpagina);

        reiniciar.setText("Reiniciar");
        reiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reiniciarActionPerformed(evt);
            }
        });
        menu.add(reiniciar);

        Tareas.setText("Obtener Procesos");
        Tareas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TareasActionPerformed(evt);
            }
        });
        menu.add(Tareas);

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        conf.setComponentPopupMenu(menu);
        conf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                confMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                confMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                confMouseExited(evt);
            }
        });

        label.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label.setText("Nombre PC");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(barEnvio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(conf, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                        .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label)
                    .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(conf, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(barEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        
    }//GEN-LAST:event_formMouseClicked

    private void confMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confMouseExited
        iconos();
    }//GEN-LAST:event_confMouseExited

    private void confMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confMouseEntered
        ImageIcon configuracion = new ImageIcon(new ImageIcon("src/iconos/configuracion.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        conf.setIcon(configuracion);
    }//GEN-LAST:event_confMouseEntered

    private void confMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confMouseClicked
        menu.show(evt.getComponent(), evt.getX(),evt.getY());
    }//GEN-LAST:event_confMouseClicked

    private void EnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnviarActionPerformed
        new EnviarArchivo(this.nombre,hostname);
    }//GEN-LAST:event_EnviarActionPerformed

    private void ApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApagarActionPerformed
        new Apagar(this.nombre,hostname);//Ingresar IP de la pc que se apagará
    }//GEN-LAST:event_ApagarActionPerformed

    private void BloquearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BloquearActionPerformed
        new Bloquear(this.nombre,hostname);//Ingresar IP de la pc que se bloqueará
    }//GEN-LAST:event_BloquearActionPerformed

    private void desbloquearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_desbloquearActionPerformed
        new Desbloquear(this.nombre, hostname);
    }//GEN-LAST:event_desbloquearActionPerformed

    private void reiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reiniciarActionPerformed
        new Reiniciar(this.nombre,hostname);
    }//GEN-LAST:event_reiniciarActionPerformed

    private void TareasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TareasActionPerformed
        ordenes.pedirProcesos(hostname);
        System.out.println("orden pc_info"+hostname);
    }//GEN-LAST:event_TareasActionPerformed

    private void CpaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CpaginaActionPerformed
        
        new Desbloquear(this.nombre, hostname);
    }//GEN-LAST:event_CpaginaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Apagar;
    private javax.swing.JMenuItem Bloquear;
    private javax.swing.JMenuItem Cpagina;
    private javax.swing.JMenuItem Enviar;
    private javax.swing.JMenuItem Tareas;
    public javax.swing.JProgressBar barEnvio;
    private javax.swing.JLabel conf;
    private javax.swing.JMenuItem desbloquear;
    private javax.swing.JLabel estado;
    private javax.swing.JLabel label;
    private javax.swing.JPopupMenu menu;
    private javax.swing.JMenuItem reiniciar;
    // End of variables declaration//GEN-END:variables
}
