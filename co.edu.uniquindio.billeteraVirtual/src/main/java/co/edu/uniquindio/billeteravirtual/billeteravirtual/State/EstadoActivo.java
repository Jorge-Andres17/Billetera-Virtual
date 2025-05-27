package co.edu.uniquindio.billeteravirtual.billeteravirtual.State;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Presupuesto;

public class EstadoActivo implements EstadoPresupuesto {
    @Override
    public void asociarCuenta(Presupuesto presupuesto, Cuenta cuenta) {
        presupuesto.setCuentaAsociada(cuenta);
        presupuesto.setEstadoPresupuesto(new EstadoInactivo());
    }
}
