package com.dao.impl;

import com.dao.EstudianteDAO;
import com.modelo.Colegio;
import com.modelo.Estudiante;
import com.modelo.Persona; // Required for iterating Colegio's list
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EstudianteDaoImpl implements EstudianteDAO {

    private Colegio colegio;

    public EstudianteDaoImpl() {
        this.colegio = Colegio.getInstance(); // Get the singleton instance
    }

    @Override
    public Optional<Estudiante> getById(int id) {
        if (colegio.getPersonas() == null) {
            return Optional.empty();
        }
        return colegio.getPersonas().stream()
                .filter(p -> p instanceof Estudiante && p.getCodigo() == id)
                .map(p -> (Estudiante) p)
                .findFirst();
    }

    @Override
    public List<Estudiante> getAll() {
        if (colegio.getPersonas() == null) {
            return new ArrayList<>(); // Return empty list if no personas
        }
        return colegio.getPersonas().stream()
                .filter(p -> p instanceof Estudiante)
                .map(p -> (Estudiante) p)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Estudiante estudiante) {
        if (estudiante == null) {
            return; // Or throw IllegalArgumentException
        }
        if (colegio.getPersonas() == null) {
            colegio.setPersonas(new ArrayList<>());
        }
        // Remove if exists to prevent duplicates, effectively making save an upsert for simplicity
        colegio.getPersonas().removeIf(p -> p instanceof Estudiante && p.getCodigo() == estudiante.getCodigo());
        colegio.getPersonas().add(estudiante);
        // Note: Logic for adding student to Curso's list of students is handled by controller/service layer
    }

    @Override
    public void update(Estudiante estudiante) {
        if (estudiante == null) {
            return; // Or throw IllegalArgumentException
        }
        if (colegio.getPersonas() == null) {
            // Cannot update if the list doesn't exist or student not found
            // Consider throwing an exception
            return; 
        }
        Optional<Estudiante> existingEstOpt = getById(estudiante.getCodigo());
        if (existingEstOpt.isPresent()) {
            // Replace the existing student object with the new one
            colegio.getPersonas().removeIf(p -> p.getCodigo() == estudiante.getCodigo() && p instanceof Estudiante);
            colegio.getPersonas().add(estudiante);
        }
        // Consider throwing an exception if student not found to update
    }

    @Override
    public void delete(int id) {
        if (colegio.getPersonas() == null) {
            return; // Or throw exception if student to delete is not found
        }
        colegio.getPersonas().removeIf(p -> p instanceof Estudiante && p.getCodigo() == id);
        // Note: Logic for removing student from Curso's list of students is handled by controller/service layer
    }
}
