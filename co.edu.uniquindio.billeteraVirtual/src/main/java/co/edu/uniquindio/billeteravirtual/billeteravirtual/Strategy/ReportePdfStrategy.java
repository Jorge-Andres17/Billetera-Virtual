package co.edu.uniquindio.billeteravirtual.billeteravirtual.Strategy;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoTransaccion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Transaccion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class ReportePdfStrategy implements ReporteStrategy {
    @Override
    public File generarReporte(Usuario usuario, LocalDate fechaInicio, LocalDate fechaFin, String tipoReporte) throws Exception {
        File archivo = File.createTempFile("reporte-financiero-", ".pdf");
        archivo.deleteOnExit();

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.LETTER);
        document.addPage(page);

        float pageWidth = page.getMediaBox().getWidth();
        float startY = 700;

        try (PDPageContentStream content = new PDPageContentStream(document, page)) {
            content.beginText();
            float fontSize = 20;
            content.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
            content.setLeading(fontSize * 4);
            content.newLineAtOffset(0, startY);
            centerText(content, "Reporte Financiero - " + tipoReporte.toUpperCase(), PDType1Font.HELVETICA_BOLD, fontSize, pageWidth);
            content.newLine();

            fontSize = 18;
            content.setFont(PDType1Font.HELVETICA, fontSize);
            content.setLeading(fontSize * 4);
            centerText(content, "Usuario: " + usuario.getNombre(), PDType1Font.HELVETICA, fontSize, pageWidth);
            content.newLine();
            centerText(content, "Desde: " + fechaInicio + " - Hasta: " + fechaFin, PDType1Font.HELVETICA, fontSize, pageWidth);
            content.newLine();

            content.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
            centerText(content, "Detalle:", PDType1Font.HELVETICA_BOLD, fontSize, pageWidth);
            content.newLine();

            content.setFont(PDType1Font.HELVETICA, fontSize);
            content.setLeading(fontSize * 3);

            int contador = 0;

            if (tipoReporte.equalsIgnoreCase("INGRESOS")) {
                for (Cuenta cuenta : usuario.getListaCuentasAsociadas()) {
                    for (Transaccion trans : cuenta.getListaTransacciones()) {
                        if (!trans.getFechaTransaccion().isBefore(fechaInicio)
                                && !trans.getFechaTransaccion().isAfter(fechaFin)
                                && (trans.getTipoTransaccion() == TipoTransaccion.DEPOSITO
                                || (trans.getTipoTransaccion() == TipoTransaccion.TRANSFERENCIA
                                && cuenta.equals(trans.getCuentaDestino())))) {

                            String linea = String.format("- [%s] %s: $%.2f",
                                    trans.getFechaTransaccion(), trans.getDescripcion(), trans.getMonto());
                            centerText(content, linea, PDType1Font.HELVETICA, fontSize, pageWidth);
                            content.newLine();
                            contador++;
                        }
                    }
                }
                if (contador == 0) {
                    centerText(content, "Sin transacciones de ingresos en este rango de fechas.", PDType1Font.HELVETICA, fontSize, pageWidth);
                    content.newLine();
                }

            } else if (tipoReporte.equalsIgnoreCase("GASTOS")) {
                for (Cuenta cuenta : usuario.getListaCuentasAsociadas()) {
                    for (Transaccion trans : cuenta.getListaTransacciones()) {
                        if (!trans.getFechaTransaccion().isBefore(fechaInicio)
                                && !trans.getFechaTransaccion().isAfter(fechaFin)
                                && (trans.getTipoTransaccion() == TipoTransaccion.RETIRO
                                || (trans.getTipoTransaccion() == TipoTransaccion.TRANSFERENCIA
                                && cuenta.equals(trans.getCuentaOrigen())))) {

                            String linea = String.format("- [%s] %s: $%.2f",
                                    trans.getFechaTransaccion(), trans.getDescripcion(), trans.getMonto());
                            centerText(content, linea, PDType1Font.HELVETICA, fontSize, pageWidth);
                            content.newLine();
                            contador++;
                        }
                    }
                }
                if (contador == 0) {
                    centerText(content, "Sin transacciones de gastos en este rango de fechas.", PDType1Font.HELVETICA, fontSize, pageWidth);
                    content.newLine();
                }

            } else if (tipoReporte.equalsIgnoreCase("SALDOS")) {
                double totalSaldo = 0;
                if (usuario.getListaCuentasAsociadas().isEmpty()) {
                    centerText(content, "Sin cuentas asociadas al usuario.", PDType1Font.HELVETICA, fontSize, pageWidth);
                    content.newLine();
                } else {
                    for (Cuenta cuenta : usuario.getListaCuentasAsociadas()) {
                        double saldo = cuenta.getPresupuesto().getMontoAsignado();
                        totalSaldo += saldo;
                        String linea = String.format("- Cuenta %s: $%.2f", cuenta.getNumeroCuenta(), saldo);
                        centerText(content, linea, PDType1Font.HELVETICA, fontSize, pageWidth);
                        content.newLine();
                    }
                    content.newLine();
                    content.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
                    centerText(content, String.format("Saldo Total: $%.2f", totalSaldo), PDType1Font.HELVETICA_BOLD, fontSize, pageWidth);
                }
            }

            content.endText();
        }

        document.save(archivo);
        document.close();
        Desktop.getDesktop().open(archivo);
        return archivo;
    }

    private void centerText(PDPageContentStream content, String text, PDFont font, float fontSize, float pageWidth) throws IOException {
        float textWidth = font.getStringWidth(text) / 1000 * fontSize;
        float x = (pageWidth - textWidth) / 2;
        content.newLineAtOffset(x, 0);
        content.showText(text);
        content.newLineAtOffset(-x, 0);
    }
}