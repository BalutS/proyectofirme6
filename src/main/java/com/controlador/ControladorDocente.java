package com.controlador;

import com.modelo.*;
import java.time.LocalDate; // Added for calificarEstudiante
import java.util.List; // Though not explicitly used, it's a common import.

public class ControladorDocente extends ControladorGeneral {
    private Profesor profesor;

    public ControladorDocente(Colegio colegio, int codigoProfesor) {
        super(colegio);
        this.profesor = colegio.buscarProfesor(codigoProfesor);
        if (this.profesor == null) {
            // Consider throwing an exception or logging if professor not found
            System.err.println("Advertencia: Profesor con código " + codigoProfesor + " no encontrado.");
        }
    }

    // Updated to match the new signature in Profesor.java
    public void calificarEstudiante(int codEst, String nombreAsignatura, String nombreCalificacion, float nota, int periodo, LocalDate fecha) {
        if (profesor != null) {
            try {
                profesor.calificarEstudiante(codEst, nombreAsignatura, nombreCalificacion, nota, periodo, fecha);
            } catch (IllegalStateException | IllegalArgumentException e) {
                // Handle or rethrow exceptions from the model, e.g., show a message dialog in a GUI context
                System.err.println("Error al calificar: " + e.getMessage());
                // In a GUI app, you might use:
                // JOptionPane.showMessageDialog(null, e.getMessage(), "Error de Calificación", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.err.println("Error: Profesor no inicializado en ControladorDocente.");
        }
    }

    public String listarEstudiantesEnCurso() {
        if (profesor == null) {
            return "Profesor no asignado o no encontrado.";
        }
        if (profesor.getCurso() == null) {
            return "El profesor no tiene un curso asignado.";
        }
        return profesor.listarEstudiantes(); 
    }

    public String verInfoCurso() {
        if (profesor == null) {
            return "Profesor no asignado o no encontrado.";
        }
        if (profesor.getCurso() == null) {
            return "El profesor no tiene un curso asignado.";
        }
        return profesor.getCurso().infoCurso(); 
    }

    public Profesor getProfesor() {
        return profesor;
    }
}
