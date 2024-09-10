package com.mrv.archive.service.impl;

import com.mrv.archive.dto.project.ProjectCreateRequestDto;
import com.mrv.archive.model.Project;
import com.mrv.archive.model.Stage;
import com.mrv.archive.model.Status;
import com.mrv.archive.repository.ProjectRepository;
import com.mrv.archive.service.ProjectService;
import com.mrv.archive.service.StageService;
import com.mrv.archive.service.StatusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final StatusService statusService;
    private final StageService stageService;

    @Override
    public Project getProject(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Project with id " + id + " not found"));
    }

    @Override
    public List<Project> getProjects(Long stageId) {
        Stage stage = stageService.getById(stageId);
        return projectRepository.findByStage(stage)
                .orElseThrow(() -> new NoSuchElementException("Projects not found for stage " + stageId));
    }

    @Override
    @Transactional
    public Project create(ProjectCreateRequestDto projectCreateRequestDto, Long stageId, List<Status> statusList) {
        Stage stage = stageService.getById(stageId);
        Project project = new Project();
        project.setName(projectCreateRequestDto.getName());
        project.setShortName(projectCreateRequestDto.getShortName());
        project.setCreatedAt(LocalDateTime.now());
        project.setCode(projectCreateRequestDto.getCode());
        project.setStage(stage);
        projectRepository.save(project);
        List<Status> statuses = statusService.create(statusList, project);
        project.setStatuses(statuses);
        return project;
    }

    @Override
    @Transactional
    public Project update(Project project) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Project with id " + id + " not found");
        }
    }
}
