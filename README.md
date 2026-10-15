# Habit Tracker

Un tracker d'habitudes développé en Java.

## 🚀 Fonctionnalités
 - **Création d'habitudes** avec des objectifs quotidiens.
 - **Enregistrement d'activités** 
 - **Suivi des statistiques** : Taux de réussites sur la derniere semaine / le dernier mois
 - **Suivi de Streak**
 - **Données persistantes** : Stocké localement dans un .json

## 🛠️ Utilisation 
- **Compilation**
```bash
mkdir -p bin && javac -cp "lib/*:src" -d bin $(find src -name "*.java")
```

- **Utilisation**
```bash
chmod +x tracker
./tracker help
```

**==========  Commandes disponibles ==========**
 list                       : Affiche toutes les habitudes
 add <id> <nom> <objectif>  : Créer une habitude
 log <id> <valeur>          : Enregistre une activité pour la journée.
 stats <id>                 : Calculer la série et le taux de complétion



Nécessite JAVA

N'ayant pas encore pris le temps d'apprendre comment utiliser un json depuis un script java, cette partie du code (Le fichier `JsonHabitRepository.java`) à été réalisé à l'aide de Gemini 4 Argon

