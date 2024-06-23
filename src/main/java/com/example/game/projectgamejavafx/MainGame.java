package com.example.game.projectgamejavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainGame extends Application {
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainGame.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 698, 684);
        Scene scene = new Scene(fxmlLoader.load(), 698, 684);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Bomber!");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        scene.setOnKeyPressed(e -> {
            GameController gameController = GameController.getInstance();
            if(e.getCode() == KeyCode.D){
                GameController.right = true;
            } else if (e.getCode() == KeyCode.A){
                GameController.left = true;
            } else if (e.getCode() == KeyCode.W){
                GameController.up = true;
                gameController.changePlaneImage("up");
            } else if (e.getCode() == KeyCode.S){
                GameController.down = true;
                gameController.changePlaneImage("down");
            } else if (e.getCode() == KeyCode.SHIFT) {
                GameController.speedUp = true;
            }
        });

        scene.setOnKeyReleased(e -> {
            GameController gameController = GameController.getInstance();
            if(e.getCode() == KeyCode.D){
                GameController.right = false;
            }else if(e.getCode() == KeyCode.A){
                GameController.left = false;
            }else if(e.getCode() == KeyCode.W){
                GameController.up = false;
                gameController.changePlaneImage("straight");
            }else if(e.getCode() == KeyCode.S){
                GameController.down = false;
                gameController.changePlaneImage("straight");
            } else if (e.getCode() == KeyCode.SHIFT) {
                GameController.speedUp = false;
            }
        });
    }
    public static void main(String[] args) {
        launch();
    }
}