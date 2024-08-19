package com.mrv.archive.service;

import com.mrv.archive.model.Year;

import java.util.List;

public interface YearService {
    Year getYear(Long year);
    List<Year> getYears(Long stageId);
    Year createYear(Long stageId, Year year);
    Year updateYear(Long stageId, Year year);
    void deleteYear(Long id);

}
