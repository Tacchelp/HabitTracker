package tracker.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tracker.model.Entry;
import tracker.model.Habit;

public class InMemoryHabitRepository implements HabitRepo{
    private ArrayList<Entry> logs;
    private HashMap<String, Habit> map;

    public InMemoryHabitRepository(){
        this.logs = new ArrayList<>();
        this.map  = new HashMap<>();
    }

    @Override 
    public Habit findHabitById(String id){ return map.get(id); }

    @Override 
    public List<Habit> getAllHabits(){
        List<Habit> l = new ArrayList<>();
        for(String id : this.map.keySet()) l.add(map.get(id));
        return l;
    }

    @Override 
    public List<Entry> getEntryByDate(LocalDate date){
        List<Entry> l = new ArrayList<>();
        for(Habit h : this.getAllHabits()){
            for(Entry e : this.getLogsForHabit(h.getId())) if (e.getDate().equals(date)) l.add(e);
        }
        return l;
    }

    @Override 
    public List<Entry> getLogsForHabit(String habitId){
        List<Entry> l = new ArrayList<>();
        Habit h = this.map.get(habitId);
        for(Entry e : this.logs) if(e.isOf(h)) l.add(e);
        return l;
    }

    @Override 
    public void saveLog(Entry log){
        if (log == null) throw new IllegalArgumentException("Cannot save a null Entry.");
        this.logs.add(log);
    }

    @Override 
    public void saveHabit(Habit habit){
        if (habit == null) throw new IllegalArgumentException("Cannot save a null habit.");
        this.map.put(habit.getId(),habit);
    }

}
