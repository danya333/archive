package com.mrv.archive.controller;

import com.mrv.archive.dto.project.ProjectCreateRequestDto;
import com.mrv.archive.dto.project.ProjectYearDto;
import com.mrv.archive.model.Project;
import com.mrv.archive.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stages/{stageId}/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;


    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        Project project = projectService.getProject(id);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<List<Project>> list(@PathVariable Long stageId, @RequestBody ProjectYearDto projectYearDto) {
        List<Project> projects = projectService.getProjects(stageId);

        return new ResponseEntity<>(projects.stream()
                .filter(project -> project
                        .getCreatedAt()
                        .getYear() == projectYearDto.getYear())
                .toList(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Project> createProject(@PathVariable Long stageId,
                                                 @RequestBody ProjectCreateRequestDto projectDto) {
        Project project = projectService.create(projectDto, stageId);
        project.setSections(null);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
