/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sidescroller;

import javafx.scene.image.ImageView;

/**
 *
 * @author Steven
 */
public class Obstacle {
    private boolean passable = false;
    private ImageView img;
    
    Obstacle(String imgPath){
        img = new ImageView(imgPath);
    }
    
    
    public ImageView getImage(){
        return img;
    }
    
    public void relocate(double x, double y){
        img.setX(x);
        img.setY(y);
    }
}
