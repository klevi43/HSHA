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
    @Autowired
    private WorkoutController workoutController;

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

    // USER WORKOUT EXERCISE RELATED METHODS

    @PostMapping("/users/{userId}/workouts/{workoutId}/exercises")
    public ResponseEntity<Exercise> addExerciseToUserWorkout(@PathVariable Integer userId,
                                                             @PathVariable Integer workoutId,
                                                             @RequestBody Exercise exercise,
                                                             HttpServletRequest request) throws ServerException {
        Optional<User> user = getUserById(userId);
        if (user.isEmpty()) {
            throw new ServerException("User: " + userId + " does not exist.");
        }

        Optional<Workout> updatedWorkout = workoutService.retrieveWorkoutById(workoutId);
        if(updatedWorkout.isEmpty()){
            throw new ServerException("Workout: " + updatedWorkout.get().getName() + " does not exist.");
        }
        exercise.setWorkouts(updatedWorkout.get());
        updatedWorkout.get().getExercises().add(exercise);
        workoutService.saveWorkout(updatedWorkout.get());

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

        if( exercise.getReps() != null) {
            updatedExercise.get().setReps(exercise.getReps());
        }

        if(exercise.getWeightInKg() !=  null) {
            updatedExercise.get().setWeightInKg(exercise.getWeightInKg());
        }

        exerciseService.saveExercise(updatedExercise.get());
        return updatedExercise.get();
    }
}
