package tracker.model;

public class Habit {
    private  String id;
    private  String name;
    private  double dailyObjective;

    public Habit(String name, double dailyObjective, String id){
        if (dailyObjective < 0)                   throw new IllegalArgumentException("Objective cannot be negative."); 
        if (name == null || name.length() == 0)   throw new IllegalArgumentException("Name cannot be empty."        );
        if (id   == null || id.length()   == 0)   throw new IllegalArgumentException("Id cannot be empty."          );
        else{           
            this.id             = id;
            this.name           = name;
            this.dailyObjective = dailyObjective;
        }
    }
    public String  getId        () { return this.id;             }
    public double  getObjective () { return this.dailyObjective; }
    public String  getName      () { return this.name;           }
    
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

    public Habit(){
        this.id = "";
        this.name = "";
        this.dailyObjective = 0;
    }

    public void setId   (String id  ) { this.id   = id;   }
    public void setName (String name) { this.name = name; }

}
