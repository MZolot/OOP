package nsu.oop.task_2_3_1;

public class Game {

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

    void addScore() {
        score += 1;
    }

    boolean win() {
        return (score == maxScore || snake.size() == field.getWidth() * field.getHeight());
    }

}
