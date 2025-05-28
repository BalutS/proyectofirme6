package com.dao.impl;

import com.dao.CalificacionDAO;
import com.modelo.Asignatura;
import com.modelo.Calificacion;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalificacionDaoImpl implements CalificacionDAO {

    // This DAO operates directly on the Calificacion list within an Asignatura object.
    // It doesn't interact with Colegio directly for Calificaciones,
    // as Calificaciones are intrinsically part of an Asignatura.

    public CalificacionDaoImpl() {
        // Constructor can be empty as it doesn't manage a global list from Colegio
    }

    @Override
    public void addCalificacionToAsignatura(Asignatura asignatura, Calificacion calificacion) {
        if (asignatura == null || calificacion == null) return;
        if (asignatura.getCalificaciones() == null) {
            asignatura.setCalificaciones(new ArrayList<>());
        }
        // Prevent adding the exact same Calificacion object instance if it's already there.
        // More robust would be to check for semantic equality if Calificacion had a unique ID or an equals method.
        if (!asignatura.getCalificaciones().contains(calificacion)) {
            asignatura.getCalificaciones().add(calificacion);
        }
    }

    @Override
    public void updateCalificacionInAsignatura(Asignatura asignatura, Calificacion oldCalificacion, Calificacion newCalificacion) {
        if (asignatura == null || oldCalificacion == null || newCalificacion == null || asignatura.getCalificaciones() == null) return;

        // This simple update relies on finding the oldCalificacion by object reference or a proper equals method.
        // For in-memory, if oldCalificacion is the exact object from the list:
        int index = asignatura.getCalificaciones().indexOf(oldCalificacion);
        if (index != -1) {
            asignatura.getCalificaciones().set(index, newCalificacion);
        } else {
            // Fallback: if not found by reference, try to find by some properties (e.g., name)
            // This is a simplistic fallback. A proper ID or more robust matching would be better.
            for (int i = 0; i < asignatura.getCalificaciones().size(); i++) {
                Calificacion current = asignatura.getCalificaciones().get(i);
                // Assuming name of calificacion (e.g. "Examen Parcial") is a key for update
                if (current.getNombre().equals(oldCalificacion.getNombre())) {
                     asignatura.getCalificaciones().set(i, newCalificacion);
                     break;
                }
            }
        }
    }

    @Override
    public void deleteCalificacionFromAsignatura(Asignatura asignatura, Calificacion calificacion) {
        if (asignatura == null || calificacion == null || asignatura.getCalificaciones() == null) return;
        // Relies on object reference or a proper equals method in Calificacion.
        asignatura.getCalificaciones().remove(calificacion);
    }

    @Override
    public List<Calificacion> getCalificacionesFromAsignatura(Asignatura asignatura) {
        if (asignatura == null || asignatura.getCalificaciones() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(asignatura.getCalificaciones()); // Return a copy
    }
}
