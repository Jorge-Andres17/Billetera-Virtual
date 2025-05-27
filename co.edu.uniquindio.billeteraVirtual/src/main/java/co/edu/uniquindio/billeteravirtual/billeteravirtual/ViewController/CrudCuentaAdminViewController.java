package co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.BilleteraVirtualApplication;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller.CrudCuentaAdminController;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Decorator.IPresupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.FactoryMethod.CuentaAhorrosFactory;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.FactoryMethod.CuentaCorrienteFactory;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.FactoryMethod.CuentaFactory;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Login.Sesion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoCuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums.TipoTransaccion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Presupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observadores.EventoUsuario;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observer;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.State.EstadoActivo;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.BilleteraVirtualConstantes.*;
import static co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.BilleteraVirtualConstantes.BODY_CUENTA_NO_AGREGADA;
import static co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.BilleteraVirtualConstantes.BODY_INCOMPLETO;
import static co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.BilleteraVirtualConstantes.HEADER_CUENTA_NO_AGREGADA;
import static co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.BilleteraVirtualConstantes.HEADER_INCOMPLETO;
import static co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.BilleteraVirtualConstantes.TITULO_CUENTA_NO_AGREGADA;
import static co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.BilleteraVirtualConstantes.TITULO_INCOMPLETO;

public class CrudCuentaAdminViewController implements Observer {
    CrudCuentaAdminController crudCuentaAdminController;
    ObservableList<Cuenta> listaCuentas = FXCollections.observableArrayList();
    ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    ObservableList<IPresupuesto> listaPresupuestos = FXCollections.observableArrayList();
    Cuenta selectCuenta;
    Map<TipoCuenta,CuentaFactory> factoryMap;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    @FXML
    private ComboBox<IPresupuesto> cbPresupuesto;

    @FXML
    private ComboBox<TipoCuenta> cbTipoCuenta;

    @FXML
    private ComboBox<Usuario> cbUsuario;

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
    private TableColumn<Cuenta, String> tcUsuario;

    @FXML
    private TextField txtNombreBanco;

    @FXML
    private TextField txtNumeroCuenta;

    @FXML
    void initialize(){
        crudCuentaAdminController = new CrudCuentaAdminController();
        crudCuentaAdminController.getModelFactory().getBilleteraVirtual().addObserver(this);
        cbPresupuesto.setDisable(true);
        initView();
        inicializarCombobox();
        cbUsuario.valueProperty().addListener((obs, oldVal, newVal) -> validarEstadoPresupuestos());
        factoryMap = new HashMap<>();
        factoryMap.put(TipoCuenta.AHORRO,new CuentaAhorrosFactory());
        factoryMap.put(TipoCuenta.CORRIENTE,new CuentaCorrienteFactory());
    }

    private void initView() {
        initDataBinding();
        obtenerListas();
        tableCuenta.getItems().clear();
        tableCuenta.setItems(listaCuentas);
        listenerSelection();
    }

    private void initDataBinding() {
        tcIdCuenta.setCellValueFactory(cellData -> new
                SimpleStringProperty(String.valueOf(cellData.getValue().getIdCuenta())));
        tcNumeroCuenta.setCellValueFactory(cellData -> new
                SimpleStringProperty(cellData.getValue().getNumeroCuenta()));
        tcNombreBanco.setCellValueFactory(cellData -> new
                SimpleStringProperty(cellData.getValue().getNombreBanco()));
        tcTipoCuenta.setCellValueFactory(cellData -> new
                SimpleStringProperty(cellData.getValue().getTipoCuenta().toString()));
        tcPresupuesto.setCellValueFactory(cellData -> new
                SimpleStringProperty(cellData.getValue().getPresupuesto().getNombrePresupuesto()));
        tcUsuario.setCellValueFactory(cellData -> new
                SimpleStringProperty(cellData.getValue().getUsuarioAsociado().getNombre()));
    }

    private void obtenerListas() {
        listaCuentas.addAll(crudCuentaAdminController.obtenerCuentas());
        listaUsuarios.addAll(crudCuentaAdminController.obtenerUsuarios());
        listaPresupuestos.addAll(crudCuentaAdminController.obtenerPresupuestos());
    }

