module ec.edu.espol.bacongameg4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    opens ec.edu.espol.bacongameg4 to javafx.fxml;
    exports ec.edu.espol.bacongameg4;
}