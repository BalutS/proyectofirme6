package com.vista;

import com.controlador.ControladorDocente;
import com.modelo.Curso;
import com.modelo.Estudiante;
import com.modelo.Asignatura;
import com.modelo.Profesor;
import com.modelo.Colegio; // Added for btnCerrarSesion

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List; // Retained though ArrayList is more specific below
import java.util.ArrayList;

public class MenuDocente extends javax.swing.JFrame {
    private ControladorDocente controlador;
    private JTextArea areaInfoCursoEstudiantes;
    private JComboBox<Estudiante> cmbEstudiantes;
    private JComboBox<Asignatura> cmbAsignaturas;
    private JButton btnAgregarCalificacion;
    private JButton btnCerrarSesion;
    // Optional buttons, not implemented in this step
    // private JButton btnModificarCalificacion;
    // private JButton btnEliminarCalificacion;

    public MenuDocente(ControladorDocente controlador) {
        this.controlador = controlador;
        initComponents(); // Initialize components first
        configurarVentana(); // Then configure window properties
        cargarDatosProfesor(); // Load data based on controller
        configurarEventos(); // Finally, set up event listeners
    }

    private void initComponents() {
        // Initialize components (fields are already declared)
        areaInfoCursoEstudiantes = new JTextArea();
        areaInfoCursoEstudiantes.setEditable(false);
        areaInfoCursoEstudiantes.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPaneInfo = new JScrollPane(areaInfoCursoEstudiantes);

        cmbEstudiantes = new JComboBox<>();
        cmbAsignaturas = new JComboBox<>();
        btnAgregarCalificacion = new JButton("Agregar Calificación");
        btnCerrarSesion = new JButton("Cerrar Sesión");

        // Create JLabels for the controls
        JLabel lblEstudiante = new JLabel("Estudiante:");
        JLabel lblAsignatura = new JLabel("Asignatura:");

        // GroupLayout setup
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Horizontal Group
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblEstudiante)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbEstudiantes, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18)
                .addComponent(lblAsignatura)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbAsignaturas, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18)
                .addComponent(btnAgregarCalificacion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)) // Pushes to left
            .addComponent(scrollPaneInfo) // Spans full width
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup() // For btnCerrarSesion
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCerrarSesion))
        );
        
        // Ensure btnAgregarCalificacion and btnCerrarSesion have reasonable minimum sizes if needed
        // layout.linkSize(SwingConstants.HORIZONTAL, btnAgregarCalificacion, btnCerrarSesion);


        // Vertical Group
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblEstudiante)
                .addComponent(cmbEstudiantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblAsignatura)
                .addComponent(cmbAsignaturas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnAgregarCalificacion))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED) // Gap
            .addComponent(scrollPaneInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE) // Main area, grows
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED) // Gap
            .addComponent(btnCerrarSesion)
        );
        
        // pack(); // pack() is called after this in the constructor sequence via configurarVentana -> setSize
                 // Actually, setSize is in configurarVentana, pack() is not explicitly called there.
                 // It's better to call pack() here or in the constructor after all setup.
                 // For this refactor, let's add pack() at the end of initComponents.
        pack(); 
    }

    private void configurarVentana() {
        if (controlador.getProfesor() != null) {
            setTitle("Menú Docente - " + controlador.getProfesor().getNombre());
        } else {
            setTitle("Menú Docente - Profesor no encontrado");
        }
        setSize(800, 600);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // EXIT_ON_CLOSE closes the whole app
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // DISPOSE_ON_CLOSE is better for sub-menus
        setLocationRelativeTo(null);
    }

    private void cargarDatosProfesor() {
        Profesor profesor = controlador.getProfesor();

        if (profesor == null) {
            areaInfoCursoEstudiantes.setText("Error: No se pudo cargar la información del profesor.");
            disableInteraction();
            return;
        }

        Curso curso = profesor.getCurso();
        areaInfoCursoEstudiantes.setText("Profesor: " + profesor.getInfoCompleta() + "\n\n"); // Using getInfoCompleta

        if (curso == null) {
            areaInfoCursoEstudiantes.append("No tiene un curso asignado actualmente.");
            disableInteraction();
        } else {
            areaInfoCursoEstudiantes.append("Curso Asignado: " + curso.toString() + "\n\n"); // Curso.toString() for grade and group
            areaInfoCursoEstudiantes.append("--- Estudiantes del Curso ---\n" + controlador.listarEstudiantesEnCurso());

            cmbEstudiantes.removeAllItems();
            if (curso.getEstudiantes() != null && !curso.getEstudiantes().isEmpty()) {
                for (Estudiante est : curso.getEstudiantes()) {
                    cmbEstudiantes.addItem(est); // Relies on Estudiante.toString()
                }
                // Trigger selection of first student to populate asignaturas
                if (cmbEstudiantes.getItemCount() > 0) {
                    cmbEstudiantes.setSelectedIndex(0); 
                }
            } else {
                 areaInfoCursoEstudiantes.append("\nNo hay estudiantes matriculados en este curso.");
                 disableStudentSpecificInteraction(); // Disable only student-specific parts
            }
            enableInteraction();
        }
    }
    
    private void disableInteraction() {
        cmbEstudiantes.setEnabled(false);
        cmbAsignaturas.setEnabled(false);
        btnAgregarCalificacion.setEnabled(false);
    }

    private void disableStudentSpecificInteraction() {
        // cmbEstudiantes might still be enabled to show "no students" or similar
        cmbAsignaturas.setEnabled(false);
        btnAgregarCalificacion.setEnabled(false);
        cmbAsignaturas.removeAllItems(); // Clear asignaturas if no student or no asignaturas for student
    }
    
    private void enableInteraction() {
        cmbEstudiantes.setEnabled(true);
        // Asignaturas and button are enabled based on student selection
    }


    private void configurarEventos() {
        cmbEstudiantes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Estudiante seleccionado = (Estudiante) cmbEstudiantes.getSelectedItem();
                cmbAsignaturas.removeAllItems();
                if (seleccionado != null) {
                    if (seleccionado.getAsignaturas() != null && !seleccionado.getAsignaturas().isEmpty()) {
                        for (Asignatura asig : seleccionado.getAsignaturas()) {
                            cmbAsignaturas.addItem(asig); // Relies on Asignatura.toString()
                        }
                        cmbAsignaturas.setEnabled(true);
                        btnAgregarCalificacion.setEnabled(true);
                    } else {
                        // No asignaturas for this student
                        cmbAsignaturas.setEnabled(false);
                        btnAgregarCalificacion.setEnabled(false);
                    }
                } else {
                     // No student selected or cmbEstudiantes is empty
                    cmbAsignaturas.setEnabled(false);
                    btnAgregarCalificacion.setEnabled(false);
                }
            }
        });

        btnAgregarCalificacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Estudiante estSeleccionado = (Estudiante) cmbEstudiantes.getSelectedItem();
                Asignatura asigSeleccionada = (Asignatura) cmbAsignaturas.getSelectedItem();

                if (estSeleccionado == null || asigSeleccionada == null) {
                    JOptionPane.showMessageDialog(MenuDocente.this, 
                        "Debe seleccionar un estudiante y una asignatura.", 
                        "Selección Requerida", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Create and show the FormularioCalificacion
                FormularioCalificacion formCalificacion = new FormularioCalificacion(
                    MenuDocente.this, // Parent frame
                    controlador,      // ControladorDocente instance
                    estSeleccionado,  // Selected Estudiante
                    asigSeleccionada  // Selected Asignatura
                );
                formCalificacion.setVisible(true);

                // Optional: Refresh data if needed after form closes.
                // For example, if MenuDocente displayed averages that might change:
                // cargarDatosProfesor(); // This would re-fetch and re-display data.
                // For now, this is not strictly necessary as changes are self-contained
                // within the grading process and not directly reflected as averages in MenuDocente's current UI.
            }
        });

        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuDocente.this.dispose();
                // Principal needs to be instantiated. Assuming Principal's constructor initializes Colegio.
                // If Colegio needs to be passed, Principal's constructor should accept it.
                // For now, using the default constructor of Principal which internally calls Colegio.getInstance().
                Principal principal = new Principal(); 
                principal.setVisible(true);
            }
        });
        
        // Manually trigger the action listener for the initially selected student (if any)
        // to populate the asignaturas combo box.
        if (cmbEstudiantes.getItemCount() > 0) {
            cmbEstudiantes.setSelectedIndex(0); // This will trigger its ActionListener
        } else {
            // Ensure dependent combos are disabled if no students initially
            cmbAsignaturas.setEnabled(false);
            btnAgregarCalificacion.setEnabled(false);
        }
    }
}
