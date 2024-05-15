package org.hsha.hsha.models;

public class Exercise {

    String name;
    String bodyPart; // body part(s) worked
    int weightKg;
    int workoutId; // foreign key for workout table
    int reps; // reps completed this set

    public Exercise(String name, String bodyPart, int weightKg, int reps) {
        this.name = name;
        this.bodyPart = bodyPart;
        this.weightKg = weightKg;
        this.reps = reps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public int getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(int weightKg) {
        this.weightKg = weightKg;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }


}
