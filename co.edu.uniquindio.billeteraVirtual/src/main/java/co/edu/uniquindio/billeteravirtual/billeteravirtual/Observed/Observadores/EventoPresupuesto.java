package co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observadores;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Decorator.IPresupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.TipoEvento;

public class EventoPresupuesto extends Evento {
    private final IPresupuesto presupuesto;

    public EventoPresupuesto(TipoEvento tipo, IPresupuesto presupuesto) {
        super(tipo);
        this.presupuesto = presupuesto;
    }

    public IPresupuesto getPresupuesto() {
        return presupuesto;
    }
}
