package co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Factory.ModelFactory;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Transaccion;

import java.util.List;

public class TransaccionController {
    ModelFactory modelFactory;

    public TransaccionController(){
        modelFactory = ModelFactory.getInstancia();
    }

    public boolean deposito(int idCuenta, double monto) {
        return modelFactory.deposito(idCuenta,monto);
    }

    public boolean transferencia(int idCuentaOrigen, int idCuentaDestino, double monto) {
        return modelFactory.transferencia(idCuentaOrigen,idCuentaDestino,monto);
    }

    public boolean retiro(int idCuenta,double monto) {
        return modelFactory.retiro(idCuenta, monto);
    }

    public List<Cuenta> obtenerCuentas() {
        return modelFactory.obtenerCuentas();
    }

    public List<Transaccion> obtenerTransacciones() {
        return modelFactory.obtenerTransacciones();
    }

    public boolean agregarTransaccion(Transaccion transaccion) {
        return modelFactory.agregarTransaccion(transaccion);
    }

    public List<Transaccion> obtenerTransaccionesAdmin() {
        return modelFactory.obtenerTransaccionesAdmin();
    }

    public List<Cuenta> obtenerCuentasAdmin() {
        return modelFactory.obtenerCuentasAdmin();
    }
}
