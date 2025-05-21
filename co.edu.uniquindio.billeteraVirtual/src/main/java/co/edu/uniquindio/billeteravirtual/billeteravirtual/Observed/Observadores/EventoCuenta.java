package co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observadores;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.TipoEvento;

public class EventoCuenta extends Evento {
    private final Cuenta cuenta;

    public EventoCuenta(TipoEvento tipo, Cuenta cuenta) {
        super(tipo);
        this.cuenta = cuenta;
    }

    public Cuenta getCuenta(){
        return cuenta;
    }
}
