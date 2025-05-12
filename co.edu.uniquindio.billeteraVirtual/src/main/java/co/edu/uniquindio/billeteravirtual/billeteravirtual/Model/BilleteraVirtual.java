package co.edu.uniquindio.billeteravirtual.billeteravirtual.Model;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Login.Sesion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoCuenta;
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
    public boolean actualizarCategoria(int id, String nombre,
                                       String descripcion) {
        Categoria categoria = obtenerCategoria(id);
        if (categoria != null) {
            categoria.setNombre(nombre);
            categoria.setDescripcion(descripcion);

            return true;
        }else{
            return false;
        }
    }


    public Categoria actualizarCategoriaDto(int id, String nombre,
                                       String descripcion) {
        Categoria categoria = obtenerCategoria(id);
        if (categoria != null) {
            categoria.setNombre(nombre);
            categoria.setDescripcion(descripcion);

        }

        return categoria;
    }

    @Override
    public boolean agregarCuenta(Cuenta cuenta) {
        Cuenta cuenta1 = obtenerCuenta(cuenta.getIdCuenta());
        if (cuenta1 == null) {
            listaCuentas.add(cuenta);
            cuenta.getUsuarioAsociado().getListaCuentasAsociadas().add(cuenta);

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
            cuenta.getUsuarioAsociado().getListaCuentasAsociadas().remove(cuenta);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean actualizarCuenta(int id, String nombreBanco,
                                    String numeroCuenta,
                                    TipoCuenta tipoCuenta) {
        Cuenta cuenta = obtenerCuenta(id);
        if (cuenta != null) {
            cuenta.setNombreBanco(nombreBanco);
            cuenta.setNumeroCuenta(numeroCuenta);
            cuenta.setTipoCuenta(tipoCuenta);

            List<Cuenta> cuentasUsuario = cuenta.getUsuarioAsociado().getListaCuentasAsociadas();
            if (!cuentasUsuario.contains(cuenta)) {
                cuentasUsuario.add(cuenta);
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean agregarPresupuesto(Presupuesto presupuesto) {
        Presupuesto presupuesto1 = obtenerPresupuesto(presupuesto.getIdPresupuesto());
        if (presupuesto1 == null) {
            if (presupuesto.getCuentaAsociada().getPresupuesto() != null) {
                return false;
            }
            listaPresupuestos.add(presupuesto);
            presupuesto.getCuentaAsociada().setPresupuesto(presupuesto);
            presupuesto.getCategoriaAsociada().getListaPresupuestos().add(presupuesto);

            return true;
        }else {
            return false;
        }
    }

    private Presupuesto obtenerPresupuesto(int idPresupuesto) {
        Presupuesto presupuesto = null;
        for (Presupuesto presupuesto1 : listaPresupuestos) {
            if (presupuesto1.getIdPresupuesto() == idPresupuesto) {
                presupuesto = presupuesto1;
                break;
            }
        }

        return presupuesto;
    }

    @Override
    public boolean eliminarPresupuesto(int idPresupuesto) {
        Presupuesto presupuesto = obtenerPresupuesto(idPresupuesto);
        if (presupuesto.getIdPresupuesto() == idPresupuesto) {
            listaPresupuestos.remove(presupuesto);
            presupuesto.getCuentaAsociada().setPresupuesto(null);

            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean actualizarPresupuesto(int idPresupuesto,
                                         String nombre,
                                         Double montoAsignado,
                                         Cuenta cuenta,
                                         Categoria categoria) {
        Presupuesto presupuesto = obtenerPresupuesto(idPresupuesto);
        if (presupuesto != null) {
            presupuesto.setNombrePresupuesto(nombre);
            presupuesto.setMontoAsignado(montoAsignado);
            presupuesto.setCuentaAsociada(cuenta);
            presupuesto.setCategoriaAsociada(categoria);

            List<Cuenta> cuentasUsuario = cuenta.getUsuarioAsociado().getListaCuentasAsociadas();
            if (!cuentasUsuario.contains(cuenta)) {
                cuentasUsuario.add(cuenta);
            }

            return true;
        } else {
            return false;
        }
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
            if (id.equals(usuario1.getIdUsuario())) {
                usuario = usuario1;
                break;
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
    public boolean actualizarPerfilUsuario(String nombre, String correo, String numeroTelefono) {
        Usuario usuario = Sesion.getUsuarioActual();
        if (usuario != null) {
            usuario.setNombre(nombre);
            usuario.setCorreo(correo);
            usuario.setNumeroTelefono(numeroTelefono);

            return true;
        }
        return false;
    }

    @Override
    public boolean agregarTransaccion(Transaccion transaccion) {
        Transaccion transaccion1 = obtenerTransaccion(transaccion.getIdTransaccion());
        if (transaccion1 == null) {
            if (transaccion.getCuentaDestino() == null || transaccion.getCuentaDestino() != null ){
                listatransacciones.add(transaccion);
                return true;
            }
        }
        return false;
    }

    private Transaccion obtenerTransaccion(int idTransaccion) {
        for (Transaccion transaccion : listatransacciones){
            if (transaccion.getIdTransaccion() == idTransaccion) {
                return transaccion;
            }
        }
        return null;
    }

    @Override
    public boolean depositar(int idCuenta, double monto) {
        if (monto <= 0) {
            return false;
        }

        for (Cuenta cuenta : Sesion.getUsuarioActual().getListaCuentasAsociadas()) {
            if (cuenta.getIdCuenta() == idCuenta && cuenta.getPresupuesto() != null) {
                double nuevoMonto = cuenta.getPresupuesto().getMontoAsignado() + monto;
                cuenta.getPresupuesto().setMontoAsignado(nuevoMonto);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean transferir(int idCuentaOrigen, int idCuentaDestino, double monto) {
        for (Cuenta cuentaOrigen : Sesion.getUsuarioActual().getListaCuentasAsociadas()) {
            if (cuentaOrigen.getIdCuenta() == idCuentaOrigen) {
                for (Cuenta cuentaDestino : Sesion.getUsuarioActual().getListaCuentasAsociadas()) {
                    if (cuentaDestino.getIdCuenta() == idCuentaDestino) {
                        double restar = cuentaOrigen.getPresupuesto().getMontoAsignado() - monto;
                        cuentaOrigen.getPresupuesto().setMontoAsignado(restar);
                        double sumar = cuentaDestino.getPresupuesto().getMontoAsignado() + monto;
                        cuentaDestino.getPresupuesto().setMontoAsignado(sumar);

                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean retirar(int idCuenta,double monto) {
        for (Cuenta cuenta : Sesion.getUsuarioActual().getListaCuentasAsociadas()) {
            if (cuenta.getIdCuenta() == idCuenta && cuenta.getPresupuesto() != null &&
                    cuenta.getPresupuesto().getMontoAsignado() > monto) {
                double nuevoMonto = cuenta.getPresupuesto().getMontoAsignado() - monto;
                cuenta.getPresupuesto().setMontoAsignado(nuevoMonto);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Transaccion> obtenerTransaccionesDelUsuario() {
        List<Transaccion> transaccionesUsuario = new ArrayList<>();
        for (Cuenta cuenta : Sesion.getUsuarioActual().getListaCuentasAsociadas()) {
            transaccionesUsuario.addAll(cuenta.getListaTransacciones());
        }
        return transaccionesUsuario;
    }

    public List<Presupuesto> obtenerPresupuestosCuentas() {
        List<Presupuesto> listaPresupuestos = new ArrayList<>();
        for (Cuenta cuenta : Sesion.getUsuarioActual().getListaCuentasAsociadas()) {
            Presupuesto presupuesto = cuenta.getPresupuesto();
            if (presupuesto != null) {
                listaPresupuestos.add(presupuesto);
            }
        }
        return listaPresupuestos;
    }
}
