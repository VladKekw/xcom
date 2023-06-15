package com.example.xcom;

import javafx.event.EventHandler;
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

public class XCOMBase {
    public enum State {
        Full, Half, Destroyed;
    }
    public Image i;
    public ImageView iv;

    public Rectangle r;
    public static Group xcomBaseGroup;
    public static ArrayList<SektoSoldier> humanBaseList = new ArrayList<>();
    public Label l;
    public XCOMBase(double x , double y){
        i = new Image(Main.class.getResource("humanbase.png").toString(), 600, 600, false, false);
        ImageView imageView = new ImageView(i);
        imageView.setX(x+5);
        imageView.setY(y+5);
        imageView.setPreserveRatio(true);
        this.iv = imageView;
        this.iv = imageView;

        this.l = new Label("X-COM Base");
        this.l.setFont(new Font("Arial", 28));
        this.l.setTextFill(Color.WHITE);
        this.l.setTranslateX(x + 150);
        this.l.setTranslateY(y - 50);


        this.r = new Rectangle(x , y , 610, 610);
        this.r.setFill(Color.TRANSPARENT);
        r.setStrokeWidth(5);
        this.r.setStroke(Color.BLACK);

        this.xcomBaseGroup = new Group();
        this.xcomBaseGroup.getChildren().addAll(this.l,this.r,this.iv);

        this.xcomBaseGroup.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage window = new Stage();
                window.initModality(Modality.NONE);
                window.setTitle("Units attacking X-COM base");
                window.setMinWidth(200);
                window.setMinHeight(100);
                VBox layout = new VBox();
                ComboBox<String> list = new ComboBox<>();
                list.setPrefWidth(150);
                int count = 1;
                for (SektoSoldier s: Main.XCOMattackers)
                {
                    list.getItems().add(count +" "+ s.toString());
                    count++;
                }
                Button okButton = new Button("OK");
                okButton.setOnAction(e -> {
                    Main.XCOMattackers.remove(list.getValue().toString().split(" "));
                    window.close();
                });
                layout.getChildren().addAll(list, okButton);
                Scene scene = new Scene(layout, 300, 150);
                window.setScene(scene);
                window.showAndWait();
            }
        });
    }
}
