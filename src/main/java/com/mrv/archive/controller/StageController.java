package com.mrv.archive.controller;

import com.mrv.archive.dto.stage.StageCreateDto;
import com.mrv.archive.dto.stage.StageResponseDto;
import com.mrv.archive.model.*;
import com.mrv.archive.service.ProjectService;
import com.mrv.archive.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/locations/{locationId}/stages")
@RequiredArgsConstructor
public class StageController {

    private final StageService stageService;
    private final ProjectService projectService;

    @GetMapping("/{stageId}")
    public ResponseEntity<StageResponseDto> getStage(@PathVariable String locationId,
                                                     @PathVariable Long stageId) {
        Stage stage = stageService.getById(stageId);
        StageResponseDto responseDto = createStageResponseDto(stage);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<StageResponseDto>> list(@PathVariable Long locationId) {
        List<StageResponseDto> stageResponseDtos = new ArrayList<>();
        List<Stage> stages = stageService.getAllStages(locationId);
        for (Stage stage : stages) {
            StageResponseDto responseDto = createStageResponseDto(stage);
            stageResponseDtos.add(responseDto);
        }
        return new ResponseEntity<>(stageResponseDtos, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<Stage> createStage(@PathVariable Long locationId,
                                             @RequestBody StageCreateDto stageCreateDto) {
        Stage stage = new Stage();
        stage.setName(stageCreateDto.getName());
        stage.setShortName(stageCreateDto.getShortName());
        Stage response = stageService.create(stage, locationId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStage(@PathVariable Long id) {
        stageService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private StageResponseDto createStageResponseDto(Stage stage) {

        List<Project> projects = projectService.getProjects(stage.getId());
        StageResponseDto responseDto = new StageResponseDto();

        responseDto.setId(stage.getId());
        responseDto.setName(stage.getName());
        responseDto.setShortName(stage.getShortName());
        responseDto.setLocation(new Location(
                stage.getLocation().getId(),
                stage.getLocation().getCountry(),
                stage.getLocation().getCity(),
                null
        ));
        Set<Integer> yearsSet = new HashSet<>();
        if (projects != null) {
            yearsSet = projects.stream()
                    .map(project -> project
                            .getCreatedAt()
                            .getYear())
                    .collect(Collectors.toSet());
        }
        responseDto.setProjectYears(yearsSet.stream().sorted().toList());
        return responseDto;
    }
}
