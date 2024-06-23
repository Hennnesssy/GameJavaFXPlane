package com.example.game.projectgamejavafx;
import javafx.scene.control.Label;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GameController {
    private final int BG_WIDTH = 698;
    @FXML
    private ImageView bg1;
    @FXML
    private ImageView bg2;
    @FXML
    private ImageView playerPlane;
    @FXML
    private ImageView enemyPlane;

    @FXML
    private ImageView enemyTower;
    @FXML
    private Label labelPause;
    @FXML
    private Label labelLose;
    private Plane playerPlaneController;
    private ParallelTransition parallelTransition;
    private Image planeUpImage;
    private Image planeDownImage;
    private Image planeStockImage;
    private TranslateTransition enemyPlaneTransition;
    private TranslateTransition enemyTowerTransition;
    @FXML
    private AnchorPane gamePane;

    public static boolean isPause = false;
    private static GameController instance;

    public GameController(){
        instance = this;
    }
    public static GameController getInstance(){
        return instance;
    }

    @FXML
    void initialize() {
        assert bg1 != null : "fx:id=\"bg1\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert bg2 != null : "fx:id=\"bg2\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert playerPlane != null : "fx:id=\"playerPlane\" was not injected: check your FXML file 'hello-view.fxml'.";

        planeUpImage = new Image(getClass().getResource("/com/example/game/images/UpImage.png").toString());
        planeStockImage = new Image(getClass().getResource("/com/example/game/images/sImage.png").toString());
        planeDownImage = new Image(getClass().getResource("/com/example/game/images/downImage.png").toString());
        playerPlane.setImage(planeStockImage);

        playerPlaneController = new Plane(playerPlane, gamePane);
        //background
        TranslateTransition bgOneTransition = new TranslateTransition(Duration.millis(5000), bg1);
        bgOneTransition.setFromX(0);
        bgOneTransition.setToX(BG_WIDTH * -1);
        bgOneTransition.setInterpolator(Interpolator.LINEAR);

        TranslateTransition bgTwoTransition = new TranslateTransition(Duration.millis(5000), bg2);
        bgTwoTransition.setFromX(0);
        bgTwoTransition.setToX(BG_WIDTH * -1);
        bgTwoTransition.setInterpolator(Interpolator.LINEAR);

        parallelTransition = new ParallelTransition(bgOneTransition, bgTwoTransition);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.play();

        enemyPlaneTransition = new TranslateTransition(Duration.millis(4000), enemyPlane);
        enemyPlaneTransition.setFromX(0);
        enemyPlaneTransition.setToX(BG_WIDTH * -1 - 300);
        enemyPlaneTransition.setInterpolator(Interpolator.LINEAR);
        enemyPlaneTransition.setCycleCount(Animation.INDEFINITE);
        enemyPlaneTransition.play();


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                playerPlaneController.updatePosition();

                if(isPause && !labelPause.isVisible()){
                    playerPlaneController.stop();
                    parallelTransition.pause();
                    enemyPlaneTransition.pause();
                    labelPause.setVisible(true);
                } else if (!isPause && labelPause.isVisible()) {
                    playerPlaneController.start();
                    parallelTransition.play();
                    enemyPlaneTransition.play();
                    labelPause.setVisible(false);
                }

                if(playerPlane.getBoundsInParent().intersects(enemyPlane.getBoundsInParent())){
                    playerPlaneController.stop();
                    parallelTransition.stop();
                    enemyPlaneTransition.stop();
                    labelLose.setVisible(true);
                }
            }
        };
        timer.start();
    }

    public Plane getPlane(){
        return playerPlaneController;
    }

    public ImageView getEnemyPlane() {
        return enemyPlane;
    }

    public ImageView getEnemyTower() {
        return enemyTower;
    }
}
//ss