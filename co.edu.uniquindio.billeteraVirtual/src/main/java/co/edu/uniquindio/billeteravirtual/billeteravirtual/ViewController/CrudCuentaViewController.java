package co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller.CrudCuentaController;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Decorator.IPresupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.FactoryMethod.CuentaAhorrosFactory;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.FactoryMethod.CuentaCorrienteFactory;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.FactoryMethod.CuentaFactory;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Login.Sesion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoCuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Presupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observadores.EventoCuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observadores.EventoPresupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observer;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.State.EstadoActivo;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.State.EstadoInactivo;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.BilleteraVirtualConstantes.*;

public class CrudCuentaViewController implements Observer {
    CrudCuentaController cuentaController;
    ObservableList<Cuenta> listaCuentas = FXCollections.observableArrayList();
    ObservableList<IPresupuesto> listaPresupuesto = FXCollections.observableArrayList();
    Cuenta selectedCuenta;
    Map<TipoCuenta,CuentaFactory> factoryMap;

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
    private Button btnDetalleCuenta;

    @FXML
    private ComboBox<IPresupuesto> cbPresupuesto;

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
        cuentaController.getModelFactory().getBilleteraVirtual().addObserver(this);
        cbTipoCuenta.setItems(FXCollections.observableArrayList(TipoCuenta.values()));
        cbTipoCuenta.setPromptText("Seleccionar");
        listaPresupuesto.addAll(
                cuentaController.obtenerPresupuestosDisponible().stream()
                        .filter(p -> p.getEstadoPresupuesto() instanceof EstadoInactivo)
                        .toList()
        );
        cbPresupuesto.setItems(listaPresupuesto);
        cbPresupuesto.setPromptText("Seleccionar");
        cbPresupuesto.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(IPresupuesto item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getNombrePresupuesto());
            }
        });
        cbPresupuesto.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(IPresupuesto item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getNombrePresupuesto());
            }
        });
        initView();
        factoryMap = new HashMap<>();
        factoryMap.put(TipoCuenta.CORRIENTE,new CuentaCorrienteFactory());
        factoryMap.put(TipoCuenta.AHORRO,new CuentaAhorrosFactory());
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
        tcPresupuesto.setCellValueFactory
                (cellData -> {
                    IPresupuesto presupuesto = cellData.getValue().getPresupuesto();
                    String texto = (presupuesto != null) ? presupuesto.getNombrePresupuesto() : "Sin asociar";
                    return new SimpleStringProperty(texto);
                });
    }

    private void obtenerCuentas() {
        listaCuentas.addAll(cuentaController.obtenerCuentas());

    }

    private void listenerSelection() {
        tableCuenta.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedCuenta = newSelection;
            mostrarInformacionCuenta(selectedCuenta);
        });
    }

    private void mostrarInformacionCuenta(Cuenta cuenta) {
        if (cuenta != null) {
            txtNombreBanco.setText(cuenta.getNombreBanco());
            txtNumeroCuenta.setText(cuenta.getNumeroCuenta());
            cbTipoCuenta.setValue(cuenta.getTipoCuenta());

            IPresupuesto presupuestoCuenta = cuenta.getPresupuesto();
            cbPresupuesto.setValue(presupuestoCuenta);
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

    @FXML
    void onDetalleCuenta(ActionEvent event) {
        detallesCuenta(tableCuenta.getSelectionModel().getSelectedItem());
    }

    private void nueva() {
        txtNombreBanco.setText("");
        txtNumeroCuenta.setText("");
        cbTipoCuenta.setValue(null);
        cbPresupuesto.setValue(null);
        tableCuenta.getSelectionModel().clearSelection();
    }

    private void agregarCuenta() {
        if (!datosValidos()) {
            mostrarMensaje(
                    TITULO_INCOMPLETO,
                    HEADER_INCOMPLETO,
                    BODY_INCOMPLETO,
                    Alert.AlertType.WARNING
            );
            return;
        }

        Cuenta cuenta = crearCuenta();
        if(cuentaController.agregarCuenta(cuenta)){
            listaCuentas.add(cuenta);
            listaPresupuesto.remove(cuenta.getPresupuesto());
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

    }

    private boolean datosValidos() {
        if (txtNombreBanco.getText().isBlank()
                || txtNumeroCuenta.getText().isBlank()
                || cbTipoCuenta.getValue() == null
                || cbPresupuesto.getValue() == null) {
            return false;
        }
        return true;
    }

    private Cuenta crearCuenta() {
        String nombreBanco = txtNombreBanco.getText();
        String numeroCuenta = txtNumeroCuenta.getText();
        TipoCuenta tipo = cbTipoCuenta.getValue();
        IPresupuesto presupuesto = cbPresupuesto.getValue();

        CuentaFactory factory = factoryMap.get(cbTipoCuenta.getValue());
        if (factory == null) {
            throw new IllegalStateException("Tipo de cuenta no soportado: " + tipo);
        }

        return factory.crearCuenta(nombreBanco, numeroCuenta, Sesion.getUsuarioActual(), presupuesto);
    }

    private void eliminarCuenta() {
        Cuenta cuentaSeleccionada = tableCuenta.getSelectionModel().getSelectedItem();
        if (cuentaSeleccionada != null) {
            if(mostrarMensajeConfirmacion(MENSAJE_ELIMINAR_CUENTA)) {
                if (cuentaController.eliminarCuenta(cuentaSeleccionada.getIdCuenta(),
                        cuentaSeleccionada.getNumeroCuenta())) {
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
            } else {
                mostrarMensaje(TITULO_ELIMINACION_CANCELADA,
                        HEADER,
                        BODY_ELIMINACION_CANCELADA,
                        Alert.AlertType.INFORMATION);
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
        if(cuentaSeleccionada != null && datosValidos()){
            if (cuentaController.actualizarCuenta(cuentaSeleccionada.getIdCuenta(),
                    txtNombreBanco.getText(),txtNumeroCuenta.getText(),
                    cbTipoCuenta.getSelectionModel().getSelectedItem(),
                    cbPresupuesto.getSelectionModel().getSelectedItem())){
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

    public void detallesCuenta(Cuenta cuenta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/co/edu/uniquindio/billeteravirtual/billeteravirtual/DetalleCuenta.fxml"));
            Parent root = loader.load();

            DetalleCuentaViewController controller = loader.getController();
            controller.setCuenta(cuenta);

            Stage stage = new Stage();
            stage.setTitle("Detalles de Cuenta");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        txtNumeroCuenta.clear();
        txtNombreBanco.clear();
        cbTipoCuenta.setValue(null);
        cbPresupuesto.setValue(null);
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

    @Override
    public void update(Object evento) {
        if (evento instanceof EventoPresupuesto e) {
            IPresupuesto presupuesto = e.getPresupuesto();
            Platform.runLater(() -> {
                switch (e.getTipo()) {
                    case AGREGAR -> {
                        if (presupuesto.getEstadoPresupuesto() instanceof EstadoActivo && !cbPresupuesto.getItems().contains(presupuesto)) {
                            cbPresupuesto.getItems().add(presupuesto);
                        }
                    }
                    case ELIMINAR -> {
                        cbPresupuesto.getItems().remove(presupuesto);
                    }
                    case ACTUALIZAR -> {
                        cbPresupuesto.getItems().removeIf(p ->
                                p.getIdPresupuesto() == (presupuesto.getIdPresupuesto()));

                        boolean estaAsociado = listaCuentas.stream()
                                .anyMatch(c -> c.getPresupuesto() != null &&
                                        c.getPresupuesto().getIdPresupuesto()==(presupuesto.getIdPresupuesto()));

                        if (!estaAsociado && presupuesto.getEstadoPresupuesto() instanceof EstadoInactivo) {
                            cbPresupuesto.getItems().add(presupuesto);
                        }
                    }
                }
            });
        } else if (evento instanceof EventoCuenta e) {
            Cuenta cuenta = e.getCuenta();
            IPresupuesto presupuesto = cuenta.getPresupuesto();
            Platform.runLater(() -> {
                switch (e.getTipo()) {
                    case AGREGAR -> {
                        if (presupuesto != null && cbPresupuesto.getItems().contains(presupuesto)) {
                            cbPresupuesto.getItems().remove(presupuesto);
                        }
                    }
                    case ELIMINAR -> {
                        if (presupuesto != null && !cbPresupuesto.getItems().contains(presupuesto)) {
                            cbPresupuesto.getItems().add(presupuesto);
                            cbPresupuesto.setValue(null);
                        }
                    }
                    case ACTUALIZAR -> {
                        if (presupuesto != null) {
                            boolean encontrado = false;
                            for (int i = 0; i < cbPresupuesto.getItems().size(); i++) {
                                IPresupuesto p = cbPresupuesto.getItems().get(i);
                                if (p.getIdPresupuesto() == presupuesto.getIdPresupuesto()) {
                                    cbPresupuesto.getItems().set(i, presupuesto);
                                    encontrado = true;
                                    break;
                                }
                            }
                            if (!encontrado && presupuesto.getEstadoPresupuesto() instanceof EstadoActivo ) {
                                cbPresupuesto.getItems().add(presupuesto);
                            }
                        }
                    }
                }
            });
        }
    }
}