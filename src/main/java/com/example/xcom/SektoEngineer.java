package com.example.xcom;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class SektoEngineer extends SektoSoldier{
    public SektoEngineer(int health, String name,double dmg, double x, double y, boolean active) {
        super(health,name,dmg,x,y,active);
        this.setSpeed(50);
        this.i = new Image(Main.class.getResource("engineer.png").toString(),70,70,false,false);
        this.imageView.setImage(this.i);
        imageView.setX(x+25);
        imageView.setY(y+25);
        heliumLevel = new HeliumLevel();
        drawUnit(heliumLevel,name,x,y);
        this.g.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) flipAcitve();
            }

        });


    }
    public SektoEngineer()
    {
        super(100,"engineer",20,0,0, false);
        this.i = new Image(Main.class.getResource("60934.png").toString(),70,70,false,false);
        this.imageView.setImage(this.i);
        HeliumLevel heliumLevel1 = new HeliumLevel();
        drawUnit(heliumLevel1,"engineer",0,0);

        this.g.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) flipAcitve();
            }

        });
    }

    @Override
    public String toString() {
        return "SektoEngineer{" +
                "posX=" + getPosX() +
                ", damage=" + getDamage() +
                ", speed=" + getSpeed() +
                ", posY=" + getPosY() +
                ", health=" + getHealth() +
                ", name='" + getName() + '\'' +
                ", isActive=" + isActive() +
                ", heliumLevel=" + heliumLevel +
                '}';
    }



}
