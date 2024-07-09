package org.hsha.hsha.services;

import org.hsha.hsha.Repository.ExerciseRepository;
import org.hsha.hsha.models.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {
    @Autowired
    ExerciseRepository exerciseRepository;

    // get all exercises from DB
    public List<Exercise> retrieveAllExercises() {
        return exerciseRepository.findAll();
    }
    //
    public List<Exercise> retrieveAllExercisesByWorkoutId(Integer workoutId) {
        return exerciseRepository.findAllByWorkout_Id(workoutId);
    }
    public Optional<Exercise> retrieveExerciseById(Integer exId) {
        return exerciseRepository.findById(exId);
    }

    public Optional<Exercise> retrieveExerciseByname(String name) {
        return exerciseRepository.findByNameContaining(name);
    }
    public Exercise saveExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public void deleteExerciseById(Integer exId) {
        exerciseRepository.deleteById(exId);
    }
}
