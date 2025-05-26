package com.vista;

import com.controlador.ControladorDocente;
import com.modelo.Estudiante;
import com.modelo.Asignatura;
// Calificacion class is used for creating the object, but not passed directly to the current controller method signature.
// However, the controller method *will* create a Calificacion object.
// The Profesor's calificarEstudiante method *does* take individual fields to create Calificacion.
// import com.modelo.Calificacion; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FormularioCalificacion extends JDialog {
    private ControladorDocente controladorDocente;
    private Estudiante estudiante;
    private Asignatura asignatura;

    private JTextField txtNombreCalificacion;
    private JTextField txtNota;
    private JTextField txtPeriodo;
    private JTextField txtFecha; // For date input, e.g., "dd/MM/yyyy"
    private JButton btnGuardar;
    private JButton btnCancelar;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public FormularioCalificacion(Frame parent, ControladorDocente controladorDocente, Estudiante estudiante, Asignatura asignatura) {
        super(parent, "Agregar Calificación - " + estudiante.getNombre() + " - " + asignatura.getNombre(), true);
        this.controladorDocente = controladorDocente;
        this.estudiante = estudiante;
        this.asignatura = asignatura;

        initComponents();
        configurarEventos();
        pack();
        setLocationRelativeTo(parent);
        setResizable(false); // Optional: prevent resizing
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Increased insets for more spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Nombre Actividad
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Nombre Actividad:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        txtNombreCalificacion = new JTextField(20);
        add(txtNombreCalificacion, gbc);

        // Nota
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Nota (0.0 - 5.0):"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        txtNota = new JTextField(5); // Smaller field for nota
        add(txtNota, gbc);

        // Periodo
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Periodo (1-4):"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        txtPeriodo = new JTextField(5); // Smaller field for periodo
        add(txtPeriodo, gbc);

        // Fecha
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Fecha (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        txtFecha = new JTextField(10);
        txtFecha.setText(LocalDate.now().format(DATE_FORMATTER)); // Default to current date
        add(txtFecha, gbc);

        // Buttons panel
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0)); // Centered buttons with spacing
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Center the panel
        gbc.fill = GridBagConstraints.NONE; // Don't stretch the panel
        add(panelBotones, gbc);
    }

    private void configurarEventos() {
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCalificacion();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void guardarCalificacion() {
        String nombreActividad = txtNombreCalificacion.getText().trim();
        String notaStr = txtNota.getText().trim();
        String periodoStr = txtPeriodo.getText().trim();
        String fechaStr = txtFecha.getText().trim();

        // Validation
        if (nombreActividad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de la actividad no puede estar vacío.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            txtNombreCalificacion.requestFocus();
            return;
        }

        float nota;
        try {
            nota = Float.parseFloat(notaStr);
            if (nota < 0.0f || nota > 5.0f) {
                JOptionPane.showMessageDialog(this, "La nota debe estar entre 0.0 y 5.0.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                txtNota.requestFocus();
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Formato de nota inválido. Ingrese un número decimal (e.g., 3.5).", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            txtNota.requestFocus();
            return;
        }

        int periodo;
        try {
            periodo = Integer.parseInt(periodoStr);
            if (periodo < 1 || periodo > 4) { // Assuming 4 periods
                JOptionPane.showMessageDialog(this, "El periodo debe ser un número entre 1 y 4.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                txtPeriodo.requestFocus();
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Formato de periodo inválido. Ingrese un número entero.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            txtPeriodo.requestFocus();
            return;
        }

        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaStr, DATE_FORMATTER);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use dd/MM/yyyy (e.g., 25/12/2023).", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            txtFecha.requestFocus();
            return;
        }

        // If validation passes:
        try {
            // The existing ControladorDocente.calificarEstudiante takes these exact parameters
            controladorDocente.calificarEstudiante(
                estudiante.getCodigo(), 
                asignatura.getNombre(), 
                nombreActividad, // This is the 'nombreCalificacion' parameter
                nota, 
                periodo, 
                fecha
            );

            JOptionPane.showMessageDialog(this, "Calificación agregada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (IllegalStateException | IllegalArgumentException ex) {
             JOptionPane.showMessageDialog(this, "Error al guardar calificación: " + ex.getMessage(), "Error del Sistema", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Un error inesperado ocurrió: " + ex.getMessage(), "Error Inesperado", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // For debugging
        }
    }
}
