package com.example.game.projectgamejavafx;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class EnemyTower {
    private TranslateTransition enemyTowerTransition;
    private ImageView enemyTowerView;
    private AnchorPane gamePane;
    private final int BG_WIDTH = 698;
    private final double towerWidth = 60;
    private final double towerHeight = 100;
    private boolean isDestroyed = false;

    public EnemyTower(AnchorPane gamePane) {
        this.gamePane = gamePane;

        enemyTowerView = new ImageView(new Image(getClass().getResource("/com/example/game/images/tower.png").toString()));
        enemyTowerView.setLayoutX(710);
        enemyTowerView.setLayoutY(560);
        enemyTowerView.setFitWidth(towerWidth);
        enemyTowerView.setFitHeight(towerHeight);
        gamePane.getChildren().add(enemyTowerView);

        initializationAnimation();
    }

    private void initializationAnimation() {
        enemyTowerTransition = new TranslateTransition(Duration.millis(5500), enemyTowerView);
        enemyTowerTransition.setFromX(0);
        enemyTowerTransition.setToX(-BG_WIDTH - 1 - 100);
        enemyTowerTransition.setInterpolator(Interpolator.LINEAR);
        enemyTowerTransition.setCycleCount(Animation.INDEFINITE);
        enemyTowerTransition.play();
    }

    public void start() {
        enemyTowerTransition.play();
    }

    public void stop() {
        if (enemyTowerTransition != null) {
            enemyTowerTransition.stop();
        }
    }
    public void pause() {
        if (enemyTowerTransition != null) {
            enemyTowerTransition.pause();
        }
    }
    public void remove() {
        gamePane.getChildren().remove(enemyTowerView);
        isDestroyed = true;
    }

    public ImageView getEnemyTowerView() {
        return enemyTowerView;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}