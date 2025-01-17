package com.mrv.archive.repository;

import com.mrv.archive.model.Project;
import com.mrv.archive.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    Optional<List<Section>> findByProject(Project project);
}
