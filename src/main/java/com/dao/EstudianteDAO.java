package com.dao;

import com.modelo.Estudiante;
import java.util.List;
import java.util.Optional;

public interface EstudianteDAO { // Not extending PersonaDAO to keep specific types
    Optional<Estudiante> getById(int id);
    List<Estudiante> getAll();
    void save(Estudiante estudiante);
    void update(Estudiante estudiante);
    void delete(int id);
}
