package org.hsha.hsha.controllers;

import org.hsha.hsha.models.ExSet;
import org.hsha.hsha.models.Exercise;
import org.hsha.hsha.models.User;
import org.hsha.hsha.models.Workout;
import org.hsha.hsha.services.ExSetService;
import org.hsha.hsha.services.ExerciseService;
import org.hsha.hsha.services.UserService;
import org.hsha.hsha.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.rmi.ServerException;
import java.util.*;

@Controller
public class WorkoutController {
    @Autowired
    UserService userService;

    @Autowired
    WorkoutService workoutService;

    @Autowired
    ExerciseService exerciseService;

    @Autowired
    ExSetService exSetService;

    // get all workouts for a user
    @GetMapping("/users/{userId}/workouts")
    public String getWorkoutsPage(@PathVariable int userId, Model model) throws Exception {

        // check if user is valid
        Optional<User> user = userService.retrieveUserById(userId);

        if(user.isEmpty()) {
            throw new Exception("User Not Found");
        }
        // get workouts
        List<Workout> workouts = user.get().getWorkouts();
        HashMap<String, Workout> uniqueWorkouts = new HashMap<>();

        for (Workout workout : workouts) {
            //if(!uniqueWorkouts.containsKey(workout.getName()) ||
            //        uniqueWorkouts.get(workout.getName()).getDate().isBefore(workout.getDate())) {
                uniqueWorkouts.put(workout.getName(), workout);
            //}
        }



        // add to model
        model.addAttribute("user", user.get());
        model.addAttribute("workouts", uniqueWorkouts);
        // return page
        return "workouts/userWorkoutDashboard";
    }

            @GetMapping("/users/{userId}/workouts/{workoutId}")
    public String getUserWorkoutById(@PathVariable Integer userId, @PathVariable Integer workoutId, Model model)
            throws ServerException {

        Optional<User> user = userService.retrieveUserById(userId);

        if(user.isEmpty()) {
            throw new ServerException("User: " + userId + " not found");
        }
        Optional<Workout> userWorkout = workoutService.retrieveWorkoutById(workoutId);
        if(userWorkout.isEmpty() || !(userWorkout.get().getUser().getId().equals( user.get().getId()))) {
            throw new ServerException("Workout: " + workoutId + " not found");
        }

        List<Exercise> workoutExercises = exerciseService.retrieveAllExercisesByWorkoutId(workoutId);
        // the problem is here on 144
        List<ExSet> exerciseExSets = exSetService.retrieveAllExSetsByWorkoutId(workoutId);

        model.addAttribute(user);
        model.addAttribute("userWorkout", userWorkout.get());
        model.addAttribute("workoutExercises", workoutExercises);
        model.addAttribute("exerciseExSets", exerciseExSets);
        return "workouts/userWorkout";
    }
}
