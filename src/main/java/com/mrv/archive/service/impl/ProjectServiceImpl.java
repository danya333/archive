package com.mrv.archive.service.impl;

import com.mrv.archive.dto.project.ProjectCreateRequestDto;
import com.mrv.archive.model.Project;
import com.mrv.archive.model.Status;
import com.mrv.archive.model.Year;
import com.mrv.archive.repository.ProjectRepository;
import com.mrv.archive.service.ProjectService;
import com.mrv.archive.service.StatusService;
import com.mrv.archive.service.YearService;
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
    private final YearService yearService;
    private final StatusService statusService;

    @Override
    public Project getProject(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Project with id " + id + " not found"));
    }

    @Override
    public List<Project> getProjects(Long yearId) {
        Year year = yearService.getYear(yearId);
        return projectRepository.findByYear(year)
                .orElseThrow(() -> new NoSuchElementException("Project with year " + year.getYear() + " not found"));
    }

    @Override
    @Transactional
    public Project create(Long yearId, ProjectCreateRequestDto projectCreateRequestDto) {
        Year year = yearService.getYear(yearId);
        Status status = statusService.getById(projectCreateRequestDto.getStatusId());
        Project project = new Project();
        project.setName(projectCreateRequestDto.getName());
        project.setShortName(projectCreateRequestDto.getShortName());
        project.setYear(year);
        project.setCreatedAt(LocalDateTime.now());
        project.setCode(projectCreateRequestDto.getCode());
        project.setStatus(status);
        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public Project update(Long yearId, Project project) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {

    }
}
