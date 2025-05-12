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

import static co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.BilleteraVirtualConstantes.*;

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

    private void obtenerCuentas() {
        listaCuentas.addAll(cuentaController.obtenerCuentas());
    }

    private void listenerSelection() {
        tableCuenta.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedCuenta = newSelection;
            mostrarInformacionUsuario(selectedCuenta);
        });
    }

    private void mostrarInformacionUsuario(Cuenta cuenta) {
        if(cuenta != null){
            txtNombreBanco.setText(cuenta.getNombreBanco());
            txtNumeroCuenta.setText(cuenta.getNumeroCuenta());
            cbTipoCuenta.setValue(cuenta.getTipoCuenta());
        }
    }

    @FXML
    void onNuevo(ActionEvent event) {
        nueva();
    }

    @FXML
    void onAgregar(ActionEvent event) {
        agregarCuenta();
    }

    @FXML
    void onEliminar(ActionEvent event) {
        eliminarCuenta();
    }

    @FXML
    void onActualizar(ActionEvent event) {
        actualizarCuenta();
    }

    private void nueva() {
        txtNombreBanco.setText("");
        txtNumeroCuenta.setText("");
        cbTipoCuenta.setValue(null);
        tableCuenta.getSelectionModel().clearSelection();
    }

    private void agregarCuenta() {
        Cuenta cuenta = crearCuenta();
        if(datosValidos(cuenta)){
            if(cuentaController.agregarCuenta(cuenta)){
                listaCuentas.add(cuenta);
                limpiarCampos();
                tableCuenta.getSelectionModel().clearSelection();
                mostrarMensaje(TITULO_CUENTA_AGREGADA,
                        HEADER_CUENTA_AGREGADA,
                        BODY_CUENTA_AGREGADA,
                        Alert.AlertType.INFORMATION);
            }else {
                mostrarMensaje(TITULO_CUENTA_NO_AGREGADA,
                        HEADER_CUENTA_NO_AGREGADA,
                        BODY_CUENTA_NO_AGREGADA,
                        Alert.AlertType.ERROR);
            }
        }else {
            mostrarMensaje(TITULO_INCOMPLETO,
                    HEADER_INCOMPLETO,
                    BODY_INCOMPLETO,
                    Alert.AlertType.WARNING);
        }
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

    private void eliminarCuenta() {
        Cuenta cuentaSeleccionada = tableCuenta.getSelectionModel().getSelectedItem();
        if (cuentaSeleccionada != null) {
            if(mostrarMensajeConfirmacion(MENSAJE_ELIMINAR_CUENTA)) {
                if (cuentaController.eliminarCuenta(cuentaSeleccionada.getIdCuenta())) {
                    listaCuentas.remove(cuentaSeleccionada);
                    limpiarCampos();
                    mostrarMensaje(TITULO_CUENTA_ELIMINADA,
                            HEADER_CUENTA_ELIMINADA,
                            BODY_CUENTA_ELIMINADA,
                            Alert.AlertType.INFORMATION);
                } else {
                    mostrarMensaje(TITULO_CUENTA_NO_ELIMINADA,
                            HEADER_CUENTA_NO_ELIMINADA,
                            BODY_CUENTA_NO_ELIMINADA,
                            Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarMensaje(TITULO_INCOMPLETO,
                    HEADER_INCOMPLETO,
                    BODY_INCOMPLETO,
                    Alert.AlertType.WARNING);
        }
    }

    private void actualizarCuenta() {
        Cuenta cuentaSeleccionada = tableCuenta.getSelectionModel().getSelectedItem();
        if(cuentaSeleccionada != null && datosValidos(cuentaSeleccionada)){
            if (cuentaController.actualizarCuenta(cuentaSeleccionada.getIdCuenta(),
                    txtNombreBanco.getText(),txtNumeroCuenta.getText(),
                    cbTipoCuenta.getSelectionModel().getSelectedItem())){
                tableCuenta.refresh();
                limpiarCampos();
                mostrarMensaje(TITULO_CUENTA_ACTUALIZADA,
                        HEADER_CUENTA_ACTUALIZADA,
                        BODY_CUENTA_ACTUALIZADA,
                        Alert.AlertType.INFORMATION);
            }else {
                mostrarMensaje(TITULO_CUENTA_NO_ACTUALIZADA,
                        HEADER_CUENTA_NO_ACTUALIZADA,
                        BODY_CUENTA_NO_ACTUALIZADA,
                        Alert.AlertType.ERROR);
            }
        }else {
            mostrarMensaje(TITULO_INCOMPLETO,
                    HEADER_INCOMPLETO,
                    BODY_INCOMPLETO,
                    Alert.AlertType.WARNING);
        }
    }

    private void limpiarCampos() {
        txtNumeroCuenta.clear();
        txtNombreBanco.clear();
        cbTipoCuenta.setValue(null);
        tableCuenta.getSelectionModel().clearSelection();
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
