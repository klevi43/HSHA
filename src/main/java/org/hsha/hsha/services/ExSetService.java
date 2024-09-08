package org.hsha.hsha.services;

import org.hsha.hsha.Repository.ExSetRepository;
import org.hsha.hsha.models.ExSet;
import org.hsha.hsha.models.Exercise;
import org.hsha.hsha.models.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

@Service
public class ExSetService {
    @Autowired
    ExSetRepository exSetRepository;
    public List<ExSet> retrieveAllExSets() {
        return exSetRepository.findAll();
    }
    //
    public Optional<ExSet> retrieveExSetById(Integer exSetId) throws ServerException {
        Optional<ExSet> searchedExSets = exSetRepository.findById(exSetId);
        validateExSet(searchedExSets, exSetId);
        return searchedExSets;
    }


    public ExSet saveExSet(ExSet exSet) {
        return exSetRepository.save(exSet);
    }

    public void deleteExSetById(Integer exSetId) {
        exSetRepository.deleteById(exSetId);
    }

    public List<ExSet> retrieveAllExSetsByExerciseId(Integer exerciseId) {
        return exSetRepository.findAllByExercise_Id(exerciseId);
    }

    public List<ExSet> retrieveAllExSetsByWorkoutId(Integer workoutId) {
        return exSetRepository.findAllByWorkout_Id(workoutId);
    }


    private static void validateExSet(Optional<ExSet> checkedExSets, Integer exSetId) throws ServerException {
        if (checkedExSets.isEmpty()) {
            throw new ServerException("ExSet id: " + exSetId + " not found.");
        }
    }
}

