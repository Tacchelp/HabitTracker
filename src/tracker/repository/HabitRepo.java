package tracker.repository;

import java.time.LocalDate;
import java.util.List;
import tracker.model.*;

import tracker.model.Habit;

public interface HabitRepo {
    void saveHabit(Habit habit);
    Habit findHabitById(String id);
    List<Habit> getAllHabits();
    void saveLog(Entry log);
    List<Entry> getLogsForHabit(String habitId);
    List<Entry> getEntryByDate(LocalDate date);
}
