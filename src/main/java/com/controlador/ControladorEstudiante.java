package com.controlador;

import com.modelo.Colegio; // For secondary constructor
import com.modelo.Curso;
import com.modelo.Estudiante;
import com.dao.EstudianteDAO;
import com.dao.CursoDAO; // Added for future flexibility, though not strictly used in current methods
import com.dao.impl.EstudianteDaoImpl;
import com.dao.impl.CursoDaoImpl; // Added for secondary constructor

import java.util.Optional;

public class ControladorEstudiante { // Removed "extends ControladorGeneral"
    private Estudiante estudiante; // Logged-in estudiante

    private final EstudianteDAO estudianteDAO;
    private final CursoDAO cursoDAO; // Kept for potential future use

    // Constructor for Dependency Injection (Preferred)
    public ControladorEstudiante(EstudianteDAO estudianteDAO, CursoDAO cursoDAO, int codigoEstudiante) {
        this.estudianteDAO = estudianteDAO;
        this.cursoDAO = cursoDAO;
        this.estudiante = this.estudianteDAO.getById(codigoEstudiante).orElse(null);
        if (this.estudiante == null) {
            System.err.println("Advertencia: Estudiante con código " + codigoEstudiante + " no encontrado en ControladorEstudiante constructor.");
        }
    }

    // Secondary constructor for direct instantiation (e.g., if Principal doesn't handle DI yet)
    public ControladorEstudiante(Colegio colegio, int codigoEstudiante) {
        // 'colegio' instance is not strictly needed here if DAOs use Colegio.getInstance()
        // However, keeping it for consistency if other parts of Principal assume it's passed.
        this.estudianteDAO = new EstudianteDaoImpl();
        this.cursoDAO = new CursoDaoImpl(); // Initialize
        this.estudiante = this.estudianteDAO.getById(codigoEstudiante).orElse(null);
        if (this.estudiante == null) {
            System.err.println("Advertencia: Estudiante con código " + codigoEstudiante + " no encontrado en ControladorEstudiante constructor.");
        }
    }

    public String verReporteAcademico() {
        if (this.estudiante == null) {
            return "Estudiante no encontrado o no inicializado.";
        }
        // Assumes Estudiante object is fully loaded with its Asignaturas and Calificaciones by the DAO
        return this.estudiante.reporteAcademico();
    }

    public String verInfoCurso() {
        if (this.estudiante == null) {
            return "Estudiante no encontrado o no inicializado.";
        }
        if (this.estudiante.getCurso() == null) {
            return "El estudiante no está asignado a ningún curso.";
        }
        // Assumes Curso object within Estudiante is sufficiently populated
        // Using Descriptible method for consistency
        return this.estudiante.getCurso().obtenerDescripcionCompleta(); 
    }

    public Estudiante getEstudiante() {
        return this.estudiante;
    }

    // New method as per task description (might have been added in a previous session to this controller)
    public String getDetalleCompletoCalificaciones() {
        if (this.estudiante == null) {
            return "Estudiante no encontrado o no inicializado.";
        }
        // Delegates to the Estudiante model's method
        return this.estudiante.getDetalleCompletoCalificaciones();
    }
}
