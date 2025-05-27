package co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller.TransaccionController;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Decorator.IPresupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoTransaccion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Presupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Transaccion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observadores.EventoCuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observer;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
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
    private MenuButton MenuButtonFiltar;

    @FXML private MenuItem itemFecha;
    @FXML private MenuItem itemTipoTransaccion;
    @FXML private MenuItem itemCuentaOrigen;
    @FXML private MenuItem itemMonto;
    @FXML private MenuItem itemRestablecer;
    private ChangeListener<LocalDate> fechaListener;
    private ChangeListener<TipoTransaccion> tipoListener;
    private ChangeListener<Cuenta> cuentaListener;
    private ChangeListener<String> montoListener;

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

        itemFecha.setOnAction(event -> filtrarPorFecha());
        itemTipoTransaccion.setOnAction(event -> filtrarPorTipo());
        itemCuentaOrigen.setOnAction(event -> filtrarPorCuentaOrigen());
        itemMonto.setOnAction(event -> filtrarPorMonto());
        itemRestablecer.setOnAction(event -> mostrarTodo());
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

    @FXML
    public void filtrarPorFecha() {
        limpiarListeners();
        configurarFiltro(true, false, false, false, false);
        fechaListener = (obs, oldVal, newVal) -> {
            if (newVal != null) {
                filtrarFecha(newVal);
            }
        };
        DatePickerFecha.valueProperty().addListener(fechaListener);
    }

    private void filtrarFecha(LocalDate fecha) {
        if (fecha != null) {
            List<Transaccion> filtradas = listaTransacciones.stream()
                    .filter(t -> t.getFechaTransaccion().equals(fecha))
                    .collect(Collectors.toList());
            tableTransaccion.setItems(FXCollections.observableArrayList(filtradas));
            limpiarCampos();
        }
    }

    @FXML
    public void filtrarPorTipo() {
        limpiarListeners();
        configurarFiltro(false, true, false, false, false);
        tipoListener = (obs, oldVal, newVal) -> {
            if (newVal != null) {
                filtrarTipo(newVal);
            }
        };
        cbTipoTransaccion.valueProperty().addListener(tipoListener);
    }

    private void filtrarTipo(TipoTransaccion tipo) {
        if (tipo != null) {
            if (tipo == TipoTransaccion.TRANSFERENCIA) {
                cbCuentaDestino.setDisable(true);
            }
            List<Transaccion> filtradas = listaTransacciones.stream()
                    .filter(t -> t.getTipoTransaccion() == tipo)
                    .collect(Collectors.toList());
            tableTransaccion.setItems(FXCollections.observableArrayList(filtradas));
        }
    }

    @FXML
    public void filtrarPorCuentaOrigen() {
        limpiarListeners();
        configurarFiltro(false, false, true, false, false);
        cuentaListener = (obs, oldVal, newVal) -> {
            if (newVal != null) {
                filtrarCuenta(newVal);
            }
        };
        cbCuentaOrigen.valueProperty().addListener(cuentaListener);
    }

    private void filtrarCuenta(Cuenta cuenta) {
        if (cuenta != null) {
            List<Transaccion> filtradas = listaTransacciones.stream()
                    .filter(t -> t.getCuentaOrigen().getNumeroCuenta().equals(cuenta.getNumeroCuenta()))
                    .collect(Collectors.toList());
            tableTransaccion.setItems(FXCollections.observableArrayList(filtradas));
        }
    }

    @FXML
    public void filtrarPorMonto() {
        limpiarListeners();
        configurarFiltro(false, false, false, true, false);
        montoListener = (obs, oldVal, newVal) -> {
            if (esNumero(newVal)) {
                filtrarMonto(Double.parseDouble(newVal));
            }
        };
        txtMonto.textProperty().addListener(montoListener);
    }

    private void filtrarMonto(Double monto) {
        if (monto != null) {
            List<Transaccion> filtradas = listaTransacciones.stream()
                    .filter(t -> t.getMonto() == monto)
                    .collect(Collectors.toList());
            tableTransaccion.setItems(FXCollections.observableArrayList(filtradas));
        }
    }

    private void limpiarListeners() {
        if (fechaListener != null) {
            DatePickerFecha.valueProperty().removeListener(fechaListener);
            fechaListener = null;
        }
        if (tipoListener != null) {
            cbTipoTransaccion.valueProperty().removeListener(tipoListener);
            tipoListener = null;
        }
        if (cuentaListener != null) {
            cbCuentaOrigen.valueProperty().removeListener(cuentaListener);
            cuentaListener = null;
        }
        if (montoListener != null) {
            txtMonto.textProperty().removeListener(montoListener);
            montoListener = null;
        }
        tableTransaccion.setItems(listaTransacciones);
        limpiarCampos();
    }

    @FXML
    public void mostrarTodo() {
        limpiarListeners();
        tableTransaccion.setItems(listaTransacciones);
        limpiarCampos();
        configurarFiltro(true, true, true, true, true);
    }

    private void configurarFiltro(boolean fecha,
                                  boolean tipo,
                                  boolean cuenta,
                                  boolean monto,
                                  boolean descripcion) {
        DatePickerFecha.setDisable(!fecha);
        cbTipoTransaccion.setDisable(!tipo);
        cbCuentaOrigen.setDisable(!cuenta);
        txtMonto.setDisable(!monto);
        txtDescripcion.setDisable(!descripcion);
    }

    private void realizarTransaccion() {
        if (!datosValidos()) {
            mostrarMensaje(
                    TITULO_INCOMPLETO,
                    HEADER_INCOMPLETO,
                    BODY_INCOMPLETO,
                    Alert.AlertType.WARNING
            );
            return;
        }

        Transaccion transaccion = crearTransaccion();

        if (transaccionController.agregarTransaccion(transaccion)) {
            listaTransacciones.add(transaccion);
            limpiarCampos();
            mostrarMensaje(
                    TITULO_TRANSACCION_EXITOSA,
                    HEADER_TRANSACCION_EXITOSA,
                    BODY_TRANSACCION_EXITOSA,
                    Alert.AlertType.INFORMATION
            );
        } else {
            mostrarMensaje(
                    TITULO_REGISTRO_FALLIDO,
                    HEADER_REGISTRO_FALLIDO,
                    BODY_REGISTRO_FALLIDO,
                    Alert.AlertType.ERROR
            );
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

    private boolean datosValidos() {
        if (DatePickerFecha.getValue() == null
                || txtMonto.getText().isBlank()
                || cbCuentaOrigen.getValue() == null
                || cbTipoTransaccion.getValue() == null) {
            return false;
        }

        double monto;
        try {
            monto = Double.parseDouble(txtMonto.getText());
            if (monto <= 0) return false;
        } catch (NumberFormatException e) {
            return false;
        }

        Cuenta cuentaOrigen = cbCuentaOrigen.getValue();
        IPresupuesto presupuesto = cuentaOrigen.getPresupuesto();
        if (presupuesto == null) return false;

        double montoDisponible = presupuesto.getMontoAsignado();
        TipoTransaccion tipo = cbTipoTransaccion.getValue();

        return switch (tipo) {
            case DEPOSITO -> true;
            case RETIRO -> montoDisponible >= monto;
            case TRANSFERENCIA -> cbCuentaDestino.getValue() != null
                    && !cbCuentaDestino.getValue().equals(cuentaOrigen)
                    && montoDisponible >= monto;
        };
    }

    private boolean esNumero(String texto) {
        try {
            Double.parseDouble(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void limpiarCampos() {
        DatePickerFecha.setValue(null);
        txtDescripcion.setText("");
        cbTipoTransaccion.getSelectionModel().select(null);
        cbCuentaOrigen.getSelectionModel().select(null);
        cbCuentaDestino.getSelectionModel().select(null);
        txtMonto.setText("");
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
