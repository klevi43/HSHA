package org.hsha.hsha.controllers;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.rmi.ServerException;
import java.util.Optional;

@Controller
public class ExSetController {
    @Autowired
    private UserService userService;

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private ExSetService exSetService;

    @GetMapping("/users/{userId}/workouts/{workoutId}/exercises/{exerciseId}/exSet/add")
    public String showAddExSetPage(@PathVariable int userId, @PathVariable int workoutId,
                                   @PathVariable int exerciseId,
                                   Model model) throws ServerException {

        Optional<User> user = userService.retrieveUserById(userId);

        if (user.isEmpty()) {
            throw new ServerException("User: " + userId + " not found");
        }
        Optional<Workout> userWorkout = workoutService.retrieveWorkoutById(workoutId);
        if (userWorkout.isEmpty() || !(userWorkout.get().getUser().getId().equals(user.get().getId()))) {
            throw new ServerException("Workout: " + workoutId + " not found");

        }

        Optional<Exercise> workoutExercise = exerciseService.retrieveExerciseById(exerciseId);
        if (workoutExercise.isEmpty() || !(workoutExercise.get().getWorkout().getId().equals(userWorkout.get().getId()))) {
            throw new ServerException("Exercise: " + exerciseId + " not found");
        }
        ExSetDto exSetDto = new ExSetDto();
        model.addAttribute("user", user);
        model.addAttribute("userWorkout", userWorkout);
        model.addAttribute("workoutExercise", workoutExercise);
        model.addAttribute("exSetDto",exSetDto);
        return "exSets/addExSet";
    }

    @PostMapping("/users/{userId}/workouts/{workoutId}/exercises/{exerciseId}/exSet/add")
        public String addExSetToExercise(@PathVariable int userId, @PathVariable int workoutId,
                                         @PathVariable int exerciseId, @ModelAttribute ExSetDto exSetDto,
                                         BindingResult result) throws ServerException {
            Optional<User> user = userService.retrieveUserById(userId);

            if (user.isEmpty()) {
                throw new ServerException("User: " + userId + " not found");
            }
            Optional<Workout> userWorkout = workoutService.retrieveWorkoutById(workoutId);
            if (userWorkout.isEmpty() || !(userWorkout.get().getUser().getId().equals(user.get().getId()))) {
                throw new ServerException("Workout: " + workoutId + " not found");

            }

            Optional<Exercise> workoutExercise = exerciseService.retrieveExerciseById(exerciseId);
            if (workoutExercise.isEmpty() || !(workoutExercise.get().getWorkout().getId().equals(userWorkout.get().getId()))) {
                throw new ServerException("Exercise: " + exerciseId + " not found");
            }
            try {
                ExSet newExSet = new ExSet();
                newExSet.setExercise(workoutExercise.get());
                newExSet.setWeightInKg(exSetDto.getWeightInKg());
                newExSet.setReps(exSetDto.getReps());
                newExSet.setWorkout(userWorkout.get());
                exSetService.saveExSet(newExSet);

            } catch(Exception e) {
                result.addError(new FieldError("exerciseDto",
                        "weightInKg", e.getMessage()));
            }
            return "redirect:/users/{userId}/workouts/{workoutId}";
        }

        @GetMapping("/users/{userId}/workouts/{workoutId}/exercises/{exerciseId}/exSet/{exSetId}/delete")
        public String showConfirmDeleteExSetPage() {
            return "exSets/confirmDeleteExSet";
        }
}

