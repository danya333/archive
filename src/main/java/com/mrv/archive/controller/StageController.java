package com.mrv.archive.controller;

import com.mrv.archive.dto.stage.StageCreateDto;
import com.mrv.archive.model.Stage;
import com.mrv.archive.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations/{locationId}/stages")
@RequiredArgsConstructor
public class StageController {

    private final StageService stageService;

    @GetMapping("/{stageId}")
    public ResponseEntity<Stage> getStage(@PathVariable String locationId,
                                          @PathVariable Long stageId) {
        Stage stage = stageService.getById(stageId);
        return new ResponseEntity<>(stage, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Stage>> list(@PathVariable Long locationId) {
        List<Stage> stages = stageService.getAllStages(locationId);
        return new ResponseEntity<>(stages, HttpStatus.OK);
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
}
