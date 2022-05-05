package nsu.oop.task_2_3_1;

import java.util.ArrayDeque;
import java.util.Deque;

class Snake {

    enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    Direction direction;
    Deque<Field.Coordinates> body;
    Game game;

    Snake(Game game, int startX, int startY) {
        this.game = game;
        body = new ArrayDeque<>();
        body.addFirst(new Field.Coordinates(startX - 2, startY));
        body.addFirst(new Field.Coordinates(startX - 1, startY));
        body.addFirst(new Field.Coordinates(startX, startY));
        direction = Direction.RIGHT;
    }

    int size() {
        return body.size();
    }

    Field.Coordinates headCoordinates() {
        return new Field.Coordinates(body.getFirst().x(), body.getFirst().y());
    }

    void changeDirection(Direction newDirection) {
        if (newDirection == Direction.RIGHT) {
            if (direction != Direction.LEFT) {
                direction = Direction.RIGHT;
            }
        } else if (newDirection == Direction.LEFT) {
            if (direction != Direction.RIGHT) {
                direction = Direction.LEFT;
            }
        } else if (newDirection == Direction.UP) {
            if (direction != Direction.DOWN) {
                direction = Direction.UP;
            }
        } else if (newDirection == Direction.DOWN) {
            if (direction != Direction.UP) {
                direction = Direction.DOWN;
            }
        }
    }

    Field.Coordinates directionToCoordinates() {
        if (direction == Direction.RIGHT) {
            return new Field.Coordinates(1, 0);
        } else if (direction == Direction.LEFT) {
            return new Field.Coordinates(-1, 0);
        } else if (direction == Direction.UP) {
            return new Field.Coordinates(0, -1);
        } else {
            return new Field.Coordinates(0, 1);
        }
    }

    boolean isBody(int x, int y) {
        return body.contains(new Field.Coordinates(x, y));
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
        body.addFirst(new Field.Coordinates(newHeadX, newHeadY));
        if (game.field.isFood(newHeadX, newHeadY)) {
            game.addScore();
            game.field.replaceFood();
        } else {
            body.removeLast();
        }
    }


}
