package co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.BilleteraVirtualApplication;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller.CrudUsuarioController;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Login.Sesion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import static co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.BilleteraVirtualConstantes.*;

public class CrudUsuarioViewController {
    CrudUsuarioController crudUsuarioController;
    ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    Usuario selectedUsuario;

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
    private TableView<Usuario> tableUsuario;

    @FXML
    private TableColumn<Usuario, String> tcCedula;

    @FXML
    private TableColumn<Usuario, String> tcCorreo;

    @FXML
    private TableColumn<Usuario, String> tcDireccion;

    @FXML
    private TableColumn<Usuario, String> tcNombre;

    @FXML
    private TableColumn<Usuario, String> tcTelefono;

    @FXML
    private TableColumn<Usuario, String> tcClave;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtClave;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    void initialize() {
        crudUsuarioController = new CrudUsuarioController();
        initView();
    }

    @FXML
    void initView(){
        initDataBinding();
        obtenerUsuarios();
        tableUsuario.getItems().clear();
        tableUsuario.setItems(listaUsuarios);
        listenerSelection();
    }

    private void initDataBinding() {
        tcNombre.setCellValueFactory
                (cellData -> new SimpleStringProperty
                        (cellData.getValue().getNombre()));
        tcCedula.setCellValueFactory
                (cellData -> new SimpleStringProperty
                        (cellData.getValue().getIdUsuario()));
        tcCorreo.setCellValueFactory
                (cellData -> new SimpleStringProperty
                        (cellData.getValue().getCorreo()));
        tcTelefono.setCellValueFactory
                (cellData -> new SimpleStringProperty
                        (cellData.getValue().getNumeroTelefono()));
        tcDireccion.setCellValueFactory
                (cellData -> new SimpleStringProperty
                        (cellData.getValue().getDireccion()));
        tcClave.setCellValueFactory
                (cellData -> new SimpleStringProperty
                        (cellData.getValue().getClave()));
    }

    private void obtenerUsuarios() {
        listaUsuarios.addAll(crudUsuarioController.obtenerUsuarios());
    }

