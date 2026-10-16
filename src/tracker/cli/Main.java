package tracker.cli;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import tracker.model.Entry;
import tracker.model.Habit;
import tracker.repository.HabitRepo;
import tracker.repository.JsonHabitRepository;
import tracker.service.Stats;

public class Main {
    public static final String FICHIER = "data.json";

    public static void main(String[] args){
        HabitRepo repo = new JsonHabitRepository(FICHIER); 
        Stats stats = new Stats();

        if (args.length == 0){
            printHelp();
            return;
        }


        switch (args[0].toLowerCase()) {
            case "list":
                printAllHabits(repo);
                break;
            case "add":
                addHabit(repo, args);
                break;
            case "log":
                registerEntry(repo, args);
                break;
            case "stats":
                printStats(repo, stats, args);
                break;
            case "getlogs":
                printLogs(repo, args);
                break;
            case "help":
                printHelp();
                break;
            default:
                System.out.println("Commande inconnue :" + args[0].toLowerCase());
                break;
        }


    }

    private static void printAllHabits(HabitRepo repo) { for(Habit h : repo.getAllHabits()) System.out.println(" -" + h); }
    private static void addHabit(HabitRepo repo, String[] args){
        if (args.length < 4){
            System.out.println("Usage : add <id> <nom> <objectif>");
            return;
        }
        String id = args[1];
        String nom = args[2];
        double objectif;
        try{
            objectif = Double.parseDouble(args[3]);
        } catch(Exception e){
            System.out.println("Usage : add <id> <nom> <objectif>");
            return;
        }
        Habit h = new Habit(nom, objectif, id, LocalDate.now());
        repo.saveHabit(h);
        System.out.println(h + "\n ajouté avec succès !");
    }

    private static void registerEntry(HabitRepo repo, String[] args){
        if (args.length < 3){
            System.out.println("Usage : log <id> <valeur>");
            return;
        }

        String id = args[1];
        double value;
        try{
            value = Double.parseDouble(args[2]);
        }catch(Exception e){
            System.out.println("Usage : log <id> <valeur>.");
            return;
        }

        Habit h = repo.findHabitById(id);
        if(h == null){
            System.out.println("L'id : \"" + id + "\" ne correspond à aucune habitude.");
            System.out.println("Vous pouvez obtenir la liste des habitudes avec la commande :");
            System.out.println(" list");
            return;
        }
        String note = "";
        int indexNote = Arrays.asList(args).indexOf("--note");
        if (indexNote != -1 && args.length > indexNote + 1 ){ note = args[indexNote + 1];}

        Entry e = new Entry(repo.findHabitById(id), LocalDate.now(), value, note);
        repo.saveLog(e);

    }

    private static void printStats(HabitRepo repo,Stats stats,String[] args){
        if (args.length < 2){
            System.out.println("Usage : stats <id>.");
            return;
        }
        Habit h = repo.findHabitById(args[1]);
        if(h == null){
            System.out.println("L'id : \"" + args[1] + "\" ne correspond à aucune habitude.");
            System.out.println("Vous pouvez obtenir la liste des habitudes avec la commande :");
            System.out.println(" list");
            return;
        }

        for(Entry e : repo.getLogsForHabit(h.getId())) stats.addEntry(e);



        int streak = stats.getCurrentStreak(h);
        LocalDate today = LocalDate.now();
        LocalDate aWeekAgo = today.minusDays(7);
        LocalDate aMonthAgo = today.minusDays(30);

        double weekRate = stats.getRateBetween(h, aWeekAgo, today);
        double mounthRate = stats.getRateBetween(h, aMonthAgo, today);
        double everRate  = stats.getRateBetween(h, h.getBeginDate(), today);
        boolean accomplishedToday = stats.isAccomplished(h, today);

        System.out.println("========================");
        System.out.println("        STATS : " + h.getName().toUpperCase());
        System.out.println("========================");
        System.out.println("- Statut du jour       : " + (accomplishedToday ? "[X] Validé" : "[ ] En attente"));
        System.out.println("- Serie actuelle       : " + streak + " jours consécutifs.");
        System.out.printf("- Taux de réussite (7j)  : %.1f%%%n", weekRate);
        System.out.printf("- Taux de réussite (30j) : %.1f%%%n", mounthRate);
        System.out.printf("- Taux de réussite (ever): %.1f%%%n*", everRate);



    }

    private static void printLogs(HabitRepo repo, String[] args){
        if (args.length < 2){ 
            System.out.println("Usage : getLogs <date>");
            return;
        }
        
        LocalDate date;
        try { date = LocalDate.parse(args[1]);
        } catch (DateTimeParseException _){
            if (args[1].equalsIgnoreCase("today")){
                date = LocalDate.now();
            }
            else{
                System.out.println("Date invalide.");
                return;
            }
        }

        for(Entry e : repo.getEntryByDate(date)) System.out.println(e);
        return;

    }


    private static void printHelp(){
        System.out.println("==========  Commandes disponibles ==========");
        System.out.println(" list                       : Affiche toutes les habitudes.");
        System.out.println(" add <id> <nom> <objectif>  : Créer une habitude.");
        System.out.println(" log <id> <valeur>          : Enregistre une activité pour la journée.");
        System.out.println(" stats <id>                 : Calculer la série et le taux de complétion.");
        System.out.println(" getLogs <date>             : Affiche toutes les entrées du jour donné.");
    }

}
