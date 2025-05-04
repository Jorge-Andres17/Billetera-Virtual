package co.edu.uniquindio.billeteravirtual.billeteravirtual.Service;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;

public interface ICuentaServices {
    boolean agregarCuenta(Cuenta cuenta);
    boolean eliminarCuenta(int idCuenta);
    boolean actualizarCuenta(String nombre,
                                  String numeroIdentificacionActual,
                                  String numeroIdentificacion,
                                  String email,
                                  String numeroCelular);
}
