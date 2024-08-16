package com.mrv.archive.repository;

import com.mrv.archive.model.Location;
import com.mrv.archive.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
    Optional<List<Stage>> findByLocation(Location location);
}
