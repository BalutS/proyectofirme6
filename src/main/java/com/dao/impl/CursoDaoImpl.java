package com.dao.impl;

import com.dao.CursoDAO;
import com.modelo.Colegio;
import com.modelo.Curso;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CursoDaoImpl implements CursoDAO {

    private Colegio colegio;

    public CursoDaoImpl() {
        this.colegio = Colegio.getInstance();
    }

    @Override
    public Optional<Curso> getByGradoAndGrupo(int grado, int grupo) {
        if (colegio.getCursos() == null) return Optional.empty();
        return colegio.getCursos().stream()
                .filter(c -> c.getGrado() == grado && c.getGrupo() == grupo)
                .findFirst();
    }

    @Override
    public List<Curso> getAll() {
        if (colegio.getCursos() == null) return new ArrayList<>();
        return new ArrayList<>(colegio.getCursos()); // Return a copy
    }

    @Override
    public void save(Curso curso) {
        if (curso == null) return;
        if (colegio.getCursos() == null) {
            colegio.setCursos(new ArrayList<>());
        }
        // Upsert logic
        colegio.getCursos().removeIf(c -> c.getGrado() == curso.getGrado() && c.getGrupo() == curso.getGrupo());
        colegio.getCursos().add(curso);
        // Relationship management (e.g., assigning students/profesor to this curso) is higher layer
    }

    @Override
    public void update(Curso curso) {
        if (curso == null) return;
        if (colegio.getCursos() == null) return; // Or throw error

        Optional<Curso> existingCursoOpt = getByGradoAndGrupo(curso.getGrado(), curso.getGrupo());
        if (existingCursoOpt.isPresent()) {
            colegio.getCursos().removeIf(c -> c.getGrado() == curso.getGrado() && c.getGrupo() == curso.getGrupo());
            colegio.getCursos().add(curso);
        }
        // Consider throwing an exception if curso not found
    }

    @Override
    public void delete(int grado, int grupo) {
        if (colegio.getCursos() == null) return;
        colegio.getCursos().removeIf(c -> c.getGrado() == grado && c.getGrupo() == grupo);
        // Relationship management (e.g., unassigning students/profesor) is higher layer
    }
}
