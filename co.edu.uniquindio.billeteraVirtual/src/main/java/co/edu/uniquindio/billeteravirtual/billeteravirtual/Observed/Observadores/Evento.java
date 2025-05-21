package co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observadores;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.TipoEvento;

public abstract class Evento {
    private final TipoEvento tipo;

    public Evento(TipoEvento tipo) {
        this.tipo = tipo;
    }

    public TipoEvento getTipo() {
        return tipo;
    }
}
