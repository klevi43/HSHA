package org.hsha.hsha.controllers;

import org.hsha.hsha.models.Exercise;
import org.hsha.hsha.services.ExSetService;
import org.hsha.hsha.services.ExerciseService;
import org.hsha.hsha.services.UserService;
import org.hsha.hsha.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExSetController {
    @Autowired
    private UserService userService;

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private ExSetService exSetService;

    @GetMapping("/users/{userId}/workouts/{workoutId}/exercises/{exerciseId}/exSet/add")
    public String showAddExSetPage() {
        return "exSets/addExSet";
    }
}
