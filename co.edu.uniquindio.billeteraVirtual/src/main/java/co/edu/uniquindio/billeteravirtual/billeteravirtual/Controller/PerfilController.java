package co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Factory.ModelFactory;

public class PerfilController {
    ModelFactory modelFactory;

    public PerfilController(){
        modelFactory = ModelFactory.getInstancia();
    }

    public boolean actualizarPerfilUsuario(String nombre, String correo, String numeroTelefono) {
        return modelFactory.actualizarPerfilUsuario(nombre,correo,numeroTelefono);
    }
}
