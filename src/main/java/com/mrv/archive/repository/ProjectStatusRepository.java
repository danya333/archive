package com.mrv.archive.repository;

import com.mrv.archive.model.Project;
import com.mrv.archive.model.Stage;
import com.mrv.archive.model.ProjectStatus;
import com.mrv.archive.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Long> {
    Optional<List<ProjectStatus>> findByProject(Project project);
    Optional<ProjectStatus> findByProjectAndStatus(Project project, Status status);
}
