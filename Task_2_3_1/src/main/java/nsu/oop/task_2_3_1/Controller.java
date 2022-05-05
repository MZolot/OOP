package nsu.oop.task_2_3_1;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller {

    Game game;
    int cellSize = 30;

    @FXML
    private Button startButton;

    @FXML
    protected void onStartButtonClick() {
        game = new Game();
        Painter painter = new Painter(game, cellSize);
        Stage stage = (Stage) startButton.getScene().getWindow();

        Canvas canvas = new Canvas(game.field.width * cellSize, game.field.height * cellSize);

        Group rootGroup = new Group(canvas);
        Scene gameScene = new Scene(rootGroup);
        stage.setScene(gameScene);
        stage.sizeToScene();
        stage.show();

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        painter.drawField(graphicsContext);
        painter.drawSnake(graphicsContext);
        painter.drawFood(graphicsContext);
    }



}
