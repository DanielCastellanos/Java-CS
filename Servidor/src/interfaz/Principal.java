
package interfaz;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import servidor.Configuracion;
import servidor.BuscarGrupo;
import servidor.Ordenes;
import cliente.Tarea;
import entity.Pc;
import servidor.Archivos;
import servidor.BuscarGrupo;

public class Principal extends javax.swing.JFrame{
    
    //obtenemos el tamaño horizontal de la pantalla
    int width=Toolkit.getDefaultToolkit().getScreenSize().width;
    //obtenenmos el tamaño vertical de la pantalla
    int height=Toolkit.getDefaultToolkit().getScreenSize().height;
    Ordenes orden=new Ordenes();
    Timer t; //timer para verificar la conexion con los clientes
    public static Configuracion confPrincipal=new Configuracion();//variable que guarda la configuracion
    public static ArrayList<Pc_info> paneles=new ArrayList<>();//variable para guardar la lista de los paneles de los clientes
    public static Image logo=new ImageIcon(new ImageIcon("src/iconos/logo chico.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)).getImage();
    
    //constructor
    public Principal() {
        initComponents();
        //damos la medida a la ventana
        this.setSize(300, 400);
        //situamos la ventana
        this.setLocation(width-310, height-450);
        //Prihibimos la redimencion de la ventana
        this.setResizable(false);
        //hacemos la visible la ventana
        this.setVisible(true);
        //colocamos el fondo de la ventana
        ColocarImagen("fondo.jpg");
        //colocamos el icono a la ventana
        this.setIconImage(logo);
        //colocamos el titulo a la ventana
        this.setTitle("Java Control Software");
        //aumentamos la velocidad de desplasamiento de la barra vertical de desplazamiento
        panelScroll.getVerticalScrollBar().setUnitIncrement(10);
        //iniciamos el icono de la barra de tareas
        AppSystemTray st = new AppSystemTray(logo, this);
        //llenamos el panel con los paneles de los usuarios
        llenarPanel(Archivos.cargarListaClientes()); 
        //Timer con el cual verificaremos la coneccion con los clientes
        t=new Timer();
        //iniciamos el timer con su tarea que se ejecutara cada 5 seg.
        t.schedule(verificarCon, 5000, 5000);
    }
    //En el inicio de la aplicaion agrega todos los usuarios guardados
    public void llenarPanel(ArrayList<Pc> equipos){
        for (Pc pc : equipos) {
            //estado conexion mandar conectado="on" desconectado="off" 
            agregaEquipo(pc);
        }
    }
    
    //Agrega paneles de usuario al panel Principal
    public static void agregaEquipo(Pc pc){
        Pc_info p=new Pc_info(pc);
        paneles.add(p);
        panel.add(p);
    }
    
    public static Image getLogo() {
        return logo;
    }
    public static void setConf(Configuracion conf)
    {
        confPrincipal=conf;
    }
    //fondo de la ventana
    public void ColocarImagen(String img) {
        Background p = new Background(300, 400, "src/iconos/" + img);
        this.add(p, BorderLayout.CENTER);
        p.repaint();
    }
    //Tarea para verificar conexion de los usuarios
    TimerTask verificarCon=new TimerTask()
    {
        @Override
        public void run() {
            for (Pc_info panel : paneles) {
                panel.conexion();
            }
        }
    };
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popUp = new javax.swing.JPopupMenu();
        MenuCliente = new javax.swing.JMenu();
        Enviar = new javax.swing.JMenuItem();
        Apagar = new javax.swing.JMenuItem();
        Bloquear = new javax.swing.JMenuItem();
        desbloquear = new javax.swing.JMenuItem();
        reiniciar = new javax.swing.JMenuItem();
        Tareas = new javax.swing.JMenuItem();
        CompartirPagina = new javax.swing.JMenuItem();
        PropiedadesCliente = new javax.swing.JMenuItem();
        Encender = new javax.swing.JMenuItem();
        configuracion = new javax.swing.JMenuItem();
        Salir = new javax.swing.JMenuItem();
        panelScroll = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        opciones = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        popUp.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        MenuCliente.setText("Opciones Cliente");

        Enviar.setText("Enviar Archivo");
        Enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnviarActionPerformed(evt);
            }
        });
        MenuCliente.add(Enviar);

        Apagar.setText("Apagar...");
        Apagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApagarActionPerformed(evt);
            }
        });
        MenuCliente.add(Apagar);

        Bloquear.setText("Bloquear");
        Bloquear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BloquearActionPerformed(evt);
            }
        });
        MenuCliente.add(Bloquear);

        desbloquear.setText("Desbloquear");
        desbloquear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                desbloquearActionPerformed(evt);
            }
        });
        MenuCliente.add(desbloquear);

        reiniciar.setText("Reiniciar");
        reiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reiniciarActionPerformed(evt);
            }
        });
        MenuCliente.add(reiniciar);

        Tareas.setText("Obtener Procesos");
        Tareas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TareasActionPerformed(evt);
            }
        });
        MenuCliente.add(Tareas);

        CompartirPagina.setText("Compartir Pagina");
        CompartirPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CompartirPaginaActionPerformed(evt);
            }
        });
        MenuCliente.add(CompartirPagina);

        PropiedadesCliente.setText("Propiedades Clientes");
        PropiedadesCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PropiedadesClienteActionPerformed(evt);
            }
        });
        MenuCliente.add(PropiedadesCliente);

        Encender.setText("Encender");
        Encender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EncenderActionPerformed(evt);
            }
        });
        MenuCliente.add(Encender);

        popUp.add(MenuCliente);

        configuracion.setText("Configuración");
        configuracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configuracionActionPerformed(evt);
            }
        });
        popUp.add(configuracion);

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

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Grupo:");

        jButton1.setText("jButton1");
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
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
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton1)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnviarActionPerformed
        new EnviarArchivo(confPrincipal.getGrupo());
    }//GEN-LAST:event_EnviarActionPerformed

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

        new VConf(confPrincipal).setVisible(true);
    }//GEN-LAST:event_configuracionActionPerformed

    private void CompartirPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CompartirPaginaActionPerformed
       new CompartirPagina(confPrincipal.getGrupo());
    }//GEN-LAST:event_CompartirPaginaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ArrayList<cliente.SesionCliente> lista=BuscarGrupo.listaSesiones;
        for (cliente.SesionCliente sesion : lista) {
            System.out.println("*****************************************");
            System.out.println(sesion.getUsr()+"\r\n"+
                    sesion.getEntrada()+"\r\n"+
                    sesion.getSalida()+"\r\n"+
                    sesion.getWebHistory()+"\r\n");
            ArrayList<cliente.Tarea> listaTareas=sesion.getTaskHistory();
            for (Tarea listaTarea : listaTareas) {
                System.out.println("_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_");
                System.out.println(listaTarea.getNombreImagen()+"\r\n"+
                        listaTarea.getPID()+"\r\n"+
                        listaTarea.getTituloVentana()+"\r\n");
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void PropiedadesClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PropiedadesClienteActionPerformed
        BuscarGrupo.propiedades=new VentanaPropiedades(BuscarGrupo.equipos);
    }//GEN-LAST:event_PropiedadesClienteActionPerformed

    private void EncenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EncenderActionPerformed
        //ciclo para encender todas las pc
    }//GEN-LAST:event_EncenderActionPerformed
    
    
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
    private javax.swing.JMenuItem Encender;
    private javax.swing.JMenuItem Enviar;
    private javax.swing.JMenu MenuCliente;
    private javax.swing.JMenuItem PropiedadesCliente;
    private javax.swing.JMenuItem Salir;
    private javax.swing.JMenuItem Tareas;
    private javax.swing.JMenuItem configuracion;
    private javax.swing.JMenuItem desbloquear;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton opciones;
    private static javax.swing.JPanel panel;
    private javax.swing.JScrollPane panelScroll;
    private javax.swing.JPopupMenu popUp;
    private javax.swing.JMenuItem reiniciar;
    // End of variables declaration//GEN-END:variables
}
