package org.hsha.hsha.models;

import java.util.Date;

public class Exercise {

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
