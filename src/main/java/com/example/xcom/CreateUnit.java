package com.example.xcom;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateUnit {
    private static ToggleGroup gr;
    public static void createUnit()
    {
        Stage window=new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Enter new unit parameters!");
        window.setMinWidth(400);
        window.setMinHeight(700);

        VBox layout = new VBox(11);
        //layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Label nameLabel=new Label();
        nameLabel.setText("Name:");
        TextField nameText = new TextField();
        Label healthLabel=new Label();
        healthLabel.setText("Health:");
        TextField healthText = new TextField();


        Label damageLabel=new Label();
        damageLabel.setText("Damage:");
        TextField damageText = new TextField();


        Label xLabel=new Label();
        xLabel.setText("X:");
        TextField xText = new TextField();


        Label yLabel=new Label();
        yLabel.setText("Y:");
        TextField yText = new TextField();


        Label activelabel=new Label();
        activelabel.setText("Choose state of unit");
        CheckBox active = new CheckBox();
        boolean act;
        if(active.isSelected())
        {
             act = true;
        }
        else {
             act = false;
        }

        Label cLabel = new Label();
        cLabel.setText("Choose type of the Unit:");
        ComboBox comboBox = new ComboBox<>();
        comboBox.getItems().addAll(
                "1. Soldier",
                "2. Engineer",
                "3. Leader"
        );
        Button okButton=new Button("OK");
        okButton.setOnAction(e->{

            String sName= nameText.getText();
            double sDamage = Double.parseDouble(damageText.getText());
            int sHealth= Integer.parseInt(healthText.getText());
            double sX = Double.parseDouble(xText.getText());
            double sY = Double.parseDouble(yText.getText());
            /*String sA =Boolean.toString(active);*/


            if(comboBox.getValue()=="1. Soldier"){
                 Main.createSoldier(sHealth,sName,sDamage, sX, sY,act);}

            if(comboBox.getValue()=="2. Engineer"){
                Main.createEngineer(sHealth,sName,sDamage, sX, sY,act);
            }
            if(comboBox.getValue()=="3. Leader"){
                Main.createLeader(sHealth,sName,sDamage, sX, sY,act);
            }
            window.close();});
        layout.getChildren().addAll(nameLabel, nameText, healthLabel, healthText,
                damageLabel,damageText,
                xLabel, xText, yLabel, yText,active,
                cLabel,activelabel,comboBox, okButton);
        Scene scene=new Scene(layout,303,300);
        window.setScene(scene);
        window.showAndWait();
    }
    }

