package co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.BilleteraVirtualApplication;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller.PerfilController;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Login.Sesion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import static co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.BilleteraVirtualConstantes.*;

public class PerfilViewController {
    PerfilController perfilController;

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnEditar;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtNumeroTelefono;

    @FXML
    void initialize() {
        perfilController = new PerfilController();
        txtNombre.setDisable(true);
        txtCorreo.setDisable(true);
        txtNumeroTelefono.setDisable(true);
        btnAceptar.setDisable(true);
        mostarInfo();
    }

    private void mostarInfo() {
        Usuario usuario = Sesion.getUsuarioActual();
        if (usuario != null) {
            txtNombre.setText(usuario.getNombre());
            txtCorreo.setText(usuario.getCorreo());
            txtNumeroTelefono.setText(usuario.getNumeroTelefono());
        }
    }

    @FXML
    void onAceptar(ActionEvent event) {
        aceptarActualizacion();
    }

    @FXML
    void onEditar(ActionEvent event) {
        editarInfo();
    }

    @FXML
    void onCerrarSesion(ActionEvent event) throws IOException {
        cerrarSesion();
    }

    private void aceptarActualizacion() {
        if (datosValidos(txtNombre.getText(),
                txtCorreo.getText(),
                txtNumeroTelefono.getText())) {
            if (perfilController.actualizarPerfilUsuario(txtNombre.getText(),
                    txtCorreo.getText(),
                    txtNumeroTelefono.getText())) {
                mostrarInfoActualizada();
                txtNombre.setDisable(true);
                txtCorreo.setDisable(true);
                txtNumeroTelefono.setDisable(true);
                btnAceptar.setDisable(true);
                mostrarMensaje(TITULO_ACTUALIZACION_EXITOSA,
                        HEADER_ACTUALIZACION_EXITOSA,
                        BODY_ACTUALIZACION_EXITOSA,
                        Alert.AlertType.INFORMATION);
            } else {
                mostrarMensaje(TITULO_ACTUALIZACION_FALLIDA,
                        HEADER_ACTUALIZACION_FALLIDA,
                        BODY_ACTUALIZACION_FALLIDA,
                        Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje(TITULO_INCOMPLETO,
                    HEADER_INCOMPLETO,
                    BODY_INCOMPLETO,
                    Alert.AlertType.WARNING);
        }
    }

    private void mostrarInfoActualizada() {
        Usuario usuarioActualizado = Sesion.getUsuarioActual();
        if (usuarioActualizado != null) {
            txtNombre.setText(usuarioActualizado.getNombre());
            txtCorreo.setText(usuarioActualizado.getCorreo());
            txtNumeroTelefono.setText(usuarioActualizado.getNumeroTelefono());
        }
    }

    private boolean datosValidos(String nombre, String correo, String numeroTelefono) {
        if (nombre.isEmpty() || correo.isEmpty() || numeroTelefono.isEmpty()) {
            return false;
        }else {
            return true;
        }
    }

    private void editarInfo() {
        txtNombre.setDisable(false);
        txtCorreo.setDisable(false);
        txtNumeroTelefono.setDisable(false);
        btnAceptar.setDisable(false);
    }

    private void cerrarSesion() throws IOException {
        Sesion.cerrarSesionUsuario();
        Stage stageActual = (Stage) btnCerrarSesion.getScene().getWindow();
        stageActual.close();
        BilleteraVirtualApplication.mostrarVentanaLogin();
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
