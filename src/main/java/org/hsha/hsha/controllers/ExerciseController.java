package org.hsha.hsha.controllers;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
}
