package com.mrv.archive.service.impl;

import com.mrv.archive.model.Project;
import com.mrv.archive.model.Stage;
import com.mrv.archive.model.ProjectStatus;
import com.mrv.archive.model.Status;
import com.mrv.archive.repository.ProjectStatusRepository;
import com.mrv.archive.service.ProjectStatusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProjectStatusServiceImpl implements ProjectStatusService {

    private final ProjectStatusRepository projectStatusRepository;

    @Override
    public ProjectStatus getProjectStatus(Long id) {
        return projectStatusRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No project status found with id: " + id));
    }

    @Override
    public List<ProjectStatus> getProjectStatusesByProject(Project project) {
        return projectStatusRepository.findByProject(project)
                .orElseThrow(() -> new NoSuchElementException("No status found for project " + project.getId()));
    }

    @Override
    @Transactional
    public ProjectStatus create(Project project, Status status) {
        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setStatus(status);
        projectStatus.setProject(project);
        return projectStatusRepository.save(projectStatus);
    }

    @Override
    @Transactional
    public void delete(Project project, Status status) {
        ProjectStatus projectStatus = projectStatusRepository.findByProjectAndStatus(project, status)
                .orElseThrow(() -> new NoSuchElementException("No status found for project " + project.getId()));
        projectStatusRepository.delete(projectStatus);
    }
}
