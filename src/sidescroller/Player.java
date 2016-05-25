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
 * @author Steven
 */
public class Player {
    private ImageView img;
    int airFrames, GROUND;
    double yVelocity;
    Rectangle Bounds;
    
    Player(String imgPath, double fitHeight, double fitWidth, Group root){
        img = new ImageView(imgPath);
        img.setFitHeight(fitHeight);
        img.setFitWidth(fitWidth);
        System.out.println(fitHeight + "   " + fitWidth);
        Bounds = new Rectangle((int)fitHeight,(int)fitWidth);
        Bounds.setFill(Color.BEIGE);
        root.getChildren().add(Bounds);
        GROUND = 150;
    }
    
    void update(){
        Gravity();
        if(Bounds.getX() != img.getX()){
            Bounds.setX(img.getX());
        }
        if(Bounds.getY() != img.getY()){
            Bounds.setY(img.getY());
        }
    }
    
    

    //Make a Gravity/Jumping Function
    public void Gravity(){
        if(img.getY() < GROUND){
            airFrames++;
            yVelocity += .8;
            
        }
        else{
            if(img.getY() > GROUND){
                img.setY(GROUND);
                yVelocity = 0;
            }
            airFrames = 0;
            
        }
        
        img.setY(img.getY() + yVelocity);
        
    }
    
    
    
    
    
    //Support Methods
    
    public void moveX(double x){
        img.setX(img.getX() + x);
    }
    public void moveY(double y){
        img.setX(img.getY() + y);
    }
    public void Jump(){
        yVelocity = -10;
    }
    public ImageView getImage(){
        return img;
    }
    public double getCenterX(){
        return img.getX() + img.getFitWidth()/2;
    }
    public double getCenterY(){
        return img.getY() + img.getFitHeight()/2;
    }
    
    
}
