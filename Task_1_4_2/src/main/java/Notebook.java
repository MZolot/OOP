import java.util.*;

public class Notebook {

    public static class Note {
        final private String name;
        final private Calendar addingTime;
        final private String content;

        public Note(String name, String content) {
            this.name = name;
            this.addingTime = Calendar.getInstance();
            this.content = content;
        }

        public void printNote() {
            System.out.println(this.name);
            System.out.println(this.addingTime.getTime());
            System.out.println(this.content);
        }
    }

    private final List<Note> allNotes;

    public Notebook() {
        allNotes = new ArrayList<>();
    }

    public void addNote(String name, String content) {
        Note newNote = new Note(name, content);
        allNotes.add(newNote);
    }

    public void removeNote(String name){
        for (int i=0; i<allNotes.size(); i++) {
            if (name.equals(allNotes.get(i).name)) {
                allNotes.remove(i);
                return;
            }
        }
        throw new IllegalArgumentException("No notes with this name");
    }

    public void printAllNotes() {
        System.out.println("================");
        for(Note note : allNotes) {
            note.printNote();
            System.out.println("================");
        }
    }

    public void printDateNotes(Calendar from, Calendar to) {
        System.out.println("================");
        for(Note note : allNotes) {
            if(note.addingTime.after(from) && note.addingTime.before(to)) {
                note.printNote();
                System.out.println("================");
            } else if (note.addingTime.after(to)) {
                return;
            }
        }
    }

}
