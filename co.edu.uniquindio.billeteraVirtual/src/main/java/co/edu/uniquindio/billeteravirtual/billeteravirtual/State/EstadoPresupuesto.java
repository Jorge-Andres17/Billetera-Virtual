package co.edu.uniquindio.billeteravirtual.billeteravirtual.State;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Presupuesto;

public interface EstadoPresupuesto {
    void asociarCuenta(Presupuesto presupuesto, Cuenta cuenta);
}
