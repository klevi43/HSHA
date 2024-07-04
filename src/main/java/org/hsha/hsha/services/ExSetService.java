package org.hsha.hsha.services;

import org.hsha.hsha.Repository.ExSetRepository;
import org.hsha.hsha.models.ExSet;
import org.hsha.hsha.models.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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
    public Optional<ExSet> retrieveExSetById(Integer exId) {
        return exSetRepository.findById(exId);
    }


    public ExSet saveExSet(ExSet exSet) {
        return exSetRepository.save(exSet);
    }

    public void deleteExerciseById(Integer exSetId) {
        exSetRepository.deleteById(exSetId);
    }

}
