package com.modelo;

// No specific ArrayList needed here unless it's for a field, which it isn't.
// import java.util.ArrayList; 

public abstract class Persona implements Descriptible { // Made abstract as Persona itself shouldn't be instantiated directly
    private String nombre;
    private int edad;
    private int cedula;
    private int codigo;
    private String tipo; // e.g., "Estudiante", "Profesor"

    public Persona() {
    }

    public Persona(String nombre, int edad, int cedula, int codigo, String tipo) {
        this.nombre = nombre;
        this.edad = edad;
        this.cedula = cedula;
        this.codigo = codigo;
        this.tipo = tipo;
    }
    

    @Override
    public String toString() {
        return "Nombre: " + getNombre() 
                + ", Edad: " + getEdad() 
                + ", Cédula: " + getCedula() 
                + ", Código: " + getCodigo() 
                + ", Tipo: " + getTipo(); // Corrected duplicate label
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the edad
     */
    public int getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * @return the cedula
     */
    public int getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String obtenerDescripcionCompleta() {
        return "Nombre: " + getNombre() + 
               ", Edad: " + getEdad() + 
               ", Cédula: " + getCedula() + 
               ", Código: " + getCodigo() + 
               ", Tipo: " + getTipo();
    }

    @Override
    public String obtenerResumen() {
        return getNombre() + " (Cód: " + getCodigo() + ")";
    }
}
