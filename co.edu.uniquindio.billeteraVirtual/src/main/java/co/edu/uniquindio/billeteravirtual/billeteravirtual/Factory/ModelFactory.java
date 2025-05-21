package co.edu.uniquindio.billeteravirtual.billeteravirtual.Factory;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Login.Autenticador;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Login.Sesion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Mapping.dto.CategoriaDto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Mapping.mappers.BilleteraVirtualMappingImpl;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.*;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoCuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Service.IBilleteraVirtualMapping;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Service.IModelFactoryServices;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.DataUtils;

import java.util.List;

public class ModelFactory implements IModelFactoryServices {
    private static ModelFactory modelFactory;
    private BilleteraVirtual billeteraVirtual;
    private Autenticador autenticador;
    private IBilleteraVirtualMapping mapper;

    public static ModelFactory getInstancia() {
        if (modelFactory == null) {
            modelFactory = new ModelFactory();
        }
        return modelFactory;
    }

    private ModelFactory() {
        mapper = new BilleteraVirtualMappingImpl();
        billeteraVirtual = DataUtils.inicializarDatos();
        autenticador = new Autenticador(billeteraVirtual);
    }

    @Override
    public boolean autenticarUsuario(String correo, String clave) {
        return autenticador.autenticarUsuario(correo, clave);
    }

    @Override
    public boolean autenticarAdministrador(String correo, String clave) {
        return autenticador.autenticarAdmin(correo, clave);
    }

    @Override
    public Autenticador getAutenticador() {
        return autenticador;
    }

    @Override
    public boolean agregarCuenta(Cuenta cuenta) {
        return billeteraVirtual.agregarCuenta(cuenta);
    }

    @Override
    public List<Cuenta> obtenerCuentas() {
        return Sesion.getUsuarioActual().getListaCuentasAsociadas();
    }

    @Override
    public boolean eliminarCuenta(int id,String numeroCuenta) {
        return billeteraVirtual.eliminarCuenta(id, numeroCuenta);
    }

    @Override
    public boolean actualizarCuenta(int idCuenta, String nombreBanco, String numeroCuenta, TipoCuenta tipoCuenta,
                                    Presupuesto presupuesto) {
        return billeteraVirtual.actualizarCuenta(idCuenta, nombreBanco, numeroCuenta, tipoCuenta,presupuesto);
    }

    @Override
    public List<CategoriaDto> obtenerCategorias() {
        return mapper.getCategoriaDto(billeteraVirtual.getListaCategorias());
    }

    @Override
    public CategoriaDto agregarCategoriaDto(String nombre, String descripcion) {
        return mapper.categoriaToCategoriaDto(billeteraVirtual.agregarCategoria(nombre, descripcion));
    }

    @Override
    public boolean eliminarCategoria(String nombre) {
        return billeteraVirtual.eliminarCategoria(nombre);
    }

    @Override
    public boolean actualizarCategoria(String nombre, String descripcion) {
        return billeteraVirtual.actualizarCategoria( nombre, descripcion);
    }

    @Override
    public CategoriaDto actualizarCategoriaDto(String nombre, String descripcion) {
        return mapper.categoriaToCategoriaDto(billeteraVirtual.actualizarCategoriaDto( nombre, descripcion));
    }

    @Override
    public boolean agregarUsuario(Usuario usuario) {
        return billeteraVirtual.agregarUsuario(usuario);
    }

    @Override
    public boolean agregarPresupuesto(Presupuesto presupuesto) {
        return billeteraVirtual.agregarPresupuesto(presupuesto);
    }

    @Override
    public List<Presupuesto> obtenerPresupuestos() {
        return Sesion.getUsuarioActual().getListaPresupuestos();
    }

    @Override
    public List<Categoria> obtenerPresupuestoCategorias() {
        return billeteraVirtual.getListaCategorias();
    }

    @Override
    public List<Cuenta> obtenerPresupuestoCuenta() {
        return Sesion.getUsuarioActual().getListaCuentasAsociadas();
    }

    @Override
    public boolean eliminarPresupuesto(String nombre) {
        return billeteraVirtual.eliminarPresupuesto(nombre);
    }

    @Override
    public boolean actualizarPresupuesto(String nombrePresupuesto,
                                         Double montoAsignado,
                                         Categoria categoria) {
        return billeteraVirtual.actualizarPresupuesto(nombrePresupuesto, montoAsignado, categoria);
    }

    @Override
    public List<Transaccion> obtenerTransacciones() {
        return billeteraVirtual.obtenerTransaccionesDelUsuario();
    }

    @Override
    public boolean agregarTransaccion(Transaccion transaccion) {
        return billeteraVirtual.agregarTransaccion(transaccion);
    }

    @Override
    public boolean actualizarPerfilUsuario(String nombre, String correo, String numeroTelefono) {
        return billeteraVirtual.actualizarPerfilUsuario(nombre,correo,numeroTelefono);
    }

    @Override
    public List<Cuenta> obtenerCuentasAdmin() {
        return billeteraVirtual.getListaCuentas();
    }

    @Override
    public List<Transaccion> obtenerTransaccionesAdmin() {
        return billeteraVirtual.getListatransacciones();
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        return billeteraVirtual.getListaUsuarios();
    }

    @Override
    public boolean eliminarUsuario(String idUsuario) {
        return billeteraVirtual.eliminarUsuario(idUsuario);
    }

    @Override
    public boolean actualizarUsuario(String nombre, String cedula, String correo, String telefono, String direccion, String clave) {
        return billeteraVirtual.actualizarUsuario(nombre,cedula,correo,telefono,direccion,clave);
    }

    @Override
    public List<Transaccion> obtenerAdminTransacciones() {
        return billeteraVirtual.getListatransacciones();
    }

    @Override
    public List<Presupuesto> obtenerPresupuestosAdmin() {
        return billeteraVirtual.getListaPresupuestos();
    }

    public BilleteraVirtual getBilleteraVirtual() {
        return billeteraVirtual;
    }
}

