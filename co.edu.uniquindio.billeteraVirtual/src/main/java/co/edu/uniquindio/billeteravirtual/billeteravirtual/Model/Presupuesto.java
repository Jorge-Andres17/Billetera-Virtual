package co.edu.uniquindio.billeteravirtual.billeteravirtual.Model;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Decorator.IPresupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.State.EstadoActivo;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.State.EstadoPresupuesto;

public class Presupuesto implements IPresupuesto {
    private static int contadorId = 0;
    private int idPresupuesto;
    private String nombrePresupuesto;
    private double MontoAsignado;
    private double MontoGastado;

    private Cuenta cuentaAsociada;
    private Categoria categoriaAsociada;
    BilleteraVirtual ownedByBilleteraVirtual;
    private EstadoPresupuesto estadoPresupuesto;

    public void asociarCuenta(Cuenta cuenta) {
        estadoPresupuesto.asociarCuenta(this, cuenta);
    }

    public void setEstadoPresupuesto(EstadoPresupuesto estadoPresupuesto) {
        this.estadoPresupuesto = estadoPresupuesto;
    }

    public EstadoPresupuesto getEstadoPresupuesto() {
        return estadoPresupuesto;
    }

    public Presupuesto(String nombrePresupuesto,
                       double MontoAsignado,
                       double MontoGastado,
                       Cuenta cuentaAsociada,
                       Categoria categoriaAsociada) {
        this.nombrePresupuesto = nombrePresupuesto;
        this.MontoAsignado = MontoAsignado;
        this.MontoGastado = MontoGastado;
        this.cuentaAsociada = cuentaAsociada;
        this.categoriaAsociada = categoriaAsociada;
        this.estadoPresupuesto = new EstadoActivo();
    }

    public void asignarId() {
        this.idPresupuesto = ++contadorId;
    }

    public int getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(int idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public String getNombrePresupuesto() {
        return nombrePresupuesto;
    }

    public void setNombrePresupuesto(String nombrePresupuesto) {
        this.nombrePresupuesto = nombrePresupuesto;
    }

    public double getMontoAsignado() {
        return MontoAsignado;
    }

    public void setMontoAsignado(double montoAsignado) {
        MontoAsignado = montoAsignado;
    }

    public double getMontoGastado() {
        return MontoGastado;
    }

    public void setMontoGastado(double montoGastado) {
        MontoGastado = montoGastado;
    }

    public Cuenta getCuentaAsociada() {
        return cuentaAsociada;
    }

    public void setCuentaAsociada(Cuenta cuentaAsociada) {
        this.cuentaAsociada = cuentaAsociada;
    }

    public Categoria getCategoriaAsociada() {
        return categoriaAsociada;
    }

    public void setCategoriaAsociada(Categoria categoriaAsociada) {
        this.categoriaAsociada = categoriaAsociada;
    }

    public BilleteraVirtual getOwnedByBilleteraVirtual() {
        return ownedByBilleteraVirtual;
    }

    public void setOwnedByBilleteraVirtual(BilleteraVirtual ownedByBilleteraVirtual) {
        this.ownedByBilleteraVirtual = ownedByBilleteraVirtual;
    }

    @Override
    public String toString() {
        return nombrePresupuesto;
    }
}
