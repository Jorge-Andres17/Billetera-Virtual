package co.edu.uniquindio.billeteravirtual.billeteravirtual.Decorator;

public class TipoPresupuestoDecorator extends PresupuestoDecorator {
    private String tipoPresupuesto;

    public TipoPresupuestoDecorator(IPresupuesto presupuesto, String tipoPresupuesto) {
        super(presupuesto);
        this.tipoPresupuesto = tipoPresupuesto;
    }

    public String getTipoPresupuesto() {
        return tipoPresupuesto;
    }

    public IPresupuesto getDecorado() {
        return presupuesto;
    }
}