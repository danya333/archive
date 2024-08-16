package com.mrv.archive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageStatus extends JpaRepository<StageStatus, Long> {
}
