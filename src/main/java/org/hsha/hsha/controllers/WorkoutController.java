package org.hsha.hsha.controllers;

import jakarta.transaction.Transactional;
import org.hsha.hsha.models.*;
import org.hsha.hsha.services.ExSetService;
import org.hsha.hsha.services.ExerciseService;
import org.hsha.hsha.services.UserService;
import org.hsha.hsha.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

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
    public String showSingleWorkoutPage(@PathVariable int userId, @PathVariable int workoutId, Model model)
            throws ServerException {

        Optional<User> user = userService.retrieveUserById(userId);

        Optional<Workout> userWorkout = workoutService.retrieveWorkoutById(workoutId);


        List<Exercise> workoutExercises = exerciseService.retrieveAllExercisesByWorkoutId(workoutId);

        List<ExSet> exerciseExSets = exSetService.retrieveAllExSetsByWorkoutId(workoutId);
        ThIterationCounter counter = new ThIterationCounter();

        model.addAttribute("user", user);
        model.addAttribute("userWorkout", userWorkout.get());
        model.addAttribute("workoutExercises", workoutExercises);
        model.addAttribute("exerciseExSets", exerciseExSets);
        model.addAttribute("counter", counter);
        return "workouts/singleUserWorkout";
    }

    @GetMapping("/users/{userId}/workouts/add")
    public String showAddWorkoutsPage(@PathVariable int userId, Model model ) throws Exception {
        // check if user is valid
        Optional<User> user = userService.retrieveUserById(userId);



        WorkoutDto workoutDto = new WorkoutDto();
        model.addAttribute("user", user.get());
        model.addAttribute("workoutDto", workoutDto);
        model.addAttribute("success", false);
        return "workouts/addWorkout";


    }

    @PostMapping("/users/{userId}/workouts/add")
    public String addWorkout(@PathVariable int userId, @ModelAttribute WorkoutDto workoutDto, BindingResult result) throws ServerException {
        Optional<User> user = userService.retrieveUserById(userId);


        try {
            Workout newWorkout = new Workout();
            newWorkout.setName(workoutDto.getName());
            newWorkout.setDate(workoutDto.getDate());
            newWorkout.setUser(user.get());
            workoutService.saveWorkout(newWorkout);
        }
        catch (Exception e) {
            result.addError(new FieldError("workoutDto",
                    "name", e.getMessage()));
        }

        return "redirect:/users/{userId}/workouts";
    }


    @GetMapping("/users/{userId}/workouts/{workoutId}/delete")
    public String showConfirmDeletePage(@PathVariable int userId, @PathVariable int workoutId, Model model)
        throws ServerException {
        Optional<User> user = userService.retrieveUserById(userId);


        Optional<Workout> userWorkout = workoutService.retrieveWorkoutById(workoutId);

        model.addAttribute("user", user);
        model.addAttribute("userWorkout", userWorkout);
        model.addAttribute("workoutExercises", userWorkout.get().getExercises());

        return "workouts/confirmDeleteWorkout";


    }

    @Transactional
    @RequestMapping("/users/{userId}/workouts/{workoutId}/delete/confirm")
    public String deleteWorkout(@PathVariable int userId, @PathVariable int workoutId)
            throws ServerException {
        Optional<User> user = userService.retrieveUserById(userId);

        Optional<Workout> userWorkout = workoutService.retrieveWorkoutById(workoutId);


        workoutService.deleteWorkoutById(workoutId);
        return "redirect:/users/{userId}/workouts";
    }
}
