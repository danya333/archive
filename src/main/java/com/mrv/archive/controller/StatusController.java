package com.mrv.archive.controller;

import com.mrv.archive.dto.status.StatusDto;
import com.mrv.archive.mapper.StatusMapper;
import com.mrv.archive.model.Status;
import com.mrv.archive.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statuses")
@RequiredArgsConstructor
public class StatusController {
    private final StatusService statusService;
    private final StatusMapper statusMapper;

    @GetMapping("/list")
    public ResponseEntity<List<StatusDto>> statusList() {
        List<Status> statuses = statusService.getAllStatuses();
        List<StatusDto> response = statusMapper.toDto(statuses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<StatusDto> addStatus(@RequestBody StatusDto statusDto) {
        Status status = statusMapper.toEntity(statusDto);
        StatusDto response = statusMapper.toDto(statusService.create(status));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusDto> updateStatus(@PathVariable Long id, @RequestBody StatusDto statusDto) {
        Status status = statusMapper.toEntity(statusDto);
        Status updatedStatus = statusService.update(id, status);
        return new ResponseEntity<>(statusMapper.toDto(updatedStatus), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable Long id) {
        statusService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
