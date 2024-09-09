package com.mrv.archive.service.impl;

import com.mrv.archive.model.Project;
import com.mrv.archive.model.Stage;
import com.mrv.archive.model.ProjectStatus;
import com.mrv.archive.model.Status;
import com.mrv.archive.repository.StatusRepository;
import com.mrv.archive.service.ProjectStatusService;
import com.mrv.archive.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;
    private final ProjectStatusService projectStatusService;

    @Override
    public Status getById(Long id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Status not found"));
    }

    @Override
    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

    @Override
    public List<Status> getStatusesByProject(Project project) {
        List<ProjectStatus> stageStatuses = projectStatusService.getProjectStatusesByProject(project);
        return stageStatuses.stream().map(ProjectStatus::getStatus).toList();
    }

    @Override
    @Transactional
    public Status create(Status status) {
        return statusRepository.save(status);
    }

    @Override
    @Transactional
    public Status update(Long id, Status status) {
        Status oldStatus = getById(id);
        oldStatus.setName(status.getName());
        return statusRepository.save(oldStatus);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (statusRepository.existsById(id)) {
            statusRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Status wit id " + id + " not found");
        }
    }
}