    private void listenerSelection() {
        tableUsuario.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedUsuario = newSelection;
            mostrarInformacionUsuario(selectedUsuario);
        });
    }

    private void mostrarInformacionUsuario(Usuario selectedUsuario) {
       if (selectedUsuario != null){
           txtCedula.setText(selectedUsuario.getIdUsuario());
           txtCorreo.setText(selectedUsuario.getCorreo());
           txtDireccion.setText(selectedUsuario.getDireccion());
           txtNombre.setText(selectedUsuario.getNombre());
           txtTelefono.setText(selectedUsuario.getNumeroTelefono());
           txtClave.setText(selectedUsuario.getClave());
       }
    }

    @FXML
    void onNuevo(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    void onAgregar(ActionEvent event) {
        agregarUsuario();
    }

    @FXML
    void onEliminar(ActionEvent event) {
        eliminarUsuario();
    }

    @FXML
    void onActualizar(ActionEvent event) {
        actualizar();
    }

    @FXML
    void onCerrarSesion(ActionEvent event) throws IOException {
        cerrarSesion();
    }

    private void agregarUsuario() {
        Usuario usuario = crearUsuario();
        if(datosValidos(usuario)){
            if (crudUsuarioController.agregarUsuario(usuario)){
                listaUsuarios.add(usuario);
                limpiarCampos();
                mostrarMensaje(TITULO_USUARIO_AGREGADO,
                        HEADER,
                        BODY_USUARIO_AGREGADO, Alert.AlertType.INFORMATION);
            }else {
                mostrarMensaje(TITULO_USUARIO_NO_AGREGADO,
                        HEADER,
                        BODY_USUARIO_NO_AGREGADO, Alert.AlertType.ERROR);
            }
        }else {
            mostrarMensaje(TITULO_INCOMPLETO,
                    HEADER_INCOMPLETO,
                    BODY_INCOMPLETO, Alert.AlertType.WARNING);
        }
    }

    private Usuario crearUsuario() {
        return new Usuario(txtCedula.getText(),
                txtNombre.getText(),
                txtCorreo.getText(),
                txtTelefono.getText(),
                txtDireccion.getText(),
                txtClave.getText());
    }

    private boolean datosValidos(Usuario usuario) {
        if (usuario.getIdUsuario() == null || usuario.getIdUsuario().equals("")
        ||  usuario.getNombre() == null || usuario.getNombre().equals("")
        || usuario.getCorreo() == null || usuario.getCorreo().equals("")
        ||  usuario.getDireccion() == null || usuario.getDireccion().equals("")
        || usuario.getClave() == null || usuario.getClave().equals("")
        || usuario.getNumeroTelefono()  == null || usuario.getNumeroTelefono().equals("")) {

            return false;
        }else {
            return true;
        }
    }

    private void eliminarUsuario() {
        Usuario usuario = tableUsuario.getSelectionModel().getSelectedItem();
        if (usuario != null) {
            if(mostrarMensajeConfirmacion(MENSAJE_ELIMINAR_USUARIO)) {
                if (crudUsuarioController.eliminarUsuario(usuario.getIdUsuario())) {
                    listaUsuarios.remove(usuario);
                    limpiarCampos();
                    mostrarMensaje(TITULO_USUARIO_ELIMINADO,
                            HEADER,
                            BODY_USUARIO_ELIMINADO, Alert.AlertType.INFORMATION);
                }else {
                    mostrarMensaje(TITULO_USUARIO_NO_ELIMINADO,
                            HEADER,
                            BODY_USUARIO_NO_ELIMINADO, Alert.AlertType.ERROR);
                }
            } else {
                mostrarMensaje(TITULO_ELIMINACION_CANCELADA,
                        HEADER,
                        BODY_ELIMINACION_CANCELADA,
                        Alert.AlertType.INFORMATION);
            }
        }else {
            mostrarMensaje(TITULO_NO_SELECCION,
                    HEADER_NO_SELECCION,
                    CONTENIDO_NO_SELECCION, Alert.AlertType.WARNING);
        }
    }

    private void actualizar() {
        Usuario usuario = tableUsuario.getSelectionModel().getSelectedItem();
        if (usuario != null) {
            if (datosValidos(usuario)) {
                if (crudUsuarioController.actualizarUsuario(txtNombre.getText(),
                        txtCedula.getText(),
                        txtCorreo.getText(),
                        txtTelefono.getText(),
                        txtDireccion.getText(),
                        txtClave.getText())){
                    tableUsuario.refresh();
                    limpiarCampos();
                    mostrarMensaje(TITULO_USUARIO_ACTUALIZADO,
                            HEADER,
                            BODY_USUARIO_ACTUALIZADO, Alert.AlertType.INFORMATION);
                }else {
                    mostrarMensaje(TITULO_USUARIO_NO_ACTUALIZADO,
                            HEADER,
                            BODY_USUARIO_NO_ACTUALIZADO, Alert.AlertType.ERROR);
                }
            }else {
                mostrarMensaje(TITULO_INCOMPLETO,
                        HEADER_INCOMPLETO,
                        BODY_INCOMPLETO, Alert.AlertType.WARNING);
            }
        }else {
            mostrarMensaje(TITULO_ACTUALIZACION_FALLIDA,
                    HEADER_ACTUALIZACION_FALLIDA,
                    BODY_ACTUALIZACION_FALLIDA, Alert.AlertType.ERROR);
        }
    }

    private void limpiarCampos() {
        txtCedula.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtClave.setText("");
        tableUsuario.getSelectionModel().clearSelection();
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

    private void cerrarSesion() throws IOException {
        Sesion.cerrarSesionAdministrador();
        Stage stageActual = (Stage) btnCerrarSesion.getScene().getWindow();
        stageActual.close();
        BilleteraVirtualApplication.mostrarVentanaLogin();
    }
}
