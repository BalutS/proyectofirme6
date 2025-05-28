package com.modelo;

import java.util.ArrayList;
import java.util.Iterator; 

public class Colegio {
    private static Colegio instancia;
    private String nombre;
    private ArrayList<Persona> personas;
    private ArrayList<Curso> cursos;
    private ArrayList<Asignatura> asignaturas;

    private Colegio(String nombre) {
        this.nombre = nombre;
        personas = new ArrayList<>();
        cursos = new ArrayList<>();
        asignaturas = new ArrayList<>();
    }
  
    public static Colegio getInstance(String nombreColegio) {
        if (instancia == null) {
            instancia = new Colegio(nombreColegio);
        }
        return instancia;
    }
    
    public static Colegio getInstance() {
        if (instancia == null) {
            instancia = new Colegio("Nombre de Colegio por Defecto"); 
        }
        return instancia;
    }
    
    public void agregarCursoAProfesor(int codigo, int grado, int grupo){
        // DAO operations will replace direct list manipulation here in future refactoring
        Profesor prof = null; 
        for (Persona persona : personas) { if (persona instanceof Profesor && persona.getCodigo() == codigo) { prof = (Profesor) persona; break; } }
        
        Curso curso = null;
        for (Curso c : cursos) { if (c.getGrado() == grado && c.getGrupo() == grupo) { curso = c; break; } }

        if (prof != null && curso != null) {
            prof.setCurso(curso);
            curso.setProfesor(prof);
        }
    }
    
    public void agregarEstudianteACurso(int codigo, int grado, int grupo){
        // DAO operations will replace direct list manipulation here in future refactoring
        Estudiante est = null;
        for (Persona persona : personas) { if (persona instanceof Estudiante && persona.getCodigo() == codigo) { est = (Estudiante) persona; break; } }

        Curso curso = null;
        for (Curso c : cursos) { if (c.getGrado() == grado && c.getGrupo() == grupo) { curso = c; break; } }
        
        if (est != null && curso != null) {
            if (curso.getEstudiantes() == null) {
                curso.setEstudiantes(new ArrayList<>());
            }
            curso.getEstudiantes().add(est);
            est.setCurso(curso);
        }
    }
    
    public void agregarAsignaturaAEstudiante(int codigo, String nombre){
        // DAO operations will replace direct list manipulation here in future refactoring
        Estudiante est = null;
        for (Persona persona : personas) { if (persona instanceof Estudiante && persona.getCodigo() == codigo) { est = (Estudiante) persona; break; } }

        Asignatura asig = null;
        for (Asignatura a : asignaturas) { if (a.getNombre().equalsIgnoreCase(nombre)) { asig = a; break; } }
        
        if (est != null && asig != null) {
            if (est.getAsignaturas() == null) {
                est.setAsignaturas(new ArrayList<>());
            }
            est.getAsignaturas().add(asig);
        }
    }
    
    // NOTE: All buscar..., reporte..., info..., listar..., and eliminar... methods 
    // are to be removed as their functionality is now handled by DAOs or will be moved to controllers.

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(ArrayList<Persona> personas) {
        this.personas = personas;
    }

    public ArrayList<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(ArrayList<Curso> cursos) {
        this.cursos = cursos;
    }

    public ArrayList<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(ArrayList<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }
}
