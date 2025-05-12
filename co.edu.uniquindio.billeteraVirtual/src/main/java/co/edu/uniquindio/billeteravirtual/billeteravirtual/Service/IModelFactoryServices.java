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

    boolean eliminarCuenta(int id);

    boolean actualizarCuenta(int idCuenta, String nombreBanco, String numeroCuenta, TipoCuenta tipoCuenta);

    boolean agregarCategoria(String nombre, String descripcion);

    List<CategoriaDto> obtenerCategorias();

    CategoriaDto agregarCategoriaDto(String nombre, String descripcion);

    boolean eliminarCategoria(int idCategoria);

    boolean actualizarCategoria(int id, String nombre, String descripcion);

    CategoriaDto actualizarCategoriaDto(int i, String nombre, String descripcion);

    boolean agregarUsuario(Usuario usuario);

    boolean agregarPresupuesto(Presupuesto presupuesto);

    List<Presupuesto> obtenerPresupuestos();

    List<Categoria> obtenerPresupuestoCategorias();

    List<Cuenta> obtenerPresupuestoCuenta();

    boolean eliminarPresupuesto(int idPresupuesto);

    boolean actualizarPresupuesto(int id, String nombrePresupuesto, Double montoAsignado, Cuenta cuenta, Categoria categoria);

    boolean deposito(int idCuenta, double monto);

    boolean transferencia(int idCuentaOrigen, int idCuentaDestino, double monto);

    boolean retiro(int idCuenta, double monto);

    List<Transaccion> obtenerTransacciones();

    boolean agregarTransaccion(Transaccion transaccion);

    boolean actualizarPerfilUsuario(String nombre, String correo, String numeroTelefono);

    List<Cuenta> obtenerCuentasAdmin();

    List<Transaccion> obtenerTransaccionesAdmin();
}
