package com.dao;

import com.modelo.Asignatura;
import java.util.List;
import java.util.Optional;

public interface AsignaturaDAO {
    Optional<Asignatura> getByName(String name);
    List<Asignatura> getAll();
    void save(Asignatura asignatura);
    void update(Asignatura asignatura); // Potentially based on name
    void delete(String name);
}
