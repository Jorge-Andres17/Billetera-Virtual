package co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller.TransaccionAdminController;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoTransaccion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Presupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Transaccion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observadores.EventoCuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observadores.EventoUsuario;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observer;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.util.stream.Collectors;
import static co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.BilleteraVirtualConstantes.*;

public class TransaccionAdminViewController implements Observer {
    TransaccionAdminController transaccionAdminController;
    ObservableList<Transaccion> listaTransacciones =  FXCollections.observableArrayList();
    ObservableList<Cuenta> listaCuentas = FXCollections.observableArrayList();
    ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    Transaccion selectedTransaccion;

    @FXML
    private DatePicker DatePickerFecha;

    @FXML
    private Button btnRealizarTransaccion;

    @FXML
    private ComboBox<Cuenta> cbCuentaDestino;

    @FXML
    private ComboBox<Cuenta> cbCuentaOrigen;

    @FXML
    private ComboBox<TipoTransaccion> cbTipoTransaccion;

    @FXML
    private ComboBox<Usuario> cbUsuario;

    @FXML
    private TableView<Transaccion> tableTransaccion;

    @FXML
    private TableColumn<Transaccion, String> tcCuentaDestino;

    @FXML
    private TableColumn<Transaccion, String> tcCuentaOrigen;

    @FXML
    private TableColumn<Transaccion, LocalDate> tcFecha;

    @FXML
    private TableColumn<Transaccion, String> tcIdTransaccion;

    @FXML
    private TableColumn<Transaccion, String> tcMonto;

    @FXML
    private TableColumn<Transaccion, TipoTransaccion> tcTipoTransaccion;

    @FXML
    private TableColumn<Transaccion, String> tcUsuario;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtMonto;

    @FXML
    void initialize(){
        transaccionAdminController = new TransaccionAdminController();
        transaccionAdminController.getModelFactory().getBilleteraVirtual().addObserver(this);
        cbCuentaOrigen.setDisable(true);
        cbCuentaDestino.setDisable(true);
        initView();
        inicializarCombobox();
        cbUsuario.valueProperty().addListener((obs, oldVal, newVal) -> validarEstadoCuentas());
        cbTipoTransaccion.valueProperty().addListener((obs, oldVal, newVal) -> validarEstadoCuentas());
        cbCuentaOrigen.valueProperty().addListener((obs, oldVal, newVal) -> validarCuentaDestino());
    }

