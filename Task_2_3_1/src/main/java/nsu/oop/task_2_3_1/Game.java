package nsu.oop.task_2_3_1;

public class Game {

    Field field;
    Snake snake;

    public Game() {
        field = new Field(this);
        snake = new Snake(this, field.getWidth() / 2, field.getHeight() / 2);
        field.replaceFood();
    }

}
