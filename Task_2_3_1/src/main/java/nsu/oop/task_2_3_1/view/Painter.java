package nsu.oop.task_2_3_1.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import nsu.oop.task_2_3_1.model.*;

public class Painter {

    private final Game game;
    private final int cellSize;

    public Painter(Game game, int cellSize) {
        this.game = game;
        this.cellSize = cellSize;
    }

    Color getCellColor(int x, int y) {
        if (game.hasObstacle(x, y)) {
            return Color.rgb(100, 0, 100);
        }
        if ((x + y) % 2 == 0) {
            return Color.rgb(30, 0, 30);
        } else {
            return Color.rgb(10, 0, 10);
        }
    }

    void drawField(GraphicsContext fieldCanvas) {
        for (int i = 0; i < game.getFieldWidth(); i++) {
            for (int j = 0; j < game.getFieldHeight(); j++) {
                fieldCanvas.setFill(getCellColor(i, j));
                fieldCanvas.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }
    }

    void drawSnake(GraphicsContext canvas) {
        canvas.setFill(Color.rgb(200, 0, 150));
        for (Game.Coordinates bodyPart : game.getSnake()) {
            canvas.fillRect(bodyPart.x() * cellSize, bodyPart.y() * cellSize, cellSize, cellSize);
        }
        canvas.setFill(Color.rgb(30, 0, 30));
        canvas.fillOval((game.getSnakeHead().getX() + 0.4) * cellSize,
                (game.getSnakeHead().getY() + 0.4) * cellSize,
                cellSize * 0.2, cellSize * 0.2);
    }

    void drawFood(GraphicsContext canvas) {
        canvas.setFill(Color.rgb(250, 150, 200));
            canvas.fillOval((game.getFoodCoordinates().getX() + 0.25) * cellSize,
                    (game.getFoodCoordinates().getY() + 0.25) * cellSize,
                    cellSize/2, cellSize/2);

    }

    public void drawGame(GraphicsContext canvas) {
        drawField(canvas);
        drawSnake(canvas);
        drawFood(canvas);
    }
}
