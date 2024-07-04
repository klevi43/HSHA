package org.hsha.hsha.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;


import java.io.Serializable;
import java.util.List;


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

    // CONSTRUCTORS
    public Exercise(Integer id, String name, String bodyPart, Workout workout, List<ExSet> exSets) {
        this.id = id;
        this.name = name;
        this.bodyPart = bodyPart;
        this.workout = workout;
        this.exSets = exSets;
    }

    public Exercise() {

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

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public List<ExSet> getExSets() {
        return exSets;
    }

    public void setExSets(List<ExSet> exSets) {
        this.exSets = exSets;
    }
}
