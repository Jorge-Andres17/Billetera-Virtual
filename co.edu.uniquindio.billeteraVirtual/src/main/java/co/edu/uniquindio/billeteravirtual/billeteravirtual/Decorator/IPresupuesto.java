package co.edu.uniquindio.billeteravirtual.billeteravirtual.Decorator;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.*;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.State.EstadoPresupuesto;

public interface IPresupuesto {
    void asignarId();
    int getIdPresupuesto();
    void setIdPresupuesto(int idPresupuesto);

    String getNombrePresupuesto();
    void setNombrePresupuesto(String nombrePresupuesto);

    double getMontoAsignado();
    void setMontoAsignado(double montoAsignado);

    double getMontoGastado();
    void setMontoGastado(double montoGastado);

    Cuenta getCuentaAsociada();
    void setCuentaAsociada(Cuenta cuentaAsociada);

    Categoria getCategoriaAsociada();
    void setCategoriaAsociada(Categoria categoriaAsociada);

    BilleteraVirtual getOwnedByBilleteraVirtual();
    void setOwnedByBilleteraVirtual(BilleteraVirtual billeteraVirtual);

    EstadoPresupuesto getEstadoPresupuesto();
    void setEstadoPresupuesto(EstadoPresupuesto estado);

    void asociarCuenta(Cuenta cuenta);
}