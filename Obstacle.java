/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sidescroller;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Steven and Andrew and Ryan
 */
public class Obstacle {
    private boolean passable = false;
    private ImageView img;
    private Rectangle Bounds;
    
    Obstacle(String imgPath, double fitHeight, double fitWidth){
        img = new ImageView(imgPath);
        img.setFitHeight(fitHeight);
        img.setFitWidth(fitWidth);
        Bounds = new Rectangle(fitHeight,fitWidth);
    }
    
    void update(){
        if(Bounds.getX() != img.getX()){
            Bounds.setX(img.getX());
        }
        if(Bounds.getY() != img.getY()){
            Bounds.setY(img.getY());
        }
    }
    
    public Rectangle getBounds(){
        return Bounds;
    }
    public void setX(double newX){
        img.setX(newX);
        update();
    }
    public void setY(double newY){
        img.setY(newY);
        update();
    }
    
    
    public ImageView getImage(){
        return img;
    }
    public void relocate(double x, double y){
        img.setX(x);
        img.setY(y);
    }
}
