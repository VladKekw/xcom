package com.example.xcom;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class SektoLeader extends SektoEngineer{
    public SektoLeader(int health, String name,double dmg, double x, double y, boolean active)
 {
     super(health,name,dmg,x,y, active);
     this.setSpeed(80);
     this.i = new Image(Main.class.getResource("60942.png").toString(),70,70,false,false);
     this.imageView.setImage(this.i);
     imageView.setX(x-3);
     imageView.setY(y+15);
     heliumLevel = new HeliumLevel();
     drawUnit(heliumLevel,name,x,y);
     this.g.setOnMouseClicked(new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent event) {
             if (event.getButton().equals(MouseButton.PRIMARY)) flipAcitve();
         }

     });

 }
 public SektoLeader()
 {
     super(250,"leader",100,0,0,false);
     this.imageView.setImage(this.i);
     HeliumLevel heliumLevel1 = new HeliumLevel();
     drawUnit(heliumLevel1,"leader",0,0);
     this.g.setOnMouseClicked(new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent event) {
             if (event.getButton().equals(MouseButton.PRIMARY)) flipAcitve();
         }

     });

 }
    

}
