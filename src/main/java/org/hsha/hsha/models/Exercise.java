package org.hsha.hsha.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;


import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="exercise_tbl")
public class Exercise implements Serializable {
    //
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String name;

    private String bodyPart; // body part(s) worked

    @JsonIgnore
    @ManyToOne
    private Workout workout;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    private List<ExSet> exSets;
   //


}
