package com.mrv.archive.repository;

import com.mrv.archive.model.Project;
import com.mrv.archive.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<List<Status>> findAllByProject(Project project);
}
