package com.modelo;

public class Profesor extends Persona {
    private Curso curso;

    public Profesor() {
        this.curso = null;
    }

    public Profesor(Curso curso, String nombre, int edad, int cedula, int codigo, String tipo) {
        super(nombre, edad, cedula, codigo, tipo);
        this.curso = curso;
    }
    
    public void calificarEstudiante(int codEst, String asig, Calificacion cal) {
        if (curso == null) {
            throw new IllegalStateException("El profesor no tiene un curso asignado.");
        }
        Estudiante estudiante = curso.buscarEstudiante(codEst);
        if (estudiante == null) {
            throw new IllegalArgumentException("Estudiante no encontrado.");
        }
        Asignatura asignatura = estudiante.buscarAsignatura(asig);
        if (asignatura == null) {
            throw new IllegalArgumentException("Asignatura no encontrada.");
        }
        asignatura.agregarCalificacion(cal);
    }
    
    public String listarEstudiantes() {
        if (curso == null) return "Sin curso asignado.";
        return curso.listarEstudiantes();
    }
    
 @Override
    public String toString() {
        String cursoInfo = (curso != null) ? " (Curso: " + curso.getGrado() + "-" + curso.getGrupo() + ")" : " (Sin curso asignado)";
        return "Docente: " + getNombre() + ", Código: " + getCodigo() + ", Cédula: " + getCedula() + ", Edad: " + getEdad() + cursoInfo;       
    }
    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        if (this.curso != null) {
            this.curso.setProfesor(null);
        }
        this.curso = curso;
        if (curso != null && curso.getProfesor() != this) {
            curso.setProfesor(this);
        }
    }
}
