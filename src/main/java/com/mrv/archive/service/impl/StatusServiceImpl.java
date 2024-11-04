package com.mrv.archive.service.impl;

import com.mrv.archive.model.Project;
import com.mrv.archive.model.Status;
import com.mrv.archive.repository.StatusRepository;
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

    @Override
    public Status getById(Long id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Status not found"));
    }


    @Override
    public List<Status> getStatusesByProject(Project project) {
        return statusRepository.findAllByProject(project).orElseThrow(
                () -> new NoSuchElementException("Statuses for project with id: " + project.getId() + "not found."));
    }

    @Override
    @Transactional
    public List<Status> create(List<Status> statuses, Project project) {
        return statuses.stream().map(s -> {
            s.setProject(project);
            return statusRepository.save(s);
        }).toList();
    }

    @Override
    @Transactional
    public Status createOneStatus(Status status, Project project) {
        status.setProject(project);
        statusRepository.save(status);
        return status;
    }

    @Override
    @Transactional
    public Status update(Long id, Status status) {
        Status oldStatus = getById(id);
        oldStatus.setName(status.getName());
        oldStatus.setStartDate(status.getStartDate());
        oldStatus.setStartDate(status.getFinishDate());
        oldStatus.setIsActive(status.getIsActive());
        return statusRepository.save(oldStatus);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (statusRepository.existsById(id)) {
            statusRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Status with id " + id + " not found");
        }
    }
}
