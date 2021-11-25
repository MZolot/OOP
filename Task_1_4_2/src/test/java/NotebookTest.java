import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NotebookTest {

    File file = new File("notes.json");
    boolean created = file.createNewFile();
    Notebook notebook = new Notebook(file);

    NotebookTest() throws IOException {
    }

    @Test
    public void incorrectNoteName() {
        String[] args = {"", "some note name"};
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> notebook.removeNote(args));
    }

    @Test
    public void nullArguments() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> notebook.executeNotebook(null));
    }

    @Test
    public void noArguments() {
        String[] args = {};
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> notebook.executeNotebook(null));
    }

    @Test
    public void notEnoughArgumentsForAdd() {
        String[] args = {"", "my note name"};
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> notebook.addNote(args));
    }

    @Test
    public void notEnoughArgumentsForRemove() {
        String[] args = {""};
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> notebook.removeNote(args));
    }

}