package co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Decorator.IPresupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Factory.ModelFactory;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Categoria;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;

import java.util.List;

public class CrudPresupuestoController {
    ModelFactory modelFactory;

    public CrudPresupuestoController(){
        modelFactory = ModelFactory.getInstancia();
    }

    public boolean agregarPresupuesto(IPresupuesto presupuesto) {
        return modelFactory.agregarPresupuesto(presupuesto);
    }

    public List<IPresupuesto> obtenerPresupuestos() {
        return modelFactory.obtenerPresupuestos();
    }

    public List<Categoria> obtenerCategorias() {
        return modelFactory.obtenerPresupuestoCategorias();
    }

    public List<Cuenta> obtenerCuentas() {
        return modelFactory.obtenerPresupuestoCuenta();
    }

    public boolean eliminarPresupuesto(String nombre) {
        return modelFactory.eliminarPresupuesto(nombre);
    }

    public boolean actualizarPresupuesto(String nombrePresupuesto,
                                         Double montoAsignado,
                                         Categoria categoria) {
        return modelFactory.actualizarPresupuesto(nombrePresupuesto,montoAsignado,categoria);
    }

    public ModelFactory getModelFactory() {
        return modelFactory;
    }
}
