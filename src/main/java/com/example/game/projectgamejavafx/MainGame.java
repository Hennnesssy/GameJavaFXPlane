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
        Scene scene = new Scene(fxmlLoader.load(), 698, 684);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Bomber!");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        GameController gameController = fxmlLoader.getController();

        scene.setOnKeyPressed(e -> {
            gameController.getPlane().handleKeyPress(e.getCode());

            if(e.getCode() == KeyCode.SPACE){
                gameController.getPlane().shoot(gameController.getEnemyPlane());
            }
        });
        scene.setOnKeyReleased(e -> {
            gameController.getPlane().handleKeyRelease(e.getCode());
        });
    }
    public static void main(String[] args) {
        launch();
    }
}
//s