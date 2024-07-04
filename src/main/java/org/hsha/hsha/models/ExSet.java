package org.hsha.hsha.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ExSet {

    @Id
    @GeneratedValue
    Integer id;


    private Integer weightInKg;
    private Integer reps;

    @ManyToOne
    @JsonIgnore
    private Exercise exercise;

    public ExSet(Integer id, Integer weightInKg, Integer reps, Exercise exercise) {
        this.id = id;
        this.weightInKg = weightInKg;
        this.reps = reps;
        this.exercise = exercise;
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
}
