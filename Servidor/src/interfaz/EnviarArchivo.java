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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import servidor.Ordenes;

/**
 *
 * @author PC11
 */
public class EnviarArchivo extends javax.swing.JFrame{
    
    
    private static File[] archivosTem;
    private Ordenes orden=new Ordenes();
    private DefaultTableModel modelo;
    ArrayList <String> documentos=new ArrayList<>();
    ArrayList <String> nombres=new ArrayList<>();
    ArrayList <String> listaArchivos=new ArrayList<>();
    boolean individual=false;
    String nombre,ip;
    String ruta;
    String nombreZip;
    long pesoTotal=0;
    String []pesos={"KB","MB","GB"};
    //metodo de envio a un solo usuario
    public EnviarArchivo(String ip) {
        initComponents();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setIconImage(Principal.getLogo());
        this.setTitle("Enviar a todos los equipos dentro del grupo");
        this.ip=ip;
        modelo=new DefaultTableModel(new Object[][]{},new Object[]{"nombre","tipo","peso"})
        {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
            
        };
        tablaArchivos.setModel(modelo);
    }
    //envio a varios usarios
    public EnviarArchivo(String nombre,String ip) {
        initComponents();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setIconImage(Principal.getLogo());
        this.setTitle("Enviar Archivo a "+nombre);
        this.nombre=nombre;
        this.ip=ip;
        modelo=new DefaultTableModel(new Object[][]{},new Object[]{"nombre","tipo","peso"})
        {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
            
        };
        tablaArchivos.setModel(modelo);
    }
    private static JFileChooser agregarFiltros(JFileChooser arch)
  {
      FileNameExtensionFilter imagenes=new FileNameExtensionFilter("JPG, PNG & GIF","jpg","png","gif");
      FileNameExtensionFilter video=new FileNameExtensionFilter("MP4,AVI,FLV,MKV & WMV","mp4","avi","flv","mkv","wmv");
      FileNameExtensionFilter audio=new FileNameExtensionFilter("WMA & MP3","mp3","wma");
      FileNameExtensionFilter archivos=new FileNameExtensionFilter("TXT & PDF","txt","pdf");
      arch.setFileFilter(imagenes);
      arch.setFileFilter(video);
      arch.setFileFilter(audio);
      arch.setFileFilter(archivos);
      return arch;
  }

    private void zip() throws IOException {
        ZipOutputStream zout = null;
        FileOutputStream fos;
        lbInfo.setText("Comprimiendo archivo en zip...\n");
        try {
            ruta=nombreZip+".zip";
             fos = new FileOutputStream(ruta);
    		
            zout = new ZipOutputStream(fos);
            int i=0;
                for (String documento : documentos) {
                ZipEntry ze = new ZipEntry(nombres.get(i));
                zout.putNextEntry(ze);
                RandomAccessFile r=new RandomAccessFile(documentos.get(i),"r");
                byte[] info=new byte[(int)r.length()];
                r.readFully(info);
                zout.write(info,0,info.length);
                zout.closeEntry();
                i++;
            }
        } finally {
            if (zout != null) {
                zout.close();
            }
        }
        orden.enviarArchivo(ruta,ip);
    }//Zuno

