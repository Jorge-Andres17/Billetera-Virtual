package co.edu.uniquindio.billeteravirtual.billeteravirtual.Service;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Categoria;

public interface ICategoriaServices {
    Categoria agregarCategoria(String nombre, String descripcion);
    boolean eliminarCategoria(String nombre);
    boolean actualizarCategoria(String nombre,
                                  String descripcion);
    Categoria actualizarCategoriaDto(String nombre,
                                     String descripcion);
}
