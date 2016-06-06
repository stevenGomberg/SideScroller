/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sidescroller;

import java.util.ArrayList;
import java.util.*;
import java.awt.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Steven and Andrew and Ryan
 */
public class SideScroller extends Application {
    Stage stage = new Stage();
    Group root = new Group();
    Scene scene = new Scene(root,500,500);
    ImageView map;
    
    //Constants
    int frame = 0,  GROUND = 150, MAP_HEIGHT = 2000, MAP_WIDTH = 2000;
    double xVelocity = 0;
    boolean screenBound = false;
    Player centerPlayer;
    
    //Corner Targets
    Point c1 = new Point(20, 20);
    Point c4 = new Point(1980, 20);
    Point c3 = new Point(1980, 1980);
    Point c2 = new Point(20, 1980);
    Point[] corners = {c1, c2, c3, c4};
    Point target = c1;
    
    //Lists
    ArrayList<Player> activePlayers = new ArrayList<Player>();
    ArrayList<Obstacle> activeNodes = new ArrayList<Obstacle>();
    
    
    @Override
    public void start(Stage stage) {
        scene.setFill(Color.RED);
        stage.setScene(scene);
        

        map = new ImageView("Images/Map.png");
        root.getChildren().add(map);
        
        EventHandler<KeyEvent> onPressed = new EventHandler<KeyEvent>(){
            public void handle(KeyEvent event){
                if(event.getCode().getName().equals("Right")){
                    xVelocity = -10;
                }
                if(event.getCode().getName().equals("Left")){
                    xVelocity = 10;
                }
                if(event.getCode().getName().equals("Up")){
                    activePlayers.get(0).Jump();
                }
            }
        };
        
        EventHandler<KeyEvent> onReleased = new EventHandler<KeyEvent>(){
            public void handle(KeyEvent event){
                if(event.getCode().getName().equals("Right")){
                    xVelocity = 0;
                }
                if(event.getCode().getName().equals("Left")){
                    xVelocity = 0;
                }
            }
        };

        
        
        startTimeline();
        
        scene.setOnKeyPressed(onPressed);
        scene.setOnKeyReleased(onReleased);
        stage.setTitle("Voila!");
        stage.show();
        
        Player mrg = new Player("Images/MrG.png",50,50);
        mrg.getImage().setX(250 - mrg.getImage().getFitWidth());
        mrg.getImage().setY(0);
        root.getChildren().add(mrg.getImage());
        activePlayers.add(mrg);
        centerPlayer = mrg;
        
        Obstacle square = new Obstacle("Images/Square.png",100,100);
        square.relocate(0, GROUND - 50);
        root.getChildren().addAll(square.getImage());
        activeNodes.add(square);
        //Testing Square Disabled
        //Obstacle square2 = new Obstacle("Images/Square.png",100,100);
        //square2.relocate(800, GROUND - 50);
        //activeNodes.add(square2);
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    boolean checkMovement(Player p, double moveX, double moveY){
        p.moveBounds(moveX, moveY);
        for(Obstacle o:activeNodes){
            if(o.getBounds().getBoundsInParent().intersects(p.getBounds().getBoundsInParent())){
                //Can't Move
                return false;
            }
        }
        //Can Move
        return true;
    }
    
    
    
    void startTimeline(){
        EventHandler<ActionEvent> perFrame;
        perFrame = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                
                Player closest;
                int count = 0;
                double d = 10000.00;
                
                //Messy and not setup for multiplayer. Needs to be Fixed VVVVVV -Steven
                for(Player p:activePlayers){
                    
                    if(screenBound && checkMovement(p, -(xVelocity - 10), p.yVelocity)){
                        p.getImage().setX(p.getImage().getX() - xVelocity);
                    }
                    p.update();
                    
                    double playerDistance = Math.sqrt(Math.pow(Math.abs(p.getImage().getX() - target.getX()), 2) + Math.pow(Math.abs(p.getImage().getY() - target.getY()), 2));
                    
                    if(playerDistance < d && playerDistance > 20)
                    {
                    	centerPlayer = p;
                    	d = playerDistance;
                    }else if(playerDistance < d)
                    {
                    	centerPlayer = p;
                    	d = playerDistance;
                    	if(count < 4)
                    	{
                    		target = corners[count];
                    		count++;
                    	}else
                    	{
                    		count = 0;
                    		target = corners[count];
                    		count++;
                    	}
                    }
                }
                    
                //Move Map
                if(checkMovement(centerPlayer, -(xVelocity - 10), centerPlayer.yVelocity)){
                    if(map.getX() + xVelocity > -MAP_WIDTH + scene.getWidth() && map.getX() + xVelocity < 0){
                        if(!screenBound){
                            map.setX(map.getX() + xVelocity);
                        }
                        else{
                            if(centerPlayer.getCenterX() == 235){
                                screenBound = false;
                                return;
                            }
                        }
                    }
                    else{
                        screenBound = true;
                    }
                }
                
                //Move Obstacles
                if(!screenBound){
                    for(Obstacle o: activeNodes){
                        o.setX(o.getImage().getX() + xVelocity);
                    }
                }   
            }
        };

        
        
        
        
        KeyFrame oneFrame = new KeyFrame(Duration.millis(1000/60),perFrame);
        Timeline timeline = new Timeline(oneFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    
    
    
    
    
}