    private void inicializarCombobox() {
        cbTipoTransaccion.setItems(FXCollections.observableArrayList(TipoTransaccion.values()));
        cbTipoTransaccion.setPromptText("Seleccionar");
        configurarComboBox(cbUsuario,
                FXCollections.observableArrayList(
                        listaUsuarios.stream()
                                .filter(usuario -> usuario.getNombre() != null)
                                .collect(Collectors.toList())
                ),
                new StringConverter<Usuario>() {
                    @Override
                    public String toString(Usuario usuario) {
                        return usuario != null ? usuario.getNombre() : "";
                    }

                    @Override
                    public Usuario fromString(String string) {
                        return cbUsuario.getItems().stream()
                                .filter(u -> u.getNombre().equals(string))
                                .findFirst()
                                .orElse(null);
                    }
                });
        configurarComboBox(cbCuentaOrigen,
                FXCollections.observableArrayList(
                        listaCuentas.stream()
                                .filter(cuenta -> cuenta.getNumeroCuenta() != null)
                                .collect(Collectors.toList())
                ),
                new StringConverter<Cuenta>() {
                    @Override
                    public String toString(Cuenta cuenta) {
                        return cuenta != null ? cuenta.getNumeroCuenta() : "";
                    }

                    @Override
                    public Cuenta fromString(String string) {
                        return cbCuentaOrigen.getItems().stream()
                                .filter(c -> c.getNumeroCuenta().equals(string))
                                .findFirst()
                                .orElse(null);
                    }
                });
        cbCuentaOrigen.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                actualizarCuentasDestino(newValue);
            } else {
                cbCuentaDestino.setItems(FXCollections.observableArrayList(listaCuentas));
            }
        });
        configurarComboBox(cbCuentaDestino,
                FXCollections.observableArrayList(
                        listaCuentas.stream()
                                .filter(cuenta -> cuenta.getNumeroCuenta() != null)
                                .collect(Collectors.toList())
                ),
                new StringConverter<Cuenta>() {
                    @Override
                    public String toString(Cuenta cuenta) {
                        return cuenta != null ? cuenta.getNumeroCuenta() : "";
                    }

                    @Override
                    public Cuenta fromString(String string) {
                        return cbCuentaDestino.getItems().stream()
                                .filter(c -> c.getNumeroCuenta().equals(string))
                                .findFirst()
                                .orElse(null);
                    }
                });
    }

    private <T> void configurarComboBox(ComboBox<T> comboBox, ObservableList<T> items, StringConverter<T> converter) {
        comboBox.setItems(items);
        comboBox.setPromptText("Seleccionar");
        comboBox.setConverter(converter);
    }

    private void actualizarCuentasDestino(Cuenta cuentaOrigenSeleccionada) {
        cbCuentaDestino.setItems(FXCollections.observableArrayList(
                listaCuentas.stream()
                        .filter(cuenta -> !cuenta.equals(cuentaOrigenSeleccionada))
                        .collect(Collectors.toList())
        ));
        cbCuentaDestino.getSelectionModel().clearSelection();
    }

    private void validarEstadoCuentas() {
        Usuario usuario = cbUsuario.getValue();
        TipoTransaccion tipo = cbTipoTransaccion.getValue();

        cbCuentaOrigen.setDisable(true);
        cbCuentaDestino.setDisable(true);
        cbCuentaOrigen.setValue(null);
        cbCuentaDestino.setValue(null);

        if (usuario != null && tipo != null) {
            ObservableList<Cuenta> cuentasUsuario = FXCollections.observableArrayList(
                    listaCuentas.stream()
                            .filter(c -> c.getUsuarioAsociado().equals(usuario))
                            .collect(Collectors.toList())
            );

            cbCuentaOrigen.setItems(cuentasUsuario);

            if (tipo == TipoTransaccion.TRANSFERENCIA) {
                cbCuentaOrigen.setDisable(false);
                cbCuentaDestino.setDisable(false);
                cbCuentaDestino.setItems(cuentasUsuario);
            } else {
                cbCuentaOrigen.setDisable(false);
                cbCuentaDestino.setDisable(true);
                cbCuentaDestino.setItems(FXCollections.observableArrayList());
            }
        }
    }

    private void validarCuentaDestino() {
        TipoTransaccion tipo = cbTipoTransaccion.getValue();
        Cuenta cuentaOrigen = cbCuentaOrigen.getValue();

        if (tipo == TipoTransaccion.TRANSFERENCIA && cuentaOrigen != null) {
            Usuario usuario = cbUsuario.getValue();
            ObservableList<Cuenta> cuentasDestino = FXCollections.observableArrayList(
                    listaCuentas.stream()
                            .filter(c -> c.getUsuarioAsociado().equals(usuario))
                            .filter(c -> !c.equals(cuentaOrigen))
                            .collect(Collectors.toList())
            );
            cbCuentaDestino.setItems(cuentasDestino);
            cbCuentaDestino.setDisable(false);
            cbCuentaDestino.setValue(null);
        } else {
            cbCuentaDestino.setDisable(true);
            cbCuentaDestino.setValue(null);
            cbCuentaDestino.setItems(FXCollections.observableArrayList());
        }
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
            cbTipoTransaccion.getSelectionModel().select(selectedTransaccion.getTipoTransaccion());
            cbUsuario.getSelectionModel().select(selectedTransaccion.getCuentaOrigen().getUsuarioAsociado());
            cbCuentaOrigen.getSelectionModel().select(selectedTransaccion.getCuentaOrigen());
            cbCuentaDestino.getSelectionModel().select(selectedTransaccion.getCuentaDestino());
            txtMonto.setText(String.valueOf(selectedTransaccion.getMonto()));
            txtDescripcion.setText(selectedTransaccion.getDescripcion());
        }
    }

    private void obtenerTransacciones() {
        listaTransacciones.addAll(transaccionAdminController.obtenerTransacciones());
        listaUsuarios.addAll(transaccionAdminController.obtenerUsuarios());
        listaCuentas.addAll(transaccionAdminController.obtenerCuentas());
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
        tcUsuario.setCellValueFactory(cellData -> new
                SimpleStringProperty(cellData.getValue().getCuentaOrigen().getUsuarioAsociado().getNombre()));
    }

    @FXML
    void onRealizarTransaccion(ActionEvent event) {
        realizarTransaccion();
    }

    private void realizarTransaccion() {
        Transaccion transaccion = crearTransaccion();
        if (datosValidos(transaccion)) {
            if (transaccionAdminController.agregarTransaccion(transaccion)) {
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
        }else if (evento instanceof EventoUsuario e) {
            Usuario usuario = e.getUsuario();
            Platform.runLater(() -> {
                switch (e.getTipo()) {
                    case AGREGAR -> {
                        if (!cbUsuario.getItems().contains(usuario)) {
                            cbUsuario.getItems().add(usuario);
                        }
                    }
                    case ELIMINAR -> {
                        cbUsuario.getItems().removeIf(u ->
                                u.getIdUsuario() == usuario.getIdUsuario()
                        );
                    }
                    case ACTUALIZAR -> {
                        for (int i = 0; i < cbUsuario.getItems().size(); i++) {
                            Usuario u = cbUsuario.getItems().get(i);
                            if (u.getIdUsuario() == usuario.getIdUsuario()) {
                                cbUsuario.getItems().set(i, usuario);
                                break;
                            }
                        }
                    }
                }
            });
        }
    }
}
