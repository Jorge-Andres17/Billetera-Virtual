package co.edu.uniquindio.billeteravirtual.billeteravirtual.Model;

import java.time.LocalDate;

public class Transaccion {
    private int idTransaccion;
    private LocalDate fechaTransaccion;
    private double Monto;
    private String descripcion;

    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;
    private Categoria categoria;

    public Transaccion() {}

    public Transaccion(int idTransaccion,
                       LocalDate fechaTransaccion,
                       double monto,
                       String descripcion,
                       Cuenta cuentaOrigen,
                       Cuenta cuentaDestino,
                       Categoria categoria) {
        this.idTransaccion = idTransaccion;
        this.fechaTransaccion = fechaTransaccion;
        this.Monto = monto;
        this.descripcion = descripcion;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.categoria = categoria;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
