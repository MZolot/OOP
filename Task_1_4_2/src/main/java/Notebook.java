import java.io.*;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public Notebook(File file) throws IOException {
        this.file = file;
        Reader reader = new FileReader(file);
        gson = new GsonBuilder().setPrettyPrinting().create();
        Type type = new TypeToken<ArrayList<Note>>() {
        }.getType();
        allNotes = gson.fromJson(reader, type);
        if (allNotes == null) {
            allNotes = new ArrayList<>();
        }
    }

    public void executeNotebook(String[] args) throws ParseException, IOException {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("No arguments");
        }
        switch (args[0]) {
            case ("-add") -> addNote(args);
            case ("-rm") -> removeNote(args);
            case ("-show") -> printAllNotes(args);
        }
    }

    public void addNote(String[] args) throws IOException {
        if (args.length < 3) {
            throw new IllegalArgumentException("Not enough arguments for creating a note");
        }
        String name = args[1];
        String content = args[2];
        Note newNote = new Note(name, content);
        allNotes.add(newNote);
        Writer writer = new FileWriter(file, false);
        gson.toJson(allNotes, writer);
        writer.flush();
        writer.close();
    }

    public void removeNote(String[] args) throws IOException {
        if (args.length == 1) {
            throw new IllegalArgumentException("Not enough arguments for deleting a note");
        }
        String name = args[1];
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
        System.out.printf("No notes named \"%s\". Nothing was removed.\n", name);
    }

    public void printAllNotes(String[] args) throws ParseException {
        if (args.length > 1) {
            printFilteredNotes(args);
            return;
        }
        System.out.println("================");
        for (Note note : allNotes) {
            note.printNote();
            System.out.println("================");
        }
    }

    private Calendar parseCalendar(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy k:mm");
        Date date = sdf.parse(str);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    private boolean containsKeyWords(String noteName, String[] keyWords) {
        if (keyWords == null || keyWords.length == 0) {
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

    public void printFilteredNotes(String[] args) throws ParseException {
        Calendar from = parseCalendar(args[1]);
        Calendar to = parseCalendar(args[2]);
        String[] keyWords = Arrays.copyOfRange(args, 3, args.length);
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
