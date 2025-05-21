package co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observadores;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.TipoEvento;

public class EventoUsuario extends Evento {
    private final Usuario usuario;

    public EventoUsuario(TipoEvento tipo, Usuario usuario) {
        super(tipo);
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
