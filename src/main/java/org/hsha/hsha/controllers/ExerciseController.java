package org.hsha.hsha.controllers;

import org.hsha.hsha.models.Exercise;
import org.hsha.hsha.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;
    //
    @GetMapping("/exercises")
    public List<Exercise>getExercises() {
        return exerciseService.retrieveAllExercises();
    }

}
