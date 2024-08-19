package com.mrv.archive.service.impl;

import com.mrv.archive.model.Stage;
import com.mrv.archive.model.Year;
import com.mrv.archive.repository.YearRepository;
import com.mrv.archive.service.StageService;
import com.mrv.archive.service.YearService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class YearServiceImpl implements YearService {

    private final YearRepository yearRepository;
    private final StageService stageService;

    @Override
    public Year getYear(Long year) {
        return yearRepository.findById(year)
                .orElseThrow(() -> new NoSuchElementException("No year found with id: " + year));
    }

    @Override
    public List<Year> getYears(Long stageId) {
        Stage stage = stageService.getById(stageId);
        return yearRepository.findByStage(stage)
                .orElseThrow(() -> new NoSuchElementException("No year found with id: " + stageId));
    }

    @Override
    @Transactional
    public Year createYear(Long stageId, Year year) {
        Stage stage = stageService.getById(stageId);
        year.setStage(stage);
        return yearRepository.save(year);
    }

    @Override
    @Transactional
    public Year updateYear(Long stageId, Year year) {
        Stage stage = stageService.getById(stageId);
        Year updatedYear = yearRepository.save(year);
        updatedYear.setStage(stage);
        updatedYear.setYear(year.getYear());
        return yearRepository.save(updatedYear);
    }

    @Override
    @Transactional
    public void deleteYear(Long id) {

    }
}
