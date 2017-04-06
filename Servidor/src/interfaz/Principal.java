
package interfaz;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import servidor.ArchivoConf;
import servidor.BuscarGrupo;
import servidor.Clientes;
import servidor.Ordenes;

public class Principal extends javax.swing.JFrame{
    
    int width=Toolkit.getDefaultToolkit().getScreenSize().width;
    int height=Toolkit.getDefaultToolkit().getScreenSize().height;
    Ordenes orden=new Ordenes();
    Clientes clientes=new Clientes();
    Timer t;
    public ArchivoConf confPrincipal=new ArchivoConf();
    public static ArrayList<Pc_info> paneles=new ArrayList<>();
    public static Image logo=new ImageIcon(new ImageIcon("src/iconos/logo chico.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)).getImage();
    public Principal() {
        initComponents();
        this.setSize(300, 400);
        this.setLocation(width-310, height-450);
        this.setResizable(false);
        this.setVisible(true);
        ColocarImagen("fondo.jpg");
        this.setIconImage(logo);
        this.setTitle("Java Control Software");
        panelScroll.getVerticalScrollBar().setUnitIncrement(10);
        AppSystemTray st = new AppSystemTray(logo, this);
        llenarPanel(clientes.cargarClientes());  //<---------------------------------------------Aqui va el array list que le mandas con los clientes
        //Timer con el cual verificaremos la coneccion con los clientes
        t=new Timer();
        t.schedule(verificarCon, 5000, 5000);
    }
    //Recibe un arraylist de Strings
    public void llenarPanel(ArrayList<Clientes> clientes){
        for (Clientes cliente : clientes) {
            //estado conexion mandar conectado="on" desconectado="off" 
            agregaEquipo(cliente);
        }
    }
    
    //Agrega paneles al Principal
    public static void agregaEquipo(Clientes c){
        Pc_info p=new Pc_info(c.getNombre(),c.getDireccion());
        paneles.add(p);
        panel.add(p);
    }
    
    public static Image getLogo() {
        return logo;
    }
   
    //fondo de la ventana
    public void ColocarImagen(String img) {
        Background p = new Background(300, 400, "src/iconos/" + img);
        this.add(p, BorderLayout.CENTER);
        p.repaint();
    }
    //Tarea para verificar conexion
    TimerTask verificarCon=new TimerTask()
    {
        @Override
        public void run() {
            Component[]paneles=panel.getComponents();
            for (Component panel : paneles) {
                ((Pc_info)panel).conexion();
            }
        }
    };
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popUp = new javax.swing.JPopupMenu();
        Enviar = new javax.swing.JMenuItem();
        Apagar = new javax.swing.JMenuItem();
        Bloquear = new javax.swing.JMenuItem();
        desbloquear = new javax.swing.JMenuItem();
        reiniciar = new javax.swing.JMenuItem();
        Tareas = new javax.swing.JMenuItem();
        configuracion = new javax.swing.JMenuItem();
        CompartirPagina = new javax.swing.JMenuItem();
        Salir = new javax.swing.JMenuItem();
        panelScroll = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        opciones = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        popUp.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        Enviar.setText("Enviar Archivo");
        Enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnviarActionPerformed(evt);
            }
        });
        popUp.add(Enviar);

        Apagar.setText("Apagar...");
        Apagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApagarActionPerformed(evt);
            }
        });
        popUp.add(Apagar);

        Bloquear.setText("Bloquear");
        Bloquear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BloquearActionPerformed(evt);
            }
        });
        popUp.add(Bloquear);

        desbloquear.setText("Desbloquear");
        desbloquear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                desbloquearActionPerformed(evt);
            }
        });
        popUp.add(desbloquear);

        reiniciar.setText("Reiniciar");
        reiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reiniciarActionPerformed(evt);
            }
        });
        popUp.add(reiniciar);

        Tareas.setText("Obtener Procesos");
        Tareas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TareasActionPerformed(evt);
            }
        });
        popUp.add(Tareas);

        configuracion.setText("Configuración");
        configuracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configuracionActionPerformed(evt);
            }
        });
        popUp.add(configuracion);

        CompartirPagina.setText("Compartir Pagina");
        CompartirPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CompartirPaginaActionPerformed(evt);
            }
        });
        popUp.add(CompartirPagina);

        Salir.setText("Salir");
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });
        popUp.add(Salir);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(255, 0, 0));

        panelScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panel.setAutoscrolls(true);
        panel.setLayout(new java.awt.GridLayout(0, 1));
        panelScroll.setViewportView(panel);

        opciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/opciones.png"))); // NOI18N
        opciones.setBorderPainted(false);
        opciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                opcionesMouseClicked(evt);
            }
        });
        opciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionesActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Grupo:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(opciones, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(opciones, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnviarActionPerformed
        new EnviarArchivo(confPrincipal.getGrupo());
    }//GEN-LAST:event_EnviarActionPerformed

    private void opcionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionesActionPerformed
        
    }//GEN-LAST:event_opcionesActionPerformed

    private void opcionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opcionesMouseClicked
        if(evt.getButton()==1)
        {
            for (Pc_info panel : paneles) {
                if(panel.estaEnviarBloq())
                {
                    Enviar.setEnabled(false);
                    break;
                }
                Enviar.setEnabled(true);
            }
            popUp.show(evt.getComponent(), evt.getX(),evt.getY());
        }
    }//GEN-LAST:event_opcionesMouseClicked

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_SalirActionPerformed

    private void ApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApagarActionPerformed
       new Apagar(confPrincipal.getGrupo());
       
    }//GEN-LAST:event_ApagarActionPerformed

    private void BloquearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BloquearActionPerformed
        new Bloquear(confPrincipal.getGrupo());
    }//GEN-LAST:event_BloquearActionPerformed

    private void reiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reiniciarActionPerformed
        System.out.println(confPrincipal.getGrupo());
        new Reiniciar(confPrincipal.getGrupo());
    }//GEN-LAST:event_reiniciarActionPerformed

    private void desbloquearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_desbloquearActionPerformed
        new Desbloquear(confPrincipal.getGrupo());
    }//GEN-LAST:event_desbloquearActionPerformed

    private void TareasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TareasActionPerformed
        orden.pedirProcesos(confPrincipal.getGrupo());
    }//GEN-LAST:event_TareasActionPerformed

    private void configuracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configuracionActionPerformed

        new VConf().setVisible(true);
    }//GEN-LAST:event_configuracionActionPerformed

    private void CompartirPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CompartirPaginaActionPerformed
       new CompartirPagina(confPrincipal.getGrupo());
    }//GEN-LAST:event_CompartirPaginaActionPerformed
    
    
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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Apagar;
    private javax.swing.JMenuItem Bloquear;
    private javax.swing.JMenuItem CompartirPagina;
    private javax.swing.JMenuItem Enviar;
    private javax.swing.JMenuItem Salir;
    private javax.swing.JMenuItem Tareas;
    private javax.swing.JMenuItem configuracion;
    private javax.swing.JMenuItem desbloquear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton opciones;
    private static javax.swing.JPanel panel;
    private javax.swing.JScrollPane panelScroll;
    private javax.swing.JPopupMenu popUp;
    private javax.swing.JMenuItem reiniciar;
    // End of variables declaration//GEN-END:variables
}
