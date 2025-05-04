package co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Administrador;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.BilleteraVirtual;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Categoria;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;

public class DataUtils {

    public static BilleteraVirtual inicializarDatos() {
        BilleteraVirtual billetera = new BilleteraVirtual();

        Administrador administrador = new Administrador();
        administrador.setIdAdministrador(1);
        administrador.setNombre("Administrador");
        administrador.setCorreo("admin");
        administrador.setClave("123");

        Usuario usuario = new Usuario();
        usuario.setCorreo("usuario");
        usuario.setClave("23");

        Usuario usuario2 = new Usuario();
        usuario2.setCorreo("usuario");
        usuario2.setClave("20");

        Categoria categoria = Categoria.builder()
                .nombre("Maicol Perez")
                .descripcion("MaidolPerez@gmail.con")
                .build();

        billetera.getListaAdministradores().add(administrador);
        billetera.getListaUsuarios().add(usuario);
        billetera.getListaUsuarios().add(usuario2);

        return billetera;
    }
}
