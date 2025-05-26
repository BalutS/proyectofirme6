package com.vista;

import com.controlador.ControladorAdmin;
import javax.swing.*; // Import all swing components
import java.awt.*;    // Import all AWT components
import java.awt.event.ActionEvent; // Though events are in lambdas, good practice
import java.awt.event.ActionListener; // Though events are in lambdas, good practice

public class MenuAdmin extends javax.swing.JFrame {
    private ControladorAdmin controlador;

    // Declare UI components as fields
    private JLabel jLabel1; // "MENU ADMIN"
    private JLabel titulo;  // "GESTION COLEGIO"
    private JButton jButton1; // "Agregar Docente"
    private JButton jButton2; // "Agregar Estudiante"
    private JButton btnListarDocentes;
    private JButton btnListarEstudiantes;
    private JButton btnCerrarSesion;
    private JButton btnCrearCurso;
    private JButton btnCrearAsignatura;
    private JButton btnEliminarEstudiante;
    private JButton btnEliminarProfesor;
    private JButton btnEliminarCurso;
    private JButton btnEliminarAsignatura;
    private JButton btnGenerarReporteEstudiante;

    public MenuAdmin() {
        // Default constructor for NetBeans compatibility or direct instantiation without controller
        initComponents();
        configurarEventos(); // Basic event setup might be needed even without controller
        setLocationRelativeTo(null);
    }

    public MenuAdmin(ControladorAdmin controlador) {
        this.controlador = controlador;
        initComponents();
        configurarEventos(); // Setup action listeners
        setLocationRelativeTo(null); // Center after pack() in initComponents
    }

