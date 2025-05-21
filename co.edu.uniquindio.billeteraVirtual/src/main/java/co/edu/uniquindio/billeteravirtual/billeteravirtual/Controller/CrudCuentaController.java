package co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Factory.ModelFactory;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoCuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Presupuesto;

import java.util.List;

public class CrudCuentaController {
    ModelFactory modelFactory;

    public CrudCuentaController(){
        modelFactory = ModelFactory.getInstancia();
    }
    public boolean agregarCuenta(Cuenta cuenta) {
        return modelFactory.agregarCuenta(cuenta);
    }

    public List<Cuenta> obtenerCuentas() {
        return modelFactory.obtenerCuentas();
    }

    public boolean eliminarCuenta(int id,String numeroCuenta) {
        return modelFactory.eliminarCuenta(id, numeroCuenta);
    }

    public boolean actualizarCuenta(int idCuenta,
                                    String nombreBanco,
                                    String numeroCuenta,
                                    TipoCuenta tipoCuenta,
                                    Presupuesto presupuesto) {
        return modelFactory.actualizarCuenta(idCuenta,nombreBanco,numeroCuenta,tipoCuenta,presupuesto);
    }

    public List<Cuenta> obtenerCuentasAdmin() {
        return modelFactory.obtenerCuentasAdmin();
    }

    public List<Presupuesto> obtenerPresupuestosDisponible() {
        return modelFactory.obtenerPresupuestos();
    }

    public ModelFactory getModelFactory() {
        return modelFactory;
    }
}
