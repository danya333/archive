package com.mrv.archive.controller;

import com.mrv.archive.dto.status.StatusDto;
import com.mrv.archive.mapper.StatusMapper;
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

    @PutMapping("/update")
    public ResponseEntity<Void> updateStatus(@RequestBody List<StatusDto> statusDtos) {
        statusDtos.forEach(s -> statusService.update(s.getId(), statusMapper.toEntity(s)));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable Long id) {
        statusService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
