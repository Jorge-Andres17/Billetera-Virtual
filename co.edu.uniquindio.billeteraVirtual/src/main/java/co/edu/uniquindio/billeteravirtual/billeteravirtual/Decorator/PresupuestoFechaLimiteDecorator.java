package co.edu.uniquindio.billeteravirtual.billeteravirtual.Decorator;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.BilleteraVirtual;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Categoria;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.State.EstadoPresupuesto;

import java.time.LocalDate;

public class PresupuestoFechaLimiteDecorator extends PresupuestoDecorator{
    private LocalDate fechaLimite;

    public PresupuestoFechaLimiteDecorator(IPresupuesto presupuesto, LocalDate fechaLimite) {
        super(presupuesto);
        this.fechaLimite = fechaLimite;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public boolean estaVigente() {
        return LocalDate.now().isBefore(fechaLimite) || LocalDate.now().isEqual(fechaLimite);
    }

    public String getEstadoTemporal() {
        return estaVigente() ? "Presupuesto activo hasta " + fechaLimite
                : "Presupuesto vencido (fecha límite: " + fechaLimite + ")";
    }

    public IPresupuesto getDecorado() {
        return presupuesto;
    }

    @Override
    public void setIdPresupuesto(int idPresupuesto) {

    }
}
