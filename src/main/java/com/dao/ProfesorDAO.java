package com.dao;

import com.modelo.Profesor;
import java.util.List;
import java.util.Optional;

public interface ProfesorDAO {
    Optional<Profesor> getById(int id);
    List<Profesor> getAll();
    void save(Profesor profesor);
    void update(Profesor profesor);
    void delete(int id);
}
