package com.example.game.projectgamejavafx;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.util.Random;

public class EnemyPlane {
    @FXML
    private ImageView enemyPlaneView;
    private TranslateTransition enemyPlaneTransition;
    private AnchorPane gamePane;
    private final int BG_WIDTH = 698;
    private AnimationTimer shootTimer;
    public EnemyPlane(AnchorPane gamePane){
        this.gamePane = gamePane;

        initializationEnemyPlane();
        initializationAnimation();
    }
    public void initializationEnemyPlane(){
        Random random = new Random();
        int randomY = random.nextInt(550 - 100 + 1) + 100;
        Image enemyPlanImage = new Image(getClass().getResource("/com/example/game/images/enemyPlane.png").toString());
        enemyPlaneView = new ImageView(enemyPlanImage);

        enemyPlaneView.setLayoutX(720);
        enemyPlaneView.setLayoutY(randomY);

        gamePane.getChildren().add(enemyPlaneView);
    }
    public void initializationAnimation(){
        enemyPlaneTransition = new TranslateTransition(Duration.millis(4000), enemyPlaneView);
        enemyPlaneTransition.setFromX(0);
        enemyPlaneTransition.setToX(BG_WIDTH * -1 - 300);
        enemyPlaneTransition.setInterpolator(Interpolator.LINEAR);
        enemyPlaneTransition.setCycleCount(Animation.INDEFINITE);
        enemyPlaneTransition.play();
    }

    public void initializationShooting(){
        shootTimer = new AnimationTimer(){
            private long lastShootTime = 0;
            private final long shootInterval = 2_000_000_000;

            @Override
            public void handle(long now){
                if(now - lastShootTime >=shootInterval){
                    shoot();
                    lastShootTime = now;
                }
            }
        };
        shootTimer.start();
    }

    public void shoot(Plane plane){
        double startX = enemyPlaneView.getLayoutX() + enemyPlaneView.getBoundsInParent().getWidth() / 2;
        double startY = enemyPlaneView.getLayoutY() + enemyPlaneView.getBoundsInParent().getHeight() / 2;
        double directionX = -1;
        double directionY = 0;

        Bullet bullet = new Bullet(startX, startY, directionX, directionY, gamePane, GameController.getInstance().getPlane());
    }

    public void start(){
        enemyPlaneTransition.play();
    }

    public void pause(){
        enemyPlaneTransition.pause();
    }
    public void stop(){
        enemyPlaneTransition.stop();
    }
    public ImageView getEnemyPlane() {
        return enemyPlaneView;
    }
    public void remove(){
        enemyPlaneTransition.stop();
        gamePane.getChildren().remove(enemyPlaneView);
    }
}
