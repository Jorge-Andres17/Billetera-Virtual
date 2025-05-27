package co.edu.uniquindio.billeteravirtual.billeteravirtual.Strategy;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;

import java.io.File;
import java.time.LocalDate;

public interface ReporteStrategy {
    File generarReporte(Usuario usuario, LocalDate fechaInicio, LocalDate fechaFin, String tipoReporte) throws Exception;
}