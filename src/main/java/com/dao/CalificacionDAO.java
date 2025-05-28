package com.dao;

import com.modelo.Asignatura;
import com.modelo.Calificacion;
// import com.modelo.Estudiante; // Not strictly needed for Option 1 if Asignatura holds its Calificaciones
import java.util.List;

public interface CalificacionDAO {
    // Option 1: Operate directly on Asignatura's list
    void addCalificacionToAsignatura(Asignatura asignatura, Calificacion calificacion);
    // For update/delete, Calificacion needs a way to be uniquely identified if not by object reference.
    // Assuming for now that the 'oldCalificacion' reference or its properties are enough for identification.
    void updateCalificacionInAsignatura(Asignatura asignatura, Calificacion oldCalificacion, Calificacion newCalificacion); 
    void deleteCalificacionFromAsignatura(Asignatura asignatura, Calificacion calificacion); 
    List<Calificacion> getCalificacionesFromAsignatura(Asignatura asignatura);
}
