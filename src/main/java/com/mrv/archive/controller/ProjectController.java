package com.mrv.archive.controller;

import com.mrv.archive.dto.project.ProjectCreateRequestDto;
import com.mrv.archive.dto.project.ProjectListResponseDto;
import com.mrv.archive.dto.project.ProjectYearDto;
import com.mrv.archive.dto.status.StatusDto;
import com.mrv.archive.mapper.StatusMapper;
import com.mrv.archive.model.Project;
import com.mrv.archive.model.Status;
import com.mrv.archive.service.ProjectService;
import com.mrv.archive.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/stages/{stageId}/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final StatusService statusService;
    private final StatusMapper statusMapper;


    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        Project project = projectService.getProject(id);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }


    @PostMapping("/list")
    public ResponseEntity<List<ProjectListResponseDto>> list(@PathVariable("stageId") Long stageId,
                                                             @RequestBody ProjectYearDto projectYearDto) {
        List<Project> projects = projectService.getProjects(stageId);
        List<Project> filteredProjects = projects.stream()
                .filter(project -> project
                        .getCreatedAt()
                        .getYear() == projectYearDto.getYear())
                .toList();
        List<ProjectListResponseDto> response = filteredProjects.stream().map(p -> ProjectListResponseDto.builder()
                .id(p.getId())
                .name(p.getName())
                .shortName(p.getShortName())
                .code(p.getCode())
                .createdAt(p.getCreatedAt())
                .statuses(p.getStatuses())
                .build()
        ).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Project> createProject(@PathVariable Long stageId,
                                                 @RequestPart ProjectCreateRequestDto projectDto,
                                                 @RequestPart List<StatusDto> statusDtos) {
        List<Status> statusList = new ArrayList<>();
        for (StatusDto statusDto : statusDtos){
            Status status = new Status();
            status.setName(statusDto.getName());
            status.setIsActive(statusDto.getIsActive());
            status.setStartDate(statusDto.getStartDate());
            status.setFinishDate(statusDto.getFinishDate());
            statusList.add(status);
        }
        Project project = projectService.create(projectDto, stageId, statusList);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
