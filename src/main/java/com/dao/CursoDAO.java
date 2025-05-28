package com.dao;

import com.modelo.Curso;
import java.util.List;
import java.util.Optional;

public interface CursoDAO {
    Optional<Curso> getByGradoAndGrupo(int grado, int grupo);
    List<Curso> getAll();
    void save(Curso curso);
    void update(Curso curso);
    void delete(int grado, int grupo); // Or by a unique ID if Curso gets one
}
