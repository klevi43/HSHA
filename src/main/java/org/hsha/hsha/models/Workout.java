package org.hsha.hsha.models;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;


import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="workout_tbl")

public class Workout {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Integer id; // identifies workout

    private String name; // name for the workout


    private Date date; // date workout completed

    @ManyToOne
    @JsonIgnore
    User user;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "workout_exercise_tbl",
            joinColumns = {@JoinColumn(name = "workout_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "exercise_id", referencedColumnName = "id")}
    )
    @JsonIgnore
    private List<Exercise> exercises; // exercise performed


    // CONSTRUCTORS
    public Workout(Integer id, String name, Date date, List<Exercise> exercises, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.date = date;
        this.exercises = exercises;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
}

