package co.edu.uniquindio.billeteravirtual.billeteravirtual.Facade;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.BilleteraVirtual;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoTransaccion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Transaccion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;

import java.util.HashMap;
import java.util.Map;

public class EstadisticasFacade {
    private final BilleteraVirtual billeteraVirtual;

    public EstadisticasFacade(BilleteraVirtual billeteraVirtual) {
        this.billeteraVirtual = billeteraVirtual;
    }

    public Map<String, Integer> obtenerGastosPorCategoria() {
        Map<String, Integer> resultado = new HashMap<>();

        for (Usuario usuario : billeteraVirtual.getListaUsuarios()) {
            for (Cuenta cuenta : usuario.getListaCuentasAsociadas()) {
                for (Transaccion trans : cuenta.getListaTransacciones()) {
                    boolean esGasto = false;
                    if (trans.getTipoTransaccion() == TipoTransaccion.RETIRO && trans.getCuentaOrigen().equals(cuenta)) {
                        esGasto = true;
                    } else if (trans.getTipoTransaccion() == TipoTransaccion.TRANSFERENCIA && trans.getCuentaOrigen().equals(cuenta)) {
                        esGasto = true;
                    }
                    if (esGasto) {
                        String categoria = trans.getCuentaOrigen().getPresupuesto().getCategoriaAsociada().getNombre();
                        resultado.put(categoria, resultado.getOrDefault(categoria, 0) + 1);
                    }
                }
            }
        }

        return resultado;
    }

    public Map<String, Integer> obtenerUsuariosConMasTransacciones() {
        Map<String, Integer> resultado = new HashMap<>();
        for (Usuario usuario :  billeteraVirtual.getListaUsuarios()) {
            int total = usuario.getListaCuentasAsociadas().stream()
                    .mapToInt(c -> c.getListaTransacciones().size())
                    .sum();
            resultado.put(usuario.getNombre(), total);
        }
        return resultado;
    }

    public Map<String, Double> obtenerSaldoPromedioUsuarios() {
        Map<String, Double> resultado = new HashMap<>();
        for (Usuario usuario : billeteraVirtual.getListaUsuarios()) {
            double suma = usuario.getListaCuentasAsociadas().stream()
                    .mapToDouble(c -> c.getPresupuesto().getMontoAsignado())
                    .sum();
            int cantidad = usuario.getListaCuentasAsociadas().size();
            resultado.put(usuario.getNombre(), cantidad > 0 ? suma / cantidad : 0);
        }
        return resultado;
    }
}
