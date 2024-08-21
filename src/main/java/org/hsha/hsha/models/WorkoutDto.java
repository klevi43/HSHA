package org.hsha.hsha.models;


import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class WorkoutDto {
    private String name;
    private LocalDate date;

}
