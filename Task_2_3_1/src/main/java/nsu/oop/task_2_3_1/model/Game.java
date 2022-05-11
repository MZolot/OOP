package nsu.oop.task_2_3_1.model;

import java.util.Deque;

public class Game {

    public record Coordinates(int x, int y) {
        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    Field field;
    Snake snake;
    int score;
    int maxScore;

    public Game(int width, int height, int scoreGoal) {
        field = new Field(this, width, height);
        snake = new Snake(this, field.getWidth() / 2, field.getHeight() / 2);
        score = 0;
        maxScore = scoreGoal;
    }

    public void addScore() {
        score += 1;
    }

    public int getScore() {
        return score;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public boolean win() {
        return (score == maxScore || snake.size() == field.getWidth() * field.getHeight());
    }

    public boolean lose() {
        return snake.canNotMove();
    }

    public void moveSnake() {
        snake.move();
    }

    public void changeSnakeDirection(Direction direction) {
        snake.changeDirection(direction);
    }

    public int getFieldWidth() {
        return field.getWidth();
    }

    public int getFieldHeight() {
        return field.getHeight();
    }

    public boolean hasObstacle(int x, int y) {
        return field.isObstacle(x, y);
    }

    public Coordinates getFoodCoordinates() {
        return field.foodCoordinates;
    }

    public Deque<Coordinates> getSnake() {
        return snake.body;
    }

    public Coordinates getSnakeHead() {
        return snake.headCoordinates();
    }

}
