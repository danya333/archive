package com.mrv.archive.repository;

import com.mrv.archive.model.Project;
import com.mrv.archive.model.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<List<Project>> findByYear(Year year);
}
