package org.hsha.hsha.services;

import org.hsha.hsha.Repository.ExerciseRepository;
import org.hsha.hsha.models.Exercise;
import org.hsha.hsha.models.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;
import java.rmi.ServerException;
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
    public Optional<Exercise> retrieveExerciseById(Integer id) throws ServerException {
        Optional<Exercise> validExercise = retrieveValidExerciseById(id);
        return validExercise;
    }


    public Exercise saveExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public void deleteExerciseById(Integer exId) {
        exerciseRepository.deleteById(exId);
    }


    private Optional<Exercise> retrieveValidExerciseById(Integer id) throws ServerException {
        Optional<Exercise> searchedExercise = exerciseRepository.findById(id);;
        if(searchedExercise.isEmpty()) {
            throw new ServerException("Exercise: " + id + " not found");
        }
        return searchedExercise;
    }

}
