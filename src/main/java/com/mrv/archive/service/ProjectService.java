package com.mrv.archive.service;

import com.mrv.archive.dto.project.ProjectCreateRequestDto;
import com.mrv.archive.model.Project;

import java.util.List;

public interface ProjectService {
    Project getProject(Long id);
    List<Project> getProjects(Long yearId);
    Project create(Long yearId, ProjectCreateRequestDto projectCreateRequestDto);
    Project update(Long yearId, Project project);
    void delete(Long id);
}
