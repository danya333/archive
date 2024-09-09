package com.mrv.archive.service;

import com.mrv.archive.model.Project;
import com.mrv.archive.model.ProjectStatus;
import com.mrv.archive.model.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface ProjectStatusService {
    ProjectStatus getProjectStatus(Long id);
    List<ProjectStatus> getProjectStatusesByProject(Project project);
    ProjectStatus create(Project project, Status status);
    void delete(Project project, Status status);
}
