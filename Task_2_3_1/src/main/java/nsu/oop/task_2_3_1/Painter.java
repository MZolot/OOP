package nsu.oop.task_2_3_1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Painter {

    private final Field field;
    private final Snake snake;
    private final int cellSize;

    Painter(Game game, int cellSize) {
        this.field = game.field;
        this.snake = game.snake;
        this.cellSize = cellSize;
    }

    Color getCellColor(int x, int y) {
        if (field.isObstacle(x, y)) {
            return Color.rgb(100, 0, 100);
        }
        if ((x + y) % 2 == 0) {
            return Color.rgb(30, 0, 30);
        } else {
            return Color.rgb(10, 0, 10);
        }
    }

    void drawField(GraphicsContext fieldCanvas) {
        for (int i = 0; i < field.getWidth(); i++) {
            for (int j = 0; j < field.getHeight(); j++) {
                fieldCanvas.setFill(getCellColor(i, j));
                fieldCanvas.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }
    }

    void drawSnake(GraphicsContext canvas) {
        canvas.setFill(Color.rgb(200, 0, 150));
        for (Field.Coordinates bodyPart : snake.body) {
            canvas.fillRect(bodyPart.x() * cellSize, bodyPart.y() * cellSize, cellSize, cellSize);
        }
        canvas.setFill(Color.rgb(30, 0, 30));
        canvas.fillOval((snake.headCoordinates().x() + 0.4) * cellSize,
                (snake.headCoordinates().y() + 0.4) * cellSize,
                cellSize * 0.2, cellSize * 0.2);
    }

    void drawFood(GraphicsContext canvas) {
        canvas.setFill(Color.rgb(250, 150, 200));
            canvas.fillOval((field.getFoodCoordinates().x() + 0.25) * cellSize,
                    (field.getFoodCoordinates().y() + 0.25) * cellSize,
                    cellSize/2, cellSize/2);

    }
}
