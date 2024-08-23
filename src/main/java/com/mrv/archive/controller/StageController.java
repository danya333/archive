package com.mrv.archive.controller;

import com.mrv.archive.dto.stage.StageCreateDto;
import com.mrv.archive.dto.stage.StageResponseDto;
import com.mrv.archive.dto.status.StatusDto;
import com.mrv.archive.model.Location;
import com.mrv.archive.model.Stage;
import com.mrv.archive.model.StageStatus;
import com.mrv.archive.model.Status;
import com.mrv.archive.service.StageService;
import com.mrv.archive.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/locations/{locationId}/stages")
@RequiredArgsConstructor
public class StageController {

    private final StageService stageService;
    private final StatusService statusService;

    @GetMapping("/{stageId}")
    public ResponseEntity<StageResponseDto> getStage(@PathVariable String locationId,
                                                     @PathVariable Long stageId) {
        Stage stage = stageService.getById(stageId);
        StageResponseDto responseDto = createStageResponseDto(stage);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}/statuses")
    public ResponseEntity<List<StatusDto>> getAvailableStatuses(@PathVariable Long id){
        Stage stage = stageService.getById(id);
        List<Status> statuses = statusService.getStatusesByStage(stage);
        List<StatusDto> statusesDto = statuses.stream()
                .map(status -> new StatusDto(status.getId(), status.getName())).toList();
        return new ResponseEntity<>(statusesDto, HttpStatus.OK);
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
        Stage response = stageService
                .create(stage, locationId, stageCreateDto.getStatusId(), stageCreateDto.getStatusIds());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStage(@PathVariable Long id){
        stageService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private StageResponseDto createStageResponseDto(Stage stage) {
        StageResponseDto responseDto = new StageResponseDto();
        responseDto.setId(stage.getId());
        responseDto.setName(stage.getName());
        responseDto.setLocation(new Location(
                stage.getLocation().getId(),
                stage.getLocation().getCountry(),
                stage.getLocation().getCity(),
                null
        ));
        responseDto.setStatus(new Status(
                stage.getStatus().getId(),
                stage.getStatus().getName(),
                null,
                null,
                null
        ));
        List<Status> statuses = stage.getStageStatuses()
                .stream()
                .map(StageStatus::getStatus)
                .toList()
                .stream()
                .map(s -> new Status(s.getId(), s.getName(), null, null, null))
                .toList();
        responseDto.setAvailableStatuses(statuses);
        return responseDto;
    }

}
