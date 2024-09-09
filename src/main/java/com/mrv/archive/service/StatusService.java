package com.mrv.archive.service;

import com.mrv.archive.model.Project;
import com.mrv.archive.model.Status;

import java.util.List;

public interface StatusService {
    Status getById(Long id);
    List<Status> getAllStatuses();
    List<Status> getStatusesByProject(Project project);
    Status create(Status status);
    Status update(Long id, Status status);
    void delete(Long id);
}
