package co.edu.uniquindio.billeteravirtual.billeteravirtual.Service;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Categoria;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Presupuesto;

public interface IPresupuestoServices {
    boolean agregarPresupuesto(Presupuesto presupuesto);
    boolean eliminarPresupuesto(int idPresupuesto);
    boolean actualizarPresupuesto(int idPresupuesto,
                                  String nombre,
                                  Double montoAsignado,
                                  Cuenta cuenta,
                                  Categoria categoria);
}
