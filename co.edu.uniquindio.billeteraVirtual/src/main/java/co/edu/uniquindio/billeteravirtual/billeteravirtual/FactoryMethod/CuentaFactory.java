package co.edu.uniquindio.billeteravirtual.billeteravirtual.FactoryMethod;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Decorator.IPresupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;

public interface CuentaFactory {
    Cuenta crearCuenta(String nombreBanco,
                       String numeroCuenta,
                       Usuario usuarioAsociado,
                       IPresupuesto presupuesto);
}
