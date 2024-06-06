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

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY) // if you want to retrieve the user and workout details in the same query, user eager strategy
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "workout_exercise_tbl",
            joinColumns = {@JoinColumn(name = "workout_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "exercise_id", referencedColumnName = "id")}
    )
    private List<Exercise> exercises = new ArrayList<>(); // exercise performed


    // CONSTRUCTORS
    public Workout(Integer id, String name, Date date, List<Exercise> exercises) {
        this.id = id;
        this.name = name;
        //this.user = user;
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

