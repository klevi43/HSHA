package org.hsha.hsha.models;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import org.springframework.validation.annotation.Validated;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name="workout_tbl")

public class Workout {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id; // identifies workout

    private String name; // name for the workout


    private LocalDate date; // date workout completed

    @ManyToOne
    @JsonIgnore
    private User user;


    @OneToMany(mappedBy = "workout", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY,
    orphanRemoval = true) // defining the field that owns the workouts
    private List<Exercise> exercises; // exercise performed

    @OneToMany(mappedBy = "workout", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<ExSet> exSets;

//
    // CONSTRUCTORS
    public Workout(Integer id, String name, LocalDate date, List<Exercise> exercises, User user,
                   List<ExSet> exSets) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.date = date;
        this.exercises = exercises;
        this.exSets = exSets;
    }
//
    public Workout() {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ExSet> getExSets() {
        return exSets;
    }

    public void setExSets(List<ExSet> exSets) {
        this.exSets = exSets;
    }
}

