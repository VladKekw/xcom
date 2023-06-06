package com.example.xcom;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Comparator;

public class SektoSoldier implements Cloneable, Comparable<SektoSoldier>, Comparator<SektoSoldier> {
    public final static int minhealth = 0;
    public final static int maxhealth = 100;
    private double posX;
    private  double damage;

    public HeliumLevel getHeliumLevel() {
        return heliumLevel;
    }

    public void setHeliumLevel(HeliumLevel heliumLevel) {
        this.heliumLevel = heliumLevel;
    }

    private int speed;
    private int attackRange = 40;
    private double posY;
    private int health;
    private String name;
    private boolean isActive;
    protected Label namelabel = new Label();
    protected Circle radius = new Circle();
    protected Line life = new Line();
    protected Image i;
    protected Group g;
    protected ImageView imageView;
    protected HeliumLevel heliumLevel;
    protected Rectangle rectActive = new Rectangle();
    protected Label heliumLabel = new Label();

    public void updateHeliumLevel(){
        this.heliumLabel.setText(Integer.toString(heliumLevel.getHeliumAmount()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SektoSoldier soldier = (SektoSoldier) o;

        if (Double.compare(soldier.posX, posX) != 0) return false;
        if (Double.compare(soldier.damage, damage) != 0) return false;
        if (speed != soldier.speed) return false;
        if (attackRange != soldier.attackRange) return false;
        if (Double.compare(soldier.posY, posY) != 0) return false;
        if (health != soldier.health) return false;
        if (isActive != soldier.isActive) return false;
        return name.equals(soldier.name);
    }
    public void checkHealth()
    {
        if(this.heliumLevel.getHeliumAmount()==0)
        {
            this.setHealth(this.health-1);
        }
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(posX);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(damage);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + speed;
        temp = Double.doubleToLongBits(posY);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + health;
        result = 31 * result + name.hashCode();
        return result;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    static {
        System.out.println("Static initialization is complete!");
        /*Main.createSoldier(100, "Soldier1",30,100,100,false);
        Main.createEngineer(100, "engineer",30,100,100,false);
        Main.createLeader(100, "leader",30,100,100,false);*/
    }
    public void flipAcitve(){
        this.isActive =!this.isActive;
        if(this.isActive)
        {
            this.rectActive.setStroke(Color.RED);
        }
        else {
            this.rectActive.setStroke(Color.TRANSPARENT);
        }
    }

    public void drawUnit(HeliumLevel lvl,String n, double x, double y ) {
        rectActive = new Rectangle(x, y,105,105);
        rectActive.setStrokeWidth(2);
        rectActive.setFill(Color.TRANSPARENT);
        if (isActive) {
            rectActive.setStroke(Color.RED);
        }
        else{
            rectActive.setStroke(Color.TRANSPARENT);
        }
        heliumLabel.setLayoutX(x+78);
        heliumLabel.setLayoutY(y-5);
        heliumLabel.setTextFill(Color.WHITE);
        heliumLabel.setFont(new Font(15));
        heliumLabel.setText(Integer.toString(lvl.getHeliumAmount()));

        namelabel.setText(n);
        namelabel.setLayoutX(x);
        namelabel.setLayoutY(y-12);
        namelabel.setFont(new Font(15));
        namelabel.setTextFill(Color.WHITE);
        life.setStrokeWidth(6);
        life.setStroke(Color.GREEN);
        life.setStartX(x+10);
        life.setStartY(y + 15);
        life.setEndY(y + 15);
        life.setEndX(x + 48);
        radius = new Circle(attackRange);
        radius.setCenterY(y + 60);
        radius.setCenterX(x + 35);
        radius.setFill(Color.TRANSPARENT);
        g = new Group(rectActive,namelabel,life,imageView,heliumLabel,radius);
        Main.group.getChildren().addAll(g);


    }

    public SektoSoldier(int health, String name,double dmg, double x, double y, boolean isActive) {
        this.health = health;
        this.name = name;
        this.damage = dmg;
        this.posX = x;
        this.posY = y;
        this.isActive= isActive;
        heliumLevel = new HeliumLevel();
        this.i = new Image(Main.class.getResource("soldier.png").toString(), 95, 95, false, false);
        imageView = new ImageView(i);
        imageView.setX(x +5);
        imageView.setY(y + 5);
        drawUnit(heliumLevel,name,posX,posY);
        this.g.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) flipAcitve();
            }

        });
    }

    public Group getG() {
        return g;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
        if(isActive)
            this.rectActive.setStroke(Color.RED);

        else this.rectActive.setStroke(Color.TRANSPARENT);
    }
    public SektoSoldier() {
        this(100, "Soldier",30, 0, 0, false);
        System.out.println("Sekto-Soldier has been created");
        HeliumLevel heliumLevel1 =new HeliumLevel();
        drawUnit(heliumLevel1,name,0,0);

        this.g.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) flipAcitve();
            }

        });
    }
    public static void cleanupCorpses() {
        for (int i = 0; i < Main.army.size(); i++) {
            SektoSoldier soldier = Main.army.get(i);
            if (soldier.getHealth() <= 0) {
                Main.group.getChildren().remove(soldier.g);
                /*logger.log(i + " died ");*/
                Main.army.remove(i);
            }

        }
    }
    public void moveLeft() {
        if (!isActive) return;
        {
             {
                double y = g.getLayoutX() - speed;
                this.g.setLayoutX(y);
            }
        }
    }
    public void moveRight() {
        if (!isActive) return;
        {
            {
                double y = g.getLayoutX() +speed;
                this.g.setLayoutX(y);
            }
        }
    }
    public void moveUp() {
        if (!isActive) return;
        {
            {
                double y = g.getLayoutY() - speed;
                this.g.setLayoutY(y);
            }
        }
    }
    public void moveDown() {
        if (!isActive) return;
        {
                double y = g.getLayoutY() + speed;
                this.g.setLayoutY(y);
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        SektoSoldier tmp = new SektoSoldier();
        Main.group.getChildren().remove(tmp.g);
        tmp.heliumLevel = (HeliumLevel) this.heliumLevel.clone();
        if (this instanceof SektoLeader) {
            tmp = new SektoLeader(this.getHealth(), "Cloned",this.getDamage(), this.getPosX(), this.getPosY(),this.isActive);
            tmp.drawUnit(this.heliumLevel,"Cloned",this.getPosX(),this.getPosY());
            Main.army.add(tmp);
            return tmp;
        }
        if (this instanceof SektoEngineer) {
            tmp = new SektoEngineer(this.getHealth(), "Cloned",this.getDamage(), this.getPosX(), this.getPosY(),this.isActive);
            tmp.drawUnit(this.heliumLevel,"Cloned",this.getPosX(),this.getPosY());
            Main.army.add(tmp);
            return tmp;
        }
        if (this instanceof SektoSoldier) {
            tmp = new SektoSoldier(this.getHealth(), "Cloned",this.getDamage(), this.getPosX(), this.getPosY(),this.isActive);
            tmp.drawUnit(this.heliumLevel,"Cloned",this.getPosX(),this.getPosY());
            Main.army.add(tmp);
            return tmp;
        }


        return null;

    }

    @Override
    public String toString() {
        return "SektoSoldier{" +
                "posX=" + posX +
                ", damage=" + damage +
                ", speed=" + speed +
                ", posY=" + posY +
                ", health=" + health +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                ", heliumLevel=" + heliumLevel +
                '}';
    }

    @Override
    public int compareTo(SektoSoldier o) {
        return 0;
    }

    @Override
    public int compare(SektoSoldier o1, SektoSoldier o2) {
        return 0;
    }
}
