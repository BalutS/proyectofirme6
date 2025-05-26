package com.vista;

import com.controlador.ControladorEstudiante;
import com.modelo.Estudiante; // Not directly used as type in fields, but good for context
import com.modelo.Asignatura; // For future use in mostrarTodasLasNotas
import com.modelo.Calificacion; // For future use in mostrarTodasLasNotas
// import com.modelo.Colegio; // Not directly used by MenuEstudiante, Principal handles it

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuEstudiante extends JFrame {
    private ControladorEstudiante controlador;
    private JTextArea areaReporte;
    private JButton btnVerMisNotas; // To show detailed grades
    private JButton btnCerrarSesion;

    public MenuEstudiante(ControladorEstudiante controlador) {
        this.controlador = controlador;
        initComponents();
        configurarVentana();
        mostrarReporteAcademico(); // Load initial report
        configurarEventos();
    }

    private void initComponents() {
        areaReporte = new JTextArea();
        areaReporte.setEditable(false);
        areaReporte.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPaneReporte = new JScrollPane(areaReporte);

        btnVerMisNotas = new JButton("Ver Todas Mis Notas Detalladas");
        btnCerrarSesion = new JButton("Cerrar Sesión");

        // Layout
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Center buttons with spacing
        panelBotones.add(btnVerMisNotas);
        panelBotones.add(btnCerrarSesion);

        setLayout(new BorderLayout(0,10)); // Add vertical gap
        add(scrollPaneReporte, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void configurarVentana() {
        if (controlador != null && controlador.getEstudiante() != null) {
            setTitle("Menú Estudiante - " + controlador.getEstudiante().getNombre());
        } else {
            setTitle("Menú Estudiante - Estudiante no encontrado");
        }
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void mostrarReporteAcademico() {
        if (controlador != null) {
            String reporte = controlador.verReporteAcademico();
            areaReporte.setText(reporte);
            areaReporte.setCaretPosition(0); // Scroll to top
        } else {
            areaReporte.setText("Error: Controlador no disponible.");
        }
    }
    
    private void mostrarTodasLasNotas() {
        if (controlador == null || controlador.getEstudiante() == null) {
            areaReporte.setText("No se pueden mostrar las notas: Estudiante no disponible.");
            return;
        }
        
        String detalleCompleto = controlador.getEstudiante().getDetalleCompletoCalificaciones();
        areaReporte.setText(detalleCompleto);
        areaReporte.setCaretPosition(0); // Scroll to top
    }


    private void configurarEventos() {
        btnVerMisNotas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTodasLasNotas(); 
            }
        });

        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuEstudiante.this.dispose();
                new Principal().setVisible(true); // Assumes Principal's default constructor is appropriate
            }
        });
    }
}
