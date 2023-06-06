package com.example.xcom;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.security.PublicKey;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;


public class Main extends Application  {
    public static Background bg;
    public BorderPane layout = new BorderPane();
    static Random random = new Random();
    public static AnchorPane group = new AnchorPane();
    public static BorderPane wrap = new BorderPane();
    public static List<SektoSoldier> army = new CopyOnWriteArrayList<>();
    public static void createSoldier(int hp, String name, double dmg, double x, double y,boolean active)
    {
        if(name.equals("")) {
            name = "soldier";
        }
        if(hp<0)
        {
            hp =100;
        }
        if( dmg < 0)
        {
            dmg = 50;
        }
        if(x==0)
        {
            x = random.nextDouble(1500);
        }
        if(y==0)
        {
            y = random.nextDouble(800);
        }
        SektoSoldier soldier = new SektoSoldier(hp,name,dmg,x,y,active);
        Main.army.add(soldier);
    }
    public void SpawnBackground() throws FileNotFoundException {
        bg = new Background();
        group.getChildren().add(bg.getBackGrp());
    }
    public static void createEngineer(int hp, String name, double dmg, double x, double y, boolean active)
    {
        if(name.equals("")) {
            name = "engineer";
        }
        if(hp<0)
        {
            hp =100;
        }
        if( dmg < 0)
        {
            dmg = 50;
        }
        if(x==0)
        {
            x = random.nextDouble(1500);
        }
        if(y==0)
        {
            y = random.nextDouble(800);
        }
        SektoEngineer soldier = new SektoEngineer(hp,name,dmg,x,y,active);
        Main.army.add(soldier);
    }
    public static void createLeader(int hp, String name, double dmg, double x, double y, boolean active)
    {
        if(name.equals("")) {
            name = "leader";
        }
        if(hp<0)
        {
            hp =100;
        }
        if( dmg < 0)
        {
            dmg = 50;
        }
        if(x==0)
        {
            x = random.nextDouble(1500);
        }
        if(y==0)
        {
            y = random.nextDouble(800);
        }
        SektoLeader soldier = new SektoLeader(hp,name,dmg,x,y, active);
        Main.army.add(soldier);
    }


    @Override
    public void start(Stage stage) throws IOException {
        SpawnBackground();
        ScrollPane scrollPane = new ScrollPane(group);
        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setMaxHeight(Background.border.getHeight());
        scrollPane.setMaxWidth(Background.border.getWidth());
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        BorderPane layout = new BorderPane(scrollPane);
        BorderPane endGroup = new BorderPane(layout);

        AlienBase alienBase =new AlienBase(60, 100);
        group.getChildren().add(alienBase.alienBaseGroup);



        Scene scene = new Scene(endGroup, 1532, 800);



        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.INSERT)
                {
                    CreateUnit.createUnit();
                }
            }
        });


        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}