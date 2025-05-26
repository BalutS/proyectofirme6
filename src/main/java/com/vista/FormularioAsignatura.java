package com.vista;

import com.controlador.ControladorAdmin;
import com.modelo.Curso;
import com.modelo.Asignatura; // Added import for Asignatura
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List; // Added import for List

public class FormularioAsignatura extends JDialog {

    private ControladorAdmin controlador;
    private JTextField txtNombreAsignatura;
    private JComboBox<Curso> cmbCursos;
    private JButton btnGuardar;
    private JButton btnCancelar;

    public FormularioAsignatura(Frame parent, ControladorAdmin controlador) {
        super(parent, "Crear Asignatura", true);
        this.controlador = controlador;

        initComponents();
        populateCursos();
        setupLayout();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        txtNombreAsignatura = new JTextField(20);
        cmbCursos = new JComboBox<>();
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarAsignatura();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void populateCursos() {
        List<Curso> cursos = controlador.getCursos(); // Changed ArrayList to List
        if (cursos != null) {
            for (Curso curso : cursos) {
                cmbCursos.addItem(curso);
            }
        }
    }

    private void setupLayout() {
        // Create labels (fields are already initialized in initComponents)
        JLabel lblDialogTitle = new JLabel("Crear Asignatura");
        lblDialogTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblDialogTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblNombreAsignaturaText = new JLabel("Nombre Asignatura:");
        lblNombreAsignaturaText.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel lblCursoText = new JLabel("Seleccionar Curso:");
        lblCursoText.setHorizontalAlignment(SwingConstants.RIGHT);

        // GroupLayout setup
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addComponent(lblDialogTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombreAsignaturaText, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCursoText, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNombreAsignatura, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(cmbCursos, 0, 200, Short.MAX_VALUE)))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // Push to center
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)) // Push to center
        );
        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnGuardar, btnCancelar});


        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(lblDialogTitle)
            .addGap(18) // Gap after title
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblNombreAsignaturaText)
                .addComponent(txtNombreAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED) // Gap between rows
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblCursoText)
                .addComponent(cmbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(25) // Gap before buttons
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnGuardar)
                .addComponent(btnCancelar))
        );
    }

    private void guardarAsignatura() {
        String nombreAsignatura = txtNombreAsignatura.getText().trim();
        Curso cursoSeleccionado = (Curso) cmbCursos.getSelectedItem();

        if (nombreAsignatura.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de la asignatura no puede estar vacío.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cursoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un curso.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Use the global existence check from ControladorAdmin
        if (controlador.existeAsignaturaGlobalmente(nombreAsignatura)) {
            // If it exists globally, check if it's already in the selected course.
            // If it is, then it's an error.
            // If it's not, the current design of `crearAsignatura` would create a new one and add it,
            // which might be okay if we want to allow different courses to have "copies" of asignatura names,
            // but the `existeAsignaturaGlobalmente` is meant to prevent duplicates in the COLEGIO's list.
            // For this iteration, if it exists globally, we will prevent creating another one.
            // A more advanced system might allow associating an existing global Asignatura to a course
            // without re-creating it.

            // Check if the globally existing asignatura is already part of this specific course.
            Asignatura asigExistenteEnCurso = cursoSeleccionado.buscarAsignatura(nombreAsignatura);
            if (asigExistenteEnCurso != null) {
                 JOptionPane.showMessageDialog(this, "Una asignatura con este nombre ya existe en el curso seleccionado.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                 return;
            } else {
                // If it exists globally but not in this course, the current `crearAsignatura` will create a new one.
                // This might lead to two Asignatura objects with the same name in the system if not careful.
                // The `existeAsignaturaGlobalmente` check is intended to prevent this.
                // So, if it exists globally, we should show an error.
                 JOptionPane.showMessageDialog(this, "Una asignatura con este nombre ya existe globalmente en el sistema. No se puede crear una nueva con el mismo nombre.", "Error de Duplicación", JOptionPane.ERROR_MESSAGE);
                 return;
            }
        }

        // If the asignatura does not exist globally, then proceed to create it.
        controlador.crearAsignatura(nombreAsignatura, cursoSeleccionado);

        JOptionPane.showMessageDialog(this, "Asignatura creada y asociada al curso exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
