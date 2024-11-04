package com.mrv.archive.service.impl;

import com.mrv.archive.dto.project.ProjectCreateRequestDto;
import com.mrv.archive.dto.status.StatusDto;
import com.mrv.archive.mapper.StatusMapper;
import com.mrv.archive.model.*;
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
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final StatusService statusService;
    private final StageService stageService;
    private final StatusMapper statusMapper;

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
    public void update(ProjectCreateRequestDto projectCreateRequestDto, Long projectId, List<StatusDto> statusDtos) {
        Project project = this.getProject(projectId);
        project.setName(projectCreateRequestDto.getName());
        project.setCode(projectCreateRequestDto.getCode());
        project.setShortName(projectCreateRequestDto.getShortName());
        for (StatusDto s : statusDtos){
            if (s.getId() == null){
                s.setIsActive(false);
                statusService.createOneStatus(statusMapper.toEntity(s), project);
            } else {
                statusService.update(s.getId(), statusMapper.toEntity(s));
            }
        }
        projectRepository.save(project);
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

    @Override
    public int getProjectCompleteness(Project project){
        List<Section> sections = project.getSections();
        int sectionCount = sections.size();
        if(sectionCount == 0){
            return 0;
        }
        int sectionTotal = 0;
        for (Section section : sections){
            List<Album> albums = section.getAlbums();
            int albumCount = albums.size();
            int albumCompleteness = 0;
            for (Album album : albums){
                albumCompleteness += album.getCompleteness();
            }
            if (albumCount > 0){
                sectionTotal += albumCompleteness / albumCount;
            }
        }
        return  sectionTotal / sectionCount;
    }
}
