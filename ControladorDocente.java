package com.controlador;

import com.modelo.*;
import java.util.List;

public class ControladorDocente extends ControladorGeneral {
    private Profesor profesor;

    public ControladorDocente(Colegio colegio, int codigoProfesor) {
        super(colegio);
        this.profesor = colegio.buscarProfesor(codigoProfesor);
    }

    public void calificarEstudiante(int codEst, String asignatura, Calificacion calificacion) {
        if (profesor != null) {
            profesor.calificarEstudiante(codEst, asignatura, calificacion);
        }
    }

    public String listarEstudiantesEnCurso() {
        return profesor != null ? profesor.listarEstudiantes() : "Sin curso asignado";
    }

    public String verInfoCurso() {
        return profesor != null ? profesor.getCurso().infoCurso() : "Sin curso asignado";
    }

    public Profesor getProfesor() {
        return profesor;
    }
}
