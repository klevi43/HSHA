package org.hsha.hsha.models;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import org.hsha.hsha.constants.Role;
import org.springframework.security.crypto.password.PasswordEncoder;


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
    private String username;
    private String password;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY) // defining the field that owns the workouts
    @JsonIgnore
    private List<Workout> workouts;



}