    private void listenerSelection() {
        tableCuenta.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectCuenta = newSelection;
            mostrarInformacionCuenta(selectCuenta);
        });
    }

    private void mostrarInformacionCuenta(Cuenta cuenta) {
        if (cuenta != null) {
            txtNombreBanco.setText(cuenta.getNombreBanco());
            txtNumeroCuenta.setText(cuenta.getNumeroCuenta());
            cbTipoCuenta.setValue(cuenta.getTipoCuenta());
            cbUsuario.setValue(cuenta.getUsuarioAsociado());

            validarEstadoPresupuestos();

            if (!cbPresupuesto.getItems().contains(cuenta.getPresupuesto())) {
                cbPresupuesto.getItems().add(cuenta.getPresupuesto());
            }
            cbPresupuesto.setValue(cuenta.getPresupuesto());
        }
    }

    private void inicializarCombobox() {
        cbTipoCuenta.setItems(FXCollections.observableArrayList(TipoCuenta.values()));
        cbTipoCuenta.setPromptText("Seleccionar");
        configurarComboBox(cbUsuario,
                FXCollections.observableArrayList(
                        listaUsuarios.stream()
                                .filter(usuario -> usuario.getNombre() != null ||
                                        usuario.getIdUsuario() != null)
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
        configurarComboBox(cbPresupuesto,
                FXCollections.observableArrayList(
                        listaPresupuestos.stream()
                                .filter(presupuesto -> presupuesto.getNombrePresupuesto()
                                        != null)
                                .collect(Collectors.toList())
                ),
                new StringConverter<IPresupuesto>() {
                    @Override
                    public String toString(IPresupuesto presupuesto) {
                        return presupuesto != null ? presupuesto.getNombrePresupuesto() : "";
                    }

                    @Override
                    public IPresupuesto fromString(String string) {
                        return cbPresupuesto.getItems().stream()
                                .filter(p -> p.getCuentaAsociada().getUsuarioAsociado().equals(string))
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

    private void validarEstadoPresupuestos() {
        Usuario usuario = cbUsuario.getValue();
        cbPresupuesto.setDisable(true);
        cbPresupuesto.setValue(null);
        if (usuario != null) {
            List<IPresupuesto> presupuestosDelUsuario = usuario.getListaPresupuestos();
            ObservableList<IPresupuesto> presupuestosUsuario = FXCollections.observableArrayList(
                    presupuestosDelUsuario.stream()
                            .filter(p ->p.getEstadoPresupuesto() instanceof EstadoActivo)
                            .collect(Collectors.toList())
            );
            cbPresupuesto.setDisable(false);
            cbPresupuesto.setItems(presupuestosUsuario);
        }
    }

    @FXML
    void onNuevo(ActionEvent event) {
        limpiarCampos();
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
    void onCerrarSesion(ActionEvent event) throws IOException {
        cerrarSesion();
    }

    private void agregarCuenta() {
        Cuenta cuenta = crearCuenta();
        if (datosValidos(cuenta)){
            if(crudCuentaAdminController.agregarCuenta(cuenta)){
                listaCuentas.add(cuenta);
                limpiarCampos();
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
                cuenta.getTipoCuenta() == null ||
                cuenta.getPresupuesto() == null ||
                cuenta.getUsuarioAsociado() == null) {
            return false;
        }else {
            return true;
        }
    }

    private Cuenta crearCuenta() {
        String nombreBanco = txtNombreBanco.getText();
        String numeroCuenta = txtNumeroCuenta.getText();
        TipoCuenta tipo = cbTipoCuenta.getValue();
        IPresupuesto presupuesto = cbPresupuesto.getValue();
        Usuario usuario = cbUsuario.getValue();
        CuentaFactory factory = factoryMap.get(tipo);

        return factory.crearCuenta(nombreBanco, numeroCuenta, usuario, presupuesto);
    }

    private void eliminarCuenta() {
        Cuenta cuentaSeleccionada = tableCuenta.getSelectionModel().getSelectedItem();
        if (cuentaSeleccionada != null) {
            if(mostrarMensajeConfirmacion(MENSAJE_ELIMINAR_CUENTA)) {
                if (crudCuentaAdminController.eliminarCuenta(cuentaSeleccionada.getIdCuenta(),
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
        if(cuentaSeleccionada != null && datosValidos(cuentaSeleccionada)){
            if (crudCuentaAdminController.actualizarCuenta(cuentaSeleccionada.getIdCuenta(),
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

    private void limpiarCampos() {
        txtNombreBanco.clear();
        txtNumeroCuenta.clear();
        cbPresupuesto.setValue(null);
        cbTipoCuenta.setValue(null);
        cbUsuario.setValue(null);
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
        if (evento instanceof EventoUsuario e) {
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

    private void cerrarSesion() throws IOException {
        Sesion.cerrarSesionAdministrador();
        Stage stageActual = (Stage) btnCerrarSesion.getScene().getWindow();
        stageActual.close();
        BilleteraVirtualApplication.mostrarVentanaLogin();
    }
}
