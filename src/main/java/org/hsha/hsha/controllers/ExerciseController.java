package org.hsha.hsha.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.hsha.hsha.models.Exercise;
import org.hsha.hsha.models.User;
import org.hsha.hsha.models.Workout;
import org.hsha.hsha.services.ExerciseService;
import org.hsha.hsha.services.UserService;
import org.hsha.hsha.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
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
    private UserService userService;
    @Autowired
    private WorkoutService workoutService;
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
        exerciseService.saveExercise(exercise);
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
    @Transactional
    @DeleteMapping("/exercises/{id}")
    public void deleteExercise(@PathVariable(value = "id") Integer exId) {
        exerciseService.deleteExerciseById(exId);
    }

    // USER WORKOUT EXERCISE RELATED METHODS

    @PostMapping("/users/{userId}/workouts/{workoutId}/exercises")
    public ResponseEntity<Exercise> addExerciseToUserWorkout(@PathVariable Integer userId,
                                                             @PathVariable Integer workoutId,
                                                             @RequestBody Exercise exercise,
                                                             HttpServletRequest request) throws ServerException {
        Optional<User> user = userService.retrieveUserById(userId);
        if (user.isEmpty()) {
            throw new ServerException("User: " + userId + " does not exist.");
        }

        Optional<Workout> updatedWorkout = workoutService.retrieveWorkoutById(workoutId);
        if(updatedWorkout.isEmpty()){
            throw new ServerException("Workout: " + updatedWorkout.get().getName() + " does not exist.");
        }
        exercise.setWorkout(updatedWorkout.get());
        updatedWorkout.get().getExercises().add(exercise);
        exerciseService.saveExercise(exercise); // last change here

        URI location = ServletUriComponentsBuilder.fromRequestUri(request)
                .path("/{id}")
                .buildAndExpand(exercise.getId())
                .toUri();
        return ResponseEntity.created(location).body(exercise);

    }

    @Transactional
    @DeleteMapping("/users/{userId}/workouts/{workoutInd}/exercises/{exerciseInd}")
    public ResponseEntity<Void> deleteExerciseFromUserWorkout(@PathVariable("userId") Integer userId,
                                                              @PathVariable("workoutInd") Integer workoutInd,
                                                              @PathVariable("exerciseInd") Integer exerciseInd)
            throws Exception {
        Optional<User> user = userService.retrieveUserById(userId);
        if(user.isEmpty()) {
            throw new Exception("User: " + userId + " not found");
        }
        Exercise exerciseToDelete = user.get().getWorkouts().get(workoutInd).getExercises().get(exerciseInd);

        System.out.println(exerciseInd);
        exerciseService.deleteExerciseById(exerciseToDelete.getId());
        return ResponseEntity.noContent().build();
    }

    @Modifying
    @PutMapping("/users/{userId}/workouts/{workoutId}/exercises/{exerciseId}")
    public Exercise updateExerciseFromUserWorkoutById(@PathVariable Integer userId,
                                                      @PathVariable Integer workoutId,
                                                      @PathVariable Integer exerciseId,
                                                      @RequestBody Exercise exercise)
            throws ServerException {
        Optional<User> user = userService.retrieveUserById(userId);
        if(user.isEmpty()) {
            throw new ServerException("User " + userId +  " not found!");
        }

        Optional<Workout> workout = workoutService.retrieveWorkoutById(workoutId);
        if(workout.isEmpty()) {
            throw new ServerException("Workout not found!");
        }

        Optional<Exercise> updatedExercise = exerciseService.retrieveExerciseById(exerciseId);

        if(updatedExercise.isEmpty()) {
            throw new ServerException("Exercise: " + exerciseId + " not found");
        }

        if(exercise.getName() != null) {
            updatedExercise.get().setName(exercise.getName());
        }

        if(exercise.getBodyPart() != null) {
            updatedExercise.get().setBodyPart(exercise.getBodyPart());
        }

        exerciseService.saveExercise(updatedExercise.get());
        return updatedExercise.get();
    }
}
