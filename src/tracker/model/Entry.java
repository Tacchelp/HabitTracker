package tracker.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Entry {
    private  final LocalDate date;
    private  final Habit     habit;
    private  final double    value;

    @JsonCreator 
    public Entry(@JsonProperty("Habit") Habit h,
                 @JsonProperty("date")  LocalDate date,
                 @JsonProperty("value") double value)
    {
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

    public Habit getHabit(){ return this.habit; }

}
