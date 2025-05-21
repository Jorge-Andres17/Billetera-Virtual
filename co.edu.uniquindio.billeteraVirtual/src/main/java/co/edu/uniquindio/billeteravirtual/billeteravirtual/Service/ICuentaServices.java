package co.edu.uniquindio.billeteravirtual.billeteravirtual.Service;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoCuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Presupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;

public interface ICuentaServices {
    boolean agregarCuenta(Cuenta cuenta);
    boolean eliminarCuenta(int idCuenta,String numeroCuenta);
    boolean actualizarCuenta(int id, String nombreBanco,
                             String numeroCuenta,
                             TipoCuenta tipoCuenta,
                             Presupuesto presupuesto);
}
