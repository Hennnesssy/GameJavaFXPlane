package com.example.game.projectgamejavafx;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;


public class Bullet {
    private ImageView bulletView;
    private double directionX, directionY;
    private double speed = 10;
    @FXML
    private AnchorPane gamePane;
//    private ImageView enemyPlane;
    private EnemyPlane enemyPlane;
    private boolean hasCollided = false;

    public Bullet(double startX, double startY, double directionX, double directionY, AnchorPane gamePane, EnemyPlane enemyPlane){
        Image spawnImage = new Image(getClass().getResource("/com/example/game/images/firstShot.png").toString());
        bulletView = new ImageView(spawnImage);
        bulletView.setX(startX);
        bulletView.setY(startY);
        this.directionX = directionX;
        this.directionY = directionY;
        this.gamePane = gamePane;
        this.enemyPlane = enemyPlane;

        gamePane.getChildren().add(bulletView);
        startFlying();
    }

    public ImageView getBulletView() {
        return bulletView;
    }

    private void startFlying(){
        new AnimationTimer(){
            @Override
            public void handle(long now){
                if(!hasCollided) {
                    bulletView.setLayoutX(bulletView.getLayoutX() + directionX * speed);
                    bulletView.setLayoutY(bulletView.getLayoutY() + directionY * speed);
                }

                if(bulletView.getBoundsInParent().intersects(gamePane.sceneToLocal(enemyPlane.getEnemyPlane().getBoundsInParent()))){
                    hasCollided = true;
                    gamePane.getChildren().remove(bulletView);
                    enemyPlane.remove();
                    GameController.getInstance().spawnEnemyPlane();
                    GameController.getInstance().updateScore();
                    this.stop();
                }
                if(bulletView.getImage().getUrl().endsWith("/com/example/game/images/firstShot.png")){
                    bulletView.setImage(new Image(getClass().
                            getResource("/com/example/game/images/midlShot.png").toString()));
                }

                if(isOutOfBounds()){
                    gamePane.getChildren().remove(bulletView);
                    this.stop();
                }
            }
        }.start();
    }
    private boolean isOutOfBounds() {
        return bulletView.getX() < 0 || bulletView.getX() > gamePane.getWidth() ||
                bulletView.getY() < 0 || bulletView.getY() > gamePane.getHeight();
    }
}
//s