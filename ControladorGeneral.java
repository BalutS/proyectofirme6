package com.controlador;

import com.modelo.Colegio;

public class ControladorGeneral {
    protected Colegio colegio;

    public ControladorGeneral(Colegio colegio) {
        this.colegio = colegio;
    }

    public boolean autenticar(int codigo, String tipo) {
        return colegio.getPersonas().stream()
            .anyMatch(p -> p.getCodigo() == codigo && p.getTipo().equalsIgnoreCase(tipo));
    }
}
