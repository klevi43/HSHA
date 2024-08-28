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
        public String deleteExSet(@PathVariable int userId, @PathVariable int workoutId,
                                  @PathVariable int exerciseId, @PathVariable int exSetId) throws ServerException {
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

            Optional<ExSet> exerciseExSet = exSetService.retrieveExSetById(exSetId);
            if(exerciseExSet.isEmpty() || !(exerciseExSet.get().getExercise().getId().equals(workoutExercise.get().getId()))) {
                throw new ServerException("Exercise Set: " + exSetId + " not found");
            }

            exSetService.deleteExSetById(exSetId);
            return "redirect:/users/{userId}/workouts/{workoutId}";
        }

        @GetMapping("/users/{userId}/workouts/{workoutId}/exercises/{exerciseId}/exSet/{exSetId}/update")
        public String showUpdateExSetPage(@PathVariable int userId, @PathVariable int workoutId,
                                          @PathVariable int exerciseId, @PathVariable int exSetId, Model model) throws ServerException {
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

            Optional<ExSet> exerciseExSet = exSetService.retrieveExSetById(exSetId);
            if(exerciseExSet.isEmpty() || !(exerciseExSet.get().getExercise().getId().equals(workoutExercise.get().getId()))) {
                throw new ServerException("Exercise Set: " + exSetId + " not found");
            }
            ExSetDto exSetDto = new ExSetDto();
            model.addAttribute("user", user);
            model.addAttribute("userWorkout", userWorkout);
            model.addAttribute("workoutExercise", workoutExercise);
            model.addAttribute("exerciseExSet", exerciseExSet);
            model.addAttribute("exSetDto", exSetDto);
            return "exSets/updateExSet";
        }


        @PostMapping("/users/{userId}/workouts/{workoutId}/exercises/{exerciseId}/exSet/{exSetId}/update")
        public String updateExSet(@PathVariable int userId, @PathVariable int workoutId,
                                  @PathVariable int exerciseId, @PathVariable int exSetId,
                                  @ModelAttribute ExSetDto exSetDto, BindingResult result) throws ServerException {
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

            Optional<ExSet> exerciseExSet = exSetService.retrieveExSetById(exSetId);
            if(exerciseExSet.isEmpty() || !(exerciseExSet.get().getExercise().getId().equals(workoutExercise.get().getId()))) {
                throw new ServerException("Exercise Set: " + exSetId + " not found");
            }

            try {
                exerciseExSet.get().setReps(exSetDto.getReps());
                exerciseExSet.get().setWeightInKg(exSetDto.getWeightInKg());
                exSetService.saveExSet(exerciseExSet.get());

            } catch (Exception e) {
                result.addError(new FieldError("exerciseDto",
                        "weightInKg", e.getMessage()));
            }

            return "redirect:/users/{userId}/workouts/{workoutId}";
        }
}

