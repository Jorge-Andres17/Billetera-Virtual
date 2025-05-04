package co.edu.uniquindio.billeteravirtual.billeteravirtual.Service;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;

public interface IUsuarioServices {
    boolean agregarUsuario(Usuario usuario);
    boolean eliminarUsuario(String numeroIdentificacion);
    boolean actualizarUsuario(String nombre,
                                  String numeroIdentificacionActual,
                                  String numeroIdentificacion,
                                  String email,
                                  String numeroCelular);
}
