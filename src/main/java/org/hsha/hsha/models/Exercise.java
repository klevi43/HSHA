package org.hsha.hsha.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="exercise_tbl")
public class Exercise implements Serializable {
    //
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String bodyPart; // body part(s) worked

    @JsonIgnore
    @ManyToOne
    private Workout workout;

    @OneToMany(mappedBy = "exercise")
    private List<ExSet> exSets;
   //


}
