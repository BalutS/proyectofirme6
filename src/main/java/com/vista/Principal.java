/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.vista;

import com.controlador.ControladorAdmin;
import com.controlador.ControladorDocente;
import com.controlador.ControladorEstudiante;
// Colegio import is removed as it's no longer a field here. DAOs will handle Colegio.getInstance().
// import com.modelo.Colegio; 

import com.dao.AsignaturaDAO;
import com.dao.CalificacionDAO;
import com.dao.CursoDAO;
import com.dao.EstudianteDAO;
import com.dao.ProfesorDAO;
import com.dao.impl.AsignaturaDaoImpl;
import com.dao.impl.CalificacionDaoImpl;
import com.dao.impl.CursoDaoImpl;
import com.dao.impl.EstudianteDaoImpl;
import com.dao.impl.ProfesorDaoImpl;

import javax.swing.JOptionPane;

/**
 *
 * @author river
 */
public class Principal extends javax.swing.JFrame {

    // private Colegio colegio; // Removed
    // private ControladorAdmin controladorAdmin; // Removed, will be instantiated on demand

    public Principal() {
        // this.colegio = Colegio.getInstance("Mi Colegio"); // Removed
        // this.controladorAdmin = new ControladorAdmin(this.colegio); // Removed
        initComponents(); 
        configurarEventos(); 
        this.setLocationRelativeTo(null); 
    }
    
    private void configurarEventos() {
        btnContinue.addActionListener(e -> {
            String rolSeleccionado = (String) rolComboBox.getSelectedItem();
            int codigo = -1; 
            
            // Instantiate DAOs here, once per login attempt.
            EstudianteDAO estudianteDAO = new EstudianteDaoImpl();
            ProfesorDAO profesorDAO = new ProfesorDaoImpl();
            CursoDAO cursoDAO = new CursoDaoImpl();
            AsignaturaDAO asignaturaDAO = new AsignaturaDaoImpl();
            CalificacionDAO calificacionDAO = new CalificacionDaoImpl(); // Needed for ControladorDocente

            // Temporary admin controller for authentication if needed by non-ADMIN roles
            // Uses the default constructor which instantiates its own DAOs internally,
            // or we could pass the DAOs we just created if its auth method used them.
            // For simplicity, assuming ControladorAdmin's default constructor is fine for auth.
            // Or better, if autenticarPersona doesn't rely on much state, it could be static,
            // or Principal could do the simple DAO check itself.
            // Let's use a temporary controller instance for authentication.
            ControladorAdmin authController = new ControladorAdmin(estudianteDAO, profesorDAO, cursoDAO, asignaturaDAO);


            try {
                if (!"ADMIN".equals(rolSeleccionado)) {
                    String codigoStr = JOptionPane.showInputDialog(this, "Ingrese su código:");
                    if (codigoStr == null) { 
                        return;
                    }
                    if (codigoStr.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "El código no puede estar vacío.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    codigo = Integer.parseInt(codigoStr.trim());
                }

                boolean autenticado = false;
                if ("ADMIN".equals(rolSeleccionado)) {
                    autenticado = true; // Admin role doesn't require code authentication here
                } else if ("DOCENTE".equals(rolSeleccionado)) {
                    // Using the autenticarPersona method from the instantiated authController
                    autenticado = authController.autenticarPersona(codigo, "Profesor"); 
                } else if ("ESTUDIANTE".equals(rolSeleccionado)) {
                    autenticado = authController.autenticarPersona(codigo, "Estudiante"); 
                }


                if (autenticado) {
                    // Dispose only after successful role-specific validation and menu creation
                    // JOptionPane.showMessageDialog(this, "Bienvenido " + rolSeleccionado + "!", "Acceso Exitoso", JOptionPane.INFORMATION_MESSAGE);
                    
                    switch (rolSeleccionado) {
                        case "ADMIN":
                            JOptionPane.showMessageDialog(this, "Bienvenido " + rolSeleccionado + "!", "Acceso Exitoso", JOptionPane.INFORMATION_MESSAGE);
                            this.dispose();
                            // Create new ControladorAdmin instance with injected DAOs for the MenuAdmin
                            ControladorAdmin adminController = new ControladorAdmin(estudianteDAO, profesorDAO, cursoDAO, asignaturaDAO);
                            MenuAdmin menuAdmin = new MenuAdmin(adminController); 
                            menuAdmin.setVisible(true);
                            break;
                        case "DOCENTE":
                            // Pass the already instantiated DAOs
                            ControladorDocente docenteController = new ControladorDocente(profesorDAO, estudianteDAO, asignaturaDAO, calificacionDAO, cursoDAO, codigo);
                            if (docenteController.getProfesor() == null || docenteController.getProfesor().getCurso() == null) { 
                                JOptionPane.showMessageDialog(this, "Docente con código " + codigo + " no encontrado o sin curso asignado.", "Error de Acceso", JOptionPane.ERROR_MESSAGE);
                                return; 
                            }
                            JOptionPane.showMessageDialog(this, "Bienvenido " + rolSeleccionado + "!", "Acceso Exitoso", JOptionPane.INFORMATION_MESSAGE);
                            this.dispose(); 
                            MenuDocente menuDocente = new MenuDocente(docenteController); 
                            menuDocente.setVisible(true);
                            break;
                        case "ESTUDIANTE":
                            // Pass the already instantiated DAOs
                            ControladorEstudiante estudianteController = new ControladorEstudiante(estudianteDAO, cursoDAO, codigo);
                            if (estudianteController.getEstudiante() == null) { 
                                JOptionPane.showMessageDialog(this, "Estudiante con código " + codigo + " no encontrado.", "Error de Acceso", JOptionPane.ERROR_MESSAGE);
                                return; 
                            }
                            JOptionPane.showMessageDialog(this, "Bienvenido " + rolSeleccionado + "!", "Acceso Exitoso", JOptionPane.INFORMATION_MESSAGE);
                            this.dispose(); 
                            MenuEstudiante menuEstudiante = new MenuEstudiante(estudianteController);
                            menuEstudiante.setVisible(true);
                            break;
                    }
                } else {
                     if (!"ADMIN".equals(rolSeleccionado)) { 
                        JOptionPane.showMessageDialog(this, "Código o rol incorrecto.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
                     } else if ("ADMIN".equals(rolSeleccionado) && !autenticado) { 
                        JOptionPane.showMessageDialog(this, "Error de autenticación para ADMIN.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
                     }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un código numérico válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); 
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        rolLabel = new javax.swing.JLabel();
        rolComboBox = new javax.swing.JComboBox<>();
        btnContinue = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        titulo.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("GESTION COLEGIO");

        rolLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        rolLabel.setText("SELECCIONA TU ROL:");

        rolComboBox.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        rolComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ADMIN", "DOCENTE", "ESTUDIANTE" }));

        btnContinue.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnContinue.setText("CONTINUAR");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);

        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER) 
                .addComponent(titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) 
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rolLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED) 
                    .addComponent(rolComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE) 
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)) 
                .addComponent(btnContinue) 
        );

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40) 
                .addComponent(titulo)
                .addGap(50, 50, 50) 
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rolLabel)
                    .addComponent(rolComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30) 
                .addComponent(btnContinue)
                .addContainerGap(40, Short.MAX_VALUE) 
        ); 

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        
                                         
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

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnContinue;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> rolComboBox;
    private javax.swing.JLabel rolLabel;
    private javax.swing.JLabel titulo;
    // End of variables declaration                   
}
