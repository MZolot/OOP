import java.io.*;
import java.lang.reflect.Type;
import java.rmi.NoSuchObjectException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class Notebook {

    private static class Note {
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

    public void addNote(String name, String content) throws IOException {
        Note newNote = new Note(name, content);
        allNotes.add(newNote);
        Writer writer = new FileWriter(file, false);
        gson.toJson(allNotes, writer);
        writer.flush();
        writer.close();
    }

    public void removeNote(String name) throws IOException {
        if (allNotes.size() == 0) {
            throw new NoSuchObjectException("Trying to remove notes from empty notebook");
        }
        for (int i = 0; i < allNotes.size(); i++) {
            if (allNotes.get(i).name.equals(name)) {
                allNotes.remove(i);
                Writer writer = new FileWriter(file, false);
                gson.toJson(allNotes, writer);
                writer.flush();
                writer.close();
                return;
            }
        }
    }

    public void printNotes(String[] args) throws ParseException {
        if (args == null || args.length == 0) {
            this.printAllNotes();
            return;
        }
        Calendar check = parseCalendar(args[0]);
        if (check == null) {
            this.printFilteredNotes(args);
        } else {
            this.printFilteredByDateNotes(args);
        }
    }

    private void printAllNotes() {
        System.out.println("================");
        for (Note note : allNotes) {
            note.printNote();
            System.out.println("================");
        }
    }

    private Calendar parseCalendar(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy k:mm");
        Date date = sdf.parse(str);
        if (date == null) {
            return null;
        }
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

    private void printFilteredNotes(String[] args) {
        String[] keyWords = Arrays.copyOfRange(args, 0, args.length);
        List<Note> goodNotes = allNotes.stream()
                .filter(a -> containsKeyWords(a.name, keyWords))
                .collect(Collectors.toList());
        System.out.println("================");
        for (Note note : goodNotes) {
            note.printNote();
            System.out.println("================");
        }
    }

    private void printFilteredByDateNotes(String[] args) throws ParseException {
        Calendar from = parseCalendar(args[0]);
        Calendar to = parseCalendar(args[1]);
        String[] keyWords = Arrays.copyOfRange(args, 2, args.length);
        List<Note> goodNotes = allNotes.stream()
                .filter(a -> containsKeyWords(a.name, keyWords))
                .filter(a -> a.addingTime.after(from))
                .filter(a -> a.addingTime.before(to))
                .collect(Collectors.toList());
        System.out.println("================");
        for (Note note : goodNotes) {
            note.printNote();
            System.out.println("================");
        }
    }

    public void clearNotebook() throws IOException {
        allNotes.clear();
        Writer writer = new FileWriter(file, false);
        gson.toJson(allNotes, writer);
        writer.flush();
        writer.close();
    }

    public List<String[]> getAttributesList() throws FileNotFoundException {
        Reader reader = new FileReader(this.file);
        Type type = new TypeToken<ArrayList<Note>>() {
        }.getType();
        List<Note> notes = gson.fromJson(reader, type);
        List<String[]> list = new ArrayList<>();
        for (Note n : notes) {
            String[] entry = new String[] {n.name, n.content};
            list.add(entry);
        }
        return list;
    }
}