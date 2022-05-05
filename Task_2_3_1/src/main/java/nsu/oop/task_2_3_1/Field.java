package nsu.oop.task_2_3_1;

import java.io.File;

class Field {

    public record Coordinates(int x, int y) {
    }

    static class Cell {
        boolean obstacle = false;
        boolean food = false;
    }

    int width = 15;
    int height = 15;
    Cell[][] field;
    Coordinates foodCoordinates;
    Game game;

    Field(Game game) {
        field = new Cell[width][height];
        for (int i = 0; i<width; i++) {
            for (int j = 0; j<height; j++) {
                field[i][j] = new Cell();
            }
        }
        foodCoordinates = new Coordinates(0, 0);
        this.game = game;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    Coordinates getFoodCoordinates() {
        return foodCoordinates;
    }

    boolean isFood(int x, int y) {
        return field[x][y].food;
    }

    boolean isObstacle(int x, int y) {
        return field[x][y].obstacle;
    }

    boolean isBorder(int x, int y) {
        return x < 0 || x > width || y < 0 || y > height;
    }

    boolean replaceFood() {
        field[foodCoordinates.x()][foodCoordinates.y()].food = false;
        if (game.snake.size() == width * height) {
            return false;
        }
        int newX = (int) (Math.random() * (width - 1));
        int newY = (int) (Math.random() * (height - 1));
        while (isObstacle(newX, newY) || game.snake.isBody(newX, newY)) {
            newX = (int) (Math.random() * (width - 1));
            newY = (int) (Math.random() * (height - 1));
        }
        foodCoordinates = new Coordinates(newX, newY);
        field[newX][newY].food = true;
        return true;
    }

}
