package org.hsha.hsha.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="user_tbl")
public class User {
    @Id // tells spring that this is the primary key
    @GeneratedValue
    Integer id;
    String username;
    String password;
    @OneToMany(mappedBy = "user") // defining the field that owns the workouts
    @JsonIgnore // workouts will no longer be part of the user requests
    private List<Workout> workouts;
//
    public User(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;

    }
    //
    public User() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }
}
