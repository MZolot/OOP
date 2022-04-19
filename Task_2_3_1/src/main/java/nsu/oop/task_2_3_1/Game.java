package nsu.oop.task_2_3_1;

import java.io.File;

public class Game {

    public record Coordinates(int x, int y) {
    }

    Field field;
    Snake snake;

    public Game() {
        field = new Field(new File("fieldConfig"), this);
        snake = new Snake(this, field.width() / 2, field.height() / 2);
    }

    public void play() {

    }
}
