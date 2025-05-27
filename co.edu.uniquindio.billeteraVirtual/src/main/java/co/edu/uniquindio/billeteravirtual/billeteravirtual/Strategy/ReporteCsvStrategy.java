package co.edu.uniquindio.billeteravirtual.billeteravirtual.Strategy;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoTransaccion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Transaccion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class ReporteCsvStrategy implements ReporteStrategy {
    @Override
    public File generarReporte(Usuario usuario, LocalDate fechaInicio, LocalDate fechaFin, String tipoReporte) throws Exception {
        File archivo = File.createTempFile("reporte-financiero-", ".csv");
        archivo.deleteOnExit();

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(archivo), StandardCharsets.UTF_8)) {
            writer.write('\uFEFF');

            writer.write("Usuario;Tipo Reporte;Fecha Inicio;Fecha Fin\n");
            writer.write(String.format("%s;%s;%s;%s\n",
                    usuario.getNombre(), tipoReporte.toUpperCase(), fechaInicio, fechaFin));
            writer.write("\n");

            writer.write("Fecha;Descripción;Monto;Cuenta\n");

            int contador = 0;

            for (Cuenta cuenta : usuario.getListaCuentasAsociadas()) {
                for (Transaccion trans : cuenta.getListaTransacciones()) {
                    if (!trans.getFechaTransaccion().isBefore(fechaInicio)
                            && !trans.getFechaTransaccion().isAfter(fechaFin)) {

                        boolean agregar = false;

                        if (tipoReporte.equalsIgnoreCase("INGRESOS") &&
                                (trans.getTipoTransaccion() == TipoTransaccion.DEPOSITO ||
                                        (trans.getTipoTransaccion() == TipoTransaccion.TRANSFERENCIA &&
                                                cuenta.equals(trans.getCuentaDestino())))) {
                            agregar = true;

                        } else if (tipoReporte.equalsIgnoreCase("GASTOS") &&
                                (trans.getTipoTransaccion() == TipoTransaccion.RETIRO ||
                                        (trans.getTipoTransaccion() == TipoTransaccion.TRANSFERENCIA &&
                                                cuenta.equals(trans.getCuentaOrigen())))) {
                            agregar = true;
                        }

                        if (agregar) {
                            writer.write(String.format("%s;%s;%.2f;%s\n",
                                    trans.getFechaTransaccion(),
                                    trans.getDescripcion().replace(";", ","),
                                    trans.getMonto(),
                                    cuenta.getNumeroCuenta()));
                            contador++;
                        }
                    }
                }
            }

            if (tipoReporte.equalsIgnoreCase("SALDOS")) {
                double total = 0;
                for (Cuenta cuenta : usuario.getListaCuentasAsociadas()) {
                    double saldo = cuenta.getPresupuesto().getMontoAsignado();
                    total += saldo;
                    writer.write(String.format("N/A;Saldo cuenta %s;%.2f;%s\n",
                            cuenta.getNumeroCuenta(), saldo, cuenta.getNumeroCuenta()));
                }
                writer.write(String.format("N/A;Saldo total;%.2f;TODAS\n", total));
            }

            if (contador == 0 && !tipoReporte.equalsIgnoreCase("SALDOS")) {
                writer.write("N/A;Sin transacciones en este rango de fechas;0.00;N/A\n");
            }
        }

        Desktop.getDesktop().open(archivo);
        return archivo;
    }
}