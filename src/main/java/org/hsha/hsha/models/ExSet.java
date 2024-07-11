package org.hsha.hsha.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class ExSet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;


    private Integer weightInKg;
    private Integer reps;

    @ManyToOne
    @JsonIgnore
    private Exercise exercise;

    @ManyToOne
    @JsonIgnore
    private Workout workout;

    public ExSet(Integer id, Integer weightInKg, Integer reps, Exercise exercise, Workout workout) {
        this.id = id;
        this.weightInKg = weightInKg;
        this.reps = reps;
        this.exercise = exercise;
        this.workout = workout;
    }
    public ExSet() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWeightInKg() {
        return weightInKg;
    }

    public void setWeightInKg(Integer weightInKg) {
        this.weightInKg = weightInKg;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }
}
