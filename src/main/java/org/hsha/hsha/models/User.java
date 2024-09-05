package org.hsha.hsha.models;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_tbl")
public class User {
    @Id // tells spring that this is the primary key
    @GeneratedValue
    private Integer id;
    private String password;

    @NonNull
    @Column(unique = true, nullable = false)
    private String email;

    private String firstName;
    private String lastName;


    private String role;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY) // defining the field that owns the workouts
    @JsonIgnore
    @OrderBy("date DESC")
    private List<Workout> workouts;



}
