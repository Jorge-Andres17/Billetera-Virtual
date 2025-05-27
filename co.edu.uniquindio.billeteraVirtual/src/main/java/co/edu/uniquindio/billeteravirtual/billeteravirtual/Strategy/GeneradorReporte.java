package co.edu.uniquindio.billeteravirtual.billeteravirtual.Strategy;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;

import java.io.File;
import java.time.LocalDate;

public class GeneradorReporte {
    private ReporteStrategy strategy;

    public void setStrategy(ReporteStrategy strategy) {
        this.strategy = strategy;
    }

    public File generar(Usuario usuario, LocalDate fechaInicio, LocalDate fechaFin, String tipoReporte) throws Exception {
        if (strategy == null) throw new IllegalStateException("Estrategia no seleccionada");
        return strategy.generarReporte(usuario, fechaInicio, fechaFin, tipoReporte);
    }
}