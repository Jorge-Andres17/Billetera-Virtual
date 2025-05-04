package co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller.CrudCuentaController;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Login.Sesion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoCuenta;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class CrudCuentaViewController {
    CrudCuentaController cuentaController;
    ObservableList<Cuenta> listaCuentas = FXCollections.observableArrayList();
    Cuenta selectedCuenta;

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
    private ComboBox<TipoCuenta> cbTipoCuenta;

    @FXML
    private TableView<Cuenta> tableCuenta;

    @FXML
    private TableColumn<Cuenta, String> tcIdCuenta;

    @FXML
    private TableColumn<Cuenta, String> tcNombreBanco;

    @FXML
    private TableColumn<Cuenta, String> tcNumeroCuenta;

    @FXML
    private TableColumn<Cuenta, String> tcPresupuesto;

    @FXML
    private TableColumn<Cuenta, String> tcTipoCuenta;

    @FXML
    private TextField txtNombreBanco;

    @FXML
    private TextField txtNumeroCuenta;

    @FXML
    void initialize() {
        cuentaController = new CrudCuentaController();
        cbTipoCuenta.setItems(FXCollections.observableArrayList(TipoCuenta.values()));
        cbTipoCuenta.setPromptText("Seleccione un Tipo Cuenta");
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerCuentas();
        tableCuenta.getItems().clear();
        tableCuenta.setItems(listaCuentas);
        listenerSelection();
    }

    private void listenerSelection() {
        tableCuenta.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedCuenta = newSelection;
            mostrarInformacionUsuario(selectedCuenta);
        });
    }

    private void initDataBinding() {
        tcIdCuenta.setCellValueFactory
                (cellData -> new SimpleStringProperty
                        (String.valueOf(cellData.getValue().getIdCuenta())));
        tcNombreBanco.setCellValueFactory
                (cellData -> new SimpleStringProperty
                        (cellData.getValue().getNombreBanco()));
        tcNumeroCuenta.setCellValueFactory
                (cellData -> new SimpleStringProperty(
                        cellData.getValue().getNumeroCuenta()));
        tcTipoCuenta.setCellValueFactory
                (cellData -> new SimpleStringProperty
                        (cellData.getValue().getTipoCuenta().toString()));
    }

    private void mostrarInformacionUsuario(Cuenta cuenta) {
        if(cuenta != null){
            txtNombreBanco.setText(cuenta.getNombreBanco());
            txtNumeroCuenta.setText(cuenta.getNumeroCuenta());
            cbTipoCuenta.setValue(cuenta.getTipoCuenta());
        }
    }

    private void obtenerCuentas() {
        listaCuentas.addAll(cuentaController.obtenerCuentas());
    }

    @FXML
    void onActualizar(ActionEvent event) {

    }

    @FXML
    void onAgregar(ActionEvent event) {
        agregarCuenta();
    }

    private void agregarCuenta() {
        Cuenta cuenta = crearCuenta();
        if(datosValidos(cuenta)){
            if(cuentaController.agregarCuenta(cuenta)){
                listaCuentas.add(cuenta);
                limpiarCampos();
                mostrarMensaje("",
                        "HEADER_INCOMPLETO",
                        "CONTENIDO_USUARIO_AGREGADO",
                        Alert.AlertType.INFORMATION);
            }else {
                mostrarMensaje("TITULO_USUARIO_NO_AGREGADO",
                        "HEADER_INCOMPLETO",
                        "CONTENIDO_USUARIO_NO_AGREGADO",
                        Alert.AlertType.ERROR);
            }
        }else {
            mostrarMensaje("TITULO_INCOMPLETO",
                    "HEADER_INCOMPLETO",
                    "CONTENIDO_INCOMPLETO",
                    Alert.AlertType.WARNING);
        }
    }

    private void limpiarCampos() {
        txtNumeroCuenta.clear();
        txtNombreBanco.clear();
        cbTipoCuenta.setValue(null);
    }

    private boolean datosValidos(Cuenta cuenta) {
        if (cuenta.getNombreBanco().isBlank() ||
                cuenta.getNumeroCuenta().isBlank() ||
                cuenta.getTipoCuenta() == null) {
            return false;
        }else {
            return true;
        }
    }

    private Cuenta crearCuenta() {
        return new Cuenta(txtNombreBanco.getText(),
                txtNumeroCuenta.getText(),
                cbTipoCuenta.getSelectionModel().getSelectedItem(),
                Sesion.getUsuarioActual());
    }

    @FXML
    void onEliminar(ActionEvent event) {
        eliminarCuenta();
    }

    private void eliminarCuenta() {
        Cuenta cuentaSeleccionada = tableCuenta.getSelectionModel().getSelectedItem();
        if (cuentaSeleccionada != null) {
            if (cuentaController.eliminarCuenta(cuentaSeleccionada.getIdCuenta())) {
                listaCuentas.remove(cuentaSeleccionada);
                limpiarCampos();
            } else {
                System.out.println("No se pudo eliminar la cuenta con ID " );
            }
        } else {
            System.out.println("No se ha seleccionado ninguna cuenta para eliminar.");
        }
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
