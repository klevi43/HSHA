package org.hsha.hsha.services;

import org.hsha.hsha.Repository.WorkoutRepository;
import org.hsha.hsha.models.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {
    @Autowired
    private WorkoutRepository workoutRepository;

    public List<Workout> retrieveAllWorkouts() {
        return workoutRepository.findAll();
    }

    public Optional<Workout> retrieveWorkoutById(int id) {
        return workoutRepository.findById(id);
    }
    public Optional<Workout> retrieveWorkoutByName(String name) {
        return workoutRepository.findWorkoutByName(name);
    }

    public void deleteWorkoutById(int id) {
        workoutRepository.deleteById(id);
    }
    public Workout saveWorkout(@RequestBody Workout workout) {
        return workoutRepository.save(workout);
    }

}
