package com.mrv.archive.service;

import com.mrv.archive.dto.stage.StageCreateDto;
import com.mrv.archive.model.Stage;

import java.util.List;

public interface StageService {
    Stage getById(Long id);
    List<Stage> getAllStages(Long locationId);
    Stage create(Stage stage, Long locationId);
    Stage update(StageCreateDto stageCreateDto, Long stageId, Long locationId);
    void delete(Long stageId);
}
