package co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller.RegistroController;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import static co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.BilleteraVirtualConstantes.*;

public class RegistroViewController {
    RegistroController registroController;

    @FXML
    private Button btnRegistrarse;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtClave;

    @FXML
    void initialize(){
        registroController = new RegistroController();
    }

    @FXML
    void onRegistrarse(ActionEvent event) {
        registrar();
    }

    private void registrar() {
        Usuario usuario = crearUsuario();
        if (datosValidos(usuario)) {
            if (registroController.agregarUsuario(usuario)) {
                limpiarCampos();
                mostrarMensaje(TITULO_REGISTRO_EXITOSO,
                        HEADER,
                        BODY_REGISTRO_EXITOSO,
                        Alert.AlertType.INFORMATION);
            } else {
                mostrarMensaje(TITULO_REGISTRO_FALLIDO,
                        HEADER,
                        BODY_REGISTRO_FALLIDO,
                        Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje(TITULO_INCOMPLETO,
                    HEADER_INCOMPLETO,
                    BODY_INCOMPLETO,
                    Alert.AlertType.WARNING);
        }
    }

    private boolean datosValidos(Usuario usuario) {
        if (txtCedula.getText().isEmpty() &&
                txtNombre.getText().isEmpty() &&
                txtCorreo.getText().isEmpty() &&
                txtDireccion.getText().isEmpty() &&
                txtTelefono.getText().isEmpty() &&
                txtClave.getText().isEmpty()) {

            return false;
        }else {
            return true;
        }
    }

    private Usuario crearUsuario() {
        return new Usuario(txtCedula.getText(),
                txtNombre.getText(),
                txtCorreo.getText(),
                txtDireccion.getText(),
                txtTelefono.getText(),
                txtClave.getText());
    }

    private void limpiarCampos(){
        txtCedula.clear();
        txtNombre.clear();
        txtCorreo.clear();
        txtDireccion.clear();
        txtTelefono.clear();
        txtClave.clear();
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
