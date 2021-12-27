import java.io.File;
import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import picocli.CommandLine;

class NotebookTest {

    private static class Comparator {
        private void compareEach(String[] expected, String[] actual) {
            Assertions.assertEquals(expected[0], actual[0]);
            Assertions.assertEquals(expected[1], actual[1]);
        }

        public void compareLists(List<String[]> expected, List<String[]> actual) {
            Assertions.assertEquals(expected.size(), actual.size());
            for (int i = 0; i < expected.size(); i++) {
                compareEach(expected.get(i), actual.get(i));
            }
        }
    }

    @Test
    public void notebookTest() throws IOException {
        File file = new File("notes.json");
        file.createNewFile();
        Notebook notebook = new Notebook(file);
        notebook.clearNotebook();
        List<String[]> entries = new ArrayList<>();
        Comparator comparator = new Comparator();

        String[] addArgs1 = {"-add", "my first note", "this is my first note ever"};
        entries.add(new String[]{"my first note", "this is my first note ever"});
        new CommandLine(new NotebookCommands()).execute(addArgs1);
        comparator.compareLists(entries, notebook.getAttributesList());

        String[] addArgs2 = {"-add", "second note", "i write notes"};
        entries.add(new String[]{"second note", "i write notes"});
        new CommandLine(new NotebookCommands()).execute(addArgs2);
        comparator.compareLists(entries, notebook.getAttributesList());

        String[] addArgs3 = {"-add", "3", "i like my notes"};
        entries.add(new String[]{"3", "i like my notes"});
        new CommandLine(new NotebookCommands()).execute(addArgs3);
        comparator.compareLists(entries, notebook.getAttributesList());

        String[] removeArgs1 = {"-rm", "my first note"};
        entries.remove(0);
        new CommandLine(new NotebookCommands()).execute(removeArgs1);
        comparator.compareLists(entries, notebook.getAttributesList());

        String[] showArgs1 = {"-show"};
        new CommandLine(new NotebookCommands()).execute(showArgs1);

        String[] showArgs2 = {"-show", "25.10.2021 7:00", "01.01.2022 0:00", "nOTe"};
        new CommandLine(new NotebookCommands()).execute(showArgs2);

    }

    @Test
    public void removeFromEmptyNotebook() throws IOException {
        File file = new File("notes.json");
        file.createNewFile();
        String[] removeArgs = {"-rm", "my first note"};
        Assertions.assertThrows(NoSuchObjectException.class,
                () -> new CommandLine(new NotebookCommands()).execute(removeArgs));
    }

}