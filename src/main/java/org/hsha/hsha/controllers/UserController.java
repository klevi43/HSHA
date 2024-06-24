package org.hsha.hsha.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.hsha.hsha.models.Exercise;
import org.hsha.hsha.models.User;
import org.hsha.hsha.models.Workout;
import org.hsha.hsha.services.UserService;
import org.hsha.hsha.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.rmi.ServerException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private WorkoutService workoutService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.retrieveAllUsers();
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable(value = "id") Integer id) {
        return userService.retrieveUserById(id);
    }

    @GetMapping("/users/{id}/workouts")
    public List<Workout> getAllUserWorkouts(@PathVariable int id) throws Exception {
        Optional<User> user = userService.retrieveUserById(id);

        if(user.isEmpty()) {
            throw new Exception("id:" + id);
        }
        return user.get().getWorkouts();
    }

    @GetMapping("users/{id}/workouts/{workoutId}")
    public Workout getUserWorkoutById(@PathVariable int id,
                                      @PathVariable int workoutId)
            throws Exception {
        Optional<User> user = userService.retrieveUserById(id);
        if(user.isEmpty()) {
            throw new Exception("id:" + id);
        }

        Workout workout = user.get().getWorkouts().get(workoutId);
        if(workout == null) {
            throw new Exception("workoutId:" + workoutId);
        }

        return workout;

    }

//    @GetMapping("/users/{id}/thWorkouts")
//    public String getUserThWorkouts(@PathVariable int id, Model model) throws Exception {
//        Optional<User> user = userService.retrieveUserById(id);
//
//        if(user.isEmpty()) {
//            throw new Exception("id: " + id);
//        }
//        List<Workout> userWorkouts = user.get().getWorkouts().stream().toList();
//        List<Exercise> userExercises = userWorkouts.get(0).getExercises();
//        model.addAttribute("userWorkouts", userWorkouts);
//        model.addAttribute("userExercises", userExercises);
//        return "thymeleafEx/UserWorkouts";
//    }
@PostMapping("/users/{id}/workouts")
public ResponseEntity<Workout> createWorkout(@PathVariable int id, @RequestBody Workout workout, HttpServletRequest request) throws Exception {
        Optional<User> user = userService.retrieveUserById(id);
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
@DeleteMapping("/users/{id}/workouts/{workoutId}")
public ResponseEntity<Void> deleteWorkoutById(@PathVariable(value = "workoutId") int workoutId) throws Exception {

        workoutService.deleteWorkoutById(workoutId);
        return ResponseEntity.noContent().build();
}

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user, HttpServletRequest request) throws ServerException{
        userService.saveUser(user);
        if (user != null) {
            URI location = ServletUriComponentsBuilder.fromRequestUri(request)
                    .path("/{id}")
                    .buildAndExpand(user.getId())
                    .toUri();
            return ResponseEntity.created(location).body(user);
        } else {
            throw new ServerException("Error in creating user");
        }

    }



    @Transactional
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable(value = "id") Integer id) {
        userService.deleteUserById(id);
    }

    @Modifying
    @PutMapping("/users/{id}")
    public User updateUserById(@PathVariable int id, @RequestBody User user) throws ServerException {
        Optional<User> updatedUser = userService.retrieveUserById(id);
        if(updatedUser.isEmpty()) {
            throw new ServerException("User not found!");
        }
        if (user.getUsername() != null)
            updatedUser.get().setUsername(user.getUsername());
        if (user.getPassword() != null)
            updatedUser.get().setPassword(user.getPassword());

        userService.saveUser(updatedUser.get());
        return user;
    }

    @Modifying
    @PutMapping("/users/{userId}/workouts/{wId}")
    public Workout updateUserWorkoutById(@PathVariable Integer userId, @PathVariable Integer wId,
                                         @RequestBody Workout workout) throws ServerException {
        Optional<User> userTocheck = userService.retrieveUserById(userId);
        if(userTocheck.isEmpty()) {
            throw new ServerException("User " + userId +  " not found!");
        }
        // get workout based
        Optional<Workout> updatedWorkout = workoutService.retrieveWorkoutById(wId);
        if(updatedWorkout.isEmpty()) {
            throw new ServerException("Workout " + wId + " not found!");
        }
        if(workout.getDate() != null) {
            updatedWorkout.get().setDate(workout.getDate());
        }

        if(workout.getName() != null) {
            updatedWorkout.get().setName(workout.getName());
        }

        if(workout.getExercises() != null) {
            updatedWorkout.get().setExercises(workout.getExercises());
        }

        workoutService.saveWorkout(updatedWorkout.get());

        return updatedWorkout.get();
    }

}
