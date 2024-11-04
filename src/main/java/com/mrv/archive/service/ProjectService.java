package com.mrv.archive.service;

import com.mrv.archive.dto.project.ProjectCreateRequestDto;
import com.mrv.archive.dto.status.StatusDto;
import com.mrv.archive.model.Project;
import com.mrv.archive.model.Stage;
import com.mrv.archive.model.Status;

import java.util.List;

public interface ProjectService {
    Project getProject(Long id);
    List<Project> getProjects(Long stageId);
    Project create(ProjectCreateRequestDto projectCreateRequestDto, Long stageId, List<Status> statusList);
    void update(ProjectCreateRequestDto projectCreateRequestDto, Long projectId, List<StatusDto> statusDtos);
    void delete(Long id);
    public int getProjectCompleteness(Project project);
}
