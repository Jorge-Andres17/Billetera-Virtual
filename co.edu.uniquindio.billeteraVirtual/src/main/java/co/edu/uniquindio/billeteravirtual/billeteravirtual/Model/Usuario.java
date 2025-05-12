package co.edu.uniquindio.billeteravirtual.billeteravirtual.Model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String idUsuario;
    private String nombre;
    private String correo;
    private String numeroTelefono;
    private String direccion;
    private String clave;

    private List<Cuenta> listaCuentasAsociadas = new ArrayList<Cuenta>();
    BilleteraVirtual owedBilleteraVirtual;

    public Usuario(){}

    public Usuario(String idUsuario,
                   String nombre,
                   String correo,
                   String numeroTelefono,
                   String direccion,
                   String clave) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.numeroTelefono = numeroTelefono;
        this.direccion = direccion;
        this.clave = clave;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public List<Cuenta> getListaCuentasAsociadas() {
        return listaCuentasAsociadas;
    }

    public void setListaCuentasAsociadas(List<Cuenta> listaCuentasAsociadas) {
        this.listaCuentasAsociadas = listaCuentasAsociadas;
    }

    public BilleteraVirtual getOwedBilleteraVirtual() {
        return owedBilleteraVirtual;
    }

    public void setOwedBilleteraVirtual(BilleteraVirtual owedBilleteraVirtual) {
        this.owedBilleteraVirtual = owedBilleteraVirtual;
    }
}
