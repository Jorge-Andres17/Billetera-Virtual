package co.edu.uniquindio.billeteravirtual.billeteravirtual.State;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Presupuesto;
import javafx.scene.control.Alert;
import static co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.BilleteraVirtualConstantes.*;

public class EstadoInactivo implements EstadoPresupuesto {
    @Override
    public void asociarCuenta(Presupuesto presupuesto, Cuenta cuenta) {
        new Alert(Alert.AlertType.WARNING, ASOCIADA);
    }
}