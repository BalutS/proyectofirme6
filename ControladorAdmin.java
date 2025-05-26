package com.controlador;

import com.modelo.*;
import java.util.List;

public class ControladorAdmin extends ControladorGeneral {

    public ControladorAdmin(Colegio colegio) {
        super(colegio);
    }

    public void agregarEstudiante(Estudiante estudiante) {
    colegio.agregarEstudiante(estudiante);
    estudiante.getCurso().getEstudiantes().add(estudiante);
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

    public void agregarAsignatura(Asignatura asignatura) {
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

    public List<Asignatura> getAsignaturas() {
        return colegio.getAsignaturas();
    }
    public boolean existeCodigoEstudiante(int codigo) {
    return colegio.getPersonas().stream()
        .filter(p -> p instanceof Estudiante)
        .anyMatch(p -> p.getCodigo() == codigo);
}


}
