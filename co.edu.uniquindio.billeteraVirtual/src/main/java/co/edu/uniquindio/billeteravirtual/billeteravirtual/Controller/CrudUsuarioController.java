package co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Factory.ModelFactory;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;
import java.util.List;

public class CrudUsuarioController {
    ModelFactory modelFactory;

    public CrudUsuarioController() {
        modelFactory = ModelFactory.getInstancia();
    }

    public boolean agregarUsuario(Usuario usuario) {
        return modelFactory.agregarUsuario(usuario);
    }

    public List<Usuario> obtenerUsuarios() {
        return modelFactory.obtenerUsuarios();
    }

    public boolean eliminarUsuario(String idUsuario) {
        return modelFactory.eliminarUsuario(idUsuario);
    }

    public boolean actualizarUsuario(String nombre,
                                     String cedula,
                                     String correo,
                                     String telefono,
                                     String direccion,
                                     String clave) {
        return modelFactory.actualizarUsuario(nombre,cedula,correo,telefono,direccion,clave);
    }

    public ModelFactory getModelFactory() {
        return modelFactory;
    }
}
