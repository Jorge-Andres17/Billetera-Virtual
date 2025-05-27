package co.edu.uniquindio.billeteravirtual.billeteravirtual.Proxy;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Login.Autenticador;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Login.Sesion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.BilleteraVirtual;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Service.IAutenticar;
import javafx.scene.control.Alert;

import static co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.BilleteraVirtualConstantes.*;

public class ProxyAutenticador implements IAutenticar {

    private final Autenticador autenticador;

    public ProxyAutenticador(BilleteraVirtual billeteraVirtual) {
        this.autenticador = new Autenticador(billeteraVirtual);
    }

    @Override
    public boolean autenticarUsuario(String correo, String clave) {
        autenticador.autenticarUsuario(correo, clave);
        if (Sesion.isUsuario() || Sesion.getAdministradorActual() != null) {
            mostrarMensaje(TITULO_USUARIO,HEADER,BODY_USUARIO, Alert.AlertType.WARNING);
            return false;
        }

        boolean resultado = autenticador.autenticarUsuario(correo, clave);
        if (resultado) {
            Sesion.setUsuarioActual(autenticador.getUsuarioAutenticado());
        }
        return resultado;
    }

    @Override
    public boolean autenticarAdmin(String correo, String clave) {
        autenticador.autenticarAdmin(correo, clave);
        if (Sesion.isUsuario() || Sesion.getAdministradorActual() != null) {
            mostrarMensaje(TITULO_ADMIN,HEADER,BODY_ADMIN, Alert.AlertType.WARNING);
            return false;
        }

        boolean resultado = autenticador.autenticarAdmin(correo, clave);
        if (resultado) {
            Sesion.setAdministradorActual(autenticador.getAdminAutenticado());
        }
        return resultado;
    }

    private void mostrarMensaje(String titulo,
                                String header,
                                String contenido,
                                Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}