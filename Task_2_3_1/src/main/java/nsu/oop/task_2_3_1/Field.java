package nsu.oop.task_2_3_1;

class Field {

    public record Coordinates(int x, int y) {
    }

    private final int width;
    private final int height;
    boolean[][] field;
    Coordinates foodCoordinates;
    Game game;

    Field(Game game, int width, int height) {
        this.width = width;
        this.height = height;
        field = new boolean[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                field[i][j] = false;
            }
        }
        /*
        field[3][1] = true;
        field[3][2] = true;
        field[3][3] = true;
         */
        foodCoordinates = new Coordinates(width / 2 + 2, height / 2);
        this.game = game;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    Coordinates getFoodCoordinates() {
        return foodCoordinates;
    }

    boolean isFood(int x, int y) {
        return (x == foodCoordinates.x && y == foodCoordinates.y);
    }

    boolean isObstacle(int x, int y) {
        return field[x][y];
    }

    boolean isBorder(int x, int y) {
        return x < 0 || x >= width || y < 0 || y >= height;
    }

    void replaceFood() {
        int newX = (int) (Math.random() * (width - 1));
        int newY = (int) (Math.random() * (height - 1));
        while (isObstacle(newX, newY) || game.snake.isBody(newX, newY)) {
            newX = (int) (Math.random() * (width - 1));
            newY = (int) (Math.random() * (height - 1));
        }
        foodCoordinates = new Coordinates(newX, newY);
    }

}