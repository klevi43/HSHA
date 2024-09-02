package org.hsha.hsha.controllers;

import org.hsha.hsha.Repository.ExerciseRepository;
import org.hsha.hsha.models.Exercise;
import org.hsha.hsha.models.ExerciseDto;
import org.hsha.hsha.models.User;
import org.hsha.hsha.models.Workout;
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
    private ExerciseRepository exerciseRepository;

    @GetMapping("users/{userId}/workouts/{workoutId}/exercises/add")
    public String showAddExercisePage(@PathVariable int userId,@PathVariable int workoutId, Model model) throws ServerException {
        Optional<User> user = userService.retrieveUserById(userId);

        if(user.isEmpty()) {
            throw new ServerException("User: " + userId + " not found");
        }
        Optional<Workout> userWorkout = workoutService.retrieveWorkoutById(workoutId);
        if(userWorkout.isEmpty() || !(userWorkout.get().getUser().getId().equals( user.get().getId()))) {
            throw new ServerException("Workout: " + workoutId + " not found");
        }

        ExerciseDto exerciseDto = new ExerciseDto();
        model.addAttribute("user", user);
        model.addAttribute("userWorkout", userWorkout);
        model.addAttribute("exerciseDto", exerciseDto);
        return "exercises/addExercise";
    }

    @PostMapping("users/{userId}/workouts/{workoutId}/exercises/add")
    public String addExerciseToWorkout(@PathVariable int userId, @PathVariable int workoutId,
                                       @ModelAttribute ExerciseDto exerciseDto, BindingResult result) throws ServerException {
        Optional<User> user = userService.retrieveUserById(userId);

        if (user.isEmpty()) {
            throw new ServerException("User: " + userId + " not found");
        }
        Optional<Workout> userWorkout = workoutService.retrieveWorkoutById(workoutId);
        if (userWorkout.isEmpty() || !(userWorkout.get().getUser().getId().equals(user.get().getId()))) {
            throw new ServerException("Workout: " + workoutId + " not found");

        }
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
        Optional<User> user = userService.retrieveUserById(userId);
        if(user.isEmpty()) {
            throw new ServerException("User: " + userId + " not found");
        }

        Optional<Workout> userWorkout = workoutService.retrieveWorkoutById(workoutId);
        if(userWorkout.isEmpty() || !(userWorkout.get().getUser().getId().equals( user.get().getId()))) {
            throw new ServerException("Workout: " + workoutId + " not found");
        }

        Optional<Exercise> workoutExercise = exerciseService.retrieveExerciseById(exerciseId);
        if(workoutExercise.isEmpty() || !(workoutExercise.get().getWorkout().getId().equals(userWorkout.get().getId()))) {
            throw new ServerException("Exercise: " + exerciseId + " not found");
        }

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
        Optional<User> user = userService.retrieveUserById(userId);
        if(user.isEmpty()) {
            throw new ServerException("User: " + userId + " not found");
        }

        Optional<Workout> userWorkout = workoutService.retrieveWorkoutById(workoutId);
        if(userWorkout.isEmpty() || !(userWorkout.get().getUser().getId().equals( user.get().getId()))) {
            throw new ServerException("Workout: " + workoutId + " not found");
        }

        Optional<Exercise> workoutExercise = exerciseService.retrieveExerciseById(exerciseId);
        if(workoutExercise.isEmpty() || !(workoutExercise.get().getWorkout().getId().equals(userWorkout.get().getId()))) {
            throw new ServerException("Exercise: " + exerciseId + " not found");
        }

        // TODO: understand why sets are not updating

        Optional<Exercise> existingExercise = exerciseService.retrieveExerciseByname(exerciseDto.getName());

        try {
            if(existingExercise.isEmpty()) {
                workoutExercise.get().setWorkout(userWorkout.get());
                workoutExercise.get().setName(exerciseDto.getName());
                workoutExercise.get().setBodyPart(exerciseDto.getBodyPart());
                workoutExercise.get().setExSets(null);
                exerciseService.saveExercise(workoutExercise.get());
            }
            else {
                workoutExercise.get().setWorkout(existingExercise.get().getWorkout());
                workoutExercise.get().setId(existingExercise.get().getId());
                workoutExercise.get().setName(existingExercise.get().getName());
                workoutExercise.get().setBodyPart(existingExercise.get().getBodyPart());
                workoutExercise.get().setExSets(existingExercise.get().getExSets());
                exerciseService.saveExercise(workoutExercise.get());
            }
        } catch (Exception e) {
            result.addError(new FieldError("exerciseDto",
                    "name", e.getMessage()));
        }
        return "redirect:/users/{userId}/workouts/{workoutId}";
    }
}
