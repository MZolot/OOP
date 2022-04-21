package nsu.oop.task_2_3_1;

import java.util.ArrayDeque;
import java.util.Deque;

class Snake {

    Deque<Field.Coordinates> body;
    Game game;

    Snake(Game game, int startX, int startY) {
        this.game = game;
        body = new ArrayDeque<>();
        body.addFirst(new Field.Coordinates(startX - 2, startY));
        body.addFirst(new Field.Coordinates(startX - 1, startY));
        body.addFirst(new Field.Coordinates(startX, startY));

    }

    int size() {
        return body.size();
    }

    Field.Coordinates headCoordinates() {
        return new Field.Coordinates(body.getFirst().x(), body.getFirst().y());
    }

    boolean isBody(int x, int y) {
        return body.contains(new Field.Coordinates(x, y));
    }

    boolean move(int shiftX, int shiftY) {
        int newHeadX = body.getFirst().x() + shiftX;
        int newHeadY = body.getFirst().y() + shiftY;
        if (game.field.isObstacle(newHeadX, newHeadY) ||
                game.field.isBorder(newHeadX, newHeadY) ||
                isBody(newHeadX, newHeadY)) {
            return false;
        }
        body.addFirst(new Field.Coordinates(newHeadX, newHeadY));
        if (!game.field.isFood(newHeadX, newHeadY)) {
            body.removeLast();
        }
        return true;
    }


}
