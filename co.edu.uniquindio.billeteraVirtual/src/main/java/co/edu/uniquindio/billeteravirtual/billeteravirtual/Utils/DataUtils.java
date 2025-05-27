package co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.*;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoCuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoTransaccion;

import java.time.LocalDate;

public class DataUtils {

    public static BilleteraVirtual inicializarDatos() {
        BilleteraVirtual billetera = new BilleteraVirtual();
        billetera.setNombre("Billetera Virtual");

        Administrador administrador = new Administrador();
        administrador.setIdAdministrador(1);
        administrador.setNombre("Administrador");
        administrador.setCorreo("administrador@gmail.com");
        administrador.setClave("1529");

        Usuario usuario = new Usuario();
        usuario.setNombre("Jorge Andres Ibarra Guzman");
        usuario.setIdUsuario("102923");
        usuario.setNumeroTelefono("3229946154");
        usuario.setDireccion("Cra 12 #25-10");
        usuario.setCorreo("jorge@gmail.com");
        usuario.setClave("23");

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Andres Felipe Ramirez");
        usuario2.setIdUsuario("102734");
        usuario2.setNumeroTelefono("3145256948");
        usuario2.setDireccion("Cra 6 #12-45");
        usuario2.setCorreo("andres@gmail.com");
        usuario2.setClave("205");

        Categoria categoria = Categoria.builder()
                .nombre("Comida")
                .descripcion("diaria")
                .build();

        Categoria categoria1 = Categoria.builder()
                .nombre("Transporte")
                .descripcion("semanal")
                .build();

        Categoria categoria3 = Categoria.builder()
                .nombre("Ropa")
                .descripcion("Mensual")
                .build();

        Categoria categoria4 = Categoria.builder()
                .nombre("Universidad")
                .descripcion("diaria")
                .build();

        Presupuesto presupuesto = new Presupuesto("Comida",
                10000,
                0,
                null,
                categoria);

        Presupuesto presupuesto1 = new Presupuesto("Transporte",
                20000,
                0,
                null,
                categoria1);

        Presupuesto presupuesto2 = new Presupuesto("Ropa presupuesto",
                15000,
                0,
                null,
                categoria3);

        Presupuesto presupuesto3 = new Presupuesto("Universidad presupuesto",
                15000,
                0,
                null,
                categoria4);

        Cuenta cuenta = new Cuenta(
                "Daviplata",
                "123456",
                TipoCuenta.AHORRO,
                usuario,
                presupuesto
        );

        Cuenta cuenta1 = new Cuenta(
                "Bancolombia",
                "657438",
                TipoCuenta.CORRIENTE,
                usuario,
                presupuesto1
        );

        Cuenta cuenta2 = new Cuenta(
                "Banco Bogota",
                "879529",
                TipoCuenta.AHORRO,
                usuario2,
                presupuesto2
        );

        Cuenta cuenta3 = new Cuenta(
                "Banco AVV",
                "468391",
                TipoCuenta.CORRIENTE,
                usuario2,
                presupuesto3
        );

        Transaccion transaccion = new Transaccion(
                LocalDate.of(2025, 4, 22),
                10000,
                "deposito",
                cuenta,
                null,
                TipoTransaccion.DEPOSITO);

        Transaccion transaccion1 = new Transaccion(
                LocalDate.of(2025,5, 8),
                5000,
                "retiro",
                cuenta1,
                null,
                TipoTransaccion.RETIRO);

        Transaccion transaccion3 = new Transaccion(
                LocalDate.of(2025, 6, 22),
                50000,
                "",
                cuenta2,
                null,
                TipoTransaccion.DEPOSITO);

        Transaccion transaccion4 = new Transaccion(
                LocalDate.of(2025,4, 17),
                10000,
                "retiro",
                cuenta2,
                null,
                TipoTransaccion.RETIRO);


        billetera.getListaAdministradores().add(administrador);
        billetera.getListaUsuarios().add(usuario);
        billetera.getListaUsuarios().add(usuario2);

        billetera.getListaCategorias().add(categoria);
        billetera.getListaCategorias().add(categoria1);
        billetera.getListaCategorias().add(categoria3);
        billetera.getListaCategorias().add(categoria4);

        billetera.agregarCuenta(cuenta);
        billetera.agregarCuenta(cuenta1);
        billetera.agregarCuenta(cuenta2);
        billetera.agregarCuenta(cuenta3);

        billetera.agregarPresupuesto(presupuesto);
        billetera.agregarPresupuesto(presupuesto1);
        usuario.getListaPresupuestos().add(presupuesto);
        usuario.getListaPresupuestos().add(presupuesto1);
        billetera.agregarPresupuesto(presupuesto2);
        billetera.agregarPresupuesto(presupuesto3);
        usuario2.getListaPresupuestos().add(presupuesto2);
        usuario2.getListaPresupuestos().add(presupuesto3);

        billetera.agregarTransaccion(transaccion);
        billetera.agregarTransaccion(transaccion1);
        billetera.agregarTransaccion(transaccion3);
        billetera.agregarTransaccion(transaccion4);


        return billetera;
    }
}
