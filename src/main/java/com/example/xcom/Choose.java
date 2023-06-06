package com.example.xcom;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Choose {
    public static void choose() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Choose unit!");
        window.setMinWidth(200);
        window.setMinHeight(200);
        Label label = new Label("Choose an object to change");

        int count = 1;
        ComboBox c = new ComboBox();
        for (SektoSoldier s : Main.army) {
            c.getItems().add(count + " " +s);
            count++;
        }

        VBox layout = new VBox(11);
        layout.setAlignment(Pos.CENTER);

        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            if (c.getValue() != null) {
                String[] strChoice = c.getValue().toString().split(" ");
                if (c.getValue().toString().contains("SektoSoldier")) {
                    ChangeUnitStats.display(Integer.parseInt(strChoice[0]));
                }
                if (c.getValue().toString().contains("SektoEngineer")) {
                    ChangeUnitStats.display(Integer.parseInt(strChoice[0]));
                }
                if (c.getValue().toString().contains("Sektoleader")) {
                    ChangeUnitStats.display(Integer.parseInt(strChoice[0]));
                }
            }
            window.close();
        });
        layout.getChildren().addAll(label, c, okButton);
        Scene scene = new Scene(layout, 303, 300);
        window.setScene(scene);
        window.showAndWait();
    }
}