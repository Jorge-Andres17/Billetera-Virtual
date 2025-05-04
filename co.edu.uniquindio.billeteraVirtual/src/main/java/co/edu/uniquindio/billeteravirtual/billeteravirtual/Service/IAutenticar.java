package co.edu.uniquindio.billeteravirtual.billeteravirtual.Service;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Administrador;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;

public interface IAutenticar {
    boolean autenticarUsuario(String correo, String clave);
    boolean autenticarAdmin(String correo, String clave);
}
