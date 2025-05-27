package co.edu.uniquindio.billeteravirtual.billeteravirtual.Builder;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Categoria;

public class CategoriaBuilder {
    protected String nombre;
    protected String descripcion;

    public CategoriaBuilder nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public CategoriaBuilder descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public Categoria build() {
        return new Categoria(nombre,descripcion);
    }
}
