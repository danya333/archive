package com.mrv.archive.service.impl;

import com.mrv.archive.model.Location;
import com.mrv.archive.repository.LocationRepository;
import com.mrv.archive.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public Location getById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Location not found"));
    }

    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    @Transactional
    public Location create(Location location) {
        return locationRepository.save(location);
    }

    @Override
    @Transactional
    public Location update(Long id, Location location) {
        Location oldLocation = getById(id);
        oldLocation.setCountry(location.getCountry());
        oldLocation.setCity(location.getCity());
        return locationRepository.save(oldLocation);
    }

    @Override
    @Transactional
    public void delete (Long id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Location not found and was not deleted");
        }
    }
}
