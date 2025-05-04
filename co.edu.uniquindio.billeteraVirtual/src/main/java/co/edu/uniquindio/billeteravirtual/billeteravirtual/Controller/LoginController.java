package co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Factory.ModelFactory;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Login.Autenticador;

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

    public Autenticador getAutenticador() {
        return modelFactory.getAutenticador();
    }

    public Autenticador getAutenticadorAdmin() {
        return modelFactory.getAutenticador();
    }
}
