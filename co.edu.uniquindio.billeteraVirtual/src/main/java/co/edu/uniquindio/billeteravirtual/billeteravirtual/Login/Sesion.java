package co.edu.uniquindio.billeteravirtual.billeteravirtual.Login;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Administrador;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;

public class Sesion {
    private static Usuario usuarioActual;
    private static Administrador administradorActual;

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static Administrador getAdministradorActual() {
        return administradorActual;
    }

    public static void setAdministradorActual(Administrador administrador) {
        administradorActual = administrador;
    }
}
