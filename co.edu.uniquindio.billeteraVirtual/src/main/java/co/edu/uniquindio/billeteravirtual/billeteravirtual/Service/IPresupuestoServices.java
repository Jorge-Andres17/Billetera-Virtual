package co.edu.uniquindio.billeteravirtual.billeteravirtual.Service;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Decorator.IPresupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Categoria;

public interface IPresupuestoServices {
    boolean agregarPresupuesto(IPresupuesto presupuesto);
    boolean eliminarPresupuesto(String nombre);
    boolean actualizarPresupuesto(String nombre,
                                  Double montoAsignado,
                                  Categoria categoria);
}
