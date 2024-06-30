package org.hsha.hsha.controllers;

import jakarta.persistence.criteria.CriteriaBuilder;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResponseExtractor;
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
    @Autowired
    private ExerciseService exerciseService;

    // USER RELATED METHODS
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
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.retrieveAllUsers();
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable(value = "id") Integer id) {
        return userService.retrieveUserById(id);
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

    // USER WORKOUT RELATED METHODS
    @GetMapping("/users/{id}/workouts")
    public List<Workout> getAllUserWorkouts(@PathVariable Integer id) throws Exception {
        Optional<User> user = userService.retrieveUserById(id);

        if(user.isEmpty()) {
            throw new Exception("id:" + id);
        }
        return user.get().getWorkouts();
    }

    @GetMapping("users/{id}/workouts/{workoutInd}")
    public Workout getUserWorkoutById(@PathVariable Integer id,
                                      @PathVariable Integer workoutInd)
            throws Exception {
        Optional<User> user = userService.retrieveUserById(id);
        if(user.isEmpty()) {
            throw new Exception("id:" + id);
        }

        Workout workout = user.get().getWorkouts().get(workoutInd);
        if(workout == null) {
            throw new Exception("workoutId:" + workoutInd);
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
@DeleteMapping("/users/{userId}/workouts/{workoutInd}")
public ResponseEntity<Void> deleteUserWorkoutById(@PathVariable Integer userId, @PathVariable(value = "workoutInd") Integer workoutInd) throws Exception {
        Optional<User> user = userService.retrieveUserById(userId);
        if(user.isEmpty()) {
            throw new Exception("User: " + userId + " not found");
        }

        Workout workoutToDelete = user.get().getWorkouts().get(workoutInd);
        workoutService.deleteWorkoutById(workoutToDelete.getId());
        return ResponseEntity.noContent().build();
}

    @Modifying
    @PutMapping("/users/{userId}/workouts/{workoutInd}")
    public Workout updateUserWorkoutById(@PathVariable Integer userId, @PathVariable Integer workoutInd,
                                         @RequestBody Workout workout) throws ServerException {
        Optional<User> user = userService.retrieveUserById(userId);
        if(user.isEmpty()) {
            throw new ServerException("User " + userId +  " not found!");
        }
        // get workout based
        Workout updatedWorkout = user.get().getWorkouts().get(workoutInd);
        if(updatedWorkout == null) {
            throw new ServerException("Workout not found!");
        }
        if(workout.getDate() != null) {
            updatedWorkout.setDate(workout.getDate());
        }

        if(workout.getName() != null) {
            updatedWorkout.setName(workout.getName());
        }


        workoutService.saveWorkout(updatedWorkout);

        return updatedWorkout;
    }

    // USER WORKOUT EXERCISE RELATED METHODS

    @PostMapping("/users/{userId}/workouts/{workoutInd}/exercises")
    public ResponseEntity<Exercise> addExerciseToUserWorkout(@PathVariable Integer userId,
                                                             @PathVariable Integer workoutInd,
                                                             @RequestBody Exercise exercise,
                                                             HttpServletRequest request) throws ServerException {
        Optional<User> user = getUserById(userId);
        if (user.isEmpty()) {
            throw new ServerException("User: " + userId + " does not exist.");
        }

        Workout updatedWorkout = user.get().getWorkouts().get(workoutInd);
        if(updatedWorkout == null){
            throw new ServerException("Workout: " + updatedWorkout.getName() + " does not exist.");
        }
        exercise.setWorkouts(updatedWorkout);
        updatedWorkout.getExercises().add(exercise);
        workoutService.saveWorkout(updatedWorkout);

        URI location = ServletUriComponentsBuilder.fromRequestUri(request)
                .path("/{id}")
                .buildAndExpand(exercise.getId())
                .toUri();
        return ResponseEntity.created(location).body(exercise);

    }

    @Transactional
    @DeleteMapping("/users/{userId}/workouts/{workoutInd}/exercises/{exerciseId}")
    public ResponseEntity<Void> deleteExerciseFromUserWorkout(@PathVariable Integer userId,
                                                              @PathVariable Integer workoutInd,
                                                              @PathVariable("exerciseId") Integer exerciseId)
    throws Exception {
        Optional<User> user = userService.retrieveUserById(userId);
        if(user.isEmpty()) {
            throw new Exception("User: " + userId + " not found");
        }


        System.out.println(exerciseId);
        exerciseService.deleteExerciseById(exerciseId);
        return ResponseEntity.noContent().build();
    }

}
