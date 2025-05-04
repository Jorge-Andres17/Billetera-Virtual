module co.edu.uniquindio.billeteravirtual.billeteravirtual {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.billeteravirtual.billeteravirtual to javafx.fxml;
    exports co.edu.uniquindio.billeteravirtual.billeteravirtual;
    opens co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController to javafx.fxml;
    exports co.edu.uniquindio.billeteravirtual.billeteravirtual.ViewController;
    opens co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller to javafx.fxml;
    exports co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller;


}