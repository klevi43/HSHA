package org.hsha.hsha.Repository;

import org.hsha.hsha.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
//
    Optional<Exercise> findByNameContaining(String name);

}
