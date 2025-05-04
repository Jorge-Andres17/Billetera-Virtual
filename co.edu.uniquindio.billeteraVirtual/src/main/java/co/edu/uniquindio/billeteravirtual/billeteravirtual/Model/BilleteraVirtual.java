package co.edu.uniquindio.billeteravirtual.billeteravirtual.Model;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Service.IBilleteraVirtualServices;

import java.util.ArrayList;
import java.util.List;

public class BilleteraVirtual implements IBilleteraVirtualServices {
    private String nombre;

    private List<Administrador> listaAdministradores = new ArrayList<Administrador>();
    private List<Usuario> listaUsuarios = new ArrayList<Usuario>();
    private List<Cuenta> listaCuentas = new ArrayList<Cuenta>();
    private List<Presupuesto> listaPresupuestos = new ArrayList<Presupuesto>();
    private List<Categoria> listaCategorias = new ArrayList<Categoria>();
    private List<Transaccion> listatransacciones = new ArrayList<Transaccion>();

    public BilleteraVirtual(){}

    public BilleteraVirtual( String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Administrador> getListaAdministradores() {
        return listaAdministradores;
    }

    public void setListaAdministradores(List<Administrador> listaAdministradores) {
        this.listaAdministradores = listaAdministradores;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Cuenta> getListaCuentas() {
        return listaCuentas;
    }

    public void setListaCuentas(List<Cuenta> listaCuentas) {
        this.listaCuentas = listaCuentas;
    }

    public List<Presupuesto> getListaPresupuestos() {
        return listaPresupuestos;
    }

    public void setListaPresupuestos(List<Presupuesto> listaPresupuestos) {
        this.listaPresupuestos = listaPresupuestos;
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public List<Transaccion> getListatransacciones() {
        return listatransacciones;
    }

    public void setListatransacciones(List<Transaccion> listatransacciones) {
        this.listatransacciones = listatransacciones;
    }

    @Override
    public boolean agregarCategoria(Categoria categoria) {
        Categoria categoria1 = obtenerCategoria(categoria.getIdCategoria());
        if (categoria1 == null) {
            listaCategorias.add(categoria);
            return true;
        }else{
            return false;
        }
    }

    public Categoria agregarCategoria(String nombre, String descripcion) {
        for (Categoria categoria : listaCategorias) {
            if (categoria.getNombre().equals(nombre) && categoria.getDescripcion().equals(descripcion)) {
                return categoria;
            }
        }
        return null;
    }

    private Categoria obtenerCategoria(int idCategoria) {
        Categoria categoria = null;
        for (Categoria categoria1 : listaCategorias) {
            if (categoria1.getIdCategoria() == idCategoria) {
                categoria = categoria1;
                break;
            }
        }

        return categoria;
    }

    @Override
    public boolean eliminarCategoria(int IdCategoria) {
        Categoria categoria = obtenerCategoria(IdCategoria);
        if (categoria.getIdCategoria() == IdCategoria) {
            listaCategorias.remove(categoria);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean actualizarCategoria(String nombre, String numeroIdentificacionActual, String numeroIdentificacion, String email, String numeroCelular) {
        return false;
    }

    @Override
    public boolean agregarCuenta(Cuenta cuenta) {
        Cuenta cuenta1 = obtenerCuenta(cuenta.getIdCuenta());
        if (cuenta1 == null) {
            listaCuentas.add(cuenta);
            return true;
        }else {
            return false;
        }
    }

    private Cuenta obtenerCuenta(int idCuenta) {
        Cuenta cuenta = null;
        for (Cuenta cuenta2 : listaCuentas) {
            if (cuenta2.getIdCuenta() == idCuenta) {
                cuenta = cuenta2;
                break;
            }
        }

        return cuenta;
    }

    @Override
    public boolean eliminarCuenta(int idCuenta) {
        Cuenta cuenta = obtenerCuenta(idCuenta);
        if (cuenta.getIdCuenta() == idCuenta) {
            listaCuentas.remove(cuenta);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean actualizarCuenta(String nombre, String numeroIdentificacionActual, String numeroIdentificacion, String email, String numeroCelular) {
        return false;
    }

    @Override
    public boolean agregarPresupuesto(Usuario usuario) {
        return false;
    }

    @Override
    public boolean eliminarPresupuesto(String numeroIdentificacion) {
        return false;
    }

    @Override
    public boolean actualizarPresupuesto(String nombre, String numeroIdentificacionActual, String numeroIdentificacion, String email, String numeroCelular) {
        return false;
    }

    @Override
    public boolean agregarUsuario(Usuario usuario) {
        Usuario usuarioNuevo = obtenerUsuario(usuario.getIdUsuario());
        if (usuarioNuevo == null) {
            listaUsuarios.add(usuario);
            return true;
        }else {
            return false;
        }
    }

    private Usuario obtenerUsuario(String id) {
        Usuario usuario = null;
        for (Usuario usuario1 : listaUsuarios) {
            if (usuario.getIdUsuario().equals(id)){
                usuario = usuario1;
            }
        }

        return usuario;
    }

    @Override
    public boolean eliminarUsuario(String id) {
        Usuario usuario = obtenerUsuario(id);
        if (usuario.getIdUsuario().equals(id)) {
            listaUsuarios.remove(usuario);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean actualizarUsuario(String nombre, String numeroIdentificacionActual, String numeroIdentificacion, String email, String numeroCelular) {
        return false;
    }

    @Override
    public boolean agregarTransaccion(Transaccion transaccion) {
        listatransacciones.add(transaccion);
        return true;
    }
}
