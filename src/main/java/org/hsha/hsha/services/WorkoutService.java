package org.hsha.hsha.services;

import org.hsha.hsha.Repository.WorkoutRepository;
import org.hsha.hsha.models.User;
import org.hsha.hsha.models.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {
    @Autowired
    private WorkoutRepository workoutRepository;

    public List<Workout> retrieveAllWorkouts() {
        return workoutRepository.findAll();
    }

    public Optional<Workout> retrieveWorkoutById(Integer id) throws ServerException {
        Optional<Workout> validWorkout = retrieveValidWorkoutById(id);
        return validWorkout;
    }


    public void deleteWorkoutById(int id) {
        workoutRepository.deleteWorkoutById(id);
    }
    public Workout saveWorkout(@RequestBody Workout workout) {
        return workoutRepository.save(workout);
    }

    public Optional<Workout> retrieveValidWorkoutById(Integer id) throws ServerException {
        Optional<Workout> searchedWorkout = workoutRepository.findById(id);
        if(searchedWorkout.isEmpty()) {
            throw new ServerException("Workout: " + id + " not found");
        }
        return searchedWorkout;
    }
}
