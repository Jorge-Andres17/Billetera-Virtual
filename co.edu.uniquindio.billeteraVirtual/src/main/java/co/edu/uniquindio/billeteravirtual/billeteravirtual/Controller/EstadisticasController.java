package co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Factory.ModelFactory;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Facade.EstadisticasFacade;
import java.util.Map;

public class EstadisticasController {
    private final ModelFactory modelFactory;
    private final EstadisticasFacade estadisticasFacade;

    public EstadisticasController() {
        this.modelFactory = ModelFactory.getInstancia();
        this.estadisticasFacade = new EstadisticasFacade(modelFactory.getBilleteraVirtual());
    }

    public Map<String, Integer> obtenerGastosPorCategoria() {
        return estadisticasFacade.obtenerGastosPorCategoria();
    }

    public Map<String, Integer> obtenerUsuariosConMasTransacciones() {
        return estadisticasFacade.obtenerUsuariosConMasTransacciones();
    }

    public Map<String, Double> obtenerSaldoPromedioUsuarios() {
        return estadisticasFacade.obtenerSaldoPromedioUsuarios();
    }

    public ModelFactory getModelFactory() {
        return modelFactory;
    }
}