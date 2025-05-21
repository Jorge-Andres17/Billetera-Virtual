package co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observadores;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Transaccion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.TipoEvento;

public class EventoTransaccion extends  Evento{
    private final Transaccion transaccion;

    public EventoTransaccion(TipoEvento tipo, Transaccion transaccion) {
        super(tipo);
        this.transaccion = transaccion;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }
}
