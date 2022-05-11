package nsu.oop.task_2_3_1.model;

import java.util.ArrayDeque;
import java.util.Deque;

class Snake {

    Game.Direction direction;
    Deque<Game.Coordinates> body;
    Game game;

    Snake(Game game, int startX, int startY) {
        this.game = game;
        body = new ArrayDeque<>();
        body.addFirst(new Game.Coordinates(startX - 2, startY));
        body.addFirst(new Game.Coordinates(startX - 1, startY));
        body.addFirst(new Game.Coordinates(startX, startY));
        direction = Game.Direction.RIGHT;
    }

    int size() {
        return body.size();
    }

    Game.Coordinates headCoordinates() {
        return new Game.Coordinates(body.getFirst().x(), body.getFirst().y());
    }

    void changeDirection(Game.Direction newDirection) {
        if (newDirection == direction) {
            return;
        }
        switch (newDirection) {
            case RIGHT -> direction = Game.Direction.RIGHT;
            case LEFT -> direction = Game.Direction.LEFT;
            case UP -> direction = Game.Direction.UP;
            case DOWN -> direction =Game.Direction.DOWN;
        }
    }

    Game.Coordinates directionToCoordinates() {
        Game.Coordinates coordinates = new Game.Coordinates(0,0);
        switch (direction) {
            case RIGHT -> coordinates = new Game.Coordinates(1, 0);
            case LEFT -> coordinates = new Game.Coordinates(-1, 0);
            case UP -> coordinates = new Game.Coordinates(0, -1);
            case DOWN -> coordinates = new Game.Coordinates(0, 1);
        }
        return coordinates;
    }

    boolean isBody(int x, int y) {
        return body.contains(new Game.Coordinates(x, y));
    }

    boolean canNotMove() { int newHeadX = headCoordinates().x() + directionToCoordinates().x();
        int newHeadY = headCoordinates().y() + directionToCoordinates().y();
        if (game.field.isBorder(newHeadX, newHeadY)) {
            return true;
        }
        return game.field.isObstacle(newHeadX, newHeadY) || isBody(newHeadX, newHeadY);
    }

    void move() {
        int newHeadX = headCoordinates().x() + directionToCoordinates().x();
        int newHeadY = headCoordinates().y() + directionToCoordinates().y();
        if(canNotMove()) {
            return;
        }
        body.addFirst(new Game.Coordinates(newHeadX, newHeadY));
        if (game.field.isFood(newHeadX, newHeadY)) {
            game.addScore();
            game.field.replaceFood();
        } else {
            body.removeLast();
        }
    }


}
