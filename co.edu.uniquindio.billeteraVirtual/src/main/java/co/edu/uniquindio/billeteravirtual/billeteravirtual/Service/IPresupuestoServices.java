package co.edu.uniquindio.billeteravirtual.billeteravirtual.Service;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Categoria;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Presupuesto;

public interface IPresupuestoServices {
    boolean agregarPresupuesto(Presupuesto presupuesto);
    boolean eliminarPresupuesto(String nombre);
    boolean actualizarPresupuesto(String nombre,
                                  Double montoAsignado,
                                  Categoria categoria);
}
