package generadorllistats;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import javax.swing.JFileChooser;

/**
 *
 * @author Eric
 */
public class GeneradorLlistats extends javax.swing.JFrame {

    /**
     * Creates new form GeneradorLlistats
     */
    private File fitxer;
    private TreeMap<String, TreeMap<String, Estudiant>> assigEstudiants;

    public GeneradorLlistats() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        label1 = new java.awt.Label();
        tNomArxiu = new javax.swing.JTextField();
        label2 = new java.awt.Label();
        bExaminar = new javax.swing.JButton();
        bGenerarLlista = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tAssignatures = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Generador de llistats");

        label1.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        label1.setText("Indica el fitxer que conté les dades a tractar: ");

        tNomArxiu.setEditable(false);

        label2.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        label2.setName(""); // NOI18N
        label2.setText("Selecciona les matèries de les quals vols generar llistes: ");

        bExaminar.setText("Examinar...");
        bExaminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExaminarActionPerformed(evt);
            }
        });

        bGenerarLlista.setText("Generar llistes");
        bGenerarLlista.setEnabled(false);
        bGenerarLlista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGenerarLlistaActionPerformed(evt);
            }
        });

        tAssignatures.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                tAssignaturesValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(tAssignatures);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tNomArxiu, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bExaminar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(18, 18, 18)
                        .addComponent(bGenerarLlista, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tNomArxiu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bExaminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(bGenerarLlista, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        label2.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>                        

    private void bExaminarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        JFileChooser examinar = new JFileChooser();
        examinar.showOpenDialog(null);
        fitxer = examinar.getSelectedFile();
        tNomArxiu.setText(fitxer.getAbsolutePath());
        Fitxer f = new Fitxer();

        try {
            assigEstudiants = f.obtenirEstudiantsAssignatures(fitxer);
            String[] assignatures = new String[assigEstudiants.size()];
            assigEstudiants.keySet().toArray(assignatures);
            tAssignatures.setListData(assignatures);
        } catch (IOException ex) {
            System.out.print(ex);
        }
    }                                         

    private void bGenerarLlistaActionPerformed(java.awt.event.ActionEvent evt) {                                               
        //Creem la llista d'assignatures que conte el fitxer
        List<String> llistaAssigXML = new ArrayList<String>(tAssignatures.getSelectedValuesList());

        //Creem l'array de les assignatures amb el tamany total de la llista anterior
        String[] assigXML = new String[llistaAssigXML.size()];

        //Omplim l'array
        llistaAssigXML.toArray(assigXML);
        
        //Conté assignatures, bàsicament per comprovar
        //tAssignatures.setListData(assigXML);

        //Creem un objecte de tipus llistaXML per a crear el fitxer
        FitxerXML llistaXML = new FitxerXML(assigXML, assigEstudiants);

        //A traves del metode crearLlistaXML creem el fitxer final XML
        llistaXML.crearLlistaXML(assigXML);
    }                                              

    private void tAssignaturesValueChanged(javax.swing.event.ListSelectionEvent evt) {                                           
        bGenerarLlista.setEnabled(true);
    }                                          

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
            java.util.logging.Logger.getLogger(GeneradorLlistats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GeneradorLlistats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GeneradorLlistats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GeneradorLlistats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GeneradorLlistats().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton bExaminar;
    private javax.swing.JButton bGenerarLlista;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private javax.swing.JList tAssignatures;
    private javax.swing.JTextField tNomArxiu;
    // End of variables declaration                   
}
