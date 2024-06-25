package org.hsha.hsha.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;


import java.io.Serializable;


@Entity
@Table(name="exercise_tbl")
public class Exercise {
    //
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String bodyPart; // body part(s) worked
    private Integer weightInKg;
    private Integer reps;

    @ManyToOne

    private Workout workout;


    // CONSTRUCTORS
    public Exercise(Integer id, String name, String bodyPart, Integer weightInKg, Integer reps, Workout workout) {
        this.id = id;
        this.name = name;
        this.bodyPart = bodyPart;
        this.weightInKg = weightInKg;
        this.reps = reps;
        this.workout = workout;
    }

    public Exercise() {

    }


    // GETTERS AND SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkouts(Workout workout) {
        this.workout = workout;
    }
}
