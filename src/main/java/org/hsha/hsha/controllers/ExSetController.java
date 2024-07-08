package org.hsha.hsha.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.hsha.hsha.models.ExSet;
import org.hsha.hsha.models.Exercise;
import org.hsha.hsha.models.User;
import org.hsha.hsha.models.Workout;
import org.hsha.hsha.services.ExSetService;
import org.hsha.hsha.services.ExerciseService;
import org.hsha.hsha.services.UserService;
import org.hsha.hsha.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.rmi.ServerException;
import java.util.Optional;
@RestController
public class ExSetController {

    @Autowired
    UserService userService;

    @Autowired
    WorkoutService workoutService;

    @Autowired
    ExerciseService exerciseService;

    @Autowired
    ExSetService exSetService;


    @PostMapping("/users/{userId}/workouts/{workoutId}/exercises/{exerciseId}")
    public ResponseEntity<ExSet> addSetToExercise(@PathVariable Integer userId,
                                                  @PathVariable Integer workoutId,
                                                  @PathVariable Integer exerciseId,
                                                  @RequestBody ExSet exSet,
                                                  HttpServletRequest request) throws ServerException {
        Optional<User> user = userService.retrieveUserById(userId);
        if (user.isEmpty()) {
            throw new ServerException("User: " + userId + " does not exist.");
        }

        Optional<Workout> workout = workoutService.retrieveWorkoutById(workoutId);
        if(workout.isEmpty()){
            throw new ServerException("Workout: " + workoutId + " does not exist.");
        }

        Optional<Exercise> updatedExercise = exerciseService.retrieveExerciseById(exerciseId);
        if(updatedExercise.isEmpty()) {
            throw new ServerException("Exercise: " + exerciseId + "does not exist.");
        }

        exSet.setExercise(updatedExercise.get());
        updatedExercise.get().getExSets().add(exSet);
        exSetService.saveExSet(exSet);


        URI location = ServletUriComponentsBuilder.fromRequestUri(request)
                .path("/{id}")
                .buildAndExpand(exSet.getId())
                .toUri();
        return ResponseEntity.created(location).body(exSet);

    }
}
