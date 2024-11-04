package com.mrv.archive.service;

import com.mrv.archive.model.Section;

import java.util.List;

public interface SectionService {
    Section getSection(Long id);
    List<Section> getSections(Long projectId);
    Section create(Long projectId, Section section);
    Section update(Section section);
    void delete(Long id);
}
