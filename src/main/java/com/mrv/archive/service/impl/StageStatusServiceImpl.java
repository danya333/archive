package com.mrv.archive.service.impl;

import com.mrv.archive.model.Stage;
import com.mrv.archive.model.StageStatus;
import com.mrv.archive.model.Status;
import com.mrv.archive.repository.StageStatusRepository;
import com.mrv.archive.service.StageStatusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StageStatusServiceImpl implements StageStatusService {

    private final StageStatusRepository stageStatusRepository;

    @Override
    public StageStatus getStageStatus(Long id) {
        return stageStatusRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No stage status found with id: " + id));
    }

    @Override
    public List<StageStatus> getStageStatusesByStage(Stage stage) {
        return stageStatusRepository.findByStage(stage)
                .orElseThrow(() -> new NoSuchElementException("No status found for stage " + stage.getId()));
    }

    @Override
    @Transactional
    public StageStatus create(Stage stage, Status status) {
        StageStatus stageStatus = new StageStatus();
        stageStatus.setStage(stage);
        stageStatus.setStatus(status);
        return stageStatusRepository.save(stageStatus);
    }

    @Override
    @Transactional
    public void delete(Stage stage, Status status) {
        StageStatus stageStatus = stageStatusRepository.findByStageAndStatus(stage, status)
                .orElseThrow(() -> new NoSuchElementException("No status found for stage " + stage.getId()));
        stageStatusRepository.delete(stageStatus);
    }
}
