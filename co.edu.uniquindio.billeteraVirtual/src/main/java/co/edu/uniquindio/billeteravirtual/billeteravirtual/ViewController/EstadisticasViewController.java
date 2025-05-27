package co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller.EstadisticasController;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observer;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import java.util.Map;

public class EstadisticasViewController implements Observer {
    private EstadisticasController estadisticasController;

    @FXML
    private BarChart<String, Number> chartGastos;

    @FXML
    private BarChart<String, Number> chartUsuarios;

    @FXML
    private PieChart chartSaldoPromedio;

    @FXML
    public void initialize() {
        estadisticasController = new EstadisticasController();
        estadisticasController.getModelFactory().getBilleteraVirtual().addObserver(this);
        cargarEstadisticas();
    }

    private void cargarEstadisticas() {
        cargarGraficoGastos();
        cargarGraficoUsuarios();
        cargarGraficoSaldoPromedio();
    }

    private void cargarGraficoGastos() {
        Map<String, Integer> gastosPorCategoria = estadisticasController.obtenerGastosPorCategoria();
        XYChart.Series<String, Number> data = new XYChart.Series<>();
        gastosPorCategoria.forEach((categoria, total) ->
                data.getData().add(new XYChart.Data<>(categoria, total)));
        chartGastos.getData().clear();
        chartGastos.getData().add(data);
    }

    private void cargarGraficoUsuarios() {
        Map<String, Integer> transaccionesPorUsuario = estadisticasController.obtenerUsuariosConMasTransacciones();
        XYChart.Series<String, Number> data = new XYChart.Series<>();
        transaccionesPorUsuario.forEach((usuario, total) ->
                data.getData().add(new XYChart.Data<>(usuario, total)));
        chartUsuarios.getData().clear();
        chartUsuarios.getData().add(data);
    }

    private void cargarGraficoSaldoPromedio() {
        Map<String, Double> saldos = estadisticasController.obtenerSaldoPromedioUsuarios();
        chartSaldoPromedio.getData().clear();
        saldos.forEach((usuario, saldo) ->
                chartSaldoPromedio.getData().add(new PieChart.Data(usuario, saldo)));
    }

    public void actualizarEstadisticas() {
        cargarEstadisticas();
    }

    @Override
    public void update(Object evento) {
        actualizarEstadisticas();
    }
}