package interfaz;

import java.awt.Color;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.zip.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import servidor.Hint;
import servidor.Ordenes;

public class EnviarArchivo extends javax.swing.JFrame{
    
    
    private static File[] archivosTem;                  //Array Para guardar los archivos seleccionados
    private Ordenes orden=new Ordenes();
    private DefaultTableModel modelo;                   //modelo de la tabla
    ArrayList <String> documentos=new ArrayList<>();    //Lista para guardar las direcciones completas de los archivos
    ArrayList <String> nombres=new ArrayList<>();       //Lista para guardar los nombres de los archivos
    ArrayList <String> listaArchivos=new ArrayList<>(); //Lista para guardar la direccion de los archivos que se agregan a la tabla
    String nombre,      //nombre del Usuario o pc
            ip;         //ip o hostname 
    String ruta;        //para guardar la Ruta del archivo Zip
    String nombreZip;   //para guardar el nombre del archivo Zip
    long pesoTotal=0;   //para guardar el peso total de los archivos seleccionados
    Thread hiloZip;     //Hilo para hacer la compresión de los archivos
    String []pesos={"KB","MB","GB"};     
    //contructor para envio grupal
    public EnviarArchivo(String ip) {
        initComponents();
        //iniciamos configuracion de la ventana
        iniciarVentana();
        //colocamos icono a la ventana
        this.setTitle("Enviar a todos los equipos dentro del grupo");
        //guardamos en la variable global la direccion/hostname resivida
        this.ip=ip;
        
        
    }
    //Constructor para envio individual
    public EnviarArchivo(String nombre,String ip) {
        initComponents();
        //iniciamos configuracion de la ventana
        iniciarVentana();
        //Colocamos titulo a ventana
        this.setTitle("Enviar Archivo a "+nombre);
        //guardamos datos recividos en las variables globales
        this.nombre=nombre;
        this.ip=ip;
        //ocultamos los botones de tipo de envio
        rbtnSecuencial.setVisible(false);
        rbtnSimultaneo.setVisible(false);
    }
    private void iniciarVentana()
    {
        //Hacemos visible la ventana
        this.setVisible(true);
        //Centramos la ventana
        this.setLocationRelativeTo(null);
        //Colocamos icono a la ventana
        this.setIconImage(Principal.getLogo());
        //Agregamos Hint al campo de texto
        txt_Nombre.setUI(new Hint("Nombre del Archivo"));
        //ocultamos los componentes de la barra de progreso general
        lblGeneral.setVisible(false);
        barraGeneral.setVisible(false);
        //ocultamos la barra de progreso de archivo
        barra.setVisible(false);
        //deshabiltamos el boton de ElimianrArchivo
        btnEliminarArchivo.setEnabled(false);
        //deshabilitamos el boton de enviar
        btnaceptar.setEnabled(false);
        //Creamos el modelo para la tabla
        modelo=new DefaultTableModel(new Object[][]{},new Object[]{"nombre","tipo","peso"})
        {
            //sobreescribimos metodo para prohibir la edicion de celdas
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
            
        };
        //Agregamos el modelo a la tabla
        tablaArchivos.setModel(modelo);
        //Quitamos el reordenamiento en la cabecera de la tabla
        tablaArchivos.getTableHeader().setReorderingAllowed(false);
    }
    private static JFileChooser agregarFiltros(JFileChooser arch)
  {
      //Filtro para imagenes
      FileNameExtensionFilter imagenes=new FileNameExtensionFilter("JPG, PNG & GIF","jpg","png","gif");
      //Filtro para Video
      FileNameExtensionFilter video=new FileNameExtensionFilter("MP4,AVI,FLV,MKV & WMV","mp4","avi","flv","mkv","wmv");
      //filtro para Audio
      FileNameExtensionFilter audio=new FileNameExtensionFilter("WMA & MP3","mp3","wma");
      //filtro para Archivos de texto
      FileNameExtensionFilter archivos=new FileNameExtensionFilter("TXT & PDF","txt","pdf");
      //agregamos los filtros al JFileChooser
      arch.setFileFilter(imagenes);
      arch.setFileFilter(video);
      arch.setFileFilter(audio);
      arch.setFileFilter(archivos);
      //devolvemos JFileChooset con los Filtros
      return arch;
  }

