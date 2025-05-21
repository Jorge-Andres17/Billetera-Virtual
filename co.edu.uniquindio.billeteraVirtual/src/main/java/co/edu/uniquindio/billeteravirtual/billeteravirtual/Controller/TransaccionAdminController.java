package co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Factory.ModelFactory;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Transaccion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;

import java.util.List;

public class TransaccionAdminController {
    ModelFactory modelFactory;

    public TransaccionAdminController(){
        modelFactory = ModelFactory.getInstancia();
    }

    public boolean agregarTransaccion(Transaccion transaccion) {
        return modelFactory.agregarTransaccion(transaccion);
    }

    public List<Transaccion> obtenerTransacciones() {
        return modelFactory.obtenerAdminTransacciones();
    }

    public List<Usuario> obtenerUsuarios() {
        return modelFactory.obtenerUsuarios();
    }

    public List<Cuenta> obtenerCuentas() {
        return modelFactory.obtenerCuentasAdmin();
    }

    public ModelFactory getModelFactory() {
        return modelFactory;
    }
}
