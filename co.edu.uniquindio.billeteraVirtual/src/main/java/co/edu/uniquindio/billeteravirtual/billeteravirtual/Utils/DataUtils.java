package co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.*;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoCuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoTransaccion;

import java.time.LocalDate;

public class DataUtils {

    public static BilleteraVirtual inicializarDatos() {
        BilleteraVirtual billetera = new BilleteraVirtual();

        Administrador administrador = new Administrador();
        administrador.setIdAdministrador(1);
        administrador.setNombre("Administrador");
        administrador.setCorreo("admin");
        administrador.setClave("123");

        Usuario usuario = new Usuario();
        usuario.setNombre("Jorge");
        usuario.setIdUsuario("102923");
        usuario.setCorreo("usuario");
        usuario.setClave("23");

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Andres");
        usuario2.setCorreo("usuario");
        usuario2.setClave("20");

        Categoria categoria = Categoria.builder()
                .nombre("Comida")
                .descripcion("diaria")
                .build();

        Categoria categoria1 = Categoria.builder()
                .nombre("Transporte")
                .descripcion("semanal")
                .build();

        Presupuesto presupuesto = new Presupuesto("Comida",
                1000,
                0,
                null,
                categoria);

        Presupuesto presupuesto1 = new Presupuesto("Transporte",
                1000,
                0,
                null,
                categoria1);

        Cuenta cuenta = new Cuenta(
                "Daviplata",
                "0123",
                TipoCuenta.AHORRO,
                usuario,
                presupuesto
        );

        Cuenta cuenta1 = new Cuenta(
                "Bancolombia",
                "01234",
                TipoCuenta.CORRIENTE,
                usuario,
                presupuesto1
        );



        Transaccion transaccion = new Transaccion(
                LocalDate.of(2025, 5, 22),1000,
                "",
                cuenta,
                null,
                TipoTransaccion.DEPOSITO);

        usuario.getListaPresupuestos().add(presupuesto);
        usuario.getListaPresupuestos().add(presupuesto1);
        billetera.getListaAdministradores().add(administrador);
        billetera.getListaUsuarios().add(usuario);
        billetera.getListaUsuarios().add(usuario2);
        billetera.getListaCategorias().add(categoria);
        billetera.getListaCategorias().add(categoria1);
        billetera.agregarCuenta(cuenta);
        billetera.agregarCuenta(cuenta1);
        billetera.getListaPresupuestos().add(presupuesto);
        billetera.getListaPresupuestos().add(presupuesto1);
        billetera.getListatransacciones().add(transaccion);
        cuenta.getListaTransacciones().add(transaccion);

        return billetera;
    }
}
