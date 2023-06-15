package com.example.xcom;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class City {
    public Image i;
    public ImageView iv;
    public Rectangle r;
    public static Group cityGroup;
    Image img = new Image((Main.class.getResource("human.png").toString()), 500, 500, false, false);
    ImageView imgv = new ImageView(img);
    public static ArrayList<SektoSoldier> cityList = new ArrayList<>();
    public Label l;
    public Bounds getDefInParent() {
        return imgv.getBoundsInParent();
    }

    public City(double x, double y) {
        i = new Image(Main.class.getResource("city.png").toString(), 500, 500, false, false);
        ImageView imageView = new ImageView(i);
        imageView.setX(x + 5);
        imageView.setY(y + 5);
        imageView.setPreserveRatio(true);
        this.iv = imageView;

        this.l = new Label("City");
        this.l.setFont(new Font("Arial", 28));
        this.l.setTextFill(Color.WHITE);
        this.l.setTranslateX(x + 150);
        this.l.setTranslateY(y - 50);


        int dmg = 60;
        int hp = 100;


        this.r = new Rectangle(x, y, 510, 510);
        this.r.setFill(Color.TRANSPARENT);
        r.setStrokeWidth(5);
        this.r.setStroke(Color.BLACK);

        this.cityGroup = new Group();
        this.cityGroup.getChildren().addAll(this.l, this.r, this.iv);

        this.cityGroup.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage window = new Stage();
                window.initModality(Modality.NONE);
                window.setTitle("Units attacking city");
                window.setMinWidth(200);
                window.setMinHeight(100);
                VBox layout = new VBox();
                ComboBox<String> list = new ComboBox<>();
                list.setPrefWidth(150);
                int count = 1;
                for (SektoSoldier s: Main.CITYattackers)
                {
                    list.getItems().add(count +" "+ s.toString());
                    count++;
                }
                Button okButton = new Button("OK");
                okButton.setOnAction(e -> {
                    Main.CITYattackers.remove(list.getValue().toString().split(" "));
                    window.close();
                });
                layout.getChildren().addAll(list, okButton);
                Scene scene = new Scene(layout, 300, 150);
                window.setScene(scene);
                window.showAndWait();
            }
        });

        class CityDefender {

            public void createCityDefender() {
                imgv.setX(x + 75);
                imgv.setY(y - 65);

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Main.group.getChildren().add(imgv);
                    }
                }, 10000, 1000);


            }
        }
    }
}
