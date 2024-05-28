package org.hsha.hsha.controllers;

import org.hsha.hsha.models.Workout;
import org.hsha.hsha.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class WorkoutController {
    @Autowired
    WorkoutService workoutService;

    @GetMapping("/workouts")
    public List<Workout> getWorkouts() {
        return workoutService.retrieveAllWorkouts();
    }

    @GetMapping("/workouts/{id}")
    public Optional<Workout> getWorkoutById(@PathVariable int id) {
        return workoutService.retrieveWorkoutById(id);
    }
//    @GetMapping("/workouts/{name}")
//    public Optional<Workout> getWorkoutByName(@PathVariable String name) {
//        return workoutService.retrieveWorkoutByName(name);
//    }

    @PostMapping("/workouts")
    public Workout createWorkout(@RequestBody Workout workout) {
        return workoutService.saveWorkout(workout);
    }
}
