package com.example.game.projectgamejavafx;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class EnemyPlaneBullet {
    private int speed = 5;
    private ImageView bulletImageView;
    private double directionX, directionY;
    private AnchorPane gamePane;
    private Plane plane;
    private GameController gameController;
    private EnemyPlane enemyPlane;
    private EnemyTower enemyTower;

    public EnemyPlaneBullet(double startX, double startY,
                            double directionX, double directionY, AnchorPane gamePane, Plane plane) {;
        Image spawnImage = new Image(getClass().getResource("/com/example/game/images/firstShot.png").toString());

        bulletImageView = new ImageView(spawnImage);
        bulletImageView.setLayoutX(startX);
        bulletImageView.setLayoutY(startY);
        this.directionX = directionX;
        this.directionY = directionY;
        this.gamePane = gamePane;
        this.plane = plane;

        this.gameController = GameController.getInstance();
        Platform.runLater(() ->gamePane.getChildren().add(bulletImageView));
        startFlying();
    }

    public ImageView getBulletImageView() {
        return bulletImageView;
    }

    private void startFlying() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                Platform.runLater(() -> {
                    bulletImageView.setLayoutX(bulletImageView.getLayoutX() + directionX * speed);
                    bulletImageView.setLayoutY(bulletImageView.getLayoutY() + directionY * speed);
                });

                if (bulletImageView.getBoundsInParent().intersects(plane.getPlayerPlane().getBoundsInParent())) {
                    Platform.runLater(() -> gamePane.getChildren().remove(bulletImageView));
                    this.stop();
                }

                if (bulletImageView.getImage().getUrl().endsWith("/com/example/game/images/firstShot.png")) {
                    Platform.runLater(() -> bulletImageView.setImage(new Image(getClass().getResource("/com/example/game/images/midlShot.png").toString())));
                }
                if (isOutOfBounds()) {
                    Platform.runLater(() -> gamePane.getChildren().remove(bulletImageView));
                    this.stop();
                }
                if(bulletImageView.getBoundsInParent().intersects(gamePane.sceneToLocal(plane.getPlayerPlane().getBoundsInParent()))){
                    Platform.runLater(() -> {
                        gamePane.getChildren().remove(bulletImageView);
                        gameController.getPlayerPlaneController().stop();
                        gameController.getParallelTransition().stop();
                        gameController.getEnemyPlane().stop();
                        gameController.spawnEnable = false;
                        gameController.isPause = true;
                        gameController.allowKey = true;
                        if (gameController.getEnemyPlane() != null) {
                            gameController.getEnemyPlane().stop();
                        }
                        if (gameController.getEnemyTower() != null) {
                            gameController.getEnemyTower().stop();
                        }
                        gameController.labelLose.setVisible(true);
                    });
                    this.stop();
                }
            }
        }.start();
    }
    private boolean isOutOfBounds(){
        return bulletImageView.getX() < 0 || bulletImageView.getX() > gamePane.getWidth() ||
                bulletImageView.getY() < 0 || bulletImageView.getY() > gamePane.getHeight();
    }
}