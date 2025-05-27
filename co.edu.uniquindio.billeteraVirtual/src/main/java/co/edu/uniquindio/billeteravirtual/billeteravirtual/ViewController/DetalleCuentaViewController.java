package co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;

public class DetalleCuentaViewController {

    @FXML
    private Label lblDetalleCUenta;

    @FXML
    private Label lblNombreBanco;

    @FXML
    private Label lblNumeroCuenta;

    @FXML
    private Label lblTipo;

    @FXML
    private Label lblSaldo;

    @FXML
    private Button cerrarButton;

    private Cuenta cuenta;

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
        mostrarDetalles();
    }

    private void mostrarDetalles() {
        if (cuenta != null) {
            lblDetalleCUenta.setText("Detalle de Cuenta");
            lblNombreBanco.setText("Banco: " + cuenta.getNombreBanco());
            lblNumeroCuenta.setText("Número de cuenta: " + cuenta.getNumeroCuenta());
            lblTipo.setText("Tipo de cuenta: " + cuenta.getTipoCuenta().toString());
            lblSaldo.setText("Saldo: $" + String.format("%.2f", cuenta.getPresupuesto().getMontoAsignado()));
        }
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) cerrarButton.getScene().getWindow();
        stage.close();
    }
}