package org.hsha.hsha.models;

public class Workout {
    String name; // name for the workout
    Exercise exercise; // exercise performed
    int weightKg; // weight in Kg
    int reps; // reps performed this set (each entry is a set)
    int workoutId; // identifies workout
    int userId; // used to associate workout with user


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

