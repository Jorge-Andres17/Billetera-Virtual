package co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller.CrudCategoriaController;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Mapping.dto.CategoriaDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Optional;
import static co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.BilleteraVirtualConstantes.*;

public class CrudCategoriaViewController{
    CrudCategoriaController crudCategoriaController;
    ObservableList<CategoriaDto> listaCategorias = FXCollections.observableArrayList();
    CategoriaDto selectedCategoria;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    @FXML
    private TableView<CategoriaDto> tableCategoria;

    @FXML
    private TableColumn<CategoriaDto, String> tcDescripcion;

    @FXML
    private TableColumn<CategoriaDto, String> tcIdCategoria;

    @FXML
    private TableColumn<CategoriaDto, String> tcNombre;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtNombre;

    @FXML
    void initialize() {
        crudCategoriaController = new CrudCategoriaController();
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerCategorias();
        tableCategoria.getItems().clear();
        tableCategoria.setItems(listaCategorias);
        listenerSelection();
    }

    private void initDataBinding() {
        tcIdCategoria.setCellValueFactory
                (cellData -> new SimpleStringProperty
                        (String.valueOf(cellData.getValue().idCategoria())));
        tcNombre.setCellValueFactory
                (cellData -> new SimpleStringProperty
                        (cellData.getValue().nombre()));
        tcDescripcion.setCellValueFactory
                (cellData -> new SimpleStringProperty
                        (cellData.getValue().descripcion()));
    }

    private void obtenerCategorias() {
        listaCategorias.addAll(crudCategoriaController.obtenerCategorias());
    }

    private void listenerSelection() {
        tableCategoria.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedCategoria = newSelection;
            mostrarinformacionCategoria(selectedCategoria);
        });
    }

    private void mostrarinformacionCategoria(CategoriaDto selectedCategoria) {
        if(selectedCategoria != null){
            txtNombre.setText(selectedCategoria.nombre());
            txtDescripcion.setText(selectedCategoria.descripcion());
        }
    }

    @FXML
    void onNuevo(ActionEvent event) {
        nueva();
    }

    @FXML
    void onAgregar(ActionEvent event) {
        agregar();
    }

    @FXML
    void onEliminar(ActionEvent event) {
        eliminar();
    }

    @FXML
    void onActualizar(ActionEvent event) {
        actualizarCategoria();
    }

    private void nueva() {
        txtNombre.setText("");
        txtDescripcion.setText("");
        tableCategoria.getSelectionModel().clearSelection();
    }

    private void agregar() {
        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        if (datosValidos(nombre, descripcion)) {
            boolean existeEnLista = listaCategorias.stream()
                    .anyMatch(dto -> dto.nombre().equalsIgnoreCase(nombre));
            if (!existeEnLista) {
                CategoriaDto nuevaCategoria = crudCategoriaController.agregarCategoriaDto(nombre, descripcion);
                if (nuevaCategoria != null) {
                    listaCategorias.add(nuevaCategoria);

                    limpiarCampos();
                    mostrarMensaje(TITULO_CATEGORIA_AGREGADO,
                            HEADER_CATEGORIA_AGREGADO,
                            BODY_CATEGORI_AGREGADO,
                            Alert.AlertType.INFORMATION);
                } else {
                    mostrarMensaje(TITULO_CATEGORIA_NO_AGREGADO,
                            HEADER_CATEGORIA_NO_AGREGADO,
                            BODY_CATEGORIA_NO_AGREGADO,
                            Alert.AlertType.ERROR);
                }
            } else {
                mostrarMensaje(TITULO_CATEGORIA_NO_AGREGADO,
                        HEADER_CATEGORIA_NO_AGREGADO,
                        BODY_CATEGORIA_NO_AGREGADO,
                        Alert.AlertType.WARNING);
            }
        } else {
            mostrarMensaje(TITULO_INCOMPLETO,
                    HEADER_INCOMPLETO,
                    BODY_INCOMPLETO,
                    Alert.AlertType.WARNING);
        }
    }

    private boolean datosValidos(String nombre, String descripcion) {
        if(txtDescripcion.getText().isEmpty() || txtNombre.getText().isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    private void eliminar() {
        CategoriaDto categoriaSeleccionada = tableCategoria.getSelectionModel().getSelectedItem();
        if (categoriaSeleccionada != null) {
            if(mostrarMensajeConfirmacion(MENSAJE_ELIMINAR_CATEGORIA)) {
                if (crudCategoriaController.eliminarCategoria(categoriaSeleccionada.nombre())) {
                    listaCategorias.remove(categoriaSeleccionada);
                    limpiarCampos();
                    mostrarMensaje(TITULO_ELIMINAR_CATEGORIA,
                            HEADER_ELIMINAR_CATEGORIA,
                            BODY_NOTIFICACION_ELIMINAR_CATEGORIA,
                            Alert.AlertType.INFORMATION);
                } else {
                    mostrarMensaje(TITULO_NO_ELIMINAR_CATEGORIA,
                            HEADER_NO_ELIMINAR_CATEGORIA,
                            BODY_NOTIFICACION_NO_ELIMINAR_CATEGORIA,
                            Alert.AlertType.ERROR);
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
                    CONTENIDO_NO_SELECCION,
                    Alert.AlertType.WARNING);
        }
    }

    private void actualizarCategoria() {
        CategoriaDto categoriaSeleccionada = tableCategoria.getSelectionModel().getSelectedItem();
        int index = tableCategoria.getSelectionModel().getSelectedIndex();

        if (categoriaSeleccionada != null && datosValidos(txtNombre.getText(), txtDescripcion.getText())) {
            String nuevoNombre = txtNombre.getText();
            String nuevaDescripcion = txtDescripcion.getText();
            boolean existeDuplicado = listaCategorias.stream()
                    .anyMatch(dto ->
                            dto.nombre().equalsIgnoreCase(nuevoNombre));

            if (existeDuplicado) {
                mostrarMensaje(TITULO_NO_ACTUALIZADA_CATEGORIA,
                        HEADER_NO_ACTUALIZADA_CATEGORIA,
                        BODY_CATEGORIA_YA_EXISTE,
                        Alert.AlertType.WARNING);
                return;
            }
            if (crudCategoriaController.actualizarCategoria(nuevoNombre, nuevaDescripcion)) {
                CategoriaDto categoriaActualizada = crudCategoriaController.actualizarCategoriaDto(nuevoNombre, nuevaDescripcion);
                listaCategorias.set(index, categoriaActualizada);
                tableCategoria.refresh();
                limpiarCampos();
                mostrarMensaje(TITULO_ACTUALIZADA_CATEGORIA,
                        HEADER_ACTUALIZADA_CATEGORIA,
                        BODY_NOTIFICACION_ACTUALIZADA_CATEGORIA,
                        Alert.AlertType.INFORMATION);
            } else {
                mostrarMensaje(TITULO_NO_ACTUALIZADA_CATEGORIA,
                        HEADER_NO_ACTUALIZADA_CATEGORIA,
                        BODY_NOTIFICACION_NO_ACTUALIZADA_CATEGORIA,
                        Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje(TITULO_NO_SELECCION,
                    HEADER_NO_SELECCION,
                    CONTENIDO_NO_SELECCION,
                    Alert.AlertType.WARNING);
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtDescripcion.clear();
        tableCategoria.getSelectionModel().clearSelection();
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
