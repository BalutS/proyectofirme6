package com.modelo;

import java.util.ArrayList;

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
    
    public void agregarEstudiante(Estudiante est){
        personas.add(est);
    }
    
    public void agregarProfesor(Profesor prof){
        personas.add(prof);
    }
    
    public void agregarCurso(Curso curso){
        cursos.add(curso);
    }
    
    public void agregarAsignatura(Asignatura asig){
        asignaturas.add(asig);
    }
    
    public void agregarCursoAProfesor(int codigo, int grado, int grupo){
        buscarProfesor(codigo).setCurso(buscarCurso(grado, grupo));
        buscarCurso(grado, grupo).setProfesor(buscarProfesor(codigo));
    }
    
    public void agregarEstudianteACurso(int codigo, int grado, int grupo){
        buscarCurso(grado, grupo).getEstudiantes().add(buscarEstudiante(codigo));
        buscarEstudiante(codigo).setCurso(buscarCurso(grado, grupo));
    }
    
    public void agregarAsignaturaAEstudiante(int codigo, String nombre){
        buscarEstudiante(codigo).getAsignaturas().add(buscarAsignatura(nombre));
    }
    
    public Profesor buscarProfesor(int cod){
        Profesor prof = null;
        for (Persona persona : personas) {
            if(persona.getCodigo() == cod){
                prof = (Profesor) persona;
            }
        }
        return prof;
    }
    
    public Estudiante buscarEstudiante(int cod){
        Estudiante est = null;
        for (Persona persona : personas) {
            if(persona.getCodigo() == cod){
                est = (Estudiante) persona;
            }
        }
        return est;
    }
    
    public Curso buscarCurso(int grado, int grupo){
        Curso cur = null;
        for (Curso curso : cursos) {
            if(curso.getGrado() == grado && curso.getGrupo() == grupo){
                cur = curso;
            }
        }
        return cur;
    }
    
    public Asignatura buscarAsignatura(String nombre){
        Asignatura asig = null;
        for (Asignatura asignatura : asignaturas) {
            if(asignatura.getNombre().equalsIgnoreCase(nombre)){
                asig = asignatura;
            }
        }
        return asig;
    }
    
    public String reporteEstudiante(int codigo){
        return buscarEstudiante(codigo).reporteAcademico();
    }
    
    public String infoCurso(int grado, int grupo){
        return buscarCurso(grado, grupo).infoCurso();
    }
    
    public String listarTodosLosCursos(){
        String lis = "";
        for (Curso curso : cursos) {
            lis += curso.infoCurso() + "\n\n";
        }
        return lis;
    }
    public String listarTodosLosProfesores() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- LISTA DE DOCENTES ---\n");
        boolean hayProfesores = false;
        for (Persona p : personas) {
            if (p instanceof Profesor) { 
                Profesor prof = (Profesor) p;
                sb.append(prof.toString()).append("\n\n"); 
                hayProfesores = true;
            }
        }
        if (!hayProfesores) {
            sb.append("No hay docentes registrados.\n");
        }
        return sb.toString();
    }
    public String listarTodosLosEstudiantes() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- LISTA DE ESTUDIANTES ---\n");
        boolean hayEstudiantes = false;
        for (Persona p : personas) {
            if (p instanceof Estudiante) { 
                Estudiante est = (Estudiante) p;
                sb.append(est.toString()).append("\n\n"); 
                hayEstudiantes = true;
            }
        }
        if (!hayEstudiantes) {
            sb.append("No hay estudiantes registrados.\n");
        }
        return sb.toString();
    }
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the personas
     */
    public ArrayList<Persona> getPersonas() {
        return personas;
    }

    /**
     * @param personas the personas to set
     */
    public void setPersonas(ArrayList<Persona> personas) {
        this.personas = personas;
    }

    /**
     * @return the cursos
     */
    public ArrayList<Curso> getCursos() {
        return cursos;
    }

    /**
     * @param cursos the cursos to set
     */
    public void setCursos(ArrayList<Curso> cursos) {
        this.cursos = cursos;
    }

    /**
     * @return the asignaturas
     */
    public ArrayList<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    /**
     * @param asignaturas the asignaturas to set
     */
    public void setAsignaturas(ArrayList<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }
    
    
    
}

