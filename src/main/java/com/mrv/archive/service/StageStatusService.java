package com.mrv.archive.service;

import com.mrv.archive.model.Stage;
import com.mrv.archive.model.StageStatus;
import com.mrv.archive.model.Status;

import java.util.List;

public interface StageStatusService {
    StageStatus getStageStatus(Long id);
    List<StageStatus> getStageStatusesByStage(Stage stage);
    StageStatus create(Stage stage, Status status);
    void delete(Stage stage, Status status);
}
