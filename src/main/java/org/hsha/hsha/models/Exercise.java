package org.hsha.hsha.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Exercise {

    @Id
    String name;
    String bodyPart; // body part(s) worked



    public Exercise(String name, String bodyPart) {
        this.name = name;
        this.bodyPart = bodyPart;
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
