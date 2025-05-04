package co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller.CrudCategoriaController;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Mapping.dto.CategoriaDto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CrudCategoriaViewController {
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

    private void obtenerCategorias() {
        listaCategorias.addAll(crudCategoriaController.obtenerCategorias());

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

    @FXML
    void onActualizar(ActionEvent event) {

    }

    @FXML
    void onAgregar(ActionEvent event) {
        agregar();
    }

    private void agregar() {
        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        if(datosValidos(nombre,descripcion)){
            if (!listaCategorias.stream()
                    .anyMatch(dto -> dto.nombre().equalsIgnoreCase(nombre))) {
                if (crudCategoriaController.agregarCategoria(nombre, descripcion)) {
                    listaCategorias.add(crudCategoriaController.agregarCategoriaDto(nombre, descripcion));
                    limpiarCampos();
                /*mostrarMensaje(TITULO_USUARIO_AGREGADO,
                        HEADER_INCOMPLETO,
                        CONTENIDO_USUARIO_AGREGADO,
                        Alert.AlertType.INFORMATION);*/
                } else {
                /*mostrarMensaje(TITULO_USUARIO_NO_AGREGADO,
                        HEADER_INCOMPLETO,
                        CONTENIDO_USUARIO_NO_AGREGADO,
                        Alert.AlertType.ERROR);*/
                }
            }
        }else {
            /*mostrarMensaje(TITULO_INCOMPLETO,
                    HEADER_INCOMPLETO,
                    CONTENIDO_INCOMPLETO,
                    Alert.AlertType.WARNING);*/
        }
    }

    private void limpiarCampos() {
    }

    private boolean datosValidos(String nombre, String descripcion) {
        if(txtDescripcion.getText().isEmpty() || txtNombre.getText().isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    @FXML
    void onNuevo(ActionEvent event) {

    }
    
    @FXML
    void onEliminar(ActionEvent event) {
        eliminar();
    }

    private void eliminar() {
        CategoriaDto categoriaSeleccionada = tableCategoria.getSelectionModel().getSelectedItem();
        if (categoriaSeleccionada != null) {
            if (crudCategoriaController.eliminarCategoria(categoriaSeleccionada.idCategoria())) {
                listaCategorias.remove(categoriaSeleccionada);
                limpiarCampos();
            } else {
                System.out.println("No se pudo eliminar la cuenta con ID " );
            }
        } else {
            System.out.println("No se ha seleccionado ninguna cuenta para eliminar.");
        }

    }

}
