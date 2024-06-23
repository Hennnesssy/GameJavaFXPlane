module com.example.game.projectgamejavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.game.projectgamejavafx to javafx.fxml;
    exports com.example.game.projectgamejavafx;
}