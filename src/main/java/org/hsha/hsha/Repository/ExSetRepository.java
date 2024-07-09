package org.hsha.hsha.Repository;

import org.hsha.hsha.models.ExSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExSetRepository extends JpaRepository<ExSet, Integer> {
    List<ExSet> findAllByExercise_Id(Integer exerciseId);
    List<ExSet> findAllByWorkout_Id(Integer workoutId);
}
