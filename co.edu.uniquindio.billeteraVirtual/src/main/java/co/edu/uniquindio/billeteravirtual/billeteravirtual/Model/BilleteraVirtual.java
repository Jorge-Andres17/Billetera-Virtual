package co.edu.uniquindio.billeteravirtual.billeteravirtual.Model;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Decorator.IPresupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Login.Sesion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoCuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.*;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observadores.*;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Service.IBilleteraVirtualServices;
import java.util.ArrayList;
import java.util.List;


public class BilleteraVirtual extends Notificador implements IBilleteraVirtualServices{
    private String nombre;

    private List<Administrador> listaAdministradores = new ArrayList<Administrador>();
    private List<Usuario> listaUsuarios = new ArrayList<Usuario>();
    private List<Cuenta> listaCuentas = new ArrayList<Cuenta>();
    private List<IPresupuesto> listaPresupuestos = new ArrayList<IPresupuesto>();
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

    public List<IPresupuesto> getListaPresupuestos() {
        return listaPresupuestos;
    }

    public void setListaPresupuestos(List<IPresupuesto> listaPresupuestos) {
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
    public Categoria agregarCategoria(String nombre, String descripcion) {
        Categoria categoria = obtenerCategoria(nombre);
        if (categoria == null) {
            listaCategorias.add(categoria = new Categoria(nombre,descripcion));
            notifyObservers(new EventoCategoria(TipoEvento.AGREGAR,categoria));

                return categoria;
        }
        return null;
    }

    private Categoria obtenerCategoria(String nombre) {
        Categoria categoria = null;
        for (Categoria categoria1 : listaCategorias) {
            if (categoria1.getNombre().equals(nombre)) {
                categoria = categoria1;
                break;
            }
        }

        return categoria;
    }

    @Override
    public boolean eliminarCategoria(String nombre) {
        Categoria categoria = obtenerCategoria(nombre);
        if (categoria.getNombre().equals(nombre)) {
            listaCategorias.remove(categoria);
            notifyObservers(new EventoCategoria(TipoEvento.ELIMINAR,categoria));

            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean actualizarCategoria( String nombre,
                                       String descripcion) {
        Categoria categoria = obtenerCategoria(nombre);
        if (categoria != null) {
            categoria.setNombre(nombre);
            categoria.setDescripcion(descripcion);
            notifyObservers(new EventoCategoria(TipoEvento.ACTUALIZAR,categoria));

            return true;
        }else{
            return false;
        }
    }

    public Categoria actualizarCategoriaDto(String nombre,
                                       String descripcion) {
        Categoria categoria = obtenerCategoria(nombre);
        if (categoria != null) {
            categoria.setNombre(nombre);
            categoria.setDescripcion(descripcion);
            notifyObservers(new EventoCategoria(TipoEvento.ACTUALIZAR,categoria));
        }

        return categoria;
    }

    @Override
    public boolean agregarCuenta(Cuenta cuenta) {
        if (cuenta == null || obtenerCuenta(cuenta.getNumeroCuenta()) != null) return false;
        cuenta.asignarId();
        listaCuentas.add(cuenta);
        Usuario usuario = cuenta.getUsuarioAsociado();
        IPresupuesto presupuesto = cuenta.getPresupuesto();
        if (usuario != null && !usuario.getListaCuentasAsociadas().contains(cuenta)) {
            usuario.getListaCuentasAsociadas().add(cuenta);
        }
        if (presupuesto != null) {
            presupuesto.setCuentaAsociada(cuenta);
        }
        notifyObservers(new EventoCuenta(TipoEvento.AGREGAR, cuenta));
        return true;
    }

    private Cuenta obtenerCuenta(String numeroCuenta) {
        for (Cuenta cuenta : listaCuentas) {
            if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
                return cuenta;
            }
        }
        return null;
    }

    @Override
    public boolean eliminarCuenta(int idCuenta, String numeroCuenta) {
        Cuenta cuenta = obtenerCuenta(numeroCuenta);
        if (cuenta.getIdCuenta() == idCuenta) {
            listaCuentas.remove(cuenta);
            cuenta.getUsuarioAsociado().getListaCuentasAsociadas().remove(cuenta);
            cuenta.getPresupuesto().setCuentaAsociada(null);
            notifyObservers(new EventoCuenta(TipoEvento.ELIMINAR,cuenta));

            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean actualizarCuenta(int id, String nombreBanco,
                                    String numeroCuenta,
                                    TipoCuenta tipoCuenta,
                                    IPresupuesto presupuesto) {
        Cuenta cuenta = obtenerCuenta(numeroCuenta);
        if (cuenta != null) {
            cuenta.setNombreBanco(nombreBanco);
            cuenta.setNumeroCuenta(numeroCuenta);
            cuenta.setTipoCuenta(tipoCuenta);
            cuenta.setPresupuesto(presupuesto);
            List<Cuenta> cuentasUsuario = cuenta.getUsuarioAsociado().getListaCuentasAsociadas();
            if (!cuentasUsuario.contains(cuenta)) {
                cuentasUsuario.add(cuenta);
            }
            notifyObservers(new EventoCuenta(TipoEvento.ACTUALIZAR,cuenta));

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean agregarPresupuesto(IPresupuesto presupuesto) {
        if (presupuesto == null || obtenerPresupuesto(presupuesto.getNombrePresupuesto()) != null) return false;
        presupuesto.asignarId();
        listaPresupuestos.add(presupuesto);
        Categoria categoria = presupuesto.getCategoriaAsociada();
        if (categoria != null) {
            categoria.getListaPresupuestos().add(presupuesto);
        }
        Usuario usuario = Sesion.getUsuarioActual();
        if (usuario != null) {
            usuario.getListaPresupuestos().add(presupuesto);
        }
        notifyObservers(new EventoPresupuesto(TipoEvento.AGREGAR, presupuesto));
        return true;
    }

    private IPresupuesto obtenerPresupuesto(String nombrePresupuesto) {
        IPresupuesto presupuesto = null;
        for (IPresupuesto presupuesto1 : listaPresupuestos) {
            if (presupuesto1.getNombrePresupuesto().equalsIgnoreCase(nombrePresupuesto)) {
                presupuesto = presupuesto1;
                break;
            }
        }

        return presupuesto;
    }

    @Override
    public boolean eliminarPresupuesto(String nombrePresupuesto) {
        IPresupuesto presupuesto = obtenerPresupuesto(nombrePresupuesto);
        if (presupuesto.getNombrePresupuesto().equalsIgnoreCase(nombrePresupuesto)) {
            listaPresupuestos.remove(presupuesto);
            notifyObservers(new EventoPresupuesto(TipoEvento.ELIMINAR,presupuesto));

            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean actualizarPresupuesto(String nombre,
                                         Double montoAsignado,
                                         Categoria categoria) {
        IPresupuesto presupuesto = obtenerPresupuesto(nombre);
        if (presupuesto != null) {
            presupuesto.setNombrePresupuesto(nombre);
            presupuesto.setMontoAsignado(montoAsignado);
            presupuesto.setCategoriaAsociada(categoria);
            notifyObservers(new EventoPresupuesto(TipoEvento.ACTUALIZAR,presupuesto));

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
            notifyObservers(new EventoUsuario(TipoEvento.AGREGAR,usuario));

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
        if (usuario.getIdUsuario().equalsIgnoreCase(id)) {
            listaUsuarios.remove(usuario);
            notifyObservers(new EventoUsuario(TipoEvento.ELIMINAR,usuario));

            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean actualizarUsuario(String nombre,
                                     String cedula,
                                     String correo,
                                     String telefono,
                                     String direccion,
                                     String clave) {
        Usuario usuario = obtenerUsuario(cedula);
        if (usuario != null) {
            usuario.setIdUsuario(cedula);
            usuario.setNombre(nombre);
            usuario.setNumeroTelefono(telefono);
            usuario.setCorreo(correo);
            usuario.setDireccion(direccion);
            usuario.setClave(clave);
            notifyObservers(new EventoUsuario(TipoEvento.ACTUALIZAR,usuario));

            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean actualizarPerfilUsuario(String cedula,
                                           String nombre,
                                           String correo,
                                           String numeroTelefono,
                                           String direccion,
                                           String clave) {
        Usuario usuario = Sesion.getUsuarioActual();
        if (usuario != null) {
            usuario.setIdUsuario(cedula);
            usuario.setNombre(nombre);
            usuario.setCorreo(correo);
            usuario.setNumeroTelefono(numeroTelefono);
            usuario.setDireccion(direccion);
            usuario.setClave(clave);

            return true;
        }
        return false;
    }

    @Override
    public boolean agregarTransaccion(Transaccion transaccion) {
        if (transaccion == null) return false;

        transaccion.asignarId();

        listatransacciones.add(transaccion);
        transaccion.getCuentaOrigen().getListaTransacciones().add(transaccion);

        if (transaccion.getCuentaDestino() != null) {
            transaccion.getCuentaDestino().getListaTransacciones().add(transaccion);
        }

        switch (transaccion.getTipoTransaccion()) {
            case DEPOSITO -> {
                IPresupuesto presupuesto = transaccion.getCuentaOrigen().getPresupuesto();
                if (presupuesto != null) {
                    presupuesto.setMontoAsignado(presupuesto.getMontoAsignado() + transaccion.getMonto());
                }
            }
            case RETIRO -> {
                IPresupuesto presupuesto = transaccion.getCuentaOrigen().getPresupuesto();
                if (presupuesto != null && presupuesto.getMontoAsignado() >= transaccion.getMonto()) {
                    presupuesto.setMontoAsignado(presupuesto.getMontoAsignado() - transaccion.getMonto());
                    presupuesto.setMontoGastado(presupuesto.getMontoGastado() + transaccion.getMonto());
                }
            }
            case TRANSFERENCIA -> {
                IPresupuesto origen = transaccion.getCuentaOrigen().getPresupuesto();
                IPresupuesto destino = transaccion.getCuentaDestino().getPresupuesto();
                if (origen != null && destino != null && origen.getMontoAsignado() >= transaccion.getMonto()) {
                    origen.setMontoAsignado(origen.getMontoAsignado() - transaccion.getMonto());
                    origen.setMontoGastado(origen.getMontoGastado() + transaccion.getMonto());
                    destino.setMontoAsignado(destino.getMontoAsignado() + transaccion.getMonto());
                }
            }
        }

        notifyObservers(new EventoTransaccion(TipoEvento.AGREGAR, transaccion));
        return true;
    }

    @Override
    public List<Transaccion> obtenerTransaccionesDelUsuario() {
        List<Transaccion> transaccionesUsuario = new ArrayList<>();
        for (Cuenta cuenta : Sesion.getUsuarioActual().getListaCuentasAsociadas()) {
            transaccionesUsuario.addAll(cuenta.getListaTransacciones());
        }
        return transaccionesUsuario;
    }
}
