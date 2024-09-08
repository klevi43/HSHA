package org.hsha.hsha.controllers;

import jakarta.transaction.Transactional;
import org.hsha.hsha.Repository.ExerciseRepository;
import org.hsha.hsha.models.*;
import org.hsha.hsha.services.ExSetService;
import org.hsha.hsha.services.ExerciseService;
import org.hsha.hsha.services.UserService;
import org.hsha.hsha.services.WorkoutService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

@Controller
public class ExerciseController {
    @Autowired
    private UserService userService;

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private ExSetService exSetService;


    @GetMapping("users/{userId}/workouts/{workoutId}/exercises/add")
    public String showAddExercisePage(@PathVariable int userId,@PathVariable int workoutId, Model model) throws ServerException {


        // Validate the user and workout
        Optional<User> user = userService.retrieveUserById(userId);

        Optional<Workout> userWorkout = workoutService.retrieveWorkoutById(workoutId);




        ExerciseDto exerciseDto = new ExerciseDto();
        model.addAttribute("user", user);
        model.addAttribute("userWorkout", userWorkout);
        model.addAttribute("exerciseDto", exerciseDto);
        return "exercises/addExercise";
    }

    @PostMapping("users/{userId}/workouts/{workoutId}/exercises/add")
    public String addExerciseToWorkout(@PathVariable int userId, @PathVariable int workoutId,
                                       @ModelAttribute ExerciseDto exerciseDto, BindingResult result) throws ServerException {
        // Validate the user and workout
        Optional<User> user = userService.retrieveUserById(userId);

        Optional<Workout> userWorkout = workoutService.retrieveWorkoutById(workoutId);



        try {
            Exercise newExercise = new Exercise();
            newExercise.setWorkout(userWorkout.get());
            newExercise.setName(exerciseDto.getName());
            newExercise.setBodyPart(exerciseDto.getBodyPart());
            exerciseService.saveExercise(newExercise);
        }
        catch (Exception e) {
            result.addError(new FieldError("exerciseDto",
                    "name", e.getMessage()));
        }
        return "redirect:/users/{userId}/workouts/{workoutId}";
    }

    @GetMapping("users/{userId}/workouts/{workoutId}/exercises/{exerciseId}/update")
    public String showUpdateExercisePage(@PathVariable int userId,@PathVariable int workoutId, @PathVariable int exerciseId, Model model)
            throws ServerException {
        // Validate the user, workout, and exercises
        Optional<User> user = userService.retrieveUserById(userId);

        Optional<Workout> userWorkout = workoutService.retrieveWorkoutById(workoutId);

        Optional<Exercise> workoutExercise = exerciseService.retrieveExerciseById(exerciseId);


        ExerciseDto exerciseDto = new ExerciseDto();
        model.addAttribute("user", user);
        model.addAttribute("userWorkout", userWorkout);
        model.addAttribute("workoutExercise", workoutExercise);
        model.addAttribute("exerciseDto", exerciseDto);
        return "exercises/updateExercise";
    }

    @RequestMapping("users/{userId}/workouts/{workoutId}/exercises/{exerciseId}/update")
    public String updateExerciseInWorkout(@PathVariable int userId,@PathVariable int workoutId,
                                        @PathVariable int exerciseId, @ModelAttribute ExerciseDto exerciseDto,
                                        BindingResult result)
            throws ServerException {
        // Validate the user, workout, and exercises
        Optional<User> user = userService.retrieveUserById(userId);

        Optional<Workout> userWorkout = workoutService.retrieveWorkoutById(workoutId);

        Optional<Exercise> workoutExercise = exerciseService.retrieveExerciseById(exerciseId);


        try {
            workoutExercise.get().setWorkout(userWorkout.get());
            workoutExercise.get().setName(exerciseDto.getName());
            workoutExercise.get().setBodyPart(exerciseDto.getBodyPart());
            exerciseService.saveExercise(workoutExercise.get());

        } catch (Exception e) {
            result.addError(new FieldError("exerciseDto",
                    "name", e.getMessage()));
        }
        return "redirect:/users/{userId}/workouts/{workoutId}";
    }

    @GetMapping("users/{userId}/workouts/{workoutId}/exercises/{exerciseId}/delete")
    public String showDeleteExercisePage(@PathVariable int userId,@PathVariable int workoutId,
                                         @PathVariable int exerciseId, Model model) throws ServerException {
        // Validate the user, workout, and exercises
        Optional<User> user = userService.retrieveUserById(userId);

        Optional<Workout> userWorkout = workoutService.retrieveWorkoutById(workoutId);

        Optional<Exercise> workoutExercise = exerciseService.retrieveExerciseById(exerciseId);

        List<ExSet> exerciseExSets = exSetService.retrieveAllExSetsByWorkoutId(workoutId);
        ThIterationCounter counter = new ThIterationCounter();

        // add model attributes
        model.addAttribute("user", user);
        model.addAttribute("userWorkout", userWorkout);
        model.addAttribute("workoutExercise", workoutExercise);
        model.addAttribute("exerciseExSets", exerciseExSets);
        model.addAttribute("counter", counter);
        // return page
        return "exercises/confirmDeleteExercise";
    }

    @Transactional
    @RequestMapping("users/{userId}/workouts/{workoutId}/exercises/{exerciseId}/delete/request")
    public String deleteExercise(@PathVariable int userId,@PathVariable int workoutId,
                                 @PathVariable int exerciseId) throws ServerException {
        Optional<User> user = userService.retrieveUserById(userId);
        Optional<Workout> userWorkout = workoutService.retrieveWorkoutById(workoutId);

        Optional<Exercise> workoutExercise = exerciseService.retrieveExerciseById(exerciseId);


        exerciseService.deleteExerciseById(workoutExercise.get().getId());

        return "redirect:/users/{userId}/workouts/{workoutId}";
    }





}
