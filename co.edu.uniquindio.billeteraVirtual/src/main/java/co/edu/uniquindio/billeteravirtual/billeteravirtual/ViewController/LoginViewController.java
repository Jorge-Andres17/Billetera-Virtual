package co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.BilleteraVirtualApplication;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller.LoginController;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Login.Sesion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Administrador;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginViewController {
    LoginController loginController;

    @FXML
    private TextField TxtContraseña;

    @FXML
    private TextField TxtCorreo;

    @FXML
    private Button btnIniciarSesion;

    @FXML
    void onIniciarSesion(ActionEvent event) {
        iniciarSesion();
    }

    @FXML
    void initialize() {
        loginController = new LoginController();
    }

    private void iniciarSesion() {
        String correo = TxtCorreo.getText();
        String clave = TxtContraseña.getText();

        if (loginController.autenticarUsuario(correo, clave)) {
            Usuario usuario = loginController.getAutenticador().getUsuarioAutenticado();
            Sesion.setUsuarioActual(usuario);
            cerrarVentanaActual();
            BilleteraVirtualApplication.mostrarVentanaUsuario();
        } else if (loginController.autenticarAdminnistrador(correo, clave)) {
            Administrador admin = loginController.getAutenticadorAdmin().getAdminAutenticado();
            Sesion.setAdministradorActual(admin);
            cerrarVentanaActual();
            BilleteraVirtualApplication.mostrarVentanaAdministrador();
        }
    }

    private void cerrarVentanaActual() {
        Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
        stage.close();
    }
}
