package co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class CrudUsuarioViewController {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    @FXML
    private TableView<?> tableUsuario;

    @FXML
    private TableColumn<?, ?> tcCedula;

    @FXML
    private TableColumn<?, ?> tcCorreo;

    @FXML
    private TableColumn<?, ?> tcDireccion;

    @FXML
    private TableColumn<?, ?> tcNombre;

    @FXML
    private TableColumn<?, ?> tcTelefono;

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
    void onActualizar(ActionEvent event) {

    }

    @FXML
    void onAgregar(ActionEvent event) {

    }

    @FXML
    void onCerrarSesion(ActionEvent event) {

    }

    @FXML
    void onEliminar(ActionEvent event) {

    }

    @FXML
    void onNuevo(ActionEvent event) {

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

    private boolean mostrarMensajeConfirmacion(String mensaje){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();
        if(action.get() == ButtonType.OK){
            return true;
        }else {
            return false;
        }
    }
}
