package org.hsha.hsha.Repository;

import org.hsha.hsha.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
//
    Optional<Workout> findWorkoutByName(String name);
    void deleteWorkoutById(int id);
}
