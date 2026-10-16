package tracker.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // Pour gérer LocalDate
import tracker.model.Entry;
import tracker.model.Habit;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/*
    Since didnt learned how to use Json file in a Java program yet,
    This whole file has been made with Gemini 4 Argon.
*/

public class JsonHabitRepository implements HabitRepo {
    private final File file;
    private final ObjectMapper mapper;
    private TrackerData data;

    public JsonHabitRepository(String filePath) {
        this.file = new File(filePath);
        this.mapper = new ObjectMapper();
        // Module nécessaire pour que Jackson sache lire et écrire le type LocalDate
        this.mapper.registerModule(new JavaTimeModule());
        loadData();
    }

    private void loadData() {
        if (!file.exists()) {
            this.data = new TrackerData();
            return;
        }
        try {
            this.data = mapper.readValue(file, TrackerData.class);
        } catch (IOException e) {
            System.err.println("Erreur de chargement JSON, initialisation d'un tracker vide : " + e.getMessage());
            this.data = new TrackerData();
        }
    }

    private void persist() {
        try {
            // Écrit les données avec une indentation propre
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, this.data);
        } catch (IOException e) {
            throw new RuntimeException("Impossible d'écrire dans le fichier JSON : " + e.getMessage(), e);
        }
    }

    @Override
    public void saveHabit(Habit habit) {
        if (habit == null) throw new IllegalArgumentException("Habit null");

        // Remplacer si l'ID existe déjà, sinon ajouter
        data.habits.removeIf(h -> h.getId().equals(habit.getId()));
        data.habits.add(habit);

        // Sauvegarder sur le disque
        persist();
    }

    @Override
    public void saveLog(Entry log) {
        if (log == null) throw new IllegalArgumentException("Entry null");

        data.entries.add(log);

        // Sauvegarder sur le disque
        persist();
    }

    @Override
    public Habit findHabitById(String id) {
        for (Habit h : data.habits) {
            if (h.getId().equals(id)) return h;
        }
        return null;
    }

    @Override
    public List<Habit> getAllHabits() {
        return new ArrayList<>(data.habits);
    }

    @Override
    public List<Entry> getLogsForHabit(String id) {
        Habit h = this.findHabitById(id);
        List<Entry> result = new ArrayList<>();
        for (Entry e : data.entries) {
            if (e.isOf(h)) result.add(e);
        }
        return result;
    }

    private static class TrackerData {
        public List<Habit> habits = new ArrayList<>();
        public List<Entry> entries = new ArrayList<>();

        public TrackerData() {}
    }

    @Override 
    public List<Entry> getEntryByDate(LocalDate date){
        List<Entry> l = new ArrayList<>();
        for(Habit h : this.getAllHabits()){
            for(Entry e : this.getLogsForHabit(h.getId())) if (e.getDate().equals(date)) l.add(e);
        }
        return l;
    }

}