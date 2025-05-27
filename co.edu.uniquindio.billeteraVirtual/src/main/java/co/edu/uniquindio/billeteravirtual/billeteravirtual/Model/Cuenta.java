package co.edu.uniquindio.billeteravirtual.billeteravirtual.Model;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Decorator.IPresupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoCuenta;

import java.util.ArrayList;
import java.util.List;

public class Cuenta {
    private static int contadorId = 0;
    private int idCuenta;
    private String nombreBanco;
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;

    private Usuario usuarioAsociado;
    private IPresupuesto presupuesto;
    private List<Transaccion> listaTransacciones = new ArrayList<Transaccion>();
    BilleteraVirtual ownedByBilleteraVirtual;

    public Cuenta(String nombreBanco,
                  String numeroCuenta,
                  TipoCuenta tipoCuenta,
                  Usuario usuarioAsociado,
                  IPresupuesto presupuesto) {
        this.nombreBanco = nombreBanco;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.usuarioAsociado = usuarioAsociado;
        this.presupuesto = presupuesto;
    }

    public void asignarId() {
        this.idCuenta = ++contadorId;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Usuario getUsuarioAsociado() {
        return usuarioAsociado;
    }

    public void setUsuarioAsociado(Usuario usuarioAsociado) {
        this.usuarioAsociado = usuarioAsociado;
    }

    public IPresupuesto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(IPresupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }

    public List<Transaccion> getListaTransacciones() {
        return listaTransacciones;
    }

    public void setListaTransacciones(List<Transaccion> listaTransacciones) {
        this.listaTransacciones = listaTransacciones;
    }

    public BilleteraVirtual getOwnedByBilleteraVirtual() {
        return ownedByBilleteraVirtual;
    }

    public void setOwnedByBilleteraVirtual(BilleteraVirtual ownedByBilleteraVirtual) {
        this.ownedByBilleteraVirtual = ownedByBilleteraVirtual;
    }
}
