/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.vista;

import com.controlador.ControladorAdmin;
// import com.modelo.Colegio; // Colegio not directly used.
import com.modelo.Curso;
import com.modelo.Estudiante;
import java.awt.Frame;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author SOTO PC
 */
public class FormularioEstudiante extends javax.swing.JDialog {

    private ControladorAdmin controlador;

  public FormularioEstudiante(Frame parent, ControladorAdmin controlador) {
    super(parent, "Agregar Estudiante", true); // Set title
    this.controlador = controlador;
    initComponents();
    // Action listeners are set directly in initComponents for NetBeans generated code,
    // or can be explicitly added here if not.
    // btnGuardar.addActionListener(e -> btnGuardarActionPerformed(e)); // Already set by Netbeans form
    // btnCancelar.addActionListener(e -> btnCancelarActionPerformed(e)); // Already set by Netbeans form
    cargarCursos();
    configurarComponentes();
    this.setLocationRelativeTo(parent); // Center dialog
}

    private void cargarCursos() {
        cmbCursos.removeAllItems();
        if (controlador != null && controlador.getCursos() != null && !controlador.getCursos().isEmpty()) {
            for (Curso curso : controlador.getCursos()) {
                cmbCursos.addItem(curso); // Relies on Curso.toString()
            }
        } else {
            // Optionally, add a placeholder or disable if no courses
            // cmbCursos.addItem(null); // Or a string like "No hay cursos disponibles"
            // cmbCursos.setEnabled(false);
        }
    }

    private void configurarComponentes() {
        txtNombre.setToolTipText("Ingrese el nombre completo");
        txtEdad.setToolTipText("Edad en años");
        txtCedula.setToolTipText("Número de cédula");
        txtCodigo.setToolTipText("Código único del estudiante");
        cmbCursos.setToolTipText("Seleccione el curso (opcional)");
    }

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String nombre = txtNombre.getText().trim();
            String edadStr = txtEdad.getText().trim();
            String cedulaStr = txtCedula.getText().trim();
            String codigoStr = txtCodigo.getText().trim();

            if (nombre.isEmpty() || edadStr.isEmpty() || cedulaStr.isEmpty() || codigoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos (Nombre, Edad, Cédula, Código) son obligatorios.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int edad = Integer.parseInt(edadStr);
            int cedula = Integer.parseInt(cedulaStr);
            int codigo = Integer.parseInt(codigoStr);
            Curso cursoSeleccionado = (Curso) cmbCursos.getSelectedItem(); // Can be null if no course selected or no courses available

            if (edad <= 0 || cedula <= 0 || codigo <= 0) {
                 JOptionPane.showMessageDialog(this, "Edad, cédula y código deben ser números positivos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Check if codigo already exists for any Persona
            if (controlador.buscarEstudiante(codigo) != null || controlador.buscarProfesor(codigo) != null) {
                JOptionPane.showMessageDialog(this, "El código ingresado ya existe para otra persona.", "Error de Duplicación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create Estudiante with an empty list of asignaturas. Asignaturas are added later.
            Estudiante estudiante = new Estudiante(nombre, edad, cedula, codigo, "Estudiante"); // Tipo "Estudiante"
            
            if (cursoSeleccionado != null) {
                // If a course is selected, assign the student to this course.
                // The agregarEstudiante method in ControladorAdmin handles adding to colegio AND to the curso's list.
                estudiante.setCurso(cursoSeleccionado); 
            }
            
            controlador.agregarEstudiante(estudiante); // This method should handle adding to colegio and to curso if set.

            JOptionPane.showMessageDialog(this, "Estudiante registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en campos numéricos (Edad, Cédula, Código). Deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose(); 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblPlaceholder1 = new javax.swing.JLabel();
        lblPlaceholder2 = new javax.swing.JLabel();
        lblEdad = new javax.swing.JLabel();
        lblCedula = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        lblCurso = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtEdad = new javax.swing.JTextField();
        txtCedula = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        cmbCursos = new javax.swing.JComboBox<>();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Agregar Nuevo Estudiante");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Agregar Estudiante");

        lblNombre.setText("Nombre:");

        lblEdad.setText("Edad:");

        lblCedula.setText("Cédula:");

        lblCodigo.setText("Código:");

        lblCurso.setText("Curso:");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombre)
                    .addComponent(lblEdad)
                    .addComponent(lblCedula)
                    .addComponent(lblCodigo)
                    .addComponent(lblCurso))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(txtEdad)
                    .addComponent(txtCedula)
                    .addComponent(txtCodigo)
                    .addComponent(cmbCursos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(50, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblTitulo)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEdad)
                    .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCedula)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCurso)
                    .addComponent(cmbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar))
                .addGap(30, 30, 30))
        );

        // jLabel3 and jLabel4 were placeholders in the original file, renamed for clarity if they were meant for layout
        // If they were actual labels, their text would need to be set.
        // For now, assuming they were part of NetBeans' layout generation and can be omitted if not used.
        // Variables declaration from original, adapted to new names
        // jLabel1 -> lblTitulo
        // jLabel2 -> lblNombre
        // jLabel5 -> lblEdad
        // jLabel6 -> lblCedula
        // jLabel7 -> lblCodigo
        // jLabel8 -> lblCurso

        pack();
    }// </editor-fold>                        

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<Curso> cmbCursos;
    private javax.swing.JLabel lblTitulo;      // Renamed from jLabel1
    private javax.swing.JLabel lblNombre;      // Renamed from jLabel2
    private javax.swing.JLabel lblPlaceholder1; // Was jLabel3
    private javax.swing.JLabel lblPlaceholder2; // Was jLabel4
    private javax.swing.JLabel lblEdad;        // Renamed from jLabel5
    private javax.swing.JLabel lblCedula;      // Renamed from jLabel6
    private javax.swing.JLabel lblCodigo;      // Renamed from jLabel7
    private javax.swing.JLabel lblCurso;       // Renamed from jLabel8
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtEdad;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration                   
}
