package co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Factory.ModelFactory;

public class PerfilController {
    ModelFactory modelFactory;

    public PerfilController(){
        modelFactory = ModelFactory.getInstancia();
    }

    public boolean actualizarPerfilUsuario(String cedula,
                                           String nombre,
                                           String correo,
                                           String numeroTelefono,
                                           String direccion,
                                           String clave) {
        return modelFactory.actualizarPerfilUsuario(cedula,nombre,correo,numeroTelefono,direccion,clave);
    }
}
