package com.controlador;

import com.modelo.*;

public class ControladorEstudiante extends ControladorGeneral {
    private Estudiante estudiante;

    public ControladorEstudiante(Colegio colegio, int codigoEstudiante) {
        super(colegio);
        this.estudiante = colegio.buscarEstudiante(codigoEstudiante);
    }

    public String verReporteAcademico() {
        return estudiante != null ? estudiante.reporteAcademico() : "Estudiante no encontrado";
    }

    public String verInfoCurso() {
        return estudiante != null ? estudiante.getCurso().infoCurso() : "Sin curso asignado";
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }
}
