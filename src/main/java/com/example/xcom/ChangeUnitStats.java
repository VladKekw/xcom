package com.example.xcom;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ChangeUnitStats {

    public static void display(int index) {
        Stage window=new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Choose an object to change");
        window.setMinWidth(250);
        window.setMinHeight(500);
        VBox layout = new VBox(11);
        //layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.BASELINE_LEFT);
        List<String> unitchange = new ArrayList<String>();
        unitchange= Main.UnitGetParamsToChange(index);


        Label nameLabel=new Label();
        nameLabel.setText("Name:");
        TextField nameText = new TextField();
        nameText.setText(unitchange.get(0));

        Label healthLabel=new Label();
        healthLabel.setText("Health:");
        TextField healthText = new TextField();
        healthText.setText(unitchange.get(1));

        Label damageLabel=new Label();
        damageLabel.setText("Damage:");
        TextField damageText = new TextField();
        damageText.setText(unitchange.get(2));

        Label xLabel=new Label();
        xLabel.setText("X:");
        TextField xText = new TextField();
        xText.setText(unitchange.get(3));

        Label yLabel=new Label();
        yLabel.setText("Y:");
        TextField yText = new TextField();
        yText.setText(unitchange.get(4));

        CheckBox active = new CheckBox();
        Label activeLabel = new Label("Choose state of unit");
        if(unitchange.get(5).equals("true"))
        {
            active.setSelected(true);
        }
        else active.setSelected(false);

        Button okButton = new Button("ok");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String sName= nameText.getText();
                double sDamage = Double.parseDouble(damageText.getText());
                int sHealth= Integer.parseInt(healthText.getText());
                double sX = Double.parseDouble(xText.getText());
                double sY = Double.parseDouble(yText.getText());
                boolean act;
                if(active.isSelected()){act = true;}
                else act =false;

                Main.changeUnit(sHealth,sName,sDamage,sX,sY,act,index-1);
                window.close();
            }
        });
        layout.getChildren().addAll(nameLabel,nameText,healthLabel,healthText,damageLabel,damageText,
                xLabel,xText,yLabel,yText,activeLabel,active,okButton);


        Scene scene=new Scene(layout,303,300);
        window.setScene(scene);
        window.showAndWait();
    }
}


