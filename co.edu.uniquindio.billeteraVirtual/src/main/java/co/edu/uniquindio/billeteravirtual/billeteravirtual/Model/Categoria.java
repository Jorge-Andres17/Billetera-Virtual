package co.edu.uniquindio.billeteravirtual.billeteravirtual.Model;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Builder.CategoriaBuilder;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Service.IPresupuestoServices;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
    private static int contadorId = 0;
    private int idCategoria;
    private String nombre;
    private String descripcion;

    private List<Presupuesto> listaPresupuestos = new ArrayList<Presupuesto>();
    BilleteraVirtual  ownedByBilleteraVirtual;

    public static Categoria crearDesdeDto( String nombre, String descripcion) {
        Categoria categoria = new Categoria(nombre, descripcion);
        return categoria;
    }

    public Categoria(String nombre, String descripcion) {
        this.idCategoria = ++contadorId;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public static CategoriaBuilder builder() {
        return new CategoriaBuilder();

    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Presupuesto> getListaPresupuestos() {
        return listaPresupuestos;
    }

    public void setListaPresupuestos(List<Presupuesto> listaPresupuestos) {
        this.listaPresupuestos = listaPresupuestos;
    }

    public BilleteraVirtual getOwnedByBilleteraVirtual() {
        return ownedByBilleteraVirtual;
    }

    public void setOwnedByBilleteraVirtual(BilleteraVirtual ownedByBilleteraVirtual) {
        this.ownedByBilleteraVirtual = ownedByBilleteraVirtual;
    }
}
