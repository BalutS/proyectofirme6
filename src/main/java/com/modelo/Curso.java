package com.modelo;

import java.util.ArrayList; 

public class Curso implements Descriptible {
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
        if (this.estudiantes == null) return null;
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getCodigo() == codigo) {
                return estudiante;
            }
        }
        return null;
    }
    
    public String listarEstudiantes () {
        StringBuilder sb = new StringBuilder(); 
        if (estudiantes == null || estudiantes.isEmpty()) {
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
        if (asignaturas == null || asignaturas.isEmpty()) {
            sb.append("    No hay asignaturas en este curso.\n");
        } else {
            for (Asignatura asig : asignaturas) {
                sb.append("    ").append(asig.getNombre()).append("\n");
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
        // For JComboBox display, as per requirements.
        return "Grado: " + grado + " Grupo: " + grupo;
    }

    public void agregarAsignatura(Asignatura asignatura) {
        if (this.asignaturas == null) {
            this.asignaturas = new ArrayList<>();
        }
        if (asignatura != null && !this.asignaturas.contains(asignatura)) { 
            this.asignaturas.add(asignatura);
        }
    }

    public Asignatura buscarAsignatura(String nombre) {
        if (this.asignaturas == null || nombre == null) return null;
        for (Asignatura asig : asignaturas) {
            if (asig.getNombre().equalsIgnoreCase(nombre)) {
                return asig;
            }
        }
        return null;
    }
    
    /*
    // Asignatura does not have a 'codigo' field. Commenting out for now.
    // This method might be needed if Asignatura gets a unique code later.
    public Asignatura buscarAsignaturaPorCodigo(int codigo) {
        if (this.asignaturas == null) return null;
        for (Asignatura asig : asignaturas) {
            // if (asig.getCodigo() == codigo) { // Asignatura has no getCodigo()
            //    return asig;
            // }
        }
        return null;
    }
    */

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
        if (this.estudiantes == null) {
            this.estudiantes = new ArrayList<>(); // Defensive initialization
        }
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
            // Optionally, set this course on the profesor object too for bidirectional link
            // if (profesor != null && profesor.getCurso() != this) {
            //     profesor.setCurso(this);
            // }
            return true;
        }
        return false; 
    }

    public ArrayList<Asignatura> getAsignaturas() {
        if (this.asignaturas == null) {
            this.asignaturas = new ArrayList<>(); // Defensive initialization
        }
        return asignaturas;
    }

    public void setAsignaturas(ArrayList<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

    @Override
    public String obtenerDescripcionCompleta() {
        StringBuilder sb = new StringBuilder();
        sb.append("Curso: ").append(getGrado()).append("-").append(getGrupo()).append("\n");
        sb.append("Profesor: ").append(getProfesor() != null ? getProfesor().getNombre() : "No asignado").append("\n");
        sb.append(getEstudiantes() != null ? getEstudiantes().size() : 0).append(" estudiantes inscritos.\n");
        sb.append("Asignaturas: ").append(getAsignaturas() != null ? getAsignaturas().size() : 0);
        // Optionally, list asignatura names:
        // if (getAsignaturas() != null && !getAsignaturas().isEmpty()) {
        //     sb.append(" (");
        //     for (int i = 0; i < getAsignaturas().size(); i++) {
        //         sb.append(getAsignaturas().get(i).getNombre());
        //         if (i < getAsignaturas().size() - 1) {
        //             sb.append(", ");
        //         }
        //     }
        //     sb.append(")");
        // }
        return sb.toString();
    }

    @Override
    public String obtenerResumen() {
        // This is similar to its current toString().
        return "Curso " + getGrado() + "-" + getGrupo(); 
    }
}
