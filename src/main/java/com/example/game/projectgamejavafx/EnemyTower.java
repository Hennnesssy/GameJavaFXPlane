package com.example.game.projectgamejavafx;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class EnemyTower {
    private TranslateTransition enemyTowerTransition;
    private ImageView enemyTowerView;
    private AnchorPane gamePane;
    private final int BG_WIDTH = 698;
    private Plane playerPlane;
    @FXML
    private ImageView enemyPlaneView;

    public EnemyTower(AnchorPane gamePane, Plane playerPlane){
        this.gamePane = gamePane;
        this.playerPlane = playerPlane;

        enemyTowerView = new ImageView(new Image(getClass().getResource("/com/example/game/images/tower.png").toString()));
        Platform.runLater(()->gamePane.getChildren().add(enemyTowerView));
        initializationAnimation();
    }

    public void initializationAnimation(){
        enemyTowerTransition = new TranslateTransition(Duration.millis(5000),  enemyTowerView);
        enemyTowerTransition.setFromX(15);
        enemyTowerTransition.setFromX(BG_WIDTH * -1);
        enemyTowerTransition.setInterpolator(Interpolator.LINEAR);
        enemyTowerTransition.setCycleCount(Animation.INDEFINITE);
        enemyTowerTransition.play();
    }
    public void shoot(Plane plane){
        if (plane != null && gamePane.getChildren().contains(enemyPlaneView)) {
            double startX = enemyTowerView.getLayoutX() + enemyTowerView.getBoundsInParent().getWidth() / 2;
            double startY = enemyTowerView.getLayoutY() + enemyTowerView.getBoundsInParent().getHeight() / 2;
            double directionX = 0;
            double directionY = 1;

            new EnemyPlaneBullet(startX, startY, directionX, directionY, gamePane, plane);
            System.out.println("enemy shoot");

        }
    }

    public void remove(){
        Platform.runLater(()-> gamePane.getChildren().remove(enemyTowerView));
    }

}
