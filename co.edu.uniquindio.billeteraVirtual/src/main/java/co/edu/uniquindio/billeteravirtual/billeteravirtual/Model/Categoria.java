package co.edu.uniquindio.billeteravirtual.billeteravirtual.Model;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Builder.CategoriaBuilder;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Decorator.IPresupuesto;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
    private static int contadorId = 0;
    private int idCategoria;
    private String nombre;
    private String descripcion;

    private List<IPresupuesto> listaPresupuestos = new ArrayList<IPresupuesto>();
    BilleteraVirtual  ownedByBilleteraVirtual;

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

    public List<IPresupuesto> getListaPresupuestos() {
        return listaPresupuestos;
    }

    public void setListaPresupuestos(List<IPresupuesto> listaPresupuestos) {
        this.listaPresupuestos = listaPresupuestos;
    }

    public BilleteraVirtual getOwnedByBilleteraVirtual() {
        return ownedByBilleteraVirtual;
    }

    public void setOwnedByBilleteraVirtual(BilleteraVirtual ownedByBilleteraVirtual) {
        this.ownedByBilleteraVirtual = ownedByBilleteraVirtual;
    }
}
