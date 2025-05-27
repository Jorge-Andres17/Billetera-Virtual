package co.edu.uniquindio.billeteravirtual.billeteravirtual.Service;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Transaccion;

import java.util.List;

public interface ITransaccionServices {
    boolean agregarTransaccion(Transaccion transaccion);
    List<Transaccion> obtenerTransaccionesDelUsuario();
}
