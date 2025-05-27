package co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Login.Sesion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Strategy.*;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Strategy.ReporteCsvStrategy;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.BilleteraVirtualConstantes.*;

public class GenerarReporteViewController {

    @FXML
    private DatePicker DateFechaInicio;

    @FXML
    private DatePicker DateFechaFin;

    @FXML
    private ComboBox<String> cbTipoReporte;

    @FXML
    private ComboBox<String> cbFormatoReporte;

    @FXML
    private Button btnGenerarReporte;

    Map<String,ReporteStrategy> estrategias;
    private final GeneradorReporte generadorReporte = new GeneradorReporte();

    @FXML
    public void initialize() {
        cbTipoReporte.getItems().addAll("Ingresos", "Gastos", "Saldos");
        cbFormatoReporte.getItems().addAll("PDF", "CSV");
        estrategias = new HashMap<>();
        estrategias.put("PDF",new ReportePdfStrategy());
        estrategias.put("CSV", new ReporteCsvStrategy());
    }

    @FXML
    private void onGenerarReporte() {
        try {
            String tipo = cbTipoReporte.getValue();
            String formato = cbFormatoReporte.getValue();
            LocalDate inicio = DateFechaInicio.getValue();
            LocalDate fin = DateFechaFin.getValue();
            Usuario usuario = Sesion.getUsuarioActual();

            if (tipo == null || formato == null || inicio == null || fin == null) {
                mostrarAlerta(COMPLETAR_CAMPOS);
                return;
            }

            ReporteStrategy estrategia = estrategias.get(formato);
            if (estrategia == null) {
                mostrarAlerta(ERROR_FORMATO);
                return;
            }

            generadorReporte.setStrategy(estrategia);
            generadorReporte.generar(usuario, inicio, fin, tipo);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(ERROR_REPORTE + e.getMessage());
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}