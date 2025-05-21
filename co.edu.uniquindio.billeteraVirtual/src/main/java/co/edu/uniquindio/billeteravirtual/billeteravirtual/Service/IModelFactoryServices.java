package co.edu.uniquindio.billeteravirtual.billeteravirtual.Service;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Login.Autenticador;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Mapping.dto.CategoriaDto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.*;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoCuenta;

import java.util.List;

public interface IModelFactoryServices {

    boolean autenticarUsuario(String correo, String clave);

    boolean autenticarAdministrador(String correo, String clave);

    Autenticador getAutenticador();

    boolean agregarCuenta(Cuenta cuenta);

    List<Cuenta> obtenerCuentas();

    boolean eliminarCuenta(int id,String numeroCuenta);

    boolean actualizarCuenta(int idCuenta, String nombreBanco, String numeroCuenta,
                             TipoCuenta tipoCuenta,Presupuesto presupuesto);

    List<CategoriaDto> obtenerCategorias();

    CategoriaDto agregarCategoriaDto(String nombre, String descripcion);

    boolean eliminarCategoria(String nombre);

    boolean actualizarCategoria( String nombre, String descripcion);

    CategoriaDto actualizarCategoriaDto( String nombre, String descripcion);

    boolean agregarUsuario(Usuario usuario);

    boolean agregarPresupuesto(Presupuesto presupuesto);

    List<Presupuesto> obtenerPresupuestos();

    List<Categoria> obtenerPresupuestoCategorias();

    List<Cuenta> obtenerPresupuestoCuenta();

    boolean eliminarPresupuesto(String nombre);

    boolean actualizarPresupuesto(String nombrePresupuesto, Double montoAsignado, Categoria categoria);

    List<Transaccion> obtenerTransacciones();

    boolean agregarTransaccion(Transaccion transaccion);

    boolean actualizarPerfilUsuario(String nombre, String correo, String numeroTelefono);

    List<Cuenta> obtenerCuentasAdmin();

    List<Transaccion> obtenerTransaccionesAdmin();

    List<Usuario> obtenerUsuarios();

    boolean eliminarUsuario(String idUsuario);

    boolean actualizarUsuario(String nombre,
                              String cedula,
                              String correo,
                              String telefono,
                              String direccion,
                              String clave);

    List<Transaccion> obtenerAdminTransacciones();

    List<Presupuesto> obtenerPresupuestosAdmin();
}
