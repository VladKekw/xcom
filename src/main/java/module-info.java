module com.example.xcom {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.xcom to javafx.fxml;
    exports com.example.xcom;
}