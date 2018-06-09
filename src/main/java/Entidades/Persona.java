package Entidades;

import java.util.Objects;

public class Persona {


    private String dni;
    private String nombre;
    private String apellido;

    public Persona(String dni, String nombre, String apellido) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getDni() {
        return this.dni;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Persona) {
            Persona other = (Persona) obj;
            return Objects.equals(this.dni, other.dni);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.dni.hashCode();
    }


}