    Thread archivo=new Thread();
    private long recursivoCarpeta(File archivo,String nombre)
    {
        long pesoArchivo=0;
        if(archivo.isDirectory())
        {
            File lista[]=archivo.listFiles();
            for (File file : lista) {
                pesoArchivo+=recursivoCarpeta(file, nombre);
            }
        }
        else
        {
            pesoArchivo=archivo.length();
            String aux=archivo.toString();
            documentos.add(archivo.getPath());
            aux=aux.substring(aux.indexOf(nombre),aux.length());
            nombres.add(aux);
        }
        return pesoArchivo;
    }
    private String peso(long peso,int i)
    {
        System.out.println(i);
        DecimalFormat df=new DecimalFormat("#.##");
        float aux=peso;
        String auxPeso;
        if(aux/1024>1024)
        {
            i=i+1;
            auxPeso=peso(peso/1024,i);
        }
        else
        {
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

        selecionar = new javax.swing.JButton();
        aceptar = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        txt_Nombre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaArchivos = new javax.swing.JTable();
        lbPesoArchivo = new javax.swing.JLabel();
        lbInfo = new javax.swing.JLabel();
        btnEliminarArchivo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        selecionar.setText("Agregar archivo");
        selecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selecionarActionPerformed(evt);
            }
        });

        aceptar.setText("Enviar");
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

        jLabel1.setText("Nombre del zip");

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

        lbInfo.setText("de 1 GB");

        btnEliminarArchivo.setText("Eliminar Archivo");
        btnEliminarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarArchivoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbPesoArchivo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addGap(386, 386, 386))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(selecionar, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarArchivo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(aceptar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_Nombre))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Nombre, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addComponent(jLabel1))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbPesoArchivo)
                    .addComponent(lbInfo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selecionar)
                    .addComponent(aceptar)
                    .addComponent(cancelar)
                    .addComponent(btnEliminarArchivo))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selecionarActionPerformed
        JFileChooser f = new JFileChooser();
        String ext = null;
        f = agregarFiltros(f);
        f.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        f.setFileFilter(f.getAcceptAllFileFilter());
        f.setMultiSelectionEnabled(true);

        if (f.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

            archivosTem = f.getSelectedFiles();
            String aux;
            for (int i = 0; i < archivosTem.length; i++) {
                aux=archivosTem[i].getPath();
                ext = aux.substring(aux.lastIndexOf((char) 92) + 1);
                if(archivoDuplicado(aux))
                {
                    JOptionPane.showMessageDialog(this,"Archivo Duplicado","Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                if (archivosTem[i].isDirectory()) {//si el la selección fue una carpeta
                    long pesoCarpeta=recursivoCarpeta(archivosTem[i],ext);
                    pesoTotal+=pesoCarpeta;
                    modelo.addRow(new Object[]{ext,"Carpeta",peso(pesoCarpeta,0)});
                } else {// si la selección fue un archivo
                    pesoTotal+=archivosTem[i].length();
                    documentos.add(aux);
                    nombres.add(ext);
                    modelo.addRow(new Object[]{ext,"Archivo",peso(archivosTem[i].length(),0)});
                }
                listaArchivos.add(aux);
                }
            }
            lbPesoArchivo.setText(peso(pesoTotal,0));
            lbPesoArchivo.setForeground(pesoTotal>1073741824?Color.RED:Color.GREEN);
            
        } else {
            System.out.println("No seleccion ");
        }
    }//GEN-LAST:event_selecionarActionPerformed

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
        try {
            nombreZip=txt_Nombre.getText().trim();
            if(!nombreZip.isEmpty())
            {
                if(pesoTotal<1073741824)
                {
                    zip();
                    this.dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "El archivo excede el peso Maximo", "Error ", JOptionPane.WARNING_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Escribe un nombre para el archivo", "Error ", JOptionPane.WARNING_MESSAGE);
            }
        } catch (IOException ex) {
            Logger.getLogger(EnviarArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_aceptarActionPerformed

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
         this.dispose();
    }//GEN-LAST:event_cancelarActionPerformed

    private void btnEliminarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarArchivoActionPerformed
        int fila=tablaArchivos.getSelectedRow();
        String direccion=listaArchivos.get(fila);
        for (int i = documentos.size()-1; i >=0; i--) {
            if(documentos.get(i).contains(direccion))
            {
                System.out.println("se removio "+documentos.get(i));
                nombres.remove(i);
                pesoTotal-=new File(documentos.get(i)).length();
                documentos.remove(i);
            }
        }
        listaArchivos.remove(fila);
        modelo.removeRow(fila);
        lbPesoArchivo.setText(peso(pesoTotal,0));
        lbPesoArchivo.setForeground(pesoTotal>1610612736?Color.RED:Color.GREEN);
        System.out.println(documentos.size()+"*****"+nombres.size());
        
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
    private javax.swing.JButton aceptar;
    private javax.swing.JButton btnEliminarArchivo;
    private javax.swing.JButton cancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbInfo;
    private javax.swing.JLabel lbPesoArchivo;
    private javax.swing.JButton selecionar;
    private javax.swing.JTable tablaArchivos;
    private javax.swing.JTextField txt_Nombre;
    // End of variables declaration//GEN-END:variables
}
