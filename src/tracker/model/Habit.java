package tracker.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Habit {
    private final LocalDate beginDate;
    private final String    id;
    private final String    name;
    private       double    dailyObjective;


    @JsonCreator 
    public Habit(@JsonProperty("name")           String name,
                 @JsonProperty("dailyObjective") double dailyObjective,
                 @JsonProperty("id")             String id,
                 @JsonProperty("beginDate")      LocalDate beginDate)
    {
        if (dailyObjective < 0)                   throw new IllegalArgumentException("Objective cannot be negative."); 
        if (name == null || name.length() == 0)   throw new IllegalArgumentException("Name cannot be empty."        );
        if (id   == null || id.length()   == 0)   throw new IllegalArgumentException("Id cannot be empty."          );
        else{           
            this.id             = id;
            this.name           = name;
            this.dailyObjective = dailyObjective;
            this.beginDate      = beginDate;
        }
    }
    public String    getId        () { return this.id;             }
    public double    getObjective () { return this.dailyObjective; }
    public String    getName      () { return this.name;           }
    public LocalDate getBeginDate () { return this.beginDate;      }

    @Override 
    public boolean equals       (Object o){
        if (o instanceof Habit) return this.getId().equals(((Habit) o).getId());
        else                    return false;
    }
    
    public void   setObjective (double newObjective) {
        if(newObjective < 0) throw new IllegalArgumentException("Objective cannot be negative.");
        else this.dailyObjective = newObjective;
    }
    @Override 
    public int hashCode(){
        return this.id.hashCode();
    }

    @Override 
    public String toString(){
        return id + " - " + name + " " + dailyObjective + "min/days.";
    }



}
