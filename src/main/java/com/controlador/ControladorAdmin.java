package com.controlador;

import com.modelo.*;
import java.util.ArrayList; // Added for new method
import java.util.List;

public class ControladorAdmin extends ControladorGeneral {

    public ControladorAdmin(Colegio colegio) {
        super(colegio);
    }

    public void agregarEstudiante(Estudiante estudiante) {
    colegio.agregarEstudiante(estudiante);
    // Ensure curso is not null before trying to add student to it.
    if (estudiante.getCurso() != null) {
        // Ensure the curso's student list is initialized.
        if (estudiante.getCurso().getEstudiantes() == null) {
            estudiante.getCurso().setEstudiantes(new ArrayList<>());
        }
        estudiante.getCurso().getEstudiantes().add(estudiante);
    }
}

    public void agregarProfesor(Profesor profesor) {
        colegio.agregarProfesor(profesor);
    }

    public Estudiante buscarEstudiante(int codigo) {
        return colegio.buscarEstudiante(codigo);
    }

    public Profesor buscarProfesor(int codigo) {
        return colegio.buscarProfesor(codigo);
    }

    public void agregarCurso(Curso curso) {
        colegio.agregarCurso(curso);
    }

    public void asignarCursoAProfesor(int codProf, int grado, int grupo) {
        colegio.agregarCursoAProfesor(codProf, grado, grupo);
    }
    
    public boolean existeCurso(int grado, int grupo) {
        return colegio.getCursos().stream()
            .anyMatch(c -> c.getGrado() == grado && c.getGrupo() == grupo);
    }
    
    public void asignarEstudianteACurso(int codEst, int grado, int grupo) {
        colegio.agregarEstudianteACurso(codEst, grado, grupo);
    }

    public void agregarAsignatura(Asignatura asignatura) { // This seems to be a general method to add to colegio
        colegio.agregarAsignatura(asignatura);
    }

    public void asignarAsignaturaAEstudiante(int codEst, String nombreAsig) {
        colegio.agregarAsignaturaAEstudiante(codEst, nombreAsig);
    }

    public String generarReporteEstudiante(int codigo) {
        return colegio.reporteEstudiante(codigo);
    }

    public String listarTodosLosCursos() {
        return colegio.listarTodosLosCursos();
    }
    
    public String listarTodosLosProfesores() {
        return colegio.listarTodosLosProfesores(); 
    }
    
    public String listarTodosLosEstudiantes() {
        return colegio.listarTodosLosEstudiantes(); 
    }
    
    public List<Curso> getCursos() {
        return colegio.getCursos();
    }

    public List<Asignatura> getAsignaturas() { // Returns from Colegio
        return colegio.getAsignaturas();
    }

    public boolean existeCodigoEstudiante(int codigo) {
        return colegio.getPersonas().stream()
            .filter(p -> p instanceof Estudiante)
            .anyMatch(p -> p.getCodigo() == codigo);
    }

    // New method as per requirements
    public void crearAsignatura(String nombreAsignatura, Curso cursoSeleccionado) {
        Asignatura nuevaAsignatura = new Asignatura(nombreAsignatura);
        
        // Add the new Asignatura to the Colegio's list of asignaturas
        colegio.agregarAsignatura(nuevaAsignatura); 
        
        // Add the new Asignatura to the selected Curso's list of asignaturas
        if (cursoSeleccionado != null) {
            cursoSeleccionado.agregarAsignatura(nuevaAsignatura);
            
            // Associate the new Asignatura with all Estudiante's currently enrolled in cursoSeleccionado
            if (cursoSeleccionado.getEstudiantes() != null) {
                for (Estudiante estudiante : cursoSeleccionado.getEstudiantes()) {
                    // Ensure student's asignatura list is initialized
                    if (estudiante.getAsignaturas() == null) {
                        estudiante.setAsignaturas(new ArrayList<>());
                    }
                    estudiante.getAsignaturas().add(nuevaAsignatura);
                }
            }
        }
    }

    // New method as per requirements (optional but recommended)
    public boolean existeAsignaturaGlobalmente(String nombreAsignatura) {
        if (colegio.getAsignaturas() == null) {
            return false; // No asignaturas in colegio yet
        }
        for (Asignatura asig : colegio.getAsignaturas()) {
            if (asig.getNombre().equalsIgnoreCase(nombreAsignatura)) {
                return true;
            }
        }
        return false;
    }

    // --- Deletion Wrapper Methods ---

    public boolean eliminarEstudiante(int codigo) {
        return colegio.eliminarEstudiante(codigo);
    }

    public boolean eliminarProfesor(int codigo) {
        return colegio.eliminarProfesor(codigo);
    }

    public boolean eliminarCurso(int grado, int grupo) {
        return colegio.eliminarCurso(grado, grupo);
    }

    public boolean eliminarAsignatura(String nombreAsignatura) {
        return colegio.eliminarAsignatura(nombreAsignatura);
    }
}
