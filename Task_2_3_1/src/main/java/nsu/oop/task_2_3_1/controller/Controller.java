package nsu.oop.task_2_3_1.controller;

import nsu.oop.task_2_3_1.HelloApplication;
import nsu.oop.task_2_3_1.view.Painter;
import nsu.oop.task_2_3_1.model.*;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Controller {

    Game game;
    int cellSize = 30;
    Stage stage;
    Painter painter;
    Timeline timeline;
    Label scoreLabel;

    @FXML
    private Button startButton;

    @FXML
    protected void onStartButtonClick() {
        game = new Game(15, 15, 5);
        painter = new Painter(game, cellSize);
        stage = (Stage) startButton.getScene().getWindow();

        Canvas canvas = new Canvas(game.getFieldWidth() * cellSize, game.getFieldHeight() * cellSize);
        scoreLabel = new Label(game.getScore() + "/" + game.getMaxScore());
        scoreLabel.setTextFill(Color.rgb(250, 150, 200));
        scoreLabel.setFont(new Font(15));
        VBox root = new VBox(scoreLabel,canvas);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene gameScene = new Scene(root);
        stage.setScene(gameScene);
        stage.sizeToScene();
        stage.show();

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        painter.drawGame(graphicsContext);

        gameScene.setOnKeyPressed(e -> {
            KeyCode code = e.getCode();
            if (code == KeyCode.RIGHT || code == KeyCode.D) {
                game.changeSnakeDirection(Game.Direction.RIGHT);
            } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                game.changeSnakeDirection(Game.Direction.LEFT);
            } else if (code == KeyCode.UP || code == KeyCode.W) {
                game.changeSnakeDirection(Game.Direction.UP);
            } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                game.changeSnakeDirection(Game.Direction.DOWN);
            }
        });

        timeline = new Timeline(new KeyFrame(Duration.millis(150), e -> {
            try {
                run(graphicsContext);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    void run(GraphicsContext gc) throws IOException {
        if (game.lose()) {
            timeline.stop();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("lose-view.fxml"));
            Scene loseScene = new Scene(fxmlLoader.load());
            stage.setWidth(600);
            stage.setHeight(400);
            stage.setScene(loseScene);
            return;
        }
        if (game.win()) {
            timeline.stop();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("win-view.fxml"));
            Scene winScene = new Scene(fxmlLoader.load());
            stage.setWidth(600);
            stage.setHeight(400);
            stage.setScene(winScene);
            return;
        }
        game.moveSnake();
        painter.drawGame(gc);
        scoreLabel.setText(game.getScore() + "/" + game.getMaxScore());
    }

}
