package org.hsha.hsha.models;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import org.hsha.hsha.constants.Role;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;


@Entity
@Table(name="user_tbl")
public class User {
    @Id // tells spring that this is the primary key
    @GeneratedValue
    private Integer id;
    private String username;
    private String password;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY) // defining the field that owns the workouts
    @JsonIgnore
    private List<Workout> workouts;


    // CONSTRUCTORS
    public User(Integer id, String username, String password, List<Workout> workouts, String email, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.workouts = workouts;
        this.email = email;
        this.role = role;

    }

    public User() {

    }

    // CREATE USER
    public static User createUser(UserForm userForm, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setEmail(userForm.getEmail());
        String password = passwordEncoder.encode(userForm.getPassword());
        user.setPassword(password);
        user.setRole(Role.USER);
        return user;
    }

    // GETTERS AND SETTERS
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }
}
