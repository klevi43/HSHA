package org.hsha.hsha.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.hsha.hsha.models.Exercise;
import org.hsha.hsha.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

@RestController
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;
    //
    @GetMapping("/exercises")
    public List<Exercise>getExercises() {
        return exerciseService.retrieveAllExercises();
    }

    @GetMapping("/exercises/{id}")
    public Optional<Exercise> getExerciseById(@PathVariable(value = "id") Integer id) {
        return exerciseService.retrieveExerciseById(id);
    }

    @GetMapping("/exercises/{name}")
    public Optional<Exercise> getExerciseByName(@PathVariable(value = "name") String name) {
        return exerciseService.retrieveExerciseByname(name);
    }

    @PostMapping("/exercises")
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise, HttpServletRequest request) throws ServerException {
        exerciseService.createExercise(exercise);
        if (exercise != null) {
            URI location = ServletUriComponentsBuilder.fromRequestUri(request)
                    .path("/{id}")
                    .buildAndExpand(exercise.getId())
                    .toUri();
            return ResponseEntity.created(location).body(exercise);
        } else {
            throw new ServerException("Error in creating new exercise");
        }
    }

    @DeleteMapping("/exercises/{id}")
    public void deleteExercise(@PathVariable(value = "id") Integer exId) {
        exerciseService.deleteExerciseById(exId);
    }

}
