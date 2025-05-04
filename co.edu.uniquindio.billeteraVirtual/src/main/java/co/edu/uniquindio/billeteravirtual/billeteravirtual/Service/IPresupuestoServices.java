package co.edu.uniquindio.billeteravirtual.billeteravirtual.Service;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;

public interface IPresupuestoServices {
    boolean agregarPresupuesto(Usuario usuario);
    boolean eliminarPresupuesto(String numeroIdentificacion);
    boolean actualizarPresupuesto(String nombre,
                              String numeroIdentificacionActual,
                              String numeroIdentificacion,
                              String email,
                              String numeroCelular);
}
