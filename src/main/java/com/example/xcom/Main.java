package com.example.xcom;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;


public class Main extends Application {
    public static Rectangle minimapBorderRect;
    public static Rectangle activeAreaRect;
    public static ImageView imageviewmap;
    public static Background backgroundscrn;
    public static boolean firsttime = true;
    static LocalDateTime beginTime = LocalDateTime.now();
    static int frames = 0;
    public static double minimapScale = 0.1;
    static Random random = new Random();
    public static AnchorPane group = new AnchorPane();
    public static AlienBase alienBase;

    static {
        try {
            alienBase = new AlienBase(60, 100);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static XCOMBase xcomBase = new XCOMBase(1900, 800);
    public static City city = new City(60, 900);

    public static List<SektoSoldier> army = new CopyOnWriteArrayList<SektoSoldier>();
    public static List<SektoSoldier> XCOMattackers = new ArrayList<SektoSoldier>();
    public static List<SektoSoldier> CITYattackers = new ArrayList<SektoSoldier>();
    public static List<SektoSoldier> AlienBase = new ArrayList<SektoSoldier>();

    public Main() throws FileNotFoundException {
    }

    public void lifecycle() {
        for (SektoSoldier soldier : army) {
            soldier.checkHealth();
            soldier.updateHeliumLevel();
            soldier.getHeliumLevel().consumeHelium();
            soldier.reDraw();
            soldier.checkForCollision();
        }
        SektoSoldier.deleteDead();
    }

    public static void createSoldier(int hp, String name, double dmg, double x, double y, boolean active) {
        if (name.equals("")) {
            name = "soldier";
        }
        if (hp < 0) {
            hp = 100;
        }
        if (dmg < 0) {
            dmg = 50;
        }
        if (x == 0) {
            x = random.nextDouble(1500);
        }
        if (y == 0) {
            y = random.nextDouble(800);
        }
        SektoSoldier soldier = new SektoSoldier(hp, name, dmg, x, y, active);
        Main.army.add(soldier);
    }

    public void SpawnBackground() throws FileNotFoundException {
        backgroundscrn = new Background();
        group.getChildren().add(backgroundscrn.getBackGrp());
    }

    public static void createEngineer(int hp, String name, double dmg, double x, double y, boolean active) {
        if (name.equals("")) {
            name = "engineer";
        }
        if (hp < 0) {
            hp = 100;
        }
        if (dmg < 0) {
            dmg = 50;
        }
        if (x == 0) {
            x = random.nextDouble(1500);
        }
        if (y == 0) {
            y = random.nextDouble(800);
        }
        SektoEngineer soldier = new SektoEngineer(hp, name, dmg, x, y, active);
        Main.army.add(soldier);
    }

    public static void createLeader(int hp, String name, double dmg, double x, double y, boolean active) {
        if (name.equals("")) {
            name = "leader";
        }
        if (hp < 0) {
            hp = 100;
        }
        if (dmg < 0) {
            dmg = 50;
        }
        if (x == 0) {
            x = random.nextDouble(1500);
        }
        if (y == 0) {
            y = random.nextDouble(800);
        }
        SektoLeader soldier = new SektoLeader(hp, name, dmg, x, y, active);
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

    public void chooseActiveAreaPos(double x, double y) {
        if (x > 0.86) {
            activeAreaRect.setX(297);
        } else {
            activeAreaRect.setX((x * 3060 - 100) * minimapScale);
        }
        if (y > 0.8) {
            activeAreaRect.setY(550 + 154);
        } else {
            activeAreaRect.setLayoutY(550 + (y * 2000) * minimapScale);
        }
    }

    public static void changeUnit(int hp, String name, double dmg, double x, double y, boolean act, int index) {
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


        group.getChildren().addAll(alienBase.alienBaseGroup, xcomBase.xcomBaseGroup, city.cityGroup);


        Scene scene = new Scene(endGroup, 1532, 800);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                lifecycle();
                LocalDateTime nextTime = LocalDateTime.now();
                if (ChronoUnit.SECONDS.between(beginTime, nextTime) > 0) {
                    frames = 0;
                    beginTime = nextTime;
                    if (imageviewmap != null) {
                        endGroup.getChildren().remove(imageviewmap);
                        endGroup.getChildren().remove(activeAreaRect);
                    } else {
                        if (firsttime) {
                            firsttime = false;
                            minimapBorderRect = new Rectangle(5000 * Main.minimapScale + 6.0, 2500 * Main.minimapScale + 6.0);
                            minimapBorderRect.setFill(Color.TRANSPARENT);
                            minimapBorderRect.setStrokeWidth(3);
                            minimapBorderRect.setStroke(Color.DARKRED);
                            minimapBorderRect.setX(0);
                            minimapBorderRect.setY(550);
                            endGroup.getChildren().add(minimapBorderRect);

                        }
                    }
                    final WritableImage SNAPSHOT = group.snapshot(new SnapshotParameters(), null);
                    imageviewmap = new ImageView(SNAPSHOT);
                    imageviewmap.setFitHeight(2500 * Main.minimapScale + 6.0);
                    imageviewmap.setX(0);
                    imageviewmap.setY(550);
                    imageviewmap.setFitWidth(5000 * Main.minimapScale + 6.0);
                    endGroup.getChildren().add(imageviewmap);
                    Scale scale = new Scale();

                    scale.setX(Main.minimapScale);
                    scale.setY(Main.minimapScale);

                    double xlocation = scrollPane.getHvalue();
                    double ylocation = scrollPane.getVvalue();
                    activeAreaRect = new Rectangle((scene.getWidth() + 550) * minimapScale, (scene.getHeight() + 150) * minimapScale);
                    Group activeRectGroup = new Group();
                    activeRectGroup.getChildren().add(activeAreaRect);
                    activeAreaRect.setFill(Color.TRANSPARENT);
                    activeAreaRect.setStrokeWidth(2);
                    activeAreaRect.setStroke(Color.PURPLE);
                    /*activeAreaRect.setX(0 + (xlocation * 3060) * minimapScale);
                    activeAreaRect.setLayoutY(550 + (ylocation * 2000) * minimapScale);*/
                    chooseActiveAreaPos(xlocation, ylocation);
                    endGroup.getChildren().add(activeAreaRect);
                } else frames++;
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
                if (minimapBorderRect.contains(mouseEvent.getX(), mouseEvent.getY())) {
                    double virtualX = (mouseEvent.getX()) / 500;
                    double virtualY = (mouseEvent.getY() - 550) / 250;
                    scrollPane.setHvalue(virtualX);
                    scrollPane.setVvalue(virtualY - 0.02);
                }
            }
        });


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.INSERT) {
                    CreateUnit.createUnit();
                }
                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    for (SektoSoldier soldier : army) {
                        soldier.setActive(false);
                    }
                }
                if (keyEvent.getCode() == KeyCode.DELETE) {
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
                            System.out.println("moved");
                        }
                    }
                }
                if (keyEvent.getCode() == KeyCode.S) {
                    for (SektoSoldier soldier : army) {
                        if (soldier.isActive()) {
                            System.out.println("moved");
                            soldier.moveDown();
                        }
                    }
                }
                if (keyEvent.getCode() == KeyCode.D) {
                    for (SektoSoldier soldier : army) {
                        if (soldier.isActive()) {
                            System.out.println("moved");
                            soldier.moveRight();
                        }
                    }
                }
                if (keyEvent.getCode() == KeyCode.A) {
                    for (SektoSoldier soldier : army) {
                        if (soldier.isActive()) {
                            soldier.moveLeft();
                        }
                    }
                }
                if (keyEvent.isControlDown()) {
                    if (keyEvent.getCode() == KeyCode.V) {
                        for (SektoSoldier soldier : army) {
                            if (soldier.isActive()) {
                                try {
                                    soldier.clone();
                                } catch (CloneNotSupportedException e) {
                                    throw new RuntimeException();
                                }
                            }
                        }
                    }
                    if (keyEvent.getCode() == KeyCode.F) {
                        Stage window = new Stage();
                        window.initModality(Modality.NONE);
                        window.setTitle("Info menu");
                        window.setMinWidth(800);
                        window.setMinHeight(500);
                        AnchorPane anchorPane = new AnchorPane();
                        VBox layout = new VBox(40);
                        ListView<SektoSoldier> list = new ListView<>();
                        list.setPrefWidth(300);
                        TextField inputField = new TextField("Input params");
                        inputField.setPrefWidth(130);
                        Button sortButton = new Button("Sort");
                        Button findButton = new Button("Find");
                        Button searchButton = new Button("Search");
                        Label sortLabal = new Label("Sorting options");
                        Label searchLabal = new Label("Search options");
                        Label macroLabal = new Label("Macroobjects list");


                        sortButton.setPrefWidth(140);
                        findButton.setPrefWidth(130);
                        searchButton.setPrefWidth(140);
                        ComboBox options = new ComboBox<>();
                        options.getItems().add("Health");
                        options.getItems().add("Damage");
                        options.getItems().add("Name");
                        options.getItems().add("HeliumAmount");
                        ComboBox options2 = new ComboBox<>();
                        options2.getItems().add("Health");
                        options2.getItems().add("Damage");
                        options2.getItems().add("Name");
                        ComboBox macro = new ComboBox<>();
                        macro.getItems().add("Alien Base");
                        macro.getItems().add("X-COM Base");
                        macro.getItems().add("City");
                        macro.getItems().add("Without shelter");
                        Button countSoldier = new Button("Count soldiers");
                        Button countEngineers = new Button("Count engineers");
                        Button countLeaders = new Button("Count leaders");

                        AnchorPane.setTopAnchor(list, 30.0);
                        AnchorPane.setLeftAnchor(list, 200.0);
                        AnchorPane.setRightAnchor(list, 200.0);

                        AnchorPane.setLeftAnchor(countSoldier, 75.0);
                        AnchorPane.setTopAnchor(countSoldier, 75.0);

                        AnchorPane.setLeftAnchor(countEngineers, 75.0);
                        AnchorPane.setTopAnchor(countEngineers, 175.0);

                        AnchorPane.setTopAnchor(countLeaders, 275.0);
                        AnchorPane.setLeftAnchor(countLeaders, 75.0);

                        AnchorPane.setTopAnchor(options2, 225.0);
                        AnchorPane.setTopAnchor(options, 65.0);

                        AnchorPane.setRightAnchor(options, 55.0);
                        AnchorPane.setRightAnchor(options2, 55.0);

                        AnchorPane.setLeftAnchor(layout, 55.0);
                        AnchorPane.setTopAnchor(layout, 35.0);

                        AnchorPane.setLeftAnchor(macro, 55.0);
                        AnchorPane.setTopAnchor(macro, 325.0);

                        AnchorPane.setRightAnchor(searchButton, 55.0);
                        AnchorPane.setRightAnchor(sortButton, 55.0);
                        AnchorPane.setTopAnchor(sortButton, 125.0);
                        AnchorPane.setTopAnchor(inputField, 275.0);
                        AnchorPane.setTopAnchor(searchButton, 325.0);
                        AnchorPane.setTopAnchor(findButton, 375.0);
                        AnchorPane.setLeftAnchor(findButton, 55.0);
                        AnchorPane.setRightAnchor(inputField, 55.0);

                        AnchorPane.setLeftAnchor(macroLabal, 75.0);
                        AnchorPane.setTopAnchor(macroLabal, 285.0);

                        AnchorPane.setRightAnchor(searchLabal, 55.0);
                        AnchorPane.setTopAnchor(searchLabal, 175.0);

                        AnchorPane.setTopAnchor(sortLabal, 30.0);
                        AnchorPane.setRightAnchor(sortLabal, 55.0);

                        layout.getChildren().addAll(countSoldier, countEngineers, countLeaders);
                        countSoldier.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {

                                List<SektoSoldier> countSold = army.stream().filter(sold -> (!(sold instanceof SektoEngineer) && !(sold instanceof SektoLeader))).collect(Collectors.toList());
                                if (list.getItems() == null) {
                                    for (SektoSoldier s : countSold) {
                                        list.getItems().add(s);
                                    }
                                } else {
                                    list.getItems().clear();
                                    for (SektoSoldier s : countSold) {
                                        list.getItems().add(s);
                                    }
                                }
                            }
                        });
                        countEngineers.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                List<SektoSoldier> counteng = army.stream().filter(sold -> sold instanceof SektoEngineer && !(sold instanceof SektoLeader)).collect(Collectors.toList());
                                if (list.getItems() == null) {
                                    for (SektoSoldier s : counteng) {
                                        list.getItems().add(s);
                                    }
                                } else {
                                    list.getItems().clear();
                                    for (SektoSoldier s : counteng) {
                                        list.getItems().add(s);
                                    }
                                }
                            }
                        });
                        countLeaders.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                List<SektoSoldier> countlead = army.stream().filter(sold -> sold instanceof SektoLeader ).collect(Collectors.toList());
                                if (list.getItems() == null) {
                                    for (SektoSoldier s : countlead) {
                                        list.getItems().add(s);
                                    }
                                } else {
                                    list.getItems().clear();
                                    for (SektoSoldier s : countlead) {
                                        list.getItems().add(s);
                                    }
                                }
                            }
                        });

                        searchButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if (list.getItems() == null) {
                                    String srchtxt;
                                    String choose = options2.getValue().toString();
                                    if(choose.equals("Health"))
                                    {
                                        srchtxt = inputField.getText();
                                        String finalSrchtxt1 = srchtxt;
                                        List<SektoSoldier> found = army.stream().filter(sold -> sold.getHealth() == Integer.parseInt(finalSrchtxt1) ).collect(Collectors.toList());
                                        for(SektoSoldier s: found)
                                        {
                                            list.getItems().add(s);
                                        }
                                    }
                                    if(choose.equals("Damage"))
                                    {
                                        srchtxt = inputField.getText();
                                        String finalSrchtxt = srchtxt;
                                        List<SektoSoldier> found = army.stream().filter(sold -> sold.getDamage() == Double.parseDouble(finalSrchtxt) ).collect(Collectors.toList());
                                        for(SektoSoldier s: found)
                                        {
                                            list.getItems().add(s);
                                        }
                                    }
                                    if(choose.equals("Name"))
                                    {
                                        srchtxt = inputField.getText();
                                        String finalSrchtxt = srchtxt;
                                        String finalSrchtxt2 = srchtxt;
                                        List<SektoSoldier> found = army.stream().filter(sold -> sold.getName().equals(finalSrchtxt2) ).collect(Collectors.toList());
                                        for(SektoSoldier s: found)
                                        {
                                            list.getItems().add(s);
                                        }
                                    }
                                }
                                else {
                                    list.getItems().clear();
                                    String srchtxt;
                                    String choose = options2.getValue().toString();
                                    if(choose.equals("Health"))
                                    {
                                        srchtxt = inputField.getText();
                                        String finalSrchtxt1 = srchtxt;
                                        List<SektoSoldier> found = army.stream().filter(sold -> sold.getHealth() == Integer.parseInt(finalSrchtxt1) ).collect(Collectors.toList());
                                        for(SektoSoldier s: found)
                                        {
                                            list.getItems().add(s);
                                        }
                                    }
                                    if(choose.equals("Damage"))
                                    {
                                        srchtxt = inputField.getText();
                                        String finalSrchtxt = srchtxt;
                                        List<SektoSoldier> found = army.stream().filter(sold -> sold.getDamage() == Double.parseDouble(finalSrchtxt) ).collect(Collectors.toList());
                                        for(SektoSoldier s: found)
                                        {
                                            list.getItems().add(s);
                                        }
                                    }
                                    if(choose.equals("Name"))
                                    {
                                        srchtxt = inputField.getText();
                                        String finalSrchtxt = srchtxt;
                                        String finalSrchtxt2 = srchtxt;
                                        List<SektoSoldier> found = army.stream().filter(sold -> sold.getName().equals(finalSrchtxt2) ).collect(Collectors.toList());
                                        for(SektoSoldier s: found)
                                        {
                                            list.getItems().add(s);
                                        }
                                    }
                                }
                            }
                        });
                        sortButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if (options.getValue().toString().contains("Name")) {
                                    List<SektoSoldier> sorted = army.stream().sorted().collect(Collectors.toList());
                                    if (list.getItems() != null) {
                                        list.getItems().clear();
                                        for (SektoSoldier s : sorted) {
                                            list.getItems().add(s);
                                        }
                                    } else {
                                        for (SektoSoldier s : sorted) {
                                            list.getItems().add(s);
                                        }
                                    }
                                }
                                if (options.getValue().toString().contains("Damage")) {
                                    List<SektoSoldier> sorted = army.stream().sorted(Comparator.comparingDouble(SektoSoldier::getDamage).reversed()).collect(Collectors.toList());
                                    if (list.getItems() != null) {
                                        list.getItems().clear();
                                        for (SektoSoldier s : sorted) {
                                            list.getItems().add(s);
                                        }
                                    } else {
                                        for (SektoSoldier s : sorted) {
                                            list.getItems().add(s);
                                        }
                                    }
                                }
                                if (options.getValue().toString().contains("Health")) {
                                    List<SektoSoldier> sorted = army.stream().sorted(Comparator.comparingInt(SektoSoldier::getHealth).reversed()).collect(Collectors.toList());
                                    if (list.getItems() != null) {
                                        list.getItems().clear();
                                        for (SektoSoldier s : sorted) {
                                            list.getItems().add(s);
                                        }
                                    } else {
                                        for (SektoSoldier s : sorted) {
                                            list.getItems().add(s);
                                        }
                                    }
                                }
                                if (options.getValue().toString().contains("HeliumAmount")) {
                                    List<SektoSoldier> sorted = army.stream().sorted(Comparator.comparingInt(SektoSoldier::getHeliumamount).reversed()).collect(Collectors.toList());
                                    if (list.getItems() != null) {
                                        list.getItems().clear();
                                        for (SektoSoldier s : sorted) {
                                            list.getItems().add(s);
                                        }
                                    } else {
                                        for (SektoSoldier s : sorted) {
                                            list.getItems().add(s);
                                        }
                                    }
                                }

                            }
                        });
                        findButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if (macro.getValue().toString().contains("Alien Base")) {
                                    if (list.getItems() != null) {
                                        list.getItems().clear();
                                        for (SektoSoldier s : AlienBase) {
                                            list.getItems().add(s);
                                        }
                                    }
                                }
                                if (macro.getValue().toString().contains("X-COM Base")) {
                                    if (list.getItems() != null) {
                                        list.getItems().clear();
                                        for (SektoSoldier s : XCOMattackers) {
                                            list.getItems().add(s);
                                        }
                                    }
                                }
                                if (macro.getValue().toString().contains("City")) {
                                    if (list.getItems() != null) {
                                        list.getItems().clear();
                                        for (SektoSoldier s : CITYattackers) {
                                            list.getItems().add(s);
                                        }
                                    }
                                }
                                if (macro.getValue().toString().contains("Without shelter")) {
                                    if (list.getItems() != null) {
                                        list.getItems().clear();
                                        List<SektoSoldier> noShelter = army.stream().filter(sold -> !sold.isGotShelter() ).collect(Collectors.toList());
                                        for (SektoSoldier s : noShelter) {
                                            list.getItems().add(s);
                                        }
                                    }
                                }

                            }
                        });


                        anchorPane.getChildren().addAll(list, layout, sortButton, searchButton, options, options2,
                                macro, inputField, findButton, searchLabal, macroLabal, sortLabal);
                        Scene scene = new Scene(anchorPane, 800, 500);
                        window.setScene(scene);
                        window.showAndWait();
                    }
                }
            }
        });


        stage.setTitle("X-COM");
        timer.start();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}