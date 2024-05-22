package org.hsha.hsha.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="exercise")
public class Exercise implements Serializable {
    //
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer exerciseId;
    @JsonProperty("exerciseName")
    private String exerciseName;
    @JsonProperty("bodyPart")
    private String bodyPart; // body part(s) worked

    @JsonIgnore
    @ManyToMany(mappedBy = "exercises")
    Set<Workout> workouts = new HashSet<>();

    public Exercise(Integer exerciseId, String exerciseName, String bodyPart) {
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.bodyPart = bodyPart;
    }

    public Exercise() {

    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }



}
