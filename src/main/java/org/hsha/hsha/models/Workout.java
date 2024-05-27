package org.hsha.hsha.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="workout_tbl")
public class Workout {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Integer id; // identifies workout

    private String name; // name for the workout
    int userId; // used to associate workout with user

    Date date; // date workout completed

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "workout_exercise_tbl",
            joinColumns = {@JoinColumn(name = "workout_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "exercise_id", referencedColumnName = "id")}
    )
    @JsonManagedReference
    private Set<Exercise> exercises = new HashSet<>(); // exercise performed


    // CONSTRUCTORS
    public Workout(Integer id, String name, int userId, Date date, Set<Exercise> exercises) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.date = date;
        this.exercises = exercises;
    }

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }
}

