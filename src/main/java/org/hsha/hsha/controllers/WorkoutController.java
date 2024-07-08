package org.hsha.hsha.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.hsha.hsha.models.User;
import org.hsha.hsha.models.Workout;
import org.hsha.hsha.services.UserService;
import org.hsha.hsha.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
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

    @Autowired
    UserService userService;

    // USER WORKOUT RELATED METHODS
    @GetMapping("/users/{id}/workouts")
    public List<Workout> getAllUserWorkouts(@PathVariable Integer id) throws Exception {
        Optional<User> user = userService.retrieveUserById(id);

        if(user.isEmpty()) {
            throw new Exception("id: " + id);
        }
        return user.get().getWorkouts();
    }

    @GetMapping("users/{id}/workouts/{workoutId}")
    public Workout getUserWorkoutById(@PathVariable Integer id,
                                      @PathVariable Integer workoutId)
            throws Exception {
        Optional<User> user = userService.retrieveUserById(id);
        if(user.isEmpty()) {
            throw new Exception("id: " + id);
        }

        Optional<Workout> workout = workoutService.retrieveWorkoutById(workoutId);
        if(workout.isEmpty()) {
            throw new Exception("workoutId: " + workoutId + "not found");
        }

        return workout.get();

    }


    @PostMapping("/users/{userId}/workouts")
    public ResponseEntity<Workout> createUserWorkout(@PathVariable Integer userId, @RequestBody Workout workout,
                                                     HttpServletRequest request) throws Exception {
        Optional<User> user = userService.retrieveUserById(userId);
        if(user.isEmpty()) {
            throw new Exception("User not found!");
        }
        workout.setUser(user.get());
        Workout savedWorkout = workoutService.saveWorkout(workout);

        URI location = ServletUriComponentsBuilder.fromRequestUri(request)
                .path("/{id}")
                .buildAndExpand(savedWorkout.getId())
                .toUri();
        return ResponseEntity.created(location).body(workout);

    }
    @Transactional
    @DeleteMapping("/users/{userId}/workouts/{workoutId}")
    public ResponseEntity<Void> deleteUserWorkoutById(@PathVariable Integer userId, @PathVariable(value = "workoutId") Integer workoutId) throws Exception {
        Optional<User> user = userService.retrieveUserById(userId);
        if(user.isEmpty()) {
            throw new Exception("User: " + userId + " not found");
        }

        Optional<Workout> workoutToDelete = workoutService.retrieveWorkoutById(workoutId);
        if(workoutToDelete.isEmpty()) {
            throw new Exception("Workout: " + workoutId + " not found");
        }
        workoutService.deleteWorkoutById(workoutToDelete.get().getId());
        return ResponseEntity.noContent().build();
    }

    @Modifying
    @PutMapping("/users/{userId}/workouts/{workoutId}")
    public Workout updateUserWorkoutById(@PathVariable Integer userId, @PathVariable Integer workoutId,
                                         @RequestBody Workout workout) throws ServerException {
        Optional<User> user = userService.retrieveUserById(userId);
        if(user.isEmpty()) {
            throw new ServerException("User " + userId +  " not found!");
        }
        // get workout based
        Optional<Workout> updatedWorkout = workoutService.retrieveWorkoutById(workoutId);
        if(updatedWorkout.isEmpty()) {
            throw new ServerException("Workout not found!");
        }
        if(workout.getDate() != null) {
            updatedWorkout.get().setDate(workout.getDate());
        }

        if(workout.getName() != null) {
            updatedWorkout.get().setName(workout.getName());
        }


        workoutService.saveWorkout(updatedWorkout.get());

        return updatedWorkout.get();
    }

    //    @GetMapping("/users/{userId}/workouts/{workoutId}")
//    public String getUserWorkoutById(@PathVariable Integer userId, @PathVariable Integer workoutId, Model model)
//            throws ServerException {
//
//        Optional<User> user = userService.retrieveUserById(userId);
//
//        if(user.isEmpty()) {
//            throw new ServerException("User: " + userId + " not found");
//        }
//        Optional<Workout> userWorkout = workoutService.retrieveWorkoutById(workoutId);
//        if(userWorkout.isEmpty()) {
//            throw new ServerException("Workout: " + workoutId + " not found");
//        }
//
//        List<Exercise> exercises = userWorkout.get().getExercises();
//        model.addAttribute(user);
//        model.addAttribute("userWorkout", userWorkout.get());
//        model.addAttribute("exercises", exercises);
//        return "userWorkouts/userWorkout";
//    }
}
