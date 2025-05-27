package co.edu.uniquindio.billeteravirtual.billeteravirtual.Decorator;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.*;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.State.EstadoPresupuesto;

public abstract class PresupuestoDecorator implements IPresupuesto {
    protected IPresupuesto presupuesto;

    public PresupuestoDecorator(IPresupuesto presupuesto){
        this.presupuesto = presupuesto;
    }

    @Override
    public void asignarId() {
        presupuesto.asignarId();
    }

    @Override
    public void setIdPresupuesto(int idPresupuesto){
        presupuesto.setIdPresupuesto(idPresupuesto);
    }

    @Override
    public int getIdPresupuesto() {
        return presupuesto.getIdPresupuesto();
    }

    @Override
    public String getNombrePresupuesto() {
        return presupuesto.getNombrePresupuesto();
    }

    @Override
    public void setNombrePresupuesto(String nombrePresupuesto) {
        presupuesto.setNombrePresupuesto(nombrePresupuesto);
    }

    @Override
    public double getMontoAsignado() {
        return presupuesto.getMontoAsignado();
    }

    @Override
    public void setMontoAsignado(double montoAsignado) {
        presupuesto.setMontoAsignado(montoAsignado);
    }

    @Override
    public double getMontoGastado() {
        return presupuesto.getMontoGastado();
    }

    @Override
    public void setMontoGastado(double montoGastado) {
        presupuesto.setMontoGastado(montoGastado);
    }

    @Override
    public Cuenta getCuentaAsociada() {
        return presupuesto.getCuentaAsociada();
    }

    @Override
    public void setCuentaAsociada(Cuenta cuentaAsociada) {
        presupuesto.setCuentaAsociada(cuentaAsociada);
    }

    @Override
    public Categoria getCategoriaAsociada() {
        return presupuesto.getCategoriaAsociada();
    }

    @Override
    public void setCategoriaAsociada(Categoria categoriaAsociada) {
        presupuesto.setCategoriaAsociada(categoriaAsociada);
    }

    @Override
    public BilleteraVirtual getOwnedByBilleteraVirtual() {
        return presupuesto.getOwnedByBilleteraVirtual();
    }

    @Override
    public void setOwnedByBilleteraVirtual(BilleteraVirtual billeteraVirtual) {
        presupuesto.setOwnedByBilleteraVirtual(billeteraVirtual);
    }

    @Override
    public EstadoPresupuesto getEstadoPresupuesto() {
        return presupuesto.getEstadoPresupuesto();
    }

    @Override
    public void setEstadoPresupuesto(EstadoPresupuesto estado) {
        presupuesto.setEstadoPresupuesto(estado);
    }

    @Override
    public void asociarCuenta(Cuenta cuenta) {
        presupuesto.asociarCuenta(cuenta);
    }
}