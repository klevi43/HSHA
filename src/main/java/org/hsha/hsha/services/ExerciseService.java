package org.hsha.hsha.services;

import org.hsha.hsha.Repository.ExerciseRepository;
import org.hsha.hsha.models.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {
    @Autowired
    ExerciseRepository exerciseRepository;

    // get all exercises from DB
    public List<Exercise> retrieveAllExercises() {
        return exerciseRepository.findAll();
    }
    //
}
