package com.example.game.projectgamejavafx;
import javafx.scene.control.Label;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
    @FXML
    private AnchorPane gamePane;
    public static boolean isPause = false;
    private static GameController instance;
    private EnemyPlane enemyPlane;
    private boolean spawnEnable = true;
    private int score = 0;
    @FXML
    private Label labelScore;
    public static boolean allowKey = false;

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

        spawnEnemyPlane();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                playerPlaneController.updatePosition();
                lose();
            }
        };
        timer.start();
    }

    public void pauseOrResume(){
        isPause =!isPause;
        if(isPause){
            pauseGame();
        }else if(!isPause){
            resumeGame();
        }
    }
    public void pauseGame(){
        playerPlaneController.stop();
        parallelTransition.pause();
        enemyPlane.pause();
        spawnEnable = false;
        enemyPlane.pause();
        labelPause.setVisible(true);
    }

    public void resumeGame(){
        playerPlaneController.start();
        parallelTransition.play();
        enemyPlane.start();
        spawnEnable = true;
        enemyPlane.start();
        labelPause.setVisible(false);
    }

    public void lose(){
        if(playerPlane.getBoundsInParent().intersects(enemyPlane.getEnemyPlane().getBoundsInParent())){
            playerPlaneController.stop();
            parallelTransition.stop();
            enemyPlane.stop();
            spawnEnable = false;
            isPause = true;
            allowKey = true;
            labelLose.setVisible(true);
        }
    }

    public void spawnEnemyPlane(){
        if(spawnEnable = true){
            if(enemyPlane != null){
                enemyPlane.remove();
            }
            enemyPlane = new EnemyPlane(gamePane);
        }
    }

    public EnemyPlane getEnemyPlane() {
        return enemyPlane;
    }

    public Plane getPlane(){
        return playerPlaneController;
    }

    public ImageView getEnemyTower() {
        return enemyTower;
    }

    public void updateScore(){
        score++;
        labelScore.setText("kill: " + score);
    }
}
//ss