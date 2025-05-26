
package com.modelo;

import java.util.ArrayList; 

public class Curso {
    private int grado;
    private int grupo;
    private ArrayList<Estudiante> estudiantes;
    private Profesor profesor; 
    private ArrayList<Asignatura> asignaturas; 

    public Curso() {
        this.estudiantes = new ArrayList<>();
        this.asignaturas = new ArrayList<>(); 
    }

    public Curso(int grado, int grupo, ArrayList<Estudiante> estudiantes) {
        this.grado = grado;
        this.grupo = grupo;
        this.estudiantes = (estudiantes != null) ? estudiantes : new ArrayList<>();
        this.asignaturas = new ArrayList<>();
    }
    
    public Curso(int grado, int grupo) {
        this.grado = grado;
        this.grupo = grupo;
        this.estudiantes = new ArrayList<>();
        this.asignaturas = new ArrayList<>();
    }

    public Estudiante buscarEstudiante(int codigo){
        Estudiante est = null;
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getCodigo() == codigo) {
                est = estudiante;
            }
        }
        return est;
    }
    
    public String listarEstudiantes () {
        StringBuilder sb = new StringBuilder(); 
        if (estudiantes.isEmpty()) {
            sb.append("    No hay estudiantes en este curso.\n");
        } else {
            for (Estudiante estudiante : estudiantes) {
                sb.append("    ").append(estudiante.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    public String listarAsignaturas() {
        StringBuilder sb = new StringBuilder();
        if (asignaturas.isEmpty()) {
            sb.append("    No hay asignaturas en este curso.\n");
        } else {
            for (Asignatura asig : asignaturas) {
                sb.append("    ").append(asig.getNombre()).append(" (Código: ").append(asig.getCodigo()).append(")\n");
            }
        }
        return sb.toString();
    }
    
    public String infoCurso(){
        return "Curso: " + grado + " - " + grupo + "\n"
                + "Profesor: " + (profesor != null ? profesor.getNombre() : "No asignado") + "\n"
                + "Estudiantes:\n" + listarEstudiantes()
                + "Asignaturas:\n" + listarAsignaturas();
    }
    
    @Override
    public String toString() {
        return "Curso: " + grado + " - " + grupo;
    }

    public void agregarAsignatura(Asignatura asignatura) {
        if (!this.asignaturas.contains(asignatura)) { 
            this.asignaturas.add(asignatura);
        }
    }

    public Asignatura buscarAsignatura(String nombre) {
        for (Asignatura asig : asignaturas) {
            if (asig.getNombre().equalsIgnoreCase(nombre)) {
                return asig;
            }
        }
        return null;
    }
    
    public Asignatura buscarAsignaturaPorCodigo(int codigo) {
        for (Asignatura asig : asignaturas) {
            if (asig.getCodigo() == codigo) {
                return asig;
            }
        }
        return null;
    }

    public int getGrado() {
        return grado;
    }

    public void setGrado(int grado) {
        this.grado = grado;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    public ArrayList<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(ArrayList<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public boolean setProfesor(Profesor profesor) {
        if (this.profesor == null) {
            this.profesor = profesor;
            return true;
        }
        return false; 
    }

    public ArrayList<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(ArrayList<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }
}
