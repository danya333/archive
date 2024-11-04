package com.mrv.archive.service;

import com.mrv.archive.model.Project;
import com.mrv.archive.model.Status;

import java.util.List;

public interface StatusService {
    Status getById(Long id);
    List<Status> getStatusesByProject(Project project);
    List<Status> create(List<Status> statuses, Project project);
    Status createOneStatus(Status status, Project project);
    Status update(Long id, Status status);
    void delete(Long id);
}
