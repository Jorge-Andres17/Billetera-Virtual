package co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller.TransaccionController;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoTransaccion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Presupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Transaccion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observadores.EventoCuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observer;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.List;

import java.util.stream.Collectors;

import static co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.BilleteraVirtualConstantes.*;

public class TransaccionViewController implements Observer {
    TransaccionController transaccionController;
    ObservableList<Transaccion> listaTransacciones = FXCollections.observableArrayList();
    Transaccion selectedTransaccion;

    @FXML
    private DatePicker DatePickerFecha;

    @FXML
    private Button btnRealizarTransaccion;

    @FXML
    private ComboBox<TipoTransaccion> cbTipoTransaccion;

    @FXML
    private ComboBox<Cuenta> cbCuentaDestino;

    @FXML
    private ComboBox<Cuenta> cbCuentaOrigen;

    @FXML
    private TableView<Transaccion> tableTransaccion;

    @FXML
    private TableColumn<Transaccion, TipoTransaccion> tcTipoTransaccion;

    @FXML
    private TableColumn<Transaccion, String> tcCuentaDestino;

    @FXML
    private TableColumn<Transaccion, String> tcCuentaOrigen;

    @FXML
    private TableColumn<Transaccion, LocalDate> tcFecha;

    @FXML
    private TableColumn<Transaccion, String> tcMonto;

    @FXML
    private TableColumn<Transaccion, String> tcIdTransaccion;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtMonto;

