package com.example.game.projectgamejavafx;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.lang.classfile.Label;

public class Plane {
    //move
    public static boolean right = false;
    public static boolean left = false;
    public static boolean up = false;
    public static boolean down = false;
    public static boolean speedUp = false;
    public static boolean shot = false;
    //values
    public int playerSpeed = 3;
    //image
    @FXML
    private ImageView playerPlane;
    private Image planeUpImage;
    private Image planeDownImage;
    private Image planeStockImage;
    @FXML
    public Label labelScore;
    @FXML
    private AnchorPane gamePane;

    public Plane(ImageView playerPlane, AnchorPane gamePane){
        this.playerPlane = playerPlane;
        initializeImages();
        this.gamePane = gamePane;
    }

    public int start(){
        return playerSpeed = 3;
    }
    public int stop(){
        return playerSpeed = 0;
    }

    public void initializeImages(){
        planeUpImage = new Image(getClass().getResource("/com/example/game/images/UpImage.png").toString());
        planeDownImage = new Image(getClass().getResource("/com/example/game/images/downImage.png").toString());
        planeStockImage = new Image(getClass().getResource("/com/example/game/images/sImage.png").toString());
    }
    public void handleKeyPress(KeyCode code){
        switch (code){
            case D -> right = true;
            case A -> left = true;
            case W -> {
                up = true;
                playerPlane.setImage(planeUpImage);
            }
            case S -> {
                down = true;
                playerPlane.setImage(planeDownImage);
            }
            case SHIFT -> speedUp = true;
        }
    }
        public void handleKeyRelease(KeyCode code){
        switch (code){
            case D -> right = false;
            case A -> left = false;
            case W -> {
                up = false;
                playerPlane.setImage(planeStockImage);
            }
            case S -> {
                down = false;
                playerPlane.setImage(planeStockImage);
            }
            case SHIFT -> speedUp = false;
        }
    }

    public void updatePosition(){
        double speedX = 0;
        double speedY = 0;

        if(right && playerPlane.getLayoutX() < 600f){
            speedX += playerSpeed;
        }
        if(left && playerPlane.getLayoutX() > 10f){
            speedX -= playerSpeed;
        }
        if(up && playerPlane.getLayoutY() > 0f) {
            playerPlane.setLayoutY(playerPlane.getLayoutY() - playerSpeed);
            speedY -= playerSpeed;
        }
        if(down && playerPlane.getLayoutY() < 550f){
            playerPlane.setLayoutY(playerPlane.getLayoutY() + playerSpeed);
            speedY += playerSpeed;
        }
        if(speedX != 0 && speedY != 0){
            speedX /= Math.sqrt(2);
            speedY /= Math.sqrt(2);
        }
        if(speedUp){
            speedX *= 1.8;
            speedY *= 1.8;
        }

        playerPlane.setLayoutX(playerPlane.getLayoutX() + speedX);
        playerPlane.setLayoutY(playerPlane.getLayoutY() + speedY);
    }
    public void shoot(ImageView image){
        double startX = playerPlane.getLayoutX() + playerPlane.getBoundsInParent().getWidth() / 2;
        double startY = playerPlane.getLayoutY() + playerPlane.getBoundsInParent().getHeight() / 2;
        double directionX = Math.cos(Math.toRadians(playerPlane.getRotate()));
        double directionY = Math.sin(Math.toRadians(playerPlane.getRotate()));

        Bullet bullet = new Bullet(startX, startY, directionX, directionY, gamePane);


    }
}
