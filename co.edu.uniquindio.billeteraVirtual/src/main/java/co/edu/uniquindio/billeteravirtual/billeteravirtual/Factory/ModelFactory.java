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

    public boolean autenticarUsuario(String correo, String clave) {
        return autenticador.autenticarUsuario(correo, clave);
    }

    public boolean autenticarAdministrador(String correo, String clave) {
        return autenticador.autenticarAdmin(correo, clave);
    }

    public Autenticador getAutenticador() {
        return autenticador;
    }

    public boolean agregarCuenta(Cuenta cuenta) {
        return billeteraVirtual.agregarCuenta(cuenta);
    }

    public List<Cuenta> obtenerCuentas() {
        return Sesion.getUsuarioActual().getListaCuentasAsociadas();
    }

    public boolean eliminarCuenta(int id) {
        return billeteraVirtual.eliminarCuenta(id);
    }

    public boolean actualizarCuenta(int idCuenta, String nombreBanco, String numeroCuenta, TipoCuenta tipoCuenta) {
        return billeteraVirtual.actualizarCuenta(idCuenta, nombreBanco, numeroCuenta, tipoCuenta);
    }

    public boolean agregarCategoria(String nombre, String descripcion) {
        return billeteraVirtual.agregarCategoria(mapper.categoriaDtoToCategoria(nombre, descripcion));
    }

    public List<CategoriaDto> obtenerCategorias() {
        return mapper.getCategoriaDto(billeteraVirtual.getListaCategorias());
    }

    public CategoriaDto agregarCategoriaDto(String nombre, String descripcion) {
        return mapper.categoriaToCategoriaDto(billeteraVirtual.agregarCategoria(nombre, descripcion));
    }

    public boolean eliminarCategoria(int idCategoria) {
        return billeteraVirtual.eliminarCategoria(idCategoria);
    }

    public boolean actualizarCategoria(int id, String nombre, String descripcion) {
        return billeteraVirtual.actualizarCategoria(id, nombre, descripcion);
    }

    public CategoriaDto actualizarCategoriaDto(int i, String nombre, String descripcion) {
        return mapper.categoriaToCategoriaDto(billeteraVirtual.actualizarCategoriaDto(i, nombre, descripcion));
    }

    public boolean agregarUsuario(Usuario usuario) {
        return billeteraVirtual.agregarUsuario(usuario);
    }

    public boolean agregarPresupuesto(Presupuesto presupuesto) {
        return billeteraVirtual.agregarPresupuesto(presupuesto);
    }

    public List<Presupuesto> obtenerPresupuestos() {
        return billeteraVirtual.obtenerPresupuestosCuentas();
    }

    public List<Categoria> obtenerPresupuestoCategorias() {
        return billeteraVirtual.getListaCategorias();
    }

    public List<Cuenta> obtenerPresupuestoCuenta() {
        return Sesion.getUsuarioActual().getListaCuentasAsociadas();
    }

    public boolean eliminarPresupuesto(int idPresupuesto) {
        return billeteraVirtual.eliminarPresupuesto(idPresupuesto);
    }

    public boolean actualizarPresupuesto(int id,
                                         String nombrePresupuesto,
                                         Double montoAsignado,
                                         Cuenta cuenta,
                                         Categoria categoria) {
        return billeteraVirtual.actualizarPresupuesto(id, nombrePresupuesto, montoAsignado, cuenta, categoria);
    }

    public boolean deposito(int idCuenta, double monto) {
        return billeteraVirtual.depositar(idCuenta, monto);
    }

    public boolean transferencia(int idCuentaOrigen, int idCuentaDestino,double monto) {
        return billeteraVirtual.transferir(idCuentaOrigen,idCuentaDestino,monto);
    }

    public boolean retiro(int idCuenta, double monto) {
        return billeteraVirtual.retirar(idCuenta,monto);
    }

    public List<Transaccion> obtenerTransacciones() {
        return billeteraVirtual.obtenerTransaccionesDelUsuario();
    }

    public boolean agregarTransaccion(Transaccion transaccion) {
        return billeteraVirtual.agregarTransaccion(transaccion);
    }

    public boolean actualizarPerfilUsuario(String nombre, String correo, String numeroTelefono) {
        return billeteraVirtual.actualizarPerfilUsuario(nombre,correo,numeroTelefono);
    }

    public List<Cuenta> obtenerCuentasAdmin() {
        return billeteraVirtual.getListaCuentas();
    }

    public List<Transaccion> obtenerTransaccionesAdmin() {
        return billeteraVirtual.getListatransacciones();
    }
}

