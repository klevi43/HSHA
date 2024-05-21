package org.hsha.hsha.models;

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
    private Integer exerciseId;
    private String exerciseName;
    private String bodyPart; // body part(s) worked

    @ManyToMany(mappedBy = "exercises")
    Set<Workout> workouts = new HashSet<>();

    public Exercise(String exerciseName, String bodyPart) {
        this.exerciseName = exerciseName;
        this.bodyPart = bodyPart;
    }

    public Exercise() {

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
