package co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Factory.ModelFactory;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Login.Autenticador;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Service.IAutenticar;

public class LoginController {
    ModelFactory modelFactory;

    public LoginController() {
        modelFactory = ModelFactory.getInstancia();
    }
    public boolean autenticarUsuario(String correo, String clave) {
        return  modelFactory.autenticarUsuario(correo,clave);
    }

    public boolean autenticarAdminnistrador(String correo, String clave) {
        return modelFactory.autenticarAdministrador(correo,clave);
    }
}
