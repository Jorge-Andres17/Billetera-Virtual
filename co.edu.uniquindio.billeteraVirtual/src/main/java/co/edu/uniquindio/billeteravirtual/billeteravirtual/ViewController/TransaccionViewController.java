package co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TransaccionViewController {

    @FXML
    private DatePicker DatePickerFecha;

    @FXML
    private Button btnRealizarTransaccion;

    @FXML
    private ComboBox<?> cbCategorias;

    @FXML
    private ComboBox<?> cbCuentaDestino;

    @FXML
    private ComboBox<?> cbCuentaOrigen;

    @FXML
    private TableView<?> tableTransaccion;

    @FXML
    private TableColumn<?, ?> tcCategoria;

    @FXML
    private TableColumn<?, ?> tcCuentaDestino;

    @FXML
    private TableColumn<?, ?> tcCuentaOrigen;

    @FXML
    private TableColumn<?, ?> tcFecha;

    @FXML
    private TableColumn<?, ?> tcMonto;

    @FXML
    private TextField textMonto;

    @FXML
    void onRealizarTransaccion(ActionEvent event) {

    }

}
