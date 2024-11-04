package com.mrv.archive.service.impl;

import com.mrv.archive.model.Project;
import com.mrv.archive.model.Section;
import com.mrv.archive.repository.SectionRepository;
import com.mrv.archive.service.ProjectService;
import com.mrv.archive.service.SectionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;
    private final ProjectService projectService;

    @Override
    public Section getSection(Long id) {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Section with id " + id + " not found"));
    }

    @Override
    public List<Section> getSections(Long projectId) {
        Project project = projectService.getProject(projectId);
        return sectionRepository.findByProject(project)
                .orElseThrow(() -> new NoSuchElementException("Sections for project with id " + projectId + " not found"));
    }

    @Override
    @Transactional
    public Section create(Long projectId, Section section) {
        Project project = projectService.getProject(projectId);
        section.setProject(project);
        section.setCreatedAt(LocalDateTime.now());
        return sectionRepository.save(section);
    }

    @Override
    @Transactional
    public Section update(Section section) {
        Section oldSection = this.getSection(section.getId());
        oldSection.setName(section.getName());
        oldSection.setShortName(section.getShortName());
        oldSection.setCode((section.getCode()));
        return sectionRepository.save(oldSection);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(sectionRepository.existsById(id)) {
            sectionRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Section with id " + id + " not found");
        }

    }
}
