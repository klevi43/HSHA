package org.hsha.hsha.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="exercise")

public class Exercise implements Serializable {

    @Id
    String name;
    String bodyPart; // body part(s) worked



    public Exercise(String name, String bodyPart) {
        this.name = name;
        this.bodyPart = bodyPart;
    }

    public Exercise() {

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



}
