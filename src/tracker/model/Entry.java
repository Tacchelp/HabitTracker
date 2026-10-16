package tracker.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Entry {
    private  final LocalDate date;
    private  final Habit     habit;
    private  final double    value;
    private  final String    note;


    @JsonCreator 
    public Entry(@JsonProperty("Habit") Habit     h,
                 @JsonProperty("date")  LocalDate date,
                 @JsonProperty("value") double    value,
                 @JsonProperty("note")  String    note)
    {
        this.habit  = h;
        this.date   = date;
        this.value  = value;
        this.note   = note;
    }

    public LocalDate getDate  () { return this.date;  }
    public double    getValue () { return this.value; }
    public String    getNote  () { return this.note;  }
    public Habit     getHabit () { return this.habit; }

    public String toString(){
        return "" + this.habit.getName() + ": " + this.date + " " + value + " " + note;
    }
    public boolean isOf(Habit h){
        return this.habit.equals(h);
    }

}
