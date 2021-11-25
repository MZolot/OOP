import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        File file = new File("notes.json");
        Notebook myNotebook = new Notebook(file);
        myNotebook.clearNotebook();
        String[] addArgs1 = {"-add", "my first note", "this is my first note ever"};
        myNotebook.executeNotebook(addArgs1);
        String[] addArgs2 = {"-add", "second note", "i write notes"};
        myNotebook.executeNotebook(addArgs2);
        String[] showArgs1 = {"-show"};
        myNotebook.executeNotebook(showArgs1); //shows "my first note" and "second note"
        System.out.println("\n\n");
        String[] showArgs2 = {"-show", "25.10.2021 7:00", "01.01.2022 0:00", "mY"};
        myNotebook.executeNotebook(showArgs2); //shows "my first note"
        String[] addArgs3 = {"-add", "3", "i like my notes"};
        myNotebook.executeNotebook(addArgs3);
        String[] removeArgs1 = {"-rm", "my first note"};
        myNotebook.executeNotebook(removeArgs1);
        System.out.println("\n\n");
        myNotebook.executeNotebook(showArgs1); //shows "second note" and "3"
    }
}
