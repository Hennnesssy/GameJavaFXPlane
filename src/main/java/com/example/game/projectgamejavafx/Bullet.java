package com.example.game.projectgamejavafx;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Bullet {
    private ImageView bulletView;
    private double directionX, directionY;
    private double speed = 10;
    @FXML
    private AnchorPane gamePane;
    private ImageView enemyPlane;

    public Bullet(double startX, double startY, double directionX, double directionY, AnchorPane gamePane){
        Image spawnImage = new Image(getClass().getResource("/com/example/game/images/firstShot.png").toString());
        bulletView = new ImageView(spawnImage);
        bulletView.setX(startX);
        bulletView.setY(startY);
        this.directionX = directionX;
        this.directionY = directionY;
        this.gamePane = gamePane;

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
                bulletView.setLayoutX(bulletView.getLayoutX() + directionX * speed);
                bulletView.setLayoutY(bulletView.getLayoutY() + directionY * speed);
                if(bulletView.getImage().getUrl().endsWith("/com/example/game/images/firstShot.png")){
                    bulletView.setImage(new Image(getClass().
                            getResource("/com/example/game/images/midlShot.png").toString()));
                }
                if(isOutOfBounds()){
                    bulletView.setImage(new Image(getClass()
                            .getResource("/com/example/game/images/endShot.png").toString()));
                    gamePane.getChildren().remove(bulletView);
                    this.stop();
                }
                if (bulletView.getBoundsInParent().intersects(gamePane.sceneToLocal(getEnemyPlane().getBoundsInParent()))) {
                    gamePane.getChildren().remove(bulletView);
                    // Інші дії, які ви хочете виконати при колізії
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