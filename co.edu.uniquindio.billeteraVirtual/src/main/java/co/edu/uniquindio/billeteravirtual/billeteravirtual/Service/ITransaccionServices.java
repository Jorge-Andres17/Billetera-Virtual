package co.edu.uniquindio.billeteravirtual.billeteravirtual.Service;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Transaccion;

import java.util.List;

public interface ITransaccionServices {
    boolean agregarTransaccion(Transaccion transaccion);

    boolean depositar(int idCuenta, double monto);

    boolean transferir(int idCuentaOrigen, int idCuentaDestino, double monto);

    boolean retirar(int idCuenta, double monto);

    List<Transaccion> obtenerTransaccionesDelUsuario();
}