    private void configurarEventos() {
        // Ensure all buttons have action listeners if they are not already set up
        // (Original code had these, so they should be preserved or re-implemented)

        // Example for jButton1 (Agregar Docente)
        if (jButton1 != null) {
            jButton1.addActionListener(e -> {
                if (this.controlador == null) {
                    JOptionPane.showMessageDialog(this, "Controlador no inicializado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                FormularioDocente formDocente = new FormularioDocente(this, controlador);
                // formDocente.setLocationRelativeTo(this); // Already done in FormularioDocente
                formDocente.setVisible(true);
            });
        }

        if (jButton2 != null) {
            jButton2.addActionListener(e -> {
                if (this.controlador == null) {
                    JOptionPane.showMessageDialog(this, "Controlador no inicializado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                FormularioEstudiante formEstudiante = new FormularioEstudiante(this, controlador);
                formEstudiante.setVisible(true);
            });
        }

        if (btnCerrarSesion != null) {
            btnCerrarSesion.addActionListener(e -> {
                Principal principal = new Principal();
                principal.setVisible(true);
                // principal.setLocationRelativeTo(null); // Done in Principal's constructor
                this.dispose();
            });
        }

        if (btnCrearCurso != null) {
            btnCrearCurso.addActionListener(e -> {
                if (this.controlador == null) {
                    JOptionPane.showMessageDialog(this, "Controlador no inicializado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                FormularioCurso formCurso = new FormularioCurso(this, controlador);
                formCurso.setVisible(true);
            });
        }
        
        if (btnListarDocentes != null) {
            btnListarDocentes.addActionListener(e -> {
                if (this.controlador == null) { return; }
                String listaDocentes = controlador.listarTodosLosProfesores();
                // Display in a JTextArea within a JScrollPane for better readability
                JTextArea textArea = new JTextArea(listaDocentes);
                textArea.setEditable(false);
                textArea.setWrapStyleWord(true);
                textArea.setLineWrap(true);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 400));
                JOptionPane.showMessageDialog(this, scrollPane, "Lista de Docentes", JOptionPane.INFORMATION_MESSAGE);
            });
        }

        if (btnListarEstudiantes != null) {
            btnListarEstudiantes.addActionListener(e -> {
                if (this.controlador == null) { return; }
                String listaEstudiantes = controlador.listarTodosLosEstudiantes();
                JTextArea textArea = new JTextArea(listaEstudiantes);
                textArea.setEditable(false);
                textArea.setWrapStyleWord(true);
                textArea.setLineWrap(true);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 400));
                JOptionPane.showMessageDialog(this, scrollPane, "Lista de Estudiantes", JOptionPane.INFORMATION_MESSAGE);
            });
        }

        if (btnCrearAsignatura != null) {
            btnCrearAsignatura.addActionListener(e -> {
                if (this.controlador == null) {
                    JOptionPane.showMessageDialog(this, "Controlador no inicializado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                FormularioAsignatura formAsignatura = new FormularioAsignatura(this, controlador);
                formAsignatura.setVisible(true);
            });
        }

        if (btnEliminarEstudiante != null) {
            btnEliminarEstudiante.addActionListener(e -> {
                if (this.controlador == null) { return; }
                String codigoStr = JOptionPane.showInputDialog(this, "Ingrese el código del estudiante a eliminar:", "Eliminar Estudiante", JOptionPane.QUESTION_MESSAGE);
                if (codigoStr != null && !codigoStr.trim().isEmpty()) {
                    try {
                        int codigo = Integer.parseInt(codigoStr.trim());
                        if (controlador.eliminarEstudiante(codigo)) {
                            JOptionPane.showMessageDialog(this, "Estudiante eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "No se encontró o no se pudo eliminar al estudiante con el código " + codigo + ".", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "El código ingresado no es un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (codigoStr != null) {
                    JOptionPane.showMessageDialog(this, "El código del estudiante no puede estar vacío.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        if (btnEliminarProfesor != null) {
            btnEliminarProfesor.addActionListener(e -> {
                 if (this.controlador == null) { return; }
                String codigoStr = JOptionPane.showInputDialog(this, "Ingrese el código del docente a eliminar:", "Eliminar Docente", JOptionPane.QUESTION_MESSAGE);
                if (codigoStr != null && !codigoStr.trim().isEmpty()) {
                    try {
                        int codigo = Integer.parseInt(codigoStr.trim());
                        if (controlador.eliminarProfesor(codigo)) {
                            JOptionPane.showMessageDialog(this, "Docente eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "No se encontró o no se pudo eliminar al docente con el código " + codigo + ".", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "El código ingresado no es un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (codigoStr != null) {
                    JOptionPane.showMessageDialog(this, "El código del docente no puede estar vacío.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        if (btnEliminarCurso != null) {
            btnEliminarCurso.addActionListener(e -> {
                if (this.controlador == null) { return; }
                String gradoStr = JOptionPane.showInputDialog(this, "Ingrese el grado del curso a eliminar:", "Eliminar Curso - Grado", JOptionPane.QUESTION_MESSAGE);
                if (gradoStr == null) return;
                if (gradoStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El grado no puede estar vacío.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String grupoStr = JOptionPane.showInputDialog(this, "Ingrese el grupo del curso a eliminar:", "Eliminar Curso - Grupo", JOptionPane.QUESTION_MESSAGE);
                if (grupoStr == null) return;
                if (grupoStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El grupo no puede estar vacío.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    int grado = Integer.parseInt(gradoStr.trim());
                    int grupo = Integer.parseInt(grupoStr.trim());
                    if (controlador.eliminarCurso(grado, grupo)) {
                        JOptionPane.showMessageDialog(this, "Curso " + grado + "-" + grupo + " eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró o no se pudo eliminar el curso " + grado + "-" + grupo + ".", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Grado y/o Grupo ingresados no son números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        if (btnEliminarAsignatura != null) {
            btnEliminarAsignatura.addActionListener(e -> {
                if (this.controlador == null) { return; }
                String nombreAsignatura = JOptionPane.showInputDialog(this, "Ingrese el nombre de la asignatura a eliminar:", "Eliminar Asignatura", JOptionPane.QUESTION_MESSAGE);
                if (nombreAsignatura != null && !nombreAsignatura.trim().isEmpty()) {
                    if (controlador.eliminarAsignatura(nombreAsignatura.trim())) {
                        JOptionPane.showMessageDialog(this, "Asignatura \"" + nombreAsignatura.trim() + "\" eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró o no se pudo eliminar la asignatura \"" + nombreAsignatura.trim() + "\".", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (nombreAsignatura != null) {
                    JOptionPane.showMessageDialog(this, "El nombre de la asignatura no puede estar vacío.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        if (btnGenerarReporteEstudiante != null) {
            btnGenerarReporteEstudiante.addActionListener(e -> {
                if (this.controlador == null) { return; }
                String codigoStr = JOptionPane.showInputDialog(this, "Ingrese el código del estudiante para generar el reporte:", "Generar Reporte Académico", JOptionPane.QUESTION_MESSAGE);
                if (codigoStr != null && !codigoStr.trim().isEmpty()) {
                    try {
                        int codigo = Integer.parseInt(codigoStr.trim());
                        if (controlador.buscarEstudiante(codigo) != null) {
                            String reporte = controlador.generarReporteEstudiante(codigo);
                            JTextArea textArea = new JTextArea(reporte);
                            textArea.setEditable(false);
                            textArea.setWrapStyleWord(true);
                            textArea.setLineWrap(true);
                            JScrollPane scrollPane = new JScrollPane(textArea);
                            scrollPane.setPreferredSize(new Dimension(500, 400));
                            JOptionPane.showMessageDialog(this, scrollPane, "Reporte Académico - Estudiante " + codigo, JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "Estudiante no encontrado con el código " + codigo + ".", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "El código ingresado no es un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (codigoStr != null) {
                    JOptionPane.showMessageDialog(this, "El código del estudiante no puede estar vacío.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }
    
    private void initComponents() {
        // Initialize components by creating new instances
        titulo = new JLabel();
        jLabel1 = new JLabel(); // This is for "MENU ADMIN"
        jButton1 = new JButton(); // Agregar Docente
        jButton2 = new JButton(); // Agregar Estudiante
        btnListarDocentes = new JButton();
        btnListarEstudiantes = new JButton();
        btnCerrarSesion = new JButton();
        btnCrearCurso = new JButton();
        btnCrearAsignatura = new JButton();
        btnEliminarEstudiante = new JButton();
        btnEliminarProfesor = new JButton(); // For "Eliminar Docente"
        btnEliminarCurso = new JButton();
        btnEliminarAsignatura = new JButton();
        btnGenerarReporteEstudiante = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menú Administrador");

        // Configure title labels
        titulo.setText("GESTION COLEGIO");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 36)); // BOLD for main title
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        jLabel1.setText("MENU ADMIN"); // Sub-title
        jLabel1.setFont(new Font("Segoe UI", Font.PLAIN, 24)); // PLAIN for sub-title
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        // Set button texts (ActionListeners are in configurarEventos)
        jButton1.setText("Agregar Docente");
        jButton2.setText("Agregar Estudiante");
        btnListarDocentes.setText("Listar Docentes");
        btnListarEstudiantes.setText("Listar Estudiantes");
        btnCerrarSesion.setText("Cerrar Sesión");
        btnCrearCurso.setText("Crear Curso");
        btnCrearAsignatura.setText("Crear Asignatura");
        btnEliminarEstudiante.setText("Eliminar Estudiante");
        btnEliminarProfesor.setText("Eliminar Docente");
        btnEliminarCurso.setText("Eliminar Curso");
        btnEliminarAsignatura.setText("Eliminar Asignatura");
        btnGenerarReporteEstudiante.setText("Reporte Académico Estudiante");
        
        // Set main window layout
        setLayout(new BorderLayout(10, 10)); // Gaps between regions

        // Title Panel (North)
        JPanel panelTitulo = new JPanel(new GridLayout(2, 1)); // 2 rows for stacked titles
        panelTitulo.add(titulo);
        panelTitulo.add(jLabel1);
        add(panelTitulo, BorderLayout.NORTH);

        // Action Buttons Panel (Center)
        // Using GridLayout with 0 rows means it will make as many rows as needed for the components, given 4 columns.
        JPanel panelBotonesAccion = new JPanel(new GridLayout(0, 4, 10, 10)); // 10px hgap & vgap
        panelBotonesAccion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding for the panel

        // Add buttons in a logical order
        panelBotonesAccion.add(jButton1);                     // Agregar Docente
        panelBotonesAccion.add(jButton2);                     // Agregar Estudiante
        panelBotonesAccion.add(btnCrearCurso);                // Crear Curso
        panelBotonesAccion.add(btnCrearAsignatura);           // Crear Asignatura
        
        panelBotonesAccion.add(btnListarDocentes);
        panelBotonesAccion.add(btnListarEstudiantes);
        panelBotonesAccion.add(btnGenerarReporteEstudiante);
        panelBotonesAccion.add(new JLabel());                 // Placeholder for grid alignment
        
        panelBotonesAccion.add(btnEliminarProfesor);          // Eliminar Docente
        panelBotonesAccion.add(btnEliminarEstudiante);
        panelBotonesAccion.add(btnEliminarCurso);
        panelBotonesAccion.add(btnEliminarAsignatura);

        // Wrap button panel in JScrollPane
        JScrollPane scrollPaneBotones = new JScrollPane(panelBotonesAccion);
        add(scrollPaneBotones, BorderLayout.CENTER);

        // Session Panel (South)
        JPanel panelSesion = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10)); // Align right, with gaps
        panelSesion.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 5)); // Padding for the panel
        panelSesion.add(btnCerrarSesion);
        add(panelSesion, BorderLayout.SOUTH);

        pack(); // Pack the frame to fit components
        // setLocationRelativeTo(null); // Moved to constructor after initComponents
    }
    // Removed empty jButton1ActionPerformed and btnCerrarSesionActionPerformed
    // as their logic is now handled by lambdas in configurarEventos.
}
