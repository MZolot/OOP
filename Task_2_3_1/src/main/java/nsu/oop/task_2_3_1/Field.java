package nsu.oop.task_2_3_1;

import java.io.File;

class Field {

    static class Cell {
        boolean obstacle = false;
        boolean food = false;
    }

    Cell[][] field;
    Game.Coordinates foodCoordinates;
    Game game;

    Field(File configFile, Game game) {
        //Deserializer deserializer = new Deserializer();
        //field = deserializer.deserializeField(configFile);
        field = new Cell[5][7];
        foodCoordinates = new Game.Coordinates(0, 0);
        this.game = game;
    }

    int width() {
        return field.length;
    }

    int height() {
        return field[0].length;
    }

    boolean isFood(int x, int y) {
        return field[x][y].food;
    }

    boolean isObstacle(int x, int y) {
        return field[x][y].obstacle;
    }

    boolean isBorder(int x, int y) {
        return x < 0 || x > field.length || y < 0 || y > field[0].length;
    }

    boolean replaceFood() {
        field[foodCoordinates.x()][foodCoordinates.y()].food = false;
        if (game.snake.size() == field.length * field[0].length) {
            return false;
        }
        int newX = (int) (Math.random() * field.length);
        int newY = (int) (Math.random() * field[0].length);
        while (isObstacle(newX, newY) || game.snake.isBody(newX, newY)) {
            newX = (int) (Math.random() * field.length);
            newY = (int) (Math.random() * field[0].length);
        }
        return true;
    }

}
