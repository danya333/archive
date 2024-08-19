package com.mrv.archive.repository;

import com.mrv.archive.model.Stage;
import com.mrv.archive.model.StageStatus;
import com.mrv.archive.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StageStatusRepository extends JpaRepository<StageStatus, Long> {
    Optional<List<StageStatus>> findByStage(Stage stage);
    Optional<StageStatus> findByStageAndStatus(Stage stage, Status status);
}
