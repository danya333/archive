package com.mrv.archive.controller;

import com.mrv.archive.dto.section.SectionCreateDto;
import com.mrv.archive.mapper.SectionCreateDtoMapper;
import com.mrv.archive.model.Section;
import com.mrv.archive.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations/{locationId}/stages/{stageId}/projects/{projectId}/sections")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;
    private final SectionCreateDtoMapper sectionCreateDtoMapper;

    @GetMapping("/{id}")
    private ResponseEntity<Section> getSection(@PathVariable Long id){
        Section section = sectionService.getSection(id);
        return new ResponseEntity<>(section, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Section>> getSections(@PathVariable Long projectId){
        List<Section> sections = sectionService.getSections(projectId);
        return new ResponseEntity<>(sections, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Section> createSection(@PathVariable Long projectId,
                                                 @RequestBody SectionCreateDto sectionDto){
        Section section = sectionCreateDtoMapper.toEntity(sectionDto);
        Section response = sectionService.create(projectId, section);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



}