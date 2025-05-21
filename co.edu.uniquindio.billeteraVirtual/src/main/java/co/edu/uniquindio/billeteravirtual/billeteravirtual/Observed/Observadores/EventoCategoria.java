package co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observadores;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Categoria;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.TipoEvento;

public class EventoCategoria extends Evento{
    private final Categoria categoria;

    public EventoCategoria(TipoEvento tipo, Categoria categoria) {
        super(tipo);
        this.categoria = categoria;
    }

    public Categoria getCategoria(){
        return categoria;
    }
}
