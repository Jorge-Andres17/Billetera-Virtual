package co.edu.uniquindio.billeteravirtual.billeteravirtual.Service;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Categoria;

public interface ICategoriaServices {
    boolean agregarCategoria(Categoria categoria);
    boolean eliminarCategoria(int idCategoria);
    boolean actualizarCategoria(String nombre,
                                  String numeroIdentificacionActual,
                                  String numeroIdentificacion,
                                  String email,
                                  String numeroCelular);
}
