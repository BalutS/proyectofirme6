package com.dao;

import com.modelo.Persona;
import java.util.List;
import java.util.Optional; // Using Optional for single results

public interface PersonaDAO {
    Optional<Persona> getById(int id);
    List<Persona> getAll();
    void save(Persona persona); // Can be used for both create and update if id is checked
    void update(Persona persona); // Explicit update
    void delete(int id);
}
