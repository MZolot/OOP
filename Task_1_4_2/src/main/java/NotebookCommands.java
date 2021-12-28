import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "notebook", mixinStandardHelpOptions = true)
class NotebookCommands implements Callable<Integer> {

    @CommandLine.Option(names = "-add", arity = "1..2", description = "add a new note to the notebook")
    String[] add;

    @CommandLine.Option(names = {"-rm", "-remove"}, arity = "1", description = "remove an existing note to the notebook")
    String remove;

    @CommandLine.Option(names = "-show", arity = "0..*", description = "show notes from the notebook")
    String[] show;

    @CommandLine.Option(names = "-open", arity = "1", description = "open notebook in another file")
    String open;

    @Override
    public Integer call() throws Exception {
        //File file = new File("notes.json");
        //Notebook notebook = new Notebook(file);
        Notebook notebook = null;
        if (add != null ) {
            if (notebook == null) {
                throw new IllegalStateException("Please open some notebook first");
            }
            if (add[1] == null) {
                notebook.addNote(add[0], "");
            } else {
                notebook.addNote(add[0], add[1]);
            }
        } else if (remove != null) {
            if (notebook == null) {
                throw new IllegalStateException("Please open some notebook first");
            }
            notebook.removeNote(remove);
        } else if (show != null) {
            if (notebook == null) {
                throw new IllegalStateException("Please open some notebook first");
            }
            notebook.printNotes(show);
        } else if (open != null) {
            File file = new File(open);
            notebook = new Notebook(file);
        }
        return null;
    }
}
