package co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Factory.ModelFactory;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Categoria;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Presupuesto;

import java.util.List;

public class CrudPresupuestoController {
    ModelFactory modelFactory;

    public CrudPresupuestoController(){
        modelFactory = ModelFactory.getInstancia();
    }

    public boolean agregarPresupuesto(Presupuesto presupuesto) {
        return modelFactory.agregarPresupuesto(presupuesto);
    }

    public List<Presupuesto> obtenerPresupuestos() {
        return modelFactory.obtenerPresupuestos();
    }

    public List<Categoria> obtenerCategorias() {
        return modelFactory.obtenerPresupuestoCategorias();
    }

    public List<Cuenta> obtenerCuentas() {
        return modelFactory.obtenerPresupuestoCuenta();
    }

    public boolean eliminarPresupuesto(int idPresupuesto) {
        return modelFactory.eliminarPresupuesto(idPresupuesto);
    }

    public boolean actualizarPresupuesto(int id,
                                         String nombrePresupuesto,
                                         Double montoAsignado,
                                         Cuenta cuenta,
                                         Categoria categoria) {
        return modelFactory.actualizarPresupuesto(id,nombrePresupuesto,montoAsignado,cuenta,categoria);
    }
}
