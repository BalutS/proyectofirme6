package com.modelo;

import java.util.ArrayList; 
import java.time.LocalDate; 

public class Profesor extends Persona {
    private Curso curso; 

    public Profesor() {
        super(); 
        this.curso = null;
    }

    public Profesor(String nombre, int edad, int cedula, int codigo, String tipo) {
        super(nombre, edad, cedula, codigo, tipo);
        this.curso = null; 
    }

    
    public Profesor(Curso curso, String nombre, int edad, int cedula, int codigo, String tipo) {
        super(nombre, edad, cedula, codigo, tipo);
        this.curso = curso;
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
        if (this.curso != null && this.curso.getProfesor() == this) {
            this.curso.setProfesor(null); 
        }
        
        this.curso = nuevoCurso;
        
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
        return super.obtenerResumen() + (getCurso() != null ? " - Prof. " + getCurso().toString() : "");
    }
}
