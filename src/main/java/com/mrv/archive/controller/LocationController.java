package com.mrv.archive.controller;

import com.mrv.archive.dto.location.LocationDto;
import com.mrv.archive.mapper.LocationMapper;
import com.mrv.archive.model.Location;
import com.mrv.archive.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private final LocationMapper locationMapper;

    @GetMapping("/list")
    public ResponseEntity<List<Location> > list() {
        List<Location> response = locationService.getAllLocations();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<LocationDto> create(@RequestBody LocationDto locationDto) {
        Location location = locationMapper.toEntity(locationDto);
        Location response = locationService.create(location);
        return new ResponseEntity<>(locationMapper.toDto(response), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationDto> update(@PathVariable Long id, @RequestBody LocationDto locationDto) {
        Location location = locationMapper.toEntity(locationDto);
        Location response = locationService.update(id, location);
        return new ResponseEntity<>(locationMapper.toDto(response), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        locationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
