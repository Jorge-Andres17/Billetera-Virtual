package co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observadores;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Presupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.TipoEvento;

public class EventoPresupuesto extends Evento {
    private final Presupuesto presupuesto;

    public EventoPresupuesto(TipoEvento tipo, Presupuesto presupuesto) {
        super(tipo);
        this.presupuesto = presupuesto;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }
}
