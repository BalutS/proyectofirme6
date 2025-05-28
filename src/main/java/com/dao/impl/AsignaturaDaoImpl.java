package com.dao.impl;

import com.dao.AsignaturaDAO;
import com.modelo.Colegio;
import com.modelo.Asignatura;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AsignaturaDaoImpl implements AsignaturaDAO {

    private Colegio colegio;

    public AsignaturaDaoImpl() {
        this.colegio = Colegio.getInstance();
    }

    @Override
    public Optional<Asignatura> getByName(String name) {
        if (colegio.getAsignaturas() == null) return Optional.empty();
        return colegio.getAsignaturas().stream()
                .filter(a -> a.getNombre().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public List<Asignatura> getAll() {
        if (colegio.getAsignaturas() == null) return new ArrayList<>();
        return new ArrayList<>(colegio.getAsignaturas()); // Return a copy
    }

    @Override
    public void save(Asignatura asignatura) {
        if (asignatura == null) return;
        if (colegio.getAsignaturas() == null) {
            colegio.setAsignaturas(new ArrayList<>());
        }
        // Upsert logic based on name
        colegio.getAsignaturas().removeIf(a -> a.getNombre().equalsIgnoreCase(asignatura.getNombre()));
        colegio.getAsignaturas().add(asignatura);
        // Relationship management (e.g., adding asignatura to Curso or Estudiante) is higher layer
    }

    @Override
    public void update(Asignatura asignatura) {
        if (asignatura == null) return;
        if (colegio.getAsignaturas() == null) return; // Or throw error

        Optional<Asignatura> existingAsigOpt = getByName(asignatura.getNombre());
        if (existingAsigOpt.isPresent()) {
            // Remove old, add new. Assumes name is the key and might be updated if name changes.
            // For in-memory, if name itself is changed, this is tricky.
            // A more robust update would use a stable ID if available.
            // For now, this replaces based on the (potentially new) name.
            colegio.getAsignaturas().removeIf(a -> a.getNombre().equalsIgnoreCase(existingAsigOpt.get().getNombre())); // remove by old name
            colegio.getAsignaturas().add(asignatura); // add new version
        }
        // Consider throwing an exception if asignatura not found by its original name (if name can change)
    }

    @Override
    public void delete(String name) {
        if (colegio.getAsignaturas() == null) return;
        colegio.getAsignaturas().removeIf(a -> a.getNombre().equalsIgnoreCase(name));
        // Relationship management (e.g., removing asignatura from Curso/Estudiante) is higher layer
    }
}