    Runnable zip=new Runnable() {
        @Override
        public void run() {
            //ocultamos el campo de texto
            txt_Nombre.setVisible(false);
            //ocultamos el boton Enviar
            btnaceptar.setVisible(false);
            //ocultamos boton Eliminar archivo
            btnEliminarArchivo.setVisible(false);
            //Ocultamos boton Seleccionar archivo
            btnselecionar.setVisible(false);
            //preparamos la salida de datos del Zip
            ZipOutputStream zout;
            //preparamos la salida de datos para el archivo
        BufferedOutputStream bos;
        try {
            //creamos el archivo y lo guardamos 
            ruta=nombreZip+".zip";
            //creamos el flujo de salida hacia el archivo Zip
             bos = new BufferedOutputStream(new FileOutputStream(ruta));
            zout = new ZipOutputStream(bos);
            int i=0;
            //Hacemos visibles todos los componentes de las barras de progreso
            lblGeneral.setVisible(true);
            barraGeneral.setVisible(true);
            barra.setVisible(true);
            barra.setStringPainted(true);
            //ponemosd de color verde la barra
            barraGeneral.setForeground(Color.GREEN);
            //habilitamos el String del porcentaje
            barraGeneral.setStringPainted(true);
            //ponemos el valor Maximo de la barra
            barraGeneral.setMaximum((int)(pesoTotal/100));
            //ponemos el valor Minimo
            barraGeneral.setMinimum(0);
            //variable para el progreso de la barra General
            long leidoTotal=0;
            lbInfo.setHorizontalAlignment(JLabel.LEFT);
                for (String documento : documentos) {
                    //creamos una nueva entrada/ducumento para el Zip
                ZipEntry ze = new ZipEntry(nombres.get(i));
                //agregamos la entrada al Zip
                zout.putNextEntry(ze);
                //obtenemos el archivo 
                File arch=new File(documentos.get(i));
                //Indicamos cual archivo se esta comprimiendo
                lbInfo.setText("comprimiendo: "+arch.getName());
                //obtenemos el tamaño del archivo
                long tamañoArch=arch.length();
                //ponemos el valor maximo de a la barra individual
                barra.setMaximum((int)(tamañoArch/100));
                //ponemos el valor minimo
                barra.setMinimum(0);
                //cambiamos el color de la barra
                barra.setForeground(Color.GREEN);
                barra.setValue(0);
                //creamos el Stream de entrada del archivo
                BufferedInputStream bis=new BufferedInputStream(new FileInputStream(documentos.get(i)));
                //tamaño de buffer para lectura del archivo
                byte[] info=new byte[4096];
                //variable para el progreso de la barra individual
                long leido=0;
                //Ciclo para lectura del archivo
                while(leido<tamañoArch)
                {
                    //Verifica que se puedan leer otros 4KB 
                    if((leido+4096)<tamañoArch)
                    {
                        //leemos 4KB del Archivo
                    bis.read(info);
                    //agregamos los 4KB a las variables de progreso
                    leido+=4096;
                    leidoTotal+=4096;
                    }
                    else
                    {
                        //si no se puede leer 4KB lee el resto del archivo
                        int resto=(int)(tamañoArch-leido);
                        //damos el tamaño al arreglo
                        info=new byte[resto];
                        //leemos el resto del archivo
                        bis.read(info);
                        //agregamos el resto del a las variables de progreso
                        leido+=resto;
                        leidoTotal+=resto;
                    }
                    //escribimos en el archivo Zip
                    zout.write(info);
                    //ponemos el valor en la barra individual
                    barra.setValue((int)(leido/100));
                    //ponemos el valor en la barra general
                    barraGeneral.setValue((int)(leidoTotal/100));
                }
                //cerramos la escritura a la entradadel Zip
                zout.closeEntry();
                //aumentamos el valor del contador
                i++;
            }
                //cerramos la escritura al archivo Zip
                zout.close();
        }   catch (FileNotFoundException ex) {
                Logger.getLogger(EnviarArchivo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(EnviarArchivo.class.getName()).log(Level.SEVERE, null, ex);
            } 
        //verificamos que tipo de envio se selecciono
        if(rbtnSimultaneo.isSelected())
        {
            //orden para archivos simultaneos y envios individuales
        orden.enviarArchivoSimultaneo(ruta,ip);
        }
        else
        {
            //orden para envio Secuencial
            orden.envioArchivoSecuencial(ruta);
        }
        //llamamos el metodo cerrar para cerrar la ventana
        cerrar();
        }
    };
public void cerrar()
{
    //cerramos la ventana
    this.dispose();
}
//metodo cumple dos funciones obtener todos los archivos y el peso total de una carpeta
    private long recursivoCarpeta(File archivo,String nombre)
    {
        //variable para guardar el peso total
        long pesoArchivo=0;
        //verificamos si es una carpeta o un archivo
        if(archivo.isDirectory())
        {
            //si es una carpeta obtenemos su lista de archivos
            File lista[]=archivo.listFiles();
            //creamos un ciclo y aplicamos el mismo metodo para verificar si es acarpeta o archivo
            for (File file : lista) {
                pesoArchivo+=recursivoCarpeta(file, nombre);
            }
        }
        else
        {
            //si es un archivo
            //obtenemos su peso
            pesoArchivo=archivo.length();
            //auxiliar para guardar la direccion del archivo
            String aux=archivo.toString();
            //guardamos la direccion del archivo
            documentos.add(archivo.getPath());
            //obtenemos la subcadena desde donde comienza el nombre de la carpeta
            aux=aux.substring(aux.indexOf(nombre),aux.length());
            //agregamos el nombre a la lista
            nombres.add(aux);
        }
        //devolvemos el peso del archivo
        return pesoArchivo;
    }
    private String peso(long peso,int i)
    {
        //creamos el formato para mostrar solo 2 decimales
        DecimalFormat df=new DecimalFormat("#.##");
        //aux para calcular el peso
        float aux=peso;
        //string para guardar el formato
        String auxPeso;
        //verificamos si el peso se puede seguir dividiendo
        if(aux/1024>1024)
        {
            //variable para decidir que tipo es 
            i=i+1;
            //si aun se puede seguir dividiendo lo mandamos al mismo metodo
            auxPeso=peso(peso/1024,i);
        }
        else
        {
            //si no se puede dividir devolvemos el formato
            auxPeso=df.format(aux/1024)+" "+pesos[i];
        }
        return auxPeso;
    }
    private boolean archivoDuplicado(String direccion)
    {
        boolean encontrado=false;
        for (String documento : listaArchivos) {
            if(documento.equals(direccion))
            {
                encontrado=true;
            }
        }
        return encontrado;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tipoEnvio = new javax.swing.ButtonGroup();
        btnselecionar = new javax.swing.JButton();
        btnaceptar = new javax.swing.JButton();
        btncancelar = new javax.swing.JButton();
        txt_Nombre = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaArchivos = new javax.swing.JTable();
        lbPesoArchivo = new javax.swing.JLabel();
        lbInfo = new javax.swing.JLabel();
        btnEliminarArchivo = new javax.swing.JButton();
        barra = new javax.swing.JProgressBar();
        barraGeneral = new javax.swing.JProgressBar();
        lblGeneral = new javax.swing.JLabel();
        rbtnSimultaneo = new javax.swing.JRadioButton();
        rbtnSecuencial = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(560, 400));

        btnselecionar.setText("Agregar archivo");
        btnselecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnselecionarActionPerformed(evt);
            }
        });

        btnaceptar.setText("Enviar");
        btnaceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaceptarActionPerformed(evt);
            }
        });

        btncancelar.setText("Cancelar");
        btncancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarActionPerformed(evt);
            }
        });

        txt_Nombre.setMaximumSize(new java.awt.Dimension(2147483647, 24));

        tablaArchivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tablaArchivos);

        lbPesoArchivo.setText("0 KB");

        lbInfo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbInfo.setText("Total: ");
        lbInfo.setMaximumSize(new java.awt.Dimension(406, 15));
        lbInfo.setMinimumSize(new java.awt.Dimension(406, 15));
        lbInfo.setPreferredSize(new java.awt.Dimension(406, 15));

        btnEliminarArchivo.setText("Eliminar Archivo");
        btnEliminarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarArchivoActionPerformed(evt);
            }
        });

        lblGeneral.setText("Progreso general");

        tipoEnvio.add(rbtnSimultaneo);
        rbtnSimultaneo.setSelected(true);
        rbtnSimultaneo.setText("Envío Simultaneo");
        rbtnSimultaneo.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        tipoEnvio.add(rbtnSecuencial);
        rbtnSecuencial.setText("Envío Secuencial");
        rbtnSecuencial.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_Nombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(barraGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lbInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(lbPesoArchivo)
                        .addGap(33, 33, 33))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnselecionar, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarArchivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnaceptar))
                    .addComponent(barra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblGeneral)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rbtnSimultaneo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbtnSecuencial)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnSimultaneo)
                    .addComponent(rbtnSecuencial))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblGeneral)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(barraGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbPesoArchivo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(barra, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnselecionar)
                    .addComponent(btnaceptar)
                    .addComponent(btncancelar)
                    .addComponent(btnEliminarArchivo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnselecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnselecionarActionPerformed
        //declaramos selector de archivos
        JFileChooser f = new JFileChooser();
        //variable para guardar el nombre del archivo/Carpeta
        String nombre = null;
        //agregamos los filtros al selector
        f = agregarFiltros(f);
        //activamos la seleccion de archivos y carpetas
        f.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //Ponemos por defecto el filtro de todos los archivos
        f.setFileFilter(f.getAcceptAllFileFilter());
        //agregamos la seleccion multiple
        f.setMultiSelectionEnabled(true);
        //Verificamos si a seleccionado algo
        if (f.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            //Guardamos los archivos seleccionados
            archivosTem = f.getSelectedFiles();
            //auxiliar para el nombre de los archivos
            String aux;
            //ciclo para verificar los archivos y carpetas
            for (int i = 0; i < archivosTem.length; i++) {
                //Obtenemos la direccion del archivo/carpeta
                aux=archivosTem[i].getPath();
                //extraemos el nombre
                nombre = aux.substring(aux.lastIndexOf((char) 92) + 1);
                //verificamos si el archivo esta duplicado
                if(archivoDuplicado(aux))
                {
                    //si esta duplicado mostramos advertencia
                    JOptionPane.showMessageDialog(this,"Archivo duplicado","Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    //verificamos si es una carpeta
                if (archivosTem[i].isDirectory()) {
                    //si el la selección fue una carpeta
                    //madamos la carpeta al metodo recursivo para obtener todos los archivos y el peso total de la carpeta
                    long pesoCarpeta=recursivoCarpeta(archivosTem[i],nombre);
                    //agregamos el peso de la carpeta al peso toral
                    pesoTotal+=pesoCarpeta;
                    //agregamos a la tabla la carpeta junto con su peso
                    modelo.addRow(new Object[]{nombre,"Carpeta",peso(pesoCarpeta,0)});
                } else {
                    // si la selección fue un archivo
                    //agregamos al peso total el peso del archivo
                    pesoTotal+=archivosTem[i].length();
                    //agregamos la direccion del archivo a la lista de direcciones 
                    documentos.add(aux);
                    //agregamos el nombre del archivo a la lista 
                    nombres.add(nombre);
                    //agregamos el archivo a la tabla
                    modelo.addRow(new Object[]{nombre,"Archivo",peso(archivosTem[i].length(),0)});
                }
                //añadimos la ruta a la lista de archivos
                listaArchivos.add(aux);
                }
            }
            //seleccionamos la primera fila
            tablaArchivos.changeSelection(0, 0, false, false);
            //agregamos a la etiqueta de peso el peso total de los archivos seleccionados
            lbPesoArchivo.setText(peso(pesoTotal,0));
            //activamos el boton de eliminar archivo
            btnEliminarArchivo.setEnabled(true);
            //activamos el boton para enviar archivos
            btnaceptar.setEnabled(true);
        } 
        else //si no a seleccionado nada
        {
            System.out.println("No seleccion ");
        }
    }//GEN-LAST:event_btnselecionarActionPerformed

    private void btnaceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaceptarActionPerformed
            //obtenemos el nombre puesto por el usuario para el archivo Zip
            nombreZip=txt_Nombre.getText().trim();
            //verifiacamos que el campono este vacio
            if(!nombreZip.isEmpty())
            {
                //si no esta vacio comenzamos el hilo para comprimir los archivos
                    hiloZip=new Thread(zip);
                    hiloZip.start();
            }
            else
            {
                //de lo contrario se le muestra advertencia 
                JOptionPane.showMessageDialog(this, "Escribe un nombre para el archivo", "Error ", JOptionPane.WARNING_MESSAGE);
            }
        
    }//GEN-LAST:event_btnaceptarActionPerformed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
        //si se pereciona el boton cancelar 
        //se detiene la comprecion de los archivos
        hiloZip.interrupt();
        //se cierra la ventana
        this.dispose();
    }//GEN-LAST:event_btncancelarActionPerformed

    private void btnEliminarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarArchivoActionPerformed
        //tomamos la fila seleccionada
        int fila=tablaArchivos.getSelectedRow();
        //obtenemos la direccion del archivo seleccionado
        String direccion=listaArchivos.get(fila);
        //ciclo para borrar los archivos
        for (int i = documentos.size()-1; i >=0; i--) {
            //si el archivo contiene la direccion se elimina
            if(documentos.get(i).contains(direccion))
            {
                //lo removemos de la lista de nombres
                nombres.remove(i);
                //quitamos su peso del peso total
                pesoTotal-=new File(documentos.get(i)).length();
                //y lo removemos de la lista de documentos
                documentos.remove(i);
            }
        }
        //lo removemos de la lista de archivos
        listaArchivos.remove(fila);
        //lo removemos de la tabla
        modelo.removeRow(fila);
        //calculamos y mostramos nuevamente el peso total
        lbPesoArchivo.setText(peso(pesoTotal,0));
        //si ya no quedan documentos bloqueamos los botones Eliminar archivo y Enviar
        btnEliminarArchivo.setEnabled(documentos.size()>0);
        btnaceptar.setEnabled(documentos.size()>0);
    }//GEN-LAST:event_btnEliminarArchivoActionPerformed
    
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
            java.util.logging.Logger.getLogger(EnviarArchivo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EnviarArchivo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EnviarArchivo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EnviarArchivo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() 
            {
                new EnviarArchivo("").setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barra;
    private javax.swing.JProgressBar barraGeneral;
    private javax.swing.JButton btnEliminarArchivo;
    private javax.swing.JButton btnaceptar;
    private javax.swing.JButton btncancelar;
    private javax.swing.JButton btnselecionar;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbInfo;
    private javax.swing.JLabel lbPesoArchivo;
    private javax.swing.JLabel lblGeneral;
    private javax.swing.JRadioButton rbtnSecuencial;
    private javax.swing.JRadioButton rbtnSimultaneo;
    private javax.swing.JTable tablaArchivos;
    private javax.swing.ButtonGroup tipoEnvio;
    private javax.swing.JTextField txt_Nombre;
    // End of variables declaration//GEN-END:variables
}
