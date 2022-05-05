package nsu.oop.task_2_3_1;

import java.util.ArrayDeque;
import java.util.Deque;

class Snake {

    Field.Coordinates direction;
    Deque<Field.Coordinates> body;
    Game game;

    Snake(Game game, int startX, int startY) {
        this.game = game;
        body = new ArrayDeque<>();
        //body.addFirst(new Field.Coordinates(startX - 2, startY));
        //body.addFirst(new Field.Coordinates(startX - 1, startY));
        body.addFirst(new Field.Coordinates(startX, startY));
        direction = new Field.Coordinates(1, 0);
    }

    int size() {
        return body.size();
    }

    Field.Coordinates headCoordinates() {
        return new Field.Coordinates(body.getFirst().x(), body.getFirst().y());
    }

    void changeDirection(int newX, int newY) {
         direction = new Field.Coordinates(newX, newY);
    }

    boolean isBody(int x, int y) {
        return body.contains(new Field.Coordinates(x, y));
    }

    boolean move() {
        int newHeadX = headCoordinates().x() + direction.x();
        int newHeadY = headCoordinates().y() + direction.y();
        if (game.field.isObstacle(newHeadX, newHeadY) ||
                game.field.isBorder(newHeadX, newHeadY) ||
                isBody(newHeadX, newHeadY)) {
            return false;
        }
        body.addFirst(new Field.Coordinates(newHeadX, newHeadY));
        if (game.field.isFood(newHeadX, newHeadY)) {
            game.field.replaceFood();
        } else {
            body.removeLast();
        }
        return true;
    }


}
