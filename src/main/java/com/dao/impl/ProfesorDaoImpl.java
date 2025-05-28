package com.dao.impl;

import com.dao.ProfesorDAO;
import com.modelo.Colegio;
import com.modelo.Profesor;
import com.modelo.Persona; // Required for iterating Colegio's list
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProfesorDaoImpl implements ProfesorDAO {

    private Colegio colegio;

    public ProfesorDaoImpl() {
        this.colegio = Colegio.getInstance(); // Get the singleton instance
    }

    @Override
    public Optional<Profesor> getById(int id) {
        if (colegio.getPersonas() == null) return Optional.empty();
        return colegio.getPersonas().stream()
                .filter(p -> p instanceof Profesor && p.getCodigo() == id)
                .map(p -> (Profesor) p)
                .findFirst();
    }

    @Override
    public List<Profesor> getAll() {
        if (colegio.getPersonas() == null) return new ArrayList<>();
        return colegio.getPersonas().stream()
                .filter(p -> p instanceof Profesor)
                .map(p -> (Profesor) p)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Profesor profesor) {
        if (profesor == null) return;
        if (colegio.getPersonas() == null) {
            colegio.setPersonas(new ArrayList<>());
        }
        // Upsert logic
        colegio.getPersonas().removeIf(p -> p instanceof Profesor && p.getCodigo() == profesor.getCodigo());
        colegio.getPersonas().add(profesor);
        // Relationship management (e.g., Profesor in Curso) is higher layer
    }

    @Override
    public void update(Profesor profesor) {
        if (profesor == null) return;
        if (colegio.getPersonas() == null) return; // Or throw error

        Optional<Profesor> existingProfOpt = getById(profesor.getCodigo());
        if (existingProfOpt.isPresent()) {
            colegio.getPersonas().removeIf(p -> p.getCodigo() == profesor.getCodigo() && p instanceof Profesor);
            colegio.getPersonas().add(profesor);
        }
        // Consider throwing an exception if profesor not found
    }

    @Override
    public void delete(int id) {
        if (colegio.getPersonas() == null) return;
        colegio.getPersonas().removeIf(p -> p instanceof Profesor && p.getCodigo() == id);
        // Relationship management (e.g., Profesor in Curso) is higher layer
    }
}
