package nsu.oop.task_2_3_1;

import java.io.File;

public class Game {

    Field field;
    Snake snake;

    public Game() {
        field = new Field(new File("fieldConfig"), this);
        snake = new Snake(this, field.width() / 2, field.height() / 2);
    }

    public void play() {
        // здесь потом добавлю работу с моделью и контроллером, будет нормальный код
        // а пока очень очень примерно
        while(field.replaceFood()) {
            snake.move(0, 0);
        }
    }
}
