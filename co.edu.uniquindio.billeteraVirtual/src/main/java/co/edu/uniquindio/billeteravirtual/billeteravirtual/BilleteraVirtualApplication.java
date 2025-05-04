package co.edu.uniquindio.billeteravirtual.billeteravirtual;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController.BilleteraVirtualViewUsuario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class BilleteraVirtualApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BilleteraVirtualApplication.class.getResource("Login_Registro.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("BilleteraVirtual");
        stage.setScene(scene);
        stage.show();
    }

    public static void mostrarVentanaUsuario() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BilleteraVirtualApplication.class.getResource("BilleteraVirtualViewUsuario.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Panel Usuario");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mostrarVentanaAdministrador() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BilleteraVirtualApplication.class.getResource("BilleteraVirutalViewAdmin.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Panel Administrador");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch();
    }
}