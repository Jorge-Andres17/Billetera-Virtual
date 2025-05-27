package co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.BilleteraVirtualApplication;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller.LoginController;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Login.Sesion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Administrador;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.BilleteraVirtualConstantes.*;

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
            Usuario usuario = Sesion.getUsuarioActual();
            Sesion.setUsuarioActual(usuario);
            cerrarVentanaActual();
            BilleteraVirtualApplication.mostrarVentanaUsuario();

        } else if (loginController.autenticarAdminnistrador(correo, clave)) {
            Administrador admin = Sesion.getAdministradorActual();
            Sesion.setAdministradorActual(admin);
            cerrarVentanaActual();
            BilleteraVirtualApplication.mostrarVentanaAdministrador();

        } else {
            mostrarMensaje(
                    ERROR_AUTENTICACION_TITULO,
                    HEADER,
                    ERROR_AUTENTICACION_MENSAJE,
                    Alert.AlertType.ERROR
            );
        }
    }

    private void cerrarVentanaActual() {
        Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
        stage.close();
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
