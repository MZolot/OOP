import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

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

    private final File file;
    private final Gson gson;
    private List<Note> allNotes;

    public Notebook() throws IOException {
        file = new File("notes.json");
        Reader reader = new FileReader(file);
        gson = new GsonBuilder().setPrettyPrinting().create();
        Type type = new TypeToken<ArrayList<Note>>() {
        }.getType();
        allNotes = gson.fromJson(reader, type);
        if (allNotes == null) {
            allNotes = new ArrayList<>();
        }
    }

    public void addNote(String name, String content) throws IOException {
        Note newNote = new Note(name, content);
        allNotes.add(newNote);
        Writer writer = new FileWriter(file, false);
        gson.toJson(allNotes, writer);
        writer.flush();
        writer.close();
    }

    public void removeNote(String name) throws IOException {
        for (int i = 0; i < allNotes.size(); i++) {
            if (name.equals(allNotes.get(i).name)) {
                allNotes.remove(i);
                Writer writer = new FileWriter(file, false);
                gson.toJson(allNotes, writer);
                writer.flush();
                writer.close();
                return;
            }
        }
        throw new IllegalArgumentException("No notes with this name");
    }

    public void printAllNotes() {
        System.out.println("================");
        for (Note note : allNotes) {
            note.printNote();
            System.out.println("================");
        }
    }

    private boolean containsKeyWords(String noteName, String[] keyWords) {
        if (keyWords == null && keyWords.length == 0) {
            return true;
        }
        String lowerCaseNoteName = noteName.toLowerCase();
        for (String keyWord : keyWords) {
            if (lowerCaseNoteName.contains(keyWord.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public void printFilteredNotes(Calendar from, Calendar to, String[] keyWords) {
        System.out.println("================");
        for (Note note : allNotes) {
            if (note.addingTime.after(from) && note.addingTime.before(to) && containsKeyWords(note.name, keyWords)) {
                note.printNote();
                System.out.println("================");
            } else if (note.addingTime.after(to)) {
                return;
            }
        }
    }

    public void clearNotebook() throws IOException {
        allNotes.clear();
        Writer writer = new FileWriter(file, false);
        gson.toJson(allNotes, writer);
        writer.flush();
        writer.close();
    }
}
