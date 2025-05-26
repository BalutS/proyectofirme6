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
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel lblTitle = new JLabel("Crear Asignatura");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblTitle, gbc);

        // Reset gridwidth
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Subject Name Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Nombre Asignatura:"), gbc);

        // Subject Name TextField
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(txtNombreAsignatura, gbc);

        // Course Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Seleccionar Curso:"), gbc);

        // Course ComboBox
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(cmbCursos, gbc);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(btnGuardar);
        buttonsPanel.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonsPanel, gbc);
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
