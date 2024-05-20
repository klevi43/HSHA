package org.hsha.hsha.models;


import jakarta.persistence.*;


import java.util.Date;
@Entity
public class Workout {
    @Id
    int workoutId; // identifies workout

    private String name; // name for the workout


    private Exercise exercise; // exercise performed
    private int weightKg; // weight in Kg
    private int reps; // reps performed this set (each entry is a set)


    int userId; // used to associate workout with user
    Date date; // date workout completed

    public Workout() {
    }

    public Workout(String name, Exercise exercise) {
        this.name = name;
        this.exercise = exercise;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JoinColumn(name = "exercise_name")
    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
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

