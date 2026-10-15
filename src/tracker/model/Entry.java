package tracker.model;

import java.time.LocalDate;

public class Entry {
    private  LocalDate date;
    private  Habit     habit;
    private  double    value;

    public Entry(Habit h, LocalDate date, double value){
        this.habit = h;
        this.date = date;
        this.value = value;
    }

    public LocalDate getDate  () { return this.date;  }
    public double    getValue () { return this.value; }

    public String toString(){
        return "" + this.habit.getName() + ": " + this.date + " " + value;
    }
    public boolean isOf(Habit h){
        return this.habit.equals(h);
    }

    public Entry(){
        this.habit = null;
        this.date  = null;
        this.value = 0;
    }

    public Habit getHabit()                  { return this.habit; }
    public void  setDate (LocalDate  date )  { this.date     = date ; }
    public void  setValue(double     value)  { this.value    = value; }
    public void  setHabit(Habit      habit)  { this.habit    = habit; }

}
