package co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller.CrudPresupuestoController;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Categoria;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Presupuesto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Transaccion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observadores.EventoCategoria;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observadores.EventoCuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observadores.EventoTransaccion;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed.Observer;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

import static co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.BilleteraVirtualConstantes.*;

public class CrudPresupuestoViewController implements Observer {
    CrudPresupuestoController crudPresupuestoController;
    ObservableList<Presupuesto> listaPresupuestos = FXCollections.observableArrayList();
    Presupuesto selectedPresupuesto;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregarPresupuesto;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    @FXML
    private ComboBox<Categoria> cbCategorias;

    @FXML
    private ComboBox<Cuenta> cbCuentasDisponibles;

    @FXML
    private TableView<Presupuesto> tablePresupuesto;

    @FXML
    private TableColumn<Presupuesto, String> tcCategoria;

    @FXML
    private TableColumn<Presupuesto, String> tcCuenta;

    @FXML
    private TableColumn<Presupuesto, String> tcIdPresupuesto;

    @FXML
    private TableColumn<Presupuesto, String> tcMontoAsignado;

    @FXML
    private TableColumn<Presupuesto, String> tcMontoGastado;

    @FXML
    private TableColumn<Presupuesto, String> tcNombre;

    @FXML
    private TextField txtMontoAsignado;

    @FXML
    private TextField txtNombrePresupuesto;

