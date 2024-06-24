package com.example.game.projectgamejavafx;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class EnemyPlane {
    //shootMove
    private TranslateTransition enemyPlaneTransition;
    private Timer shootTimer;
    private Random random = new Random();
    //
    @FXML
    private ImageView enemyPlaneView;
    private AnchorPane gamePane;
    private final int BG_WIDTH = 698;

//    private AnimationTimer shootTimer;
    private Plane playerPlane;
    public EnemyPlane(AnchorPane gamePane,Plane playerPlane){
        this.gamePane = gamePane;
        this.playerPlane = playerPlane;

        initializationEnemyPlane();
        initializationAnimation();
        startRandomShooting();
//        initializationShooting();
    }
    public void initializationEnemyPlane(){
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
        enemyPlaneTransition.setCycleCount(1);
        enemyPlaneTransition.setOnFinished(actionEvent -> remove());
        enemyPlaneTransition.play();
    }

//    public void initializationShooting(){
//        shootTimer = new AnimationTimer(){
//            private long lastShootTime = 0;
//            private final long shootInterval = 2_000_000_000;
//
//            @Override
//            public void handle(long now){
//                if(now - lastShootTime >=shootInterval){
//                    shoot(playerPlane);
//                    lastShootTime = now;
//                }
//            }
//        };
//        shootTimer.start();
//    }

//    public void startRandomShooting() {
//        shootTimer = new Timer();
//
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                shoot(playerPlane);
//                int delay = random.nextInt(3000 - 1000 + 1) + 1000;
//                shootTimer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        shoot(playerPlane);
//                    }
//                }, delay);
//            }
//        };
//
//        int initialDelay = random.nextInt(3000 - 1000 + 1) + 1000;
//        shootTimer.schedule(task, initialDelay);
//    }
public void startRandomShooting() {
    new AnimationTimer() {
        private long lastShootTime = 0;
        private final long shootInterval = 4000000000L;

        @Override
        public void handle(long now) {
            if (now - lastShootTime >= shootInterval) {
                Platform.runLater(() -> shoot(playerPlane));
                lastShootTime = now;
            }
        }
    }.start();
}
    public void shoot(Plane plane){
        if (plane != null && gamePane.getChildren().contains(enemyPlaneView)) {
            double startX = enemyPlaneView.getLayoutX() + enemyPlaneView.getBoundsInParent().getWidth() / 2;
            double startY = enemyPlaneView.getLayoutY() + enemyPlaneView.getBoundsInParent().getHeight() / 2;
            double directionX = -1;
            double directionY = 0;

            new EnemyPlaneBullet(startX, startY, directionX, directionY, gamePane, plane);
            System.out.println("enemy shoot");

        }
    }

    public void start(){
        enemyPlaneTransition.play();
    }

    public void pause(){
        enemyPlaneTransition.pause();
    }
    public void stop(){
        enemyPlaneTransition.stop();
        if (shootTimer != null) {
            shootTimer.cancel();
        }
    }
    public ImageView getEnemyPlane() {
        return enemyPlaneView;
    }
    public void remove(){
        stop();
        gamePane.getChildren().remove(enemyPlaneView);
//        enemyPlaneTransition.stop();
//        gamePane.getChildren().remove(enemyPlaneView);
//        if(shootTimer != null){
//            shootTimer.cancel();
    }
}
