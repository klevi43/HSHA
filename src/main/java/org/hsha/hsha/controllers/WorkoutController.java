package org.hsha.hsha.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.hsha.hsha.models.Workout;
import org.hsha.hsha.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;
//
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
    public ResponseEntity<Workout> createWorkout(@RequestBody Workout workout, HttpServletRequest request) throws ServerException {
        workoutService.saveWorkout(workout);
        if (workout != null) {
            URI location = ServletUriComponentsBuilder.fromRequestUri(request)
                    .path("/{id}")
                    .buildAndExpand(workout.getId())
                    .toUri();
            return ResponseEntity.created(location).body(workout);
        } else {
            throw new ServerException("Error in creating new workout");
        }

    }
}