    @FXML
    void initialize() {
        crudPresupuestoController = new CrudPresupuestoController();
        crudPresupuestoController.getModelFactory().getBilleteraVirtual().addObserver(this);
        cbCategorias.setItems(FXCollections.observableArrayList(crudPresupuestoController.obtenerCategorias()));
        cbCategorias.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Categoria item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });
        cbCategorias.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Categoria item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });
        cbCategorias.setPromptText("Selecciona una categoria");
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerPresupuestos();
        tablePresupuesto.getItems().clear();
        tablePresupuesto.setItems(listaPresupuestos);
        listenerSelection();
    }

    private void initDataBinding() {
        tcIdPresupuesto.setCellValueFactory
                (cellData -> new SimpleStringProperty
                        (String.valueOf(cellData.getValue().getIdPresupuesto())));
        tcNombre.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        cellData.getValue().getNombrePresupuesto()));
        tcCuenta.setCellValueFactory
                (cellData -> {
                    Cuenta cuenta = cellData.getValue().getCuentaAsociada();
                    String texto = (cuenta != null) ? cuenta.getNumeroCuenta() : "Sin asociar";
                    return new SimpleStringProperty(texto);
                });
        tcCategoria.setCellValueFactory
                (cellData -> new SimpleStringProperty
                (cellData.getValue().getCategoriaAsociada().getNombre()));
        tcMontoAsignado.setCellValueFactory
                (cellData -> new SimpleStringProperty
                        (String.valueOf(cellData.getValue().getMontoAsignado())));
        tcMontoGastado.setCellValueFactory
                (cellData -> new SimpleStringProperty
                        (String.valueOf(cellData.getValue().getMontoGastado())));
    }

    private void obtenerPresupuestos() {
        listaPresupuestos.addAll(crudPresupuestoController.obtenerPresupuestos());
    }

    private void listenerSelection() {
        tablePresupuesto.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedPresupuesto = newSelection;
            mostrarInformacionPresupuesto(selectedPresupuesto);
        });
    }

    private void mostrarInformacionPresupuesto(Presupuesto selectedPresupuesto) {
        if(selectedPresupuesto != null){
            txtNombrePresupuesto.setText(selectedPresupuesto.getNombrePresupuesto());
            txtMontoAsignado.setText(String.valueOf(selectedPresupuesto.getMontoAsignado()));
            cbCategorias.setValue(selectedPresupuesto.getCategoriaAsociada());
        }
    }

    @FXML
    void onNuevo(ActionEvent event) {
        nuevo();
    }

    @FXML
    void onAgregar(ActionEvent event) {
        agregarPresupuesto();
    }

    @FXML
    void onEliminar(ActionEvent event) {
        eliminarPresupuesto();
    }

    @FXML
    void onActualizar(ActionEvent event) {
        actualizar();
    }

    private void nuevo() {
        limpiarCampos();
    }

    private void agregarPresupuesto() {
        Presupuesto presupuesto = crearPresupuesto();
        if (datosValidos(presupuesto)) {
            if (crudPresupuestoController.agregarPresupuesto(presupuesto)) {
                listaPresupuestos.add(presupuesto);
                limpiarCampos();
                mostrarMensaje(TITULO_PRESUPUESTO_AGREGADO,
                        HEADER_PRESUPUESTO_AGREGADO,
                        BODY_PRESUPUESTO_AGREGADO,
                        Alert.AlertType.INFORMATION);
            } else {
                mostrarMensaje(TITULO_PRESUPUESTO_NO_AGREGADO,
                        HEADER_PRESUPUESTO_NO_AGREGADO,
                        BODY_PRESUPUESTO_NO_AGREGADO,
                        Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje(TITULO_INCOMPLETO,
                    HEADER_INCOMPLETO,
                    BODY_INCOMPLETO,
                    Alert.AlertType.WARNING);
        }
    }

    private Presupuesto crearPresupuesto() {
        return new Presupuesto(txtNombrePresupuesto.getText(),
                Double.parseDouble(txtMontoAsignado.getText()),
                0,
                null,
                cbCategorias.getValue());
    }

    private boolean datosValidos(Presupuesto presupuesto) {
        if (txtNombrePresupuesto.getText().isBlank()) {
            return false;
        }
        String montoTexto = txtMontoAsignado.getText();
        try {
            double monto = Double.parseDouble(montoTexto);
            if (monto < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        if (cbCategorias == null ||
                cbCategorias.getValue() == null) {
            return false;
        }

        return true;
    }

    private void eliminarPresupuesto() {
        Presupuesto presupuesto = tablePresupuesto.getSelectionModel().getSelectedItem();
        if (presupuesto != null) {
            if (mostrarMensajeConfirmacion(MENSAJE_ELIMINAR_CUENTA)) {
                if (crudPresupuestoController.eliminarPresupuesto(presupuesto.getNombrePresupuesto())) {
                    listaPresupuestos.remove(presupuesto);
                    limpiarCampos();
                    mostrarMensaje(TITULO_PRESUPUESTO_ELIMINADO,
                            HEADER_PRESUPUESTO_ELIMINADO,
                            BODY_PRESUPUESTO_ELIMINADO,
                            Alert.AlertType.INFORMATION);
                } else {
                    mostrarMensaje(TITULO_PRESUPUESTO_NO_ELIMINADO,
                            HEADER_PRESUPUESTO_NO_ELIMINADO,
                            BODY_PRESUPUESTO_NO_ELIMINADO,
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

    private void actualizar() {
        Presupuesto presupuesto = tablePresupuesto.getSelectionModel().getSelectedItem();
        if (presupuesto != null && datosValidos(presupuesto)) {
            if (crudPresupuestoController.actualizarPresupuesto(txtNombrePresupuesto.getText(),
                    Double.parseDouble(txtMontoAsignado.getText()),
                    cbCategorias.getValue())) {

                limpiarCampos();
                tablePresupuesto.refresh();
                mostrarMensaje(TITULO_PRESUPUESTO_ACTUALIZADO,
                        HEADER_PRESUPUESTO_ACTUALIZADO,
                        BODY_PRESUPUESTO_ACTUALIZADO,
                        Alert.AlertType.INFORMATION);
            } else {
                mostrarMensaje(TITULO_PRESUPUESTO_NO_ACTUALIZADO,
                        HEADER_PRESUPUESTO_NO_ACTUALIZADO,
                        BODY_PRESUPUESTO_NO_ACTUALIZADO,
                        Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje(TITULO_INCOMPLETO,
                    HEADER_INCOMPLETO,
                    BODY_INCOMPLETO,
                    Alert.AlertType.WARNING);
        }
    }

    private void limpiarCampos() {
        txtNombrePresupuesto.clear();
        txtMontoAsignado.clear();
        cbCategorias.getSelectionModel().clearSelection();
        tablePresupuesto.refresh();
        tablePresupuesto.getSelectionModel().clearSelection();
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
        if (evento instanceof EventoCategoria e) {
            Categoria categoria = e.getCategoria();
            Platform.runLater(() -> {
                switch (e.getTipo()) {
                    case AGREGAR -> {
                        if (!cbCategorias.getItems().contains(categoria)) {
                            cbCategorias.getItems().add(categoria);
                        }
                    }
                    case ELIMINAR -> cbCategorias.getItems().remove(categoria);
                    case ACTUALIZAR -> {
                        for (int i = 0; i < cbCategorias.getItems().size(); i++) {
                            Categoria c = cbCategorias.getItems().get(i);
                            if (c.getIdCategoria() == (categoria.getIdCategoria())) {
                                cbCategorias.getItems().set(i, categoria);
                                break;
                            }
                        }
                    }
                }
            });
        } else if (evento instanceof EventoCuenta e) {
            Cuenta cuenta = e.getCuenta();
            Platform.runLater(() -> {
                switch (e.getTipo()) {
                    case AGREGAR, ELIMINAR, ACTUALIZAR -> tablePresupuesto.refresh();
                }
            });
        } else if (evento instanceof EventoTransaccion e) {
            Transaccion transaccion = e.getTransaccion();
            Platform.runLater(() -> {
                switch (e.getTipo()){
                    case AGREGAR -> tablePresupuesto.refresh();
                }
            });
        }
    }
}
