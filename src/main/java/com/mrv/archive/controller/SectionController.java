package com.mrv.archive.controller;

import com.mrv.archive.dto.section.SectionCreateDto;
import com.mrv.archive.dto.section.SectionListResponseDto;
import com.mrv.archive.mapper.SectionCreateDtoMapper;
import com.mrv.archive.model.Project;
import com.mrv.archive.model.Section;
import com.mrv.archive.service.ProjectService;
import com.mrv.archive.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/sections")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;
    private final ProjectService projectService;
    private final SectionCreateDtoMapper sectionCreateDtoMapper;

    @GetMapping("/{id}")
    private ResponseEntity<Section> getSection(@PathVariable Long id){
        Section section = sectionService.getSection(id);
        return new ResponseEntity<>(section, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<SectionListResponseDto> getSections(@PathVariable Long projectId){
        List<Section> sections = sectionService.getSections(projectId);
        Project project = projectService.getProject(projectId);
        SectionListResponseDto response = SectionListResponseDto.builder()
                .city(project.getStage().getLocation().getCity())
                .stage(project.getStage().getName())
                .projectName(project.getName())
                .sections(sections)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Section> createSection(@PathVariable Long projectId,
                                                 @RequestBody SectionCreateDto sectionDto){
        Section section = sectionCreateDtoMapper.toEntity(sectionDto);
        Section response = sectionService.create(projectId, section);
        response.setAlbums(null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Section> updateSection(@PathVariable Long id,
                                              @RequestBody SectionCreateDto sectionDto){
        Section section = sectionCreateDtoMapper.toEntity(sectionDto);
        section.setId(id);
        return new ResponseEntity<>(sectionService.update(section), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSection(@PathVariable Long id){
        sectionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
