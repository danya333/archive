package com.mrv.archive.service;

import com.mrv.archive.model.Location;

import java.util.List;

public interface LocationService {

    Location getById(Long id);
    List<Location> getAllLocations();
    Location create(Location location);
    Location update(Long id, Location location);
    void delete(Long id);

}
