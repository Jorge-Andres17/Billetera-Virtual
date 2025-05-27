package co.edu.uniquindio.billeteravirtual.billeteravirtual.Model;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoTransaccion;

import java.time.LocalDate;

public class Transaccion {
    private static int contadorId = 0;
    private int idTransaccion;
    private LocalDate fechaTransaccion;
    private double Monto;
    private String descripcion;

    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;
    private TipoTransaccion tipoTransaccion;
    BilleteraVirtual ownedByBilleteraVirtual;

    public Transaccion(LocalDate fechaTransaccion,
                       double monto,
                       String descripcion,
                       Cuenta cuentaOrigen,
                       Cuenta cuentaDestino,
                       TipoTransaccion tipoTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
        this.Monto = monto;
        this.descripcion = descripcion;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.tipoTransaccion = tipoTransaccion;
    }

    public void asignarId() {
        this.idTransaccion = ++contadorId;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public LocalDate getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(LocalDate fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public double getMonto() {
        return Monto;
    }

    public void setMonto(double monto) {
        Monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(Cuenta cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public Cuenta getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(Cuenta cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public TipoTransaccion getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(TipoTransaccion tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public BilleteraVirtual getOwnedByBilleteraVirtual() {
        return ownedByBilleteraVirtual;
    }

    public void setOwnedByBilleteraVirtual(BilleteraVirtual ownedByBilleteraVirtual) {
        this.ownedByBilleteraVirtual = ownedByBilleteraVirtual;
    }
}
