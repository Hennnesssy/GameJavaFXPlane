package com.example.game.projectgamejavafx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class GameController {
    private final int BG_WIDTH = 698;
    @FXML
    private ImageView bg1;
    @FXML
    private ImageView bg2;
    @FXML
    private ImageView playerPlane;
    private ParallelTransition parallelTransition;
    public static boolean right = false;
    public static boolean left = false;
    public static boolean up = false;
    public static boolean down = false;
    public static boolean speedUp = false;
    private Image planeUpImage;
    private Image planeDownImage;
    private  Image planeStockImage;//!!
    private static GameController instance;
    public int playerSpeed = 3;
    public double angleSpeed = 1.5;

    public GameController(){
        instance = this;
    }
    public static GameController getInstance(){
        return instance;
    }

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
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            double speedX = 0;
            double speedY = 0;
            //movement
            if(right && playerPlane.getLayoutX() < 600f){
                //playerPlane.setLayoutX(playerPlane.getLayoutX() + playerSpeed);
                speedX += playerSpeed;
            }
            if(left && playerPlane.getLayoutX() > 10f){
                //playerPlane.setLayoutX(playerPlane.getLayoutX() - playerSpeed);
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


    };

    @FXML
    void initialize() {
        assert bg1 != null : "fx:id=\"bg1\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert bg2 != null : "fx:id=\"bg2\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert playerPlane != null : "fx:id=\"playerPlane\" was not injected: check your FXML file 'hello-view.fxml'.";

        planeUpImage = new Image(getClass().getResource("/com/example/game/images/UpImage.png").toString());
        planeStockImage = new Image(getClass().getResource("/com/example/game/images/sImage.png").toString());
        planeDownImage = new Image(getClass().getResource("/com/example/game/images/downImage.png").toString());



        playerPlane.setImage(planeStockImage);

        TranslateTransition bgOneTransition = new TranslateTransition(Duration.millis(5000), bg1);
        bgOneTransition.setFromX(0);
        bgOneTransition.setToX(BG_WIDTH * -1);
        bgOneTransition.setInterpolator(Interpolator.LINEAR);

        TranslateTransition bgTwoTransition = new TranslateTransition(Duration.millis(5000), bg2);
        bgTwoTransition.setFromX(0);
        bgTwoTransition.setToX(BG_WIDTH * -1);
        bgTwoTransition.setInterpolator(Interpolator.LINEAR);

        TranslateTransition enemyPlaneTransition = new TranslateTransition(Duration.millis(4000), enemyPlane);
        enemyPlaneTransition.setFromX(0);
        enemyPlaneTransition.setToX(BG_WIDTH * -1 - 300);
        enemyPlaneTransition.setInterpolator(Interpolator.LINEAR);
        enemyPlaneTransition.setCycleCount(Animation.INDEFINITE);
        enemyPlaneTransition.play();

        parallelTransition = new ParallelTransition(bgOneTransition, bgTwoTransition);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.play();

        timer.start();
    }
}