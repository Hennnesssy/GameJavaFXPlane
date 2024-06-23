package com.example.game.projectgamejavafx;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Plane {
    //move
    public static boolean right = false;
    public static boolean left = false;
    public static boolean up = false;
    public static boolean down = false;
    public static boolean speedUp = false;
    //values
    public int playerSpeed = 3;
    public double angleSpeed = 1.5;
    //image
    @FXML
    private ImageView playerPlane;
    private Image planeUpImage;
    private Image planeDownImage;
    private Image planeStockImage;

    public void changePlaneImage(String direction){
        switch (direction){
            case "up":
                playerPlane.setImage(planeUpImage);
                break;
            case "down":
                playerPlane.setImage(planeDownImage);
                break;
            default:
                playerPlane.setImage(planeStockImage);
        }
    }
    void planController(){
        if(playerPlane.getLayoutY() > 550f){

        }
    }
}
