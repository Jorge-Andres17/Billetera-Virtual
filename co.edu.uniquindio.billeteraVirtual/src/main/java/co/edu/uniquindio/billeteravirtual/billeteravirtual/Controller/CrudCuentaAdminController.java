package co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Decorator.IPresupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Factory.ModelFactory;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoCuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;
import java.util.List;

public class CrudCuentaAdminController {
    ModelFactory modelFactory;

    public CrudCuentaAdminController(){
        modelFactory = ModelFactory.getInstancia();
    }

    public List<Cuenta> obtenerCuentas() {
        return modelFactory.obtenerCuentasAdmin();
    }

    public List<Usuario> obtenerUsuarios() {
        return modelFactory.obtenerUsuarios();
    }

    public List<IPresupuesto> obtenerPresupuestos() {
        return modelFactory.obtenerPresupuestosAdmin();
    }

    public boolean agregarCuenta(Cuenta cuenta) {
        return modelFactory.agregarCuenta(cuenta);
    }

    public boolean eliminarCuenta(int idCuenta, String numeroCuenta) {
        return modelFactory.eliminarCuenta(idCuenta,numeroCuenta);
    }

    public boolean actualizarCuenta(int idCuenta, String nombreBanco,
                                    String numeroCuenta, TipoCuenta tipoCuenta,
                                    IPresupuesto presupuesto) {
        return modelFactory.actualizarCuenta(idCuenta,nombreBanco,numeroCuenta,
                tipoCuenta,presupuesto);
    }

    public ModelFactory getModelFactory() {
        return modelFactory;
    }
}
