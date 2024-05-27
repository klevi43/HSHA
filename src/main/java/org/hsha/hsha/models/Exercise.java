package org.hsha.hsha.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="exercise_tbl")
public class Exercise implements Serializable {
    //
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String bodyPart; // body part(s) worked
    private Integer weightInKg;
    private Integer reps;

    @ManyToMany(mappedBy = "exercises", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Workout> workouts = new HashSet<>();


    // CONSTRUCTORS
    public Exercise(Integer id, String name, String bodyPart, Integer weightInKg, Integer reps, Set<Workout> workouts) {
        this.id = id;
        this.name = name;
        this.bodyPart = bodyPart;
        this.weightInKg = weightInKg;
        this.reps = reps;
        this.workouts = workouts;
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

    public Set<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(Set<Workout> workouts) {
        this.workouts = workouts;
    }
}