    @FXML
    void initialize() {
        transaccionController = new TransaccionController();
        transaccionController.getModelFactory().getBilleteraVirtual().addObserver(this);
        cbTipoTransaccion.setItems(FXCollections.observableArrayList(TipoTransaccion.values()));
        ObservableList<Cuenta> cuentas = FXCollections.observableArrayList(transaccionController.obtenerCuentas());
        cbCuentaOrigen.setItems(cuentas);
        cbCuentaDestino.setItems(cuentas);
        cbCuentaOrigen.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, selectedCuentaOrigen) -> {
            if (selectedCuentaOrigen != null) {
                List<Cuenta> cuentasFiltradas = cuentas.stream()
                        .filter(c -> !c.equals(selectedCuentaOrigen))
                        .collect(Collectors.toList());
                cbCuentaDestino.setItems(FXCollections.observableArrayList(cuentasFiltradas));
            } else {
                cbCuentaDestino.setItems(cuentas);
            }
        });
        Callback<ListView<Cuenta>, ListCell<Cuenta>> cellFactory = lvc -> new ListCell<>() {
            @Override
            protected void updateItem(Cuenta item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNumeroCuenta());
            }
        };
        cbCuentaOrigen.setCellFactory(cellFactory);
        cbCuentaOrigen.setButtonCell(cellFactory.call(null));
        cbCuentaDestino.setCellFactory(cellFactory);
        cbCuentaDestino.setButtonCell(cellFactory.call(null));
        cbCuentaDestino.setDisable(true);
        cbTipoTransaccion.valueProperty().addListener((obs, oldTipo, newTipo) -> {
            if (newTipo == TipoTransaccion.TRANSFERENCIA) {
                cbCuentaDestino.setDisable(false);
            } else {
                cbCuentaDestino.setDisable(true);
                cbCuentaDestino.setValue(null);
            }
        });
        cbCuentaOrigen.setPromptText("Seleccionar");
        cbCuentaDestino.setPromptText("Seleccionar");
        cbTipoTransaccion.setPromptText("Seleccionar");
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerTransacciones();
        tableTransaccion.getItems().clear();
        tableTransaccion.setItems(listaTransacciones);
        listenerSelection();
    }

    private void listenerSelection() {
        tableTransaccion.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedTransaccion = newSelection;
            mostrarInfoTransaccion(selectedTransaccion);
        });
    }

    private void mostrarInfoTransaccion(Transaccion selectedTransaccion) {
        if (selectedTransaccion != null) {
            DatePickerFecha.setValue(selectedTransaccion.getFechaTransaccion());
            txtDescripcion.setText(selectedTransaccion.getDescripcion());
            cbTipoTransaccion.getSelectionModel().select(selectedTransaccion.getTipoTransaccion());
            cbCuentaOrigen.getSelectionModel().select(selectedTransaccion.getCuentaOrigen());
            cbCuentaDestino.getSelectionModel().select(selectedTransaccion.getCuentaDestino());
            txtMonto.setText(String.valueOf(selectedTransaccion.getMonto()));

        }
    }

    private void obtenerTransacciones() {
            listaTransacciones.addAll(transaccionController.obtenerTransacciones());
    }

    private void initDataBinding() {
        tcIdTransaccion.setCellValueFactory(cellData -> new
                SimpleStringProperty(String.valueOf(cellData.getValue().getIdTransaccion())));
        tcFecha.setCellValueFactory(cellData -> new
                SimpleObjectProperty<>(cellData.getValue().getFechaTransaccion()));
        tcCuentaOrigen.setCellValueFactory(cellData -> new
                SimpleStringProperty(cellData.getValue().getCuentaOrigen().getNumeroCuenta()));
        tcCuentaDestino.setCellValueFactory(cellData -> {
            Cuenta destino = cellData.getValue().getCuentaDestino();
            String texto = (destino != null) ? destino.getNumeroCuenta() : "No aplica";
            return new SimpleStringProperty(texto);
        });
        tcMonto.setCellValueFactory(cellData -> new
                SimpleStringProperty(String.valueOf(cellData.getValue().getMonto())));
        tcTipoTransaccion.setCellValueFactory(cellData -> new
                SimpleObjectProperty<>(cellData.getValue().getTipoTransaccion()));
    }

    @FXML
    void onRealizarTransaccion(ActionEvent event) {
        realizarTransaccion();
    }

    private void realizarTransaccion() {
        Transaccion transaccion = crearTransaccion();
            if (datosValidos(transaccion)) {
                if (transaccionController.agregarTransaccion(transaccion)) {
                    listaTransacciones.add(transaccion);
                    mostrarMensaje(TITULO_TRANSACCION_EXITOSA,
                            HEADER_TRANSACCION_EXITOSA,
                            BODY_TRANSACCION_EXITOSA,
                            Alert.AlertType.INFORMATION);
                } else {
                    mostrarMensaje(TITULO_REGISTRO_FALLIDO,
                            HEADER_REGISTRO_FALLIDO,
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

    private Transaccion crearTransaccion() {
        return new Transaccion(DatePickerFecha.getValue(),
                Double.parseDouble(txtMonto.getText()),
                txtDescripcion.getText(),
                cbCuentaOrigen.getValue(),
                cbCuentaDestino.getValue(),
                cbTipoTransaccion.getValue());
    }

    private boolean datosValidos(Transaccion transaccion) {
        if (transaccion.getFechaTransaccion() == null
                || transaccion.getFechaTransaccion().isBefore(DatePickerFecha.getValue())
                || transaccion.getMonto() <= 0
                || transaccion.getCuentaOrigen() == null) {
            return false;
        }
        Presupuesto presupuestoOrigen = transaccion.getCuentaOrigen().getPresupuesto();
        if (presupuestoOrigen == null) {
            return false;
        }
        double montoDisponible = presupuestoOrigen.getMontoAsignado();
        switch (transaccion.getTipoTransaccion()) {
            case DEPOSITO:
                return true;

            case RETIRO:
                return montoDisponible >= transaccion.getMonto();

            case TRANSFERENCIA:
                return transaccion.getCuentaDestino() != null
                        && !transaccion.getCuentaDestino().equals(transaccion.getCuentaOrigen())
                        && montoDisponible >= transaccion.getMonto();
            default:
                return false;
        }
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

    @Override
    public void update(Object evento) {
        if (evento instanceof EventoCuenta e) {
            Cuenta cuenta = e.getCuenta();
            Platform.runLater(() -> {
                switch (e.getTipo()) {
                    case AGREGAR -> {
                        if (!cbCuentaOrigen.getItems().contains(cuenta)) {
                            cbCuentaOrigen.getItems().add(cuenta);
                        }
                    }
                    case ELIMINAR -> {
                        cbCuentaOrigen.getItems().removeIf(c ->
                                c.getIdCuenta() == cuenta.getIdCuenta()
                        );
                    }
                    case ACTUALIZAR -> {
                        for (int i = 0; i < cbCuentaOrigen.getItems().size(); i++) {
                            Cuenta c = cbCuentaOrigen.getItems().get(i);
                            if (c.getIdCuenta() == cuenta.getIdCuenta()) {
                                cbCuentaOrigen.getItems().set(i, cuenta);
                                break;
                            }
                        }
                    }
                }
            });
        }
    }
}
