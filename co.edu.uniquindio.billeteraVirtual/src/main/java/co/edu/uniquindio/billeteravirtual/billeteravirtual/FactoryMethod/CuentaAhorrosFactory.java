package co.edu.uniquindio.billeteravirtual.billeteravirtual.FactoryMethod;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Decorator.IPresupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoCuenta;

public class CuentaAhorrosFactory implements CuentaFactory{

    @Override
    public Cuenta crearCuenta(String nombreBanco,
                              String numeroCuenta,
                              Usuario usuarioAsociado,
                              IPresupuesto presupuesto) {
        return new Cuenta(nombreBanco, numeroCuenta, TipoCuenta.AHORRO, usuarioAsociado, presupuesto);
    }
}
