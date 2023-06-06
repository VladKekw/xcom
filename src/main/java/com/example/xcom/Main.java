package com.example.xcom;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.security.PublicKey;
import java.util.ArrayList;
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
    public void lifecycle()
    {
        for(SektoSoldier soldier: army)
        {
            soldier.checkHealth();
            soldier.updateHeliumLevel();
            soldier.getHeliumLevel().consumeHelium();
            SektoSoldier.cleanupCorpses();

        }
    }
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
    public static ArrayList<String> UnitGetParamsToChange(int index) {
        SektoSoldier s = army.get(index - 1);
        ArrayList<String> array = new ArrayList<>();
        array.add(s.getName());
        array.add(Integer.toString(s.getHealth()));
        array.add(Double.toString(s.getDamage()));
        array.add(Double.toString(s.getPosX()));
        array.add(Double.toString(s.getPosY()));
        array.add(Boolean.toString(s.isActive()));
        return array;
    }
    public static void changeUnit(int hp, String name, double dmg, double x, double y, boolean act, int index)
    {
        SektoSoldier s = army.get(index);
        s.setName(name);
        s.setHealth(hp);
        s.setDamage(dmg);
        s.setPosX(x);
        s.setPosY(y);
        s.setActive(act);

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
        HumanBase humanBase = new HumanBase(1900, 800);
        City city = new City(60,900);
        group.getChildren().addAll(alienBase.alienBaseGroup, humanBase.humanBaseGroup,city.cityGroup);
        /*group.getChildren().add(humanBase.humanBaseGroup);

*/

        Scene scene = new Scene(endGroup, 1532, 800);
        AnimationTimer timer =  new AnimationTimer() {
            @Override
            public void handle(long l) {
                lifecycle();
            }
        };
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                        Choose.choose();
                    }
                }
            }
        });


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.INSERT)
                {
                    CreateUnit.createUnit();
                }
                if(keyEvent.getCode() == KeyCode.ESCAPE)
                {
                    for(SektoSoldier soldier: army)
                    {
                        soldier.setActive(false);
                    }
                }
                if(keyEvent.getCode() == KeyCode.DELETE)
                {
                    for (int i = army.size() - 1; i >= 0; --i) {
                        SektoSoldier soldier = army.get(i);
                        if (soldier.isActive()) {
                            group.getChildren().remove(soldier);

                            army.remove(i);
                            soldier.getG().setVisible(false);
                        }
                    }
                }
                if (keyEvent.getCode() == KeyCode.W) {
                    for (SektoSoldier soldier : army) {
                        if (soldier.isActive()) {
                            soldier.moveUp();

                        }
                    }
                }
                if(keyEvent.getCode() == KeyCode.S)
                {
                    for(SektoSoldier soldier: army)
                    {
                        if(soldier.isActive())
                        {
                            soldier.moveDown();
                        }
                    }
                }
                if(keyEvent.getCode() == KeyCode.D)
                {
                    for(SektoSoldier soldier: army)
                    {
                        if(soldier.isActive())
                        {
                            soldier.moveRight();
                        }
                    }
                }
                if(keyEvent.getCode() == KeyCode.A)
                {
                    for(SektoSoldier soldier: army)
                    {
                        if(soldier.isActive())
                        {
                            soldier.moveLeft();
                        }
                    }
                }
                if(keyEvent.isControlDown())
                {
                    if(keyEvent.getCode() == KeyCode.V)
                    {
                        for(SektoSoldier soldier : army)
                        {
                            if(soldier.isActive())
                            {
                                try {
                                    soldier.clone();
                                }
                                catch (CloneNotSupportedException e)
                                {
                                    throw new RuntimeException();
                                }
                            }
                        }
                    }
                }
            }
        });


        stage.setTitle("Hello!");
        timer.start();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}