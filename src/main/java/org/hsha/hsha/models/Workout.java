package org.hsha.hsha.models;


import jakarta.persistence.*;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="workout")
public class Workout {
    @Id
    Integer workoutId; // identifies workout

    private String name; // name for the workout

    // FIX ME!!!!!!!
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "workout_exercise",
            joinColumns = {@JoinColumn(name = "workout_id")},
            inverseJoinColumns = {@JoinColumn(name = "exercise_id")}
    )
    private Set<Exercise> exercises = new HashSet<>(); // exercise performed
    private int weightKg; // weight in Kg
    private int reps; // reps performed this set (each entry is a set)


    int userId; // used to associate workout with user
    Date date; // date workout completed

    public Workout() {
    }

    public Workout(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(int weightKg) {
        this.weightKg = weightKg;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

