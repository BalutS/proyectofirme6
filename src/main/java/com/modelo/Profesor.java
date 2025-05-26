package com.modelo;

import java.util.ArrayList; // Required if Calificacion list is added to Profesor directly
import java.time.LocalDate; // Required for creating Calificacion objects

public class Profesor extends Persona {
    private Curso curso; // The course this professor teaches

    public Profesor() {
        super(); // Call to Persona constructor
        this.curso = null;
    }

    public Profesor(String nombre, int edad, int cedula, int codigo, String tipo) {
        super(nombre, edad, cedula, codigo, tipo);
        this.curso = null; // Initially no course, assigned later
    }

    // Constructor with Curso, if needed during instantiation
    public Profesor(Curso curso, String nombre, int edad, int cedula, int codigo, String tipo) {
        super(nombre, edad, cedula, codigo, tipo);
        this.curso = curso;
        // Optionally, ensure the course also knows about this professor
        // if (curso != null && curso.getProfesor() != this) {
        //     curso.setProfesor(this);
        // }
    }
    
    public void calificarEstudiante(int codEst, String nombreAsignatura, String nombreCalificacion, float nota, int periodo, LocalDate fecha) {
        if (this.curso == null) {
            throw new IllegalStateException("El profesor no tiene un curso asignado y no puede calificar.");
        }
        Estudiante estudiante = this.curso.buscarEstudiante(codEst);
        if (estudiante == null) {
            throw new IllegalArgumentException("Estudiante con código " + codEst + " no encontrado en el curso del profesor.");
        }
        Asignatura asignatura = estudiante.buscarAsignatura(nombreAsignatura);
        if (asignatura == null) {
            throw new IllegalArgumentException("Asignatura \"" + nombreAsignatura + "\" no encontrada para el estudiante " + estudiante.getNombre() + ".");
        }
        
        Calificacion nuevaCalificacion = new Calificacion(nombreCalificacion, nota, periodo, fecha);
        asignatura.agregarCalificacion(nuevaCalificacion);
    }
    
    public String listarEstudiantes() {
        if (curso == null) {
            return "El profesor no tiene un curso asignado.";
        }
        return curso.listarEstudiantes();
    }
    
    @Override
    public String toString() {
        // For JComboBox display or simple listings
        return getNombre() + " (Cód: " + getCodigo() + ")";
    }

    public String getInfoCompleta() {
        String cursoInfo = (curso != null) ? " (Curso: " + curso.getGrado() + "-" + curso.getGrupo() + ")" : " (Sin curso asignado)";
        return "Docente: " + getNombre() + ", Código: " + getCodigo() + ", Cédula: " + getCedula() + ", Edad: " + getEdad() + cursoInfo;       
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso nuevoCurso) {
        // If this professor was previously assigned to a different course, that course should no longer have this professor.
        if (this.curso != null && this.curso.getProfesor() == this) {
            this.curso.setProfesor(null); 
        }
        
        this.curso = nuevoCurso;
        
        // If the new course is not null and doesn't already have this professor, assign this professor to it.
        if (nuevoCurso != null && nuevoCurso.getProfesor() != this) {
            nuevoCurso.setProfesor(this);
        }
    }

    @Override
    public String obtenerDescripcionCompleta() {
        return super.obtenerDescripcionCompleta() + (getCurso() != null ? ", Curso Asignado: " + getCurso().toString() : ", Sin curso asignado actualmente");
    }

    @Override
    public String obtenerResumen() {
        // The super.obtenerResumen() already provides "Nombre (Cód: X)"
        // The toString() was also changed to this format for JComboBox.
        return super.obtenerResumen() + (getCurso() != null ? " - Prof. " + getCurso().toString() : "");
    }
}
