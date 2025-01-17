package com.mrv.archive.service.impl;

import com.mrv.archive.model.Location;
import com.mrv.archive.model.Stage;
import com.mrv.archive.model.Status;
import com.mrv.archive.repository.StageRepository;
import com.mrv.archive.service.LocationService;
import com.mrv.archive.service.StageService;
import com.mrv.archive.service.StageStatusService;
import com.mrv.archive.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StageServiceImpl implements StageService {

    private final StageRepository stageRepository;
    private final LocationService locationService;
    private final StatusService statusService;
    private final StageStatusService stageStatusService;

    @Override
    public Stage getById(Long id) {
        return stageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No stage found with id: " + id));
    }

    @Override
    public List<Stage> getAllStages(Long locationId) {
        Location location = locationService.getById(locationId);
        return stageRepository.findByLocation(location)
                .orElseThrow(() -> new NoSuchElementException("Stages not found with locationId: " + locationId));
    }

    @Override
    @Transactional
    public Stage create(Stage stage, Long locationId, Long statusId, List<Long> statusIds) {
        Location location = locationService.getById(locationId);
        Status status = statusService.getById(statusId);
        stage.setLocation(location);
        stage.setStatus(status);
        stageRepository.save(stage);
        statusIds.forEach(id -> stage.getStageStatuses()
                .add(stageStatusService.create(stage, statusService.getById(id))));
        return stageRepository.save(stage);
    }

    @Override
    @Transactional
    // Нужно доработать работу со статусами
    public Stage update(Stage stage, Long stageId, Long locationId, Long statusId) {
        Stage oldStage = this.getById(stageId);
        oldStage.setLocation(locationService.getById(locationId));
        oldStage.setStatus(statusService.getById(statusId));
        oldStage.setName(stage.getName());
        return stageRepository.save(oldStage);
    }

    @Override
    @Transactional
    public void delete(Long stageId) {
        if (stageRepository.existsById(stageId)) {
            stageRepository.deleteById(stageId);
        } else {
            throw new NoSuchElementException("No stage found with id: " + stageId);
        }
    }
}
