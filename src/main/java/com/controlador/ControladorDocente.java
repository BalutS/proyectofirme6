package com.controlador;

import com.modelo.*;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ControladorDocente { // Removed "extends ControladorGeneral"
    private Profesor profesor; // Logged-in professor

    private final ProfesorDAO profesorDAO;
    private final EstudianteDAO estudianteDAO;
    private final AsignaturaDAO asignaturaDAO;
    private final CalificacionDAO calificacionDAO;
    private final CursoDAO cursoDAO; 

    // Constructor for Dependency Injection (Preferred)
    public ControladorDocente(ProfesorDAO profesorDAO, EstudianteDAO estudianteDAO, 
                              AsignaturaDAO asignaturaDAO, CalificacionDAO calificacionDAO, 
                              CursoDAO cursoDAO, int codigoProfesor) {
        // super() call removed as it no longer extends ControladorGeneral
        this.profesorDAO = profesorDAO;
        this.estudianteDAO = estudianteDAO;
        this.asignaturaDAO = asignaturaDAO;
        this.calificacionDAO = calificacionDAO;
        this.cursoDAO = cursoDAO;
        this.profesor = profesorDAO.getById(codigoProfesor).orElse(null);
        if (this.profesor == null) {
            System.err.println("Advertencia: Profesor con código " + codigoProfesor + " no encontrado en ControladorDocente constructor.");
        }
    }

    // Secondary constructor for direct instantiation (e.g., if Principal doesn't handle DI yet)
    public ControladorDocente(Colegio colegio, int codigoProfesor) {
        // This constructor might still need 'colegio' if other parts of the system pass it,
        // but DAOs will use Colegio.getInstance(). For consistency if super(colegio) was vital.
        // For now, let's assume DAOs handle Colegio instance internally.
        this.profesorDAO = new ProfesorDaoImpl();
        this.estudianteDAO = new EstudianteDaoImpl();
        this.asignaturaDAO = new AsignaturaDaoImpl();
        this.calificacionDAO = new CalificacionDaoImpl();
        this.cursoDAO = new CursoDaoImpl(); // Initialize
        this.profesor = this.profesorDAO.getById(codigoProfesor).orElse(null);
        if (this.profesor == null) {
            System.err.println("Advertencia: Profesor con código " + codigoProfesor + " no encontrado en ControladorDocente constructor.");
        }
    }


    public void calificarEstudiante(int codEst, String nombreAsignatura, String nombreCalificacion, float nota, int periodo, LocalDate fecha) {
        if (this.profesor == null) {
            System.err.println("Error: Profesor no autenticado o no encontrado.");
            // Optionally throw an exception or return a status
            return;
        }
        
        Optional<Estudiante> estOpt = estudianteDAO.getById(codEst);
        if (!estOpt.isPresent()) {
            System.err.println("Error: Estudiante con código " + codEst + " no encontrado.");
            // Optionally throw an exception
            return;
        }
        Estudiante estudiante = estOpt.get();

        // Ensure the student is in the professor's course (important validation)
        if (profesor.getCurso() == null || 
            profesor.getCurso().getEstudiantes() == null || 
            profesor.getCurso().getEstudiantes().stream().noneMatch(e -> e.getCodigo() == codEst)) {
            System.err.println("Error: Estudiante con código " + codEst + " no pertenece al curso del profesor " + profesor.getNombre() + ".");
            return;
        }

        Optional<Asignatura> asigOpt = estudiante.getAsignaturas().stream()
                .filter(a -> a.getNombre().equalsIgnoreCase(nombreAsignatura))
                .findFirst();

        if (asigOpt.isPresent()) {
            Asignatura asignatura = asigOpt.get();
            Calificacion nuevaCalificacion = new Calificacion(nombreCalificacion, nota, periodo, fecha);
            calificacionDAO.addCalificacionToAsignatura(asignatura, nuevaCalificacion);
            // No explicit update needed for Asignatura/Estudiante if CalificacionDAO modifies the object
            // directly and these objects are part of the main lists managed by Colegio.
            // However, if DAOs were to work with copies, an update would be needed here.
            // e.g., estudianteDAO.update(estudiante); or asignaturaDAO.update(asignatura);
             System.out.println("Calificación agregada a: " + asignatura.getNombre() + " para " + estudiante.getNombre());
        } else {
            System.err.println("Error: Asignatura \"" + nombreAsignatura + "\" no encontrada para el estudiante " + estudiante.getNombre() + ".");
        }
    }

    public String listarEstudiantesEnCurso() {
        if (this.profesor == null) {
            return "Profesor no autenticado o no encontrado.";
        }
        if (this.profesor.getCurso() == null) {
            return "El profesor no está asignado a ningún curso.";
        }
        
        Curso cursoProfesor = this.profesor.getCurso();
        // Fetch the most up-to-date course details, including its student list
        Optional<Curso> cursoOpt = cursoDAO.getByGradoAndGrupo(cursoProfesor.getGrado(), cursoProfesor.getGrupo());

        if (cursoOpt.isPresent()) {
            List<Estudiante> estudiantes = cursoOpt.get().getEstudiantes();
            if (estudiantes == null || estudiantes.isEmpty()) {
                return "No hay estudiantes matriculados en este curso.";
            }
            StringBuilder sb = new StringBuilder();
            for (Estudiante est : estudiantes) {
                sb.append(est.obtenerResumen()).append("\n"); // Using Descriptible
            }
            return sb.toString();
        } else {
            return "Error al cargar la información del curso.";
        }
    }

    public String verInfoCurso() {
        if (this.profesor == null) {
            return "Profesor no autenticado o no encontrado.";
        }
        if (this.profesor.getCurso() == null) {
            return "El profesor no está asignado a ningún curso.";
        }
        // The Curso object on Profesor should be sufficient if it's kept updated.
        // For more robustness, one could re-fetch from cursoDAO, but it might be redundant
        // if relationship management is solid.
        return this.profesor.getCurso().obtenerDescripcionCompleta(); // Using Descriptible
    }

    public Profesor getProfesor() {
        return profesor;
    }
}
