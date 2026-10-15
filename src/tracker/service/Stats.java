package tracker.service;

import tracker.model.Habit;
import tracker.model.Entry;
import java.time.LocalDate;
import java.util.ArrayList;

public class Stats {
    private ArrayList<Entry> entryList;

    public Stats(){
        this.entryList = new ArrayList<>();
    }

    public void addEntry(Entry e){
        if(e == null) throw new IllegalArgumentException("Entry cannot be null.");
        this.entryList.add(e);
    }

    private static ArrayList<Entry> getByDate(ArrayList<Entry> a, LocalDate date){
        ArrayList<Entry> l = new ArrayList<>();
        for(Entry e : a) if(e.getDate().equals(date)) l.add(e);
        return l;
    }
    private static ArrayList<Entry> getByHabit(ArrayList<Entry> a, Habit h){
        ArrayList<Entry> l = new ArrayList<>();
        for(Entry e : a) if(e.isOf(h)) l.add(e);
        return l;
    }


    public ArrayList<Entry> getByDate(LocalDate date){
        return getByDate(this.entryList, date);
    }

    public ArrayList<Entry> getByHabit(Habit h){
        return getByHabit(this.entryList, h);
    }

    public boolean isAccomplished(Habit h, LocalDate date){
        ArrayList<Entry> l = getByHabit(this.getByDate(date), h);
        double sum = 0;
        for(Entry e : l) sum += e.getValue();
        return h.getObjective() < sum;
    }

    public int getStreakFrom(Habit h, LocalDate date){
        if(isAccomplished(h, date)){return 1 + this.getStreakFrom(h, date.minusDays(1));}
        else return 0;
    }

    public int getCurrentStreak(Habit h){ return this.getStreakFrom(h, LocalDate.now()); }
    public int getRateBetween(Habit h, LocalDate d1, LocalDate d2){
        if(d2.isBefore(d1)) throw new IllegalArgumentException("The second date might be after the first one.");
        else{
            int nbAccomplished = 0, nbTotal = 0;
            while(!d1.isAfter(d2)){
                nbTotal += 1;
                if(isAccomplished(h, d1)) nbAccomplished += 1;
                d1 = d1.plusDays(1);
            }
            return 100 * nbAccomplished / nbTotal;
        }
    }
}
